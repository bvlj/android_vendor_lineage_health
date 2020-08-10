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

import org.lineageos.mod.health.common.HealthStoreUri;
import org.lineageos.mod.health.common.annotations.ActivityMetric;
import org.lineageos.mod.health.common.annotations.MetricType;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.sdk.model.records.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Keep
@SuppressWarnings("unused")
public abstract class RecordsRepo<T extends Record> {
    protected static final String DEFAULT_ORDER = RecordColumns.TIME + " DESC";

    @NonNull
    protected final ContentResolver contentResolver;
    @NonNull
    private final Uri baseUri;

    RecordsRepo(@NonNull ContentResolver contentResolver, @NonNull Uri baseUri) {
        this.contentResolver = contentResolver;
        this.baseUri = baseUri;
    }

    @NonNull
    public final List<T> getAll() {
        final Cursor cursor = contentResolver.query(baseUri, null,
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

    public final boolean insert(@NonNull T record) {
        final ContentValues contentValues = record.toContentValues();
        final Uri uri = contentResolver.insert(HealthStoreUri.ACTIVITY, contentValues);
        return uri != null;
    }

    public final boolean update(@NonNull T record) {
        final Uri updateUri = getUri(record);
        final ContentValues cv = record.toContentValues();
        final int updated = contentResolver.update(updateUri, cv, null, null);
        return updated == 1;
    }

    public final boolean delete(@NonNull T record) {
        final Uri deleteUri = getUri(record);
        final int deleted = contentResolver.delete(deleteUri, null, null);
        return deleted == 1;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP, RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public ContentProviderResult[] executeBatch(
            @NonNull ArrayList<ContentProviderOperation> ops
    ) throws OperationApplicationException, RemoteException {
        return contentResolver.applyBatch(baseUri.toString(), ops);
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
    private Uri getUri(@NonNull Record activityRecord) {
        return getUri(activityRecord.getMetric(), activityRecord.getId());
    }

    @NonNull
    private Uri getUri(@ActivityMetric int metric, long id) {
        final String path = String.format(Locale.ROOT, "/%1$d/%2$d", metric, id);
        return Uri.withAppendedPath(baseUri, path);
    }
}
