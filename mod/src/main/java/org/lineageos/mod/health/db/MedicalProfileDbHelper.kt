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

package org.lineageos.mod.health.db

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import org.lineageos.mod.health.db.tables.MedicalProfileTable
import org.lineageos.mod.util.SingletonHolder

internal class MedicalProfileDbHelper private constructor(
    context: Context?
) : SQLiteOpenHelper(
    context?.applicationContext?.createDeviceProtectedStorageContext(),
    NAME,
    null,
    DB_VERSION
) {
    companion object : SingletonHolder<MedicalProfileDbHelper, Context?>({
        MedicalProfileDbHelper(it)
    }) {
        private const val DB_VERSION = 1
        private const val NAME = "medicalProfile"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            MedicalProfileTable.onCreate(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            MedicalProfileTable.onUpgrade(db, oldVersion, newVersion)
        }
    }
}
