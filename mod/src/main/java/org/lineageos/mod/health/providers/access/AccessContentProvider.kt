/*
 * Copyright (C) 2021 The LineageOS Project
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

package org.lineageos.mod.health.providers.access

import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import net.sqlcipher.database.SQLiteQueryBuilder
import org.lineageos.mod.health.UriConst
import org.lineageos.mod.health.common.CareCacheUri
import org.lineageos.mod.health.common.db.AccessColumns
import org.lineageos.mod.health.db.CareCacheDbHelper
import org.lineageos.mod.health.db.tables.AccessTable
import org.lineageos.mod.health.providers.BaseCareCacheContentProvider
import org.lineageos.mod.health.security.KeyMaster
import org.lineageos.mod.health.validators.AccessValidator

class AccessContentProvider : BaseCareCacheContentProvider() {

    companion object {
        private const val WHERE_BY_PKG_METRIC =
            "${AccessColumns.PKG_NAME} = ? AND ${AccessColumns.METRIC} = ?"
        private const val DEFAULT_QUERY_SORT =
            "${AccessColumns.PKG_NAME} ASC ${AccessColumns.METRIC} ASC"

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(CareCacheUri.Authority.ACCESS, "/", UriConst.MATCH_ALL)
            addURI(CareCacheUri.Authority.ACCESS, "/*", UriConst.MATCH_PKG)
            addURI(CareCacheUri.Authority.ACCESS, "/*/#", UriConst.MATCH_ITEM)
        }
    }

    private lateinit var keyMaster: KeyMaster

    override fun onCreate(): Boolean {
        keyMaster = KeyMaster.getInstance(context!!)
        return super.onCreate()
    }

    override fun getDatabaseHelper(context: Context): SQLiteOpenHelper {
        return CareCacheDbHelper.getInstance(context)
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        return openHelper!!.getReadableDatabase(keyMaster.getDbKey())
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return openHelper!!.getWritableDatabase(keyMaster.getDbKey())
    }

    override fun insertInTransactionImpl(uri: Uri, values: ContentValues): Uri? {

        val match = uriMatcher.match(uri)
        if (match != UriConst.MATCH_ALL) {
            throw IllegalArgumentException("Unknown insert URL $uri")
        }

        val db = getWritableDatabase()
        val id = db.insert(AccessTable.NAME, null, values)

        val pkg = values.getAsString(AccessColumns.PKG_NAME)
        val metric = values.getAsInteger(AccessColumns.METRIC)
        return if (id < 0) null else Uri.withAppendedPath(uri, "$pkg/$metric")
    }

    override fun updateInTransactionImpl(uri: Uri, values: ContentValues): Int {
        val match = uriMatcher.match(uri)
        if (match != UriConst.MATCH_ITEM) {
            throw IllegalArgumentException("Unknown update URL $uri")
        }

        val segments = uri.pathSegments
        val localSelectionArgs = arrayOf(
            segments[segments.size - 2],
            segments[segments.size - 1]
        )

        val db = getWritableDatabase()
        return db.update(AccessTable.NAME, values, WHERE_BY_PKG_METRIC, localSelectionArgs)
    }

    override fun deleteInTransactionImpl(uri: Uri): Int {
        val localSelection: String?
        val localSelectionArgs: Array<String>?
        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_ALL -> {
                localSelection = null
                localSelectionArgs = null
            }
            UriConst.MATCH_ITEM -> {
                localSelection = WHERE_BY_PKG_METRIC
                val pkg = segments[segments.size - 2]
                val metric = segments[segments.size - 1]
                localSelectionArgs = arrayOf(pkg, metric)
            }
            else -> throw IllegalArgumentException("Unknown delete URL $uri")
        }

        val db = getWritableDatabase()
        return db.delete(AccessTable.NAME, localSelection, localSelectionArgs)
    }

    override fun queryImpl(
        uri: Uri,
        projection: Array<out String>?,
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
                    localSortOrder = DEFAULT_QUERY_SORT
                }
            }
            UriConst.MATCH_ITEM -> {
                if (localSelection.isNotEmpty()) {
                    localSelection += " AND "
                }
                localSelection += WHERE_BY_PKG_METRIC
                localSelectionArgs += arrayOf(
                    segments[segments.size - 2],
                    segments[segments.size - 1]
                )
            }
            else -> throw IllegalArgumentException("Unknown query URL $uri")
        }

        val qb = SQLiteQueryBuilder().apply { tables = AccessTable.NAME }
        val cursor = qb.query(
            getReadableDatabase(),
            projection,
            localSelection,
            localSelectionArgs,
            null,
            null,
            localSortOrder
        )
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun notifyChange() {
        context!!.contentResolver.notifyChange(CareCacheUri.ACCESS, null)
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        UriConst.MATCH_PKG -> "vnd.android.cursor.dir"
        UriConst.MATCH_ITEM -> "vnd.android.cursor.item"
        else -> null
    }

    override fun verifyTransactionAllowed(
        type: Type,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) {
        when (type) {
            Type.INSERT -> {
                AccessValidator.validate(values)
            }
            Type.UPDATE -> {
                if (selection != null || selectionArgs != null) {
                    throw IllegalArgumentException("Cannot update with a WHERE clause")
                }
                AccessValidator.validate(values)
            }
            Type.DELETE -> {
                if (selection != null || selectionArgs != null) {
                    throw IllegalArgumentException("Cannot delete with a WHERE clause")
                }
            }
            Type.QUERY -> {
            }
        }
    }
}
