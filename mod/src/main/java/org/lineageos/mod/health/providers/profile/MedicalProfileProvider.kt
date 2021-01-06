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

package org.lineageos.mod.health.providers.profile

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import net.sqlcipher.database.SQLiteQueryBuilder
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.db.MedicalProfileDbHelper
import org.lineageos.mod.health.db.SqlCipherLoader
import org.lineageos.mod.health.db.tables.MedicalProfileTable
import org.lineageos.mod.health.providers.BaseHealthStoreContentProvider
import org.lineageos.mod.health.validators.MedicalProfileValidator

class MedicalProfileProvider : BaseHealthStoreContentProvider() {

    companion object {
        private val PASSWORD = null as ByteArray?
    }

    override fun onCreate(): Boolean {
        SqlCipherLoader.loadLibs(context!!)
        return super.onCreate()
    }

    override fun getDatabaseHelper(context: Context): SQLiteOpenHelper {
        return MedicalProfileDbHelper.getInstance(context)
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        return openHelper!!.getReadableDatabase(PASSWORD)
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return openHelper!!.getWritableDatabase(PASSWORD)
    }

    override fun insertInTransactionImpl(uri: Uri, values: ContentValues): Uri? {
        val db = getWritableDatabase()
        db.delete(MedicalProfileTable.NAME, null, null)
        val id = db.insert(MedicalProfileTable.NAME, null, values)
        return if (id < 0) null else ContentUris.withAppendedId(HealthStoreUri.MEDICAL_PROFILE, id)
    }

    override fun updateInTransactionImpl(uri: Uri, values: ContentValues): Int {
        throw UnsupportedOperationException()
    }

    override fun deleteInTransactionImpl(uri: Uri): Int {
        val db = getWritableDatabase()
        return db.delete(MedicalProfileTable.NAME, null, null)
    }

    override fun queryImpl(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val db = getReadableDatabase()
        val qb = SQLiteQueryBuilder().apply { tables = MedicalProfileTable.NAME }

        val cursor = qb.query(
            db, projection, "", emptyArray(),
            null, null, ""
        )
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun notifyChange() {
        context!!.contentResolver.notifyChange(HealthStoreUri.MEDICAL_PROFILE, null)
    }

    override fun getType(uri: Uri) = "vnd.android.cursor.item"

    override fun verifyTransactionAllowed(
        type: Type,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) {
        when (type) {
            Type.INSERT -> {
                MedicalProfileValidator.validate(values)
            }
            Type.UPDATE -> throw UnsupportedOperationException("Use .insert() instead")
            Type.DELETE,
            Type.QUERY -> {
            }
        }
    }
}
