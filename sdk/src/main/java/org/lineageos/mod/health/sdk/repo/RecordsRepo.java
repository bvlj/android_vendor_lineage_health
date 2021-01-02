/*
 * Copyright (C) 2020 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.mod.health.sdk.repo;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;

import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.common.values.AccessPolicyValues;
import org.lineageos.mod.health.common.values.annotations.ActivityMetric;
import org.lineageos.mod.health.common.values.annotations.MetricType;
import org.lineageos.mod.health.sdk.model.records.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Base records repo.
 */
@Keep
public abstract class RecordsRepo<T extends Record> {
    /**
     * @hide
     */
    protected static final String DEFAULT_ORDER = RecordColumns.TIME + " DESC";

    /**
     * @hide
     */
    @NonNull
    protected final ContentResolver contentResolver;
    /**
     * @hide
     */
    @NonNull
    private final Uri baseUri;

    RecordsRepo(@NonNull ContentResolver contentResolver, @NonNull Uri baseUri) {
        this.contentResolver = contentResolver;
        this.baseUri = baseUri;
    }

    @NonNull
    public abstract List<T> getAll();

    /**
     * Insert a record into the {@link ContentProvider}.
     *
     * @param record the record to insert. Must not be null
     * @return one of: <ul>
     *     <li>{@link OperationResult.Success} with the new record id as value if the operation
     *         completed successfully</li>
     *     <li>{@link OperationResult.Failure} if it was not possible to insert the record.
     *         Please check the data you're trying to insert</li>
     *     <li>{@link OperationResult.PolicyError} if the record insertion was blocked by a
     *         policy. You may want to notify the user about this issue</li>
     * </ul>
     */
    public final OperationResult insert(@NonNull T record) {
        final ContentValues contentValues = record.toContentValues();
        final Uri uri = contentResolver.insert(getUri(record.getMetric()), contentValues);

        if (uri == null) {
            return OperationResult.Failure.INSTANCE;
        }
        if (AccessPolicyValues.DENIED_URI.equals(uri)) {
            return OperationResult.PolicyError.INSTANCE;
        }
        try {
            long id = Long.parseLong(uri.getLastPathSegment());
            return new OperationResult.Success<>(id);
        } catch (NumberFormatException e) {
            return OperationResult.Failure.INSTANCE;
        }
    }

    /**
     * Update a record inside the {@link ContentProvider}.
     *
     * @return one of: <ul>
     *     <li>{@link OperationResult.Success} if the operation completed successfully</li>
     *     <li>{@link OperationResult.Failure} if it was not possible to update the record.
     *         Please check the data you're trying to update</li>
     *     <li>{@link OperationResult.PolicyError} if the record update was blocked by a
     *         policy. You may want to notify the user about this issue</li>
     * </ul>
     */
    public final OperationResult update(@NonNull T record) {
        final Uri updateUri = getUri(record);
        final ContentValues cv = record.toContentValues();
        final int result = contentResolver.update(updateUri, cv, null, null);
        switch (result) {
            case AccessPolicyValues.DENIED_RESULT:
                return OperationResult.PolicyError.INSTANCE;
            case 1:
                return new OperationResult.Success<>(1);
            default:
                return OperationResult.Failure.INSTANCE;
        }
    }

    /**
     * Remove a record from the {@link ContentProvider}
     *
     * @return one of: <ul>
     *     <li>{@link OperationResult.Success} if the operation completed successfully</li>
     *     <li>{@link OperationResult.Failure} if it was not possible to remove the record.
     *         Please check the data you're trying to delete</li>
     *     <li>{@link OperationResult.PolicyError} if the record deletion was blocked by a
     *         policy. You may want to notify the user about this issue</li>
     * </ul>
     */
    public final OperationResult delete(@NonNull T record) {
        final Uri deleteUri = getUri(record);
        final int result = contentResolver.delete(deleteUri, null, null);
        switch (result) {
            case AccessPolicyValues.DENIED_RESULT:
                return OperationResult.PolicyError.INSTANCE;
            case 1:
                return new OperationResult.Success<>(1);
            default:
                return OperationResult.Failure.INSTANCE;
        }
    }

    @NonNull
    public final ContentProviderResult[] executeBatch(
            @NonNull BatchOperations.Builder<T> builder) throws
            OperationApplicationException, RemoteException {
        final BatchOperations<T> batchOperations = new BatchOperations<>(baseUri);
        builder.build(batchOperations);
        final ArrayList<ContentProviderOperation> operations = batchOperations.build();
        return contentResolver.applyBatch(baseUri.getAuthority(), operations);
    }

    @NonNull
    protected final List<T> getByMetric(@MetricType int metric) {
        final Uri uri = Uri.withAppendedPath(baseUri, String.valueOf(metric));
        final Cursor cursor = contentResolver.query(uri, null,
                null, null, DEFAULT_ORDER);
        if (cursor == null) {
            return new ArrayList<>();
        }

        try {
            return parseCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    @Nullable
    protected final T getById(@ActivityMetric int metric, long id) {
        final Uri uri = getUri(metric, id);
        final Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }

        try {
            return (cursor.moveToFirst() ? parseRow(cursor) : null);
        } finally {
            cursor.close();
        }
    }

    @NonNull
    protected abstract T parseRow(@NonNull Cursor cursor);

    @NonNull
    private List<T> parseCursor(@NonNull Cursor cursor) {
        final List<T> list = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return list;
        }

        do {
            list.add(parseRow(cursor));
        } while (cursor.moveToNext());
        return list;
    }

    @NonNull
    private Uri getUri(@ActivityMetric int metric) {
        final String path = String.format(Locale.ROOT, "%1$d", metric);
        return Uri.withAppendedPath(baseUri, path);
    }

    @NonNull
    private Uri getUri(@NonNull Record activityRecord) {
        return getUri(activityRecord.getMetric(), activityRecord.getId());
    }

    @NonNull
    private Uri getUri(@ActivityMetric int metric, long id) {
        final String path = String.format(Locale.ROOT, "%1$d/%2$d", metric, id);
        return Uri.withAppendedPath(baseUri, path);
    }


    /* Test only */

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    @VisibleForTesting
    public boolean deleteAll()  {
        final List<T> records = getAll();
        try {
            executeBatch(composer -> records.forEach(composer::delete));
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
