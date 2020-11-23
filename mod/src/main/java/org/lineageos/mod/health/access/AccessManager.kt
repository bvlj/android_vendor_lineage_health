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

package org.lineageos.mod.health.access

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import org.lineageos.mod.health.common.values.annotations.HealthStorePermission
import org.lineageos.mod.health.common.values.annotations.MetricType
import org.lineageos.mod.health.common.values.Permission
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.common.db.AccessColumns

internal class AccessManager(
    private val contentResolver: ContentResolver
) {

    fun canRead(pkgName: String, @MetricType metric: Int): Boolean {
        return getPermissionsFor(pkgName, metric) and Permission.READ != 0
    }

    fun canWrite(pkgName: String, @MetricType metric: Int): Boolean {
        return getPermissionsFor(pkgName, metric) and Permission.WRITE != 0
    }

    @HealthStorePermission
    @SuppressLint("Recycle") // AStudio goes mad over the elvis operator
    private fun getPermissionsFor(pkgName: String, @MetricType metric: Int): Int {
        val uri = Uri.withAppendedPath(HealthStoreUri.ACCESS, "$pkgName/$metric")
        val query = contentResolver.query(
            uri,
            arrayOf(AccessColumns.PERMISSIONS),
            null,
            null
        ) ?: return Permission.NONE

        if (!query.moveToFirst()) {
            // Not found, assume it's allowed
            return Permission.ALL
        }

        query.use {
            return it.getInt(0)
        }
    }
}
