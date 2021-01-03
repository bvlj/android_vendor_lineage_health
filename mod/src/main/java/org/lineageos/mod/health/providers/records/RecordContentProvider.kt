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

package org.lineageos.mod.health.providers.records

import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import net.sqlcipher.database.SQLiteQueryBuilder
import org.lineageos.mod.health.UriConst
import org.lineageos.mod.health.access.AccessManager
import org.lineageos.mod.health.access.EmptyCursor
import org.lineageos.mod.health.access.canRead
import org.lineageos.mod.health.access.canWrite
import org.lineageos.mod.health.common.db.RecordColumns
import org.lineageos.mod.health.common.values.AccessPolicyValues
import org.lineageos.mod.health.db.HealthStoreDbHelper
import org.lineageos.mod.health.providers.BaseHealthStoreContentProvider
import org.lineageos.mod.health.security.KeyMaster
import org.lineageos.mod.health.validators.RecordValidator

abstract class RecordContentProvider(
    private val contentUri: Uri,
    authority: String,
    private val tableName: String
) : BaseHealthStoreContentProvider() {

    companion object {
        private const val WHERE_BY_METRIC =
            "${RecordColumns._METRIC} = ?"
        private const val WHERE_BY_METRIC_ID =
            "${RecordColumns._METRIC} = ? AND ${RecordColumns._ID} = ?"
        private const val DEFAULT_QUERY_SORT =
            "${RecordColumns.TIME} DESC"
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(authority, "#", UriConst.MATCH_METRIC)
        addURI(authority, "#/#", UriConst.MATCH_ITEM)
    }

    private lateinit var keyMaster: KeyMaster
    private lateinit var accessManager: AccessManager

    override fun onCreate(): Boolean {
        keyMaster = KeyMaster.getInstance(context!!)
        accessManager = AccessManager(context!!.contentResolver)
        return super.onCreate()
    }

    override fun getDatabaseHelper(context: Context): SQLiteOpenHelper {
        return HealthStoreDbHelper.getInstance(context)
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        return openHelper!!.getReadableDatabase(keyMaster.getDbKey())
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return openHelper!!.getWritableDatabase(keyMaster.getDbKey())
    }

    override fun insertInTransactionImpl(uri: Uri, values: ContentValues): Uri? {
        val match = uriMatcher.match(uri)
        if (match != UriConst.MATCH_METRIC) {
            throw IllegalArgumentException("Unknown insert URL $uri")
        }

        val metric = values.getAsInteger(RecordColumns._METRIC).toString()
        val pathMetric = uri.lastPathSegment
        if (metric != pathMetric) {
            throw IllegalArgumentException(
                "Trying to insert a record with metric $metric as a metric $pathMetric"
            )
        }

        if (!canWrite(accessManager, metric)) {
            return AccessPolicyValues.DENIED_URI
        }

        val db = getWritableDatabase()
        val id = db.insert(tableName, null, values)

        return if (id < 0) null else Uri.withAppendedPath(contentUri, "$metric/$id")
    }

    override fun updateInTransactionImpl(uri: Uri, values: ContentValues): Int {
        val match = uriMatcher.match(uri)
        if (match != UriConst.MATCH_ITEM) {
            throw IllegalArgumentException("Unknown update URL $uri")
        }

        val metric = values.getAsInteger(RecordColumns._METRIC).toString()
        if (!canWrite(accessManager, metric)) {
            return AccessPolicyValues.DENIED_COUNT
        }

        val segments = uri.pathSegments
        val localSelectionArgs = arrayOf(
            segments[segments.size - 2],
            segments[segments.size - 1]
        )

        val db = getWritableDatabase()
        return db.update(tableName, values, WHERE_BY_METRIC_ID, localSelectionArgs)
    }

    override fun deleteInTransactionImpl(uri: Uri): Int {
        val localSelection: String
        val localSelectionArgs: Array<String>
        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_METRIC -> {
                localSelection = WHERE_BY_METRIC
                localSelectionArgs = arrayOf(
                    segments[segments.size - 1]
                )
            }
            UriConst.MATCH_ITEM -> {
                localSelection = WHERE_BY_METRIC_ID
                localSelectionArgs = arrayOf(
                    segments[segments.size - 2],
                    segments[segments.size - 1]
                )
            }
            else -> throw IllegalArgumentException("Unknown delete URL $uri")
        }

        val metric = localSelectionArgs.last()
        if (!canWrite(accessManager, metric)) {
            return AccessPolicyValues.DENIED_COUNT
        }

        val db = getWritableDatabase()
        return db.delete(tableName, localSelection, localSelectionArgs)
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
        val metric: String
        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_METRIC -> {
                metric = segments[segments.size - 1]
                if (localSortOrder.isEmpty()) {
                    localSortOrder = DEFAULT_QUERY_SORT
                }
                if (localSelection.isNotEmpty()) {
                    localSelection += " AND "
                }
                localSelection += WHERE_BY_METRIC
                localSelectionArgs += arrayOf(
                    metric
                )
            }
            UriConst.MATCH_ITEM -> {
                metric = segments[segments.size - 2]
                if (localSelection.isNotEmpty()) {
                    localSelection += " AND "
                }
                localSelection += WHERE_BY_METRIC_ID
                localSelectionArgs += arrayOf(
                    metric,
                    segments[segments.size - 1]
                )
            }
            else -> throw IllegalArgumentException("Unknown query URL $uri")
        }

        if (!canRead(accessManager, metric)) {
            return EmptyCursor
        }

        val qb = SQLiteQueryBuilder().apply { tables = tableName }
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
        context!!.contentResolver.notifyChange(contentUri, null)
    }

    override fun verifyTransactionAllowed(
        type: Type,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) {
        when (type) {
            Type.INSERT -> {
                RecordValidator.validate(values)
            }
            Type.UPDATE -> {
                if (selection != null || selectionArgs != null) {
                    throw IllegalArgumentException("Cannot update with a WHERE clause")
                }
                RecordValidator.validate(values)
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

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        UriConst.MATCH_METRIC -> "vnd.android.cursor.dir"
        UriConst.MATCH_ITEM -> "vnd.android.cursor.item"
        else -> null
    }
}
