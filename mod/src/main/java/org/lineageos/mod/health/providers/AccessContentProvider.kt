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

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import net.sqlcipher.database.SQLiteQueryBuilder
import org.lineageos.mod.health.UriConst
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.common.db.AccessColumns
import org.lineageos.mod.health.db.HealthStoreDbHelper
import org.lineageos.mod.health.db.tables.AccessTable
import org.lineageos.mod.health.security.KeyMaster

internal class AccessContentProvider : ContentProvider() {

    private lateinit var keyMaster: KeyMaster
    private lateinit var accessDbHelper: HealthStoreDbHelper

    override fun onCreate(): Boolean {
        accessDbHelper = HealthStoreDbHelper.getInstance(context)
        keyMaster = KeyMaster.getInstance(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var localSelection = selection ?: ""
        var localSelectionArgs = selectionArgs ?: emptyArray()
        var localSortOrder = sortOrder ?: ""

        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_ALL -> {
                if (localSortOrder.isEmpty()) {
                    localSortOrder = "${AccessColumns.PKG_NAME} ASC"
                }
            }

            UriConst.MATCH_ID -> {
                if (localSelection.isNotEmpty()) localSelection += " AND "
                localSelection += " ${AccessColumns._ID} = ?"

                val id = segments[segments.size - 1]
                localSelectionArgs += id
            }

            UriConst.MATCH_ITEM -> {
                if (localSelection.isNotEmpty()) localSelection += " AND "
                localSelection += " ${AccessColumns.PKG_NAME} = ? AND " +
                    "${AccessColumns.METRIC} = ?"

                val pkg = segments[segments.size - 2]
                val metric = segments[segments.size - 1]
                localSelectionArgs += arrayOf(pkg, metric)
            }
            else -> return null
        }

        return withMyId {
            val qb = SQLiteQueryBuilder().apply { tables = AccessTable.NAME }
            val db = accessDbHelper.getReadableDatabase(keyMaster.getDbKey())

            val cursor = qb.query(
                db, projection, localSelection, selectionArgs,
                null, null, localSortOrder
            )
            cursor.setNotificationUri(context!!.contentResolver, uri)
            cursor
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) != UriConst.MATCH_ALL) return null

        return withMyId {
            val db = accessDbHelper.getWritableDatabase(keyMaster.getDbKey())
            val rowId = db.insert(AccessTable.NAME, null, values)
            if (rowId <= 0) return@withMyId null

            context!!.contentResolver.notifyChange(HealthStoreUri.ACCESS, null)
            ContentUris.withAppendedId(HealthStoreUri.ACCESS, rowId)
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        var localSelection = selection ?: ""
        var localSelectionArgs = selectionArgs ?: emptyArray()

        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_ALL -> {
                // do nothing
            }

            UriConst.MATCH_ID -> {
                if (selection != null || selectionArgs != null) {
                    throw UnsupportedOperationException(
                        "Cannot update URI $uri with a WHERE clause"
                    )
                }
                localSelection = "${AccessColumns._ID} = ?"
                localSelectionArgs = arrayOf(segments[segments.size - 1])
            }

            else -> throw UnsupportedOperationException("Cannot update this URI: $uri")
        }

        return withMyId {
            val db = accessDbHelper.getWritableDatabase(keyMaster.getDbKey())
            val count = db.update(AccessTable.NAME, values, localSelection, localSelectionArgs)
            if (count > 0) {
                context!!.contentResolver.notifyChange(HealthStoreUri.ACCESS, null)
            }
            count
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var localSelection = selection ?: ""
        var localSelectionArgs = selectionArgs ?: emptyArray()

        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_ALL -> {
                // do nothing
            }

            UriConst.MATCH_ID -> {
                if (localSelection.isNotEmpty() || localSelectionArgs.isNotEmpty()) {
                    throw UnsupportedOperationException(
                        "Cannot delete URI $uri with a WHERE clause"
                    )
                }
                localSelection = "${AccessColumns._ID} = ?"
                localSelectionArgs = arrayOf(segments[segments.size - 1])
            }

            else -> throw UnsupportedOperationException("Cannot delete this URI: $uri")
        }

        return withMyId {
            val db = accessDbHelper.getWritableDatabase(keyMaster.getDbKey())
            val count = db.delete(AccessTable.NAME, localSelection, localSelectionArgs)
            if (count > 0) {
                context!!.contentResolver.notifyChange(HealthStoreUri.ACCESS, null)
            }
            count
        }
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        UriConst.MATCH_ALL -> "vnd.android.cursor.dir"
        UriConst.MATCH_ID,
        UriConst.MATCH_ITEM -> "vnd.android.cursor.item"
        else -> null
    }

    companion object {
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(HealthStoreUri.Authority.ACCESS, "/all", UriConst.MATCH_ALL)
            addURI(HealthStoreUri.Authority.ACCESS, "/#", UriConst.MATCH_ID)
            addURI(HealthStoreUri.Authority.ACCESS, "/*/#", UriConst.MATCH_ITEM)
        }
    }
}
