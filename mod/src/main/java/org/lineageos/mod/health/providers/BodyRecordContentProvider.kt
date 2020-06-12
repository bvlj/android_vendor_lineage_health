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

package org.lineageos.mod.health.providers

import android.content.UriMatcher
import org.lineageos.mod.health.UriConst
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.db.tables.BodyTable

internal class BodyRecordContentProvider : RecordContentProvider(
    HealthStoreUri.BODY,
    uriMatcher,
    BodyTable.NAME
) {

    companion object {
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(HealthStoreUri.AUTHORITY, "body", UriConst.MATCH_ALL)
            addURI(HealthStoreUri.AUTHORITY, "body/#", UriConst.MATCH_METRIC)
            addURI(HealthStoreUri.AUTHORITY, "body/#/#", UriConst.MATCH_ITEM)
        }
    }
}
