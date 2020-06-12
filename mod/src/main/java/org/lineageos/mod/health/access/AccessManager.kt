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
import android.content.ContentProvider
import android.content.ContentResolver
import org.lineageos.mod.health.common.annotations.HealthStorePermission
import org.lineageos.mod.health.common.annotations.MetricType
import org.lineageos.mod.health.common.annotations.Permission
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
        val query = contentResolver.query(
            HealthStoreUri.ACCESS,
            arrayOf(AccessColumns.PERMISSIONS),
            "${AccessColumns.PKG_NAME} = ? AND ${AccessColumns.METRIC} = ?",
            arrayOf(pkgName, metric.toString()),
            null
        ) ?: return Permission.NONE

        if (!query.moveToFirst()) {
            // Not found, assume it's allowed
            return Permission.ALL
        }

        val permission = query.getInt(0)
        query.close()

        return permission
    }
}

internal fun ContentProvider.assertCanRead(
    accessManager: AccessManager,
    metric: String
) {
    val callingPkg = callingPackage ?: "[unknown package]"
    val metricInt = metric.toIntOrNull()
    if (metricInt == null || !accessManager.canRead(callingPkg, metricInt)) {
        throw SecurityException(
            "$callingPkg is not allowed to read from metric $metric"
        )
    }
}

internal fun ContentProvider.assertCanWrite(
    accessManager: AccessManager,
    metric: String
) {
    val callingPkg = callingPackage ?: "[unknown package]"
    val metricInt = metric.toIntOrNull()
    if (metricInt == null || !accessManager.canWrite(callingPkg, metricInt)) {
        throw SecurityException(
            "$callingPkg is not allowed to write to metric $metric"
        )
    }
}
