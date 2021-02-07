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
import android.util.Log;

import androidx.annotation.CallSuper;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Base records repo.
 */
@Keep
public abstract class RecordsRepo<T extends Record> {
    private static final String TAG = "RecordsRepo";

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
    @CallSuper
    public OperationResult insert(@NonNull T record) {
        final ContentValues contentValues = record.toContentValues();
        final Uri uri = contentResolver.insert(getUri(record.getMetric()), contentValues);

        if (uri == null) {
            return OperationResult.Failure.INSTANCE;
        }
        if (AccessPolicyValues.DENIED_URI.equals(uri)) {
            return OperationResult.PolicyError.INSTANCE;
        }
        try {
            final Long id = Long.valueOf(uri.getLastPathSegment());
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
    @CallSuper
    public OperationResult update(@NonNull T record) {
        final Uri updateUri = getUri(record);
        final ContentValues cv = record.toContentValues();
        final int result = contentResolver.update(updateUri, cv, null, null);
        switch (result) {
            case AccessPolicyValues.DENIED_COUNT:
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
    @CallSuper
    public OperationResult delete(@NonNull T record) {
        final Uri deleteUri = getUri(record);
        final int result = contentResolver.delete(deleteUri, null, null);
        switch (result) {
            case AccessPolicyValues.DENIED_COUNT:
                return OperationResult.PolicyError.INSTANCE;
            case 1:
                return new OperationResult.Success<>(1);
            default:
                return OperationResult.Failure.INSTANCE;
        }
    }

    @NonNull
    public final OperationResult[] executeBatch(
            @NonNull BatchOperations.Builder<T> builder) {
        final BatchOperations<T> batchOperations = new BatchOperations<>(baseUri);
        builder.build(batchOperations);
        final ArrayList<ContentProviderOperation> operations = batchOperations.build();

        final OperationResult[] results = new OperationResult[operations.size()];
        // Default to failure
        Arrays.fill(results, OperationResult.Failure.INSTANCE);
        try {
            // Perform operations
            ContentProviderResult[] cpResults = contentResolver.applyBatch(
                    baseUri.getAuthority(), operations);

            // Convert results to "our" format
            for (int i = 0; i < cpResults.length; i++) {
                ContentProviderOperation op = operations.get(i);
                if (op.isInsert()) {
                    final Uri uri = cpResults[i].uri;
                    if (uri == null) {
                        results[i] = OperationResult.Failure.INSTANCE;
                    } else if (AccessPolicyValues.DENIED_URI.equals(uri)) {
                        results[i] = OperationResult.PolicyError.INSTANCE;
                    } else {
                        final Long id = Long.valueOf(uri.getLastPathSegment());
                        results[i] = new OperationResult.Success<>(id);
                    }
                } else {
                    final int count = cpResults[i].count;
                    if (count == AccessPolicyValues.DENIED_COUNT) {
                        results[i] = OperationResult.PolicyError.INSTANCE;
                    } else if (count == 1) {
                        results[i] = new OperationResult.Success<>(1);
                    } else {
                        results[i] = OperationResult.Failure.INSTANCE;
                    }
                }
            }
        } catch (OperationApplicationException | RemoteException e) {
            Log.e(TAG, "Error while performing batch operations", e);
        }

        return results;
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
    public final boolean deleteAll()  {
        final List<T> records = getAll();
        final OperationResult[] results = executeBatch(composer ->
                records.forEach(composer::delete));
        for (OperationResult result : results) {
            if (!(result instanceof OperationResult.Success)) {
                return false;
            }
        }
        return true;
    }
}
