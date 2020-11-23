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
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.lineageos.mod.health.sdk.model.records.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Batch operations performed on a repository
 */
public final class BatchOperations<T extends Record> {
    @NonNull
    private final Uri baseUri;
    @NonNull
    private final List<BatchOperation> operations;

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    BatchOperations(@NonNull final Uri baseUri) {
        this.baseUri = baseUri;
        this.operations = new ArrayList<>();
    }

    /**
     * Delete a record
     */
    @NonNull
    public BatchOperations<T> delete(@NonNull T record) {
        operations.add(new BatchOperation.Delete<>(record));
        return this;
    }

    /**
     * Insert a record
     */
    @NonNull
    public BatchOperations<T> insert(@NonNull T record) {
        operations.add(new BatchOperation.Insert<>(record));
        return this;
    }

    /**
     * Update a record
     */
    @NonNull
    public BatchOperations<T> update(@NonNull T record) {
        operations.add(new BatchOperation.Update<>(record));
        return this;
    }

    /**
     * @hide
     */
    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    ArrayList<ContentProviderOperation> build() {
        final ArrayList<ContentProviderOperation> list = new ArrayList<>();

        operations.forEach(it -> {
            final String metricPath = String.valueOf(it.record.getMetric());
            final String idPath = String.format(Locale.ROOT, "%1$s/%2$d",
                    metricPath, it.record.getId());
            final Uri metricUri = Uri.withAppendedPath(baseUri, metricPath);
            final Uri idUri = Uri.withAppendedPath(baseUri, idPath);

            if (it instanceof BatchOperation.Delete) {
                list.add(ContentProviderOperation.newDelete(idUri)
                        .build());
            } else if (it instanceof BatchOperation.Insert) {
                list.add(ContentProviderOperation.newInsert(metricUri)
                        .withValues(it.record.toContentValues())
                        .build());
            } else if (it instanceof BatchOperation.Update) {
                list.add(ContentProviderOperation.newUpdate(idUri)
                        .withValues(it.record.toContentValues())
                        .build());
            }
        });
        return list;
    }

    public interface Builder<T extends Record> {

        void build(@NonNull BatchOperations<T> composer);
    }
}
