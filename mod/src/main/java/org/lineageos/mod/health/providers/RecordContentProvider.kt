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
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import org.lineageos.mod.health.access.AccessManager
import org.lineageos.mod.health.access.canRead
import org.lineageos.mod.health.access.canWrite
import org.lineageos.mod.health.UriConst
import org.lineageos.mod.health.access.EmptyCursor
import org.lineageos.mod.health.db.HealthStoreDbHelper
import org.lineageos.mod.health.common.db.RecordColumns

internal abstract class RecordContentProvider(
    private val contentUri: Uri,
    authority: String,
    private val tableName: String
) : ContentProvider() {

    private lateinit var dbHelper: HealthStoreDbHelper
    private lateinit var accessManager: AccessManager
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(authority, "/#", UriConst.MATCH_METRIC)
        addURI(authority, "#/#", UriConst.MATCH_ITEM)
    }

    override fun onCreate(): Boolean {
        dbHelper = HealthStoreDbHelper.getInstance(context)
        accessManager = AccessManager(context!!.contentResolver)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        var localSelectionArgs = selectionArgs ?: emptyArray()
        var localSelection = selection ?: ""
        val metric: String

        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_METRIC -> {
                metric = segments[segments.size - 1]
                localSelectionArgs += metric

                if (localSelection.isNotEmpty()) localSelection += " AND "
                localSelection += " ${RecordColumns._METRIC} = ?"
            }

            UriConst.MATCH_ITEM -> {
                metric = segments[segments.size - 2]
                val id = segments[segments.size - 1]
                localSelectionArgs += arrayOf(metric, id)

                if (localSelection.isNotEmpty()) localSelection += " AND "
                localSelection += " ${RecordColumns._METRIC} = ? AND " +
                    "${RecordColumns._ID} = ?"
            }

            else -> return null
        }

        if (!canRead(accessManager, metric)) {
            return EmptyCursor
        }

        return withMyId {
            val qb = SQLiteQueryBuilder().apply { tables = tableName }
            val db = dbHelper.readableDatabase

            val cursor = qb.query(
                db, projection, localSelection, selectionArgs,
                null, null, sortOrder
            )
            cursor.setNotificationUri(context!!.contentResolver, uri)
            cursor
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (uriMatcher.match(uri) != UriConst.MATCH_ALL) return null

        val metric = values?.getAsInteger(RecordColumns._METRIC).toString()

        if (!canWrite(accessManager, metric)) {
            return null
        }

        return withMyId {
            val db = dbHelper.writableDatabase
            val rowId = db.insert(tableName, null, values)
            if (rowId <= 0) return@withMyId null

            context!!.contentResolver.notifyChange(contentUri, null)
            ContentUris.withAppendedId(contentUri, rowId)
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (uriMatcher.match(uri) != UriConst.MATCH_ITEM) {
            throw UnsupportedOperationException("Cannot update this URI: $uri")
        }

        val segments = uri.pathSegments

        val metric = segments[segments.size - 2]
        val id = segments[segments.size - 1]
        val localSelectionArgs = arrayOf(metric, id)
        val localSelection = "${RecordColumns._METRIC} = ? AND ${RecordColumns._ID} = ?"

        if (!canWrite(accessManager, metric)) {
            return 0
        }

        return withMyId {
            val db = dbHelper.writableDatabase
            val count = db.update(tableName, values, localSelection, localSelectionArgs)
            if (count > 0) {
                context!!.contentResolver.notifyChange(contentUri, null)
            }
            count
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val localSelectionArgs: Array<String>
        val localSelection: String
        val metric: String

        val segments = uri.pathSegments

        when (uriMatcher.match(uri)) {
            UriConst.MATCH_METRIC -> {
                metric = segments[segments.size - 1]
                localSelectionArgs = arrayOf(metric)
                localSelection = "${RecordColumns._METRIC} = ?"
            }

            UriConst.MATCH_ITEM -> {
                metric = segments[segments.size - 2]
                val id = segments[segments.size - 1]
                localSelectionArgs = arrayOf(metric, id)
                localSelection = "${RecordColumns._METRIC} = ? AND ${RecordColumns._ID} = ?"
            }

            else -> throw UnsupportedOperationException("Cannot delete this URI: $uri")
        }

        if (!canWrite(accessManager, metric)) {
            return 0
        }

        return withMyId {
            val db = dbHelper.writableDatabase
            val count = db.delete(tableName, localSelection, localSelectionArgs)
            if (count > 0) {
                context!!.contentResolver.notifyChange(contentUri, null)
            }
            count
        }
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {
        UriConst.MATCH_ALL,
        UriConst.MATCH_METRIC -> "vnd.android.cursor.dir"
        UriConst.MATCH_ITEM -> "vnd.android.cursor.item"
        else -> null
    }
}
