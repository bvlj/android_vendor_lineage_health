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
import org.lineageos.mod.health.db.tables.AccessTable
import org.lineageos.mod.health.db.tables.ActivityTable
import org.lineageos.mod.health.db.tables.BodyTable
import org.lineageos.mod.health.db.tables.BreathingTable
import org.lineageos.mod.health.db.tables.HeartBloodTable
import org.lineageos.mod.health.db.tables.MindfulnessTable
import org.lineageos.mod.util.SingletonHolder

class CareCacheDbHelper private constructor(
    context: Context?
) : SQLiteOpenHelper(
    context,
    NAME,
    null,
    DB_VERSION
) {
    companion object : SingletonHolder<CareCacheDbHelper, Context?>({ CareCacheDbHelper(it) }) {
        private const val DB_VERSION = 1
        private const val NAME = "healthStore"
    }

    private val tables = arrayOf(
        AccessTable,
        ActivityTable,
        BodyTable,
        BreathingTable,
        HeartBloodTable,
        MindfulnessTable
    )

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            tables.forEach { it.onCreate(db) }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            tables.forEach { it.onUpgrade(db, oldVersion, newVersion) }
        }
    }
}
