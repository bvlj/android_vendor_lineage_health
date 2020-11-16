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

package org.lineageos.mod.health.sdk.ktx.batch

import android.content.ContentProviderOperation
import android.net.Uri
import org.lineageos.mod.health.sdk.model.records.Record

class HsBatchOperationBuilder<T : Record> internal constructor(
    private val uri: Uri
) {

    private val operations = mutableListOf<HsBatchOperation<T>>()

    fun delete(record: T) {
        operations.add(HsBatchOperation.Delete(record))
    }

    fun insert(record: T) {
        operations.add(HsBatchOperation.Insert(record))
    }

    fun update(record: T) {
        operations.add(HsBatchOperation.Update(record))
    }

    internal fun build(): ArrayList<ContentProviderOperation> {
        val list = arrayListOf<ContentProviderOperation>()
        operations.forEach {
            when (it) {
                is HsBatchOperation.Delete ->
                    list.add(
                        ContentProviderOperation.newDelete(uri)
                            .withValues(it.record.toContentValues())
                            .build()
                    )
                is HsBatchOperation.Insert ->
                    list.add(
                        ContentProviderOperation.newInsert(uri)
                            .withValues(it.record.toContentValues())
                            .build()
                    )
                is HsBatchOperation.Update ->
                    list.add(
                        ContentProviderOperation.newUpdate(uri)
                            .withValues(it.record.toContentValues())
                            .build()
                    )
            }
        }
        return list
    }
}

