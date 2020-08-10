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

package org.lineageos.mod.health.db.tables

import android.database.sqlite.SQLiteDatabase
import org.lineageos.mod.health.common.db.MedicalProfileColumns

internal object MedicalProfileTable : Table {
    const val NAME = "profile"

    private const val CREATE_CMD = "CREATE TABLE IF NOT EXISTS $NAME (" +
        "${MedicalProfileColumns._ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
        "${MedicalProfileColumns.ALLERGIES} TEXT NOT NULL DEFAULT '', " +
        "${MedicalProfileColumns.BLOOD_TYPE} INTEGER NOT NULL DEFAULT 0, " +
        "${MedicalProfileColumns.HEIGHT} REAL NOT NULL DEFAULT 0, " +
        "${MedicalProfileColumns.MEDICATIONS} TEXT NOT NULL DEFAULT '', " +
        "${MedicalProfileColumns.NOTES} TEXT NOT NULL DEFAULT '', " +
        "${MedicalProfileColumns.ORGAN_DONOR} INTEGER NOT NULL DEFAULT 0, " +
        "${MedicalProfileColumns.BIOLOGICAL_SEX} INTEGER NOT NULL DEFAULT 0 " +
        ")"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_CMD)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // empty
    }
}
