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

import android.database.AbstractCursor

/**
 * An empty cursor returned in "disallowed" queries
 * performed from apps that have been blocked from one
 * or more metrics by the user
 */
object EmptyCursor : AbstractCursor() {

    override fun getLong(column: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return 0
    }

    override fun getColumnNames(): Array<String> {
        return emptyArray()
    }

    override fun getShort(column: Int): Short {
        return 0
    }

    override fun getFloat(column: Int): Float {
        return 0f
    }

    override fun getDouble(column: Int): Double {
        return 0.0
    }

    override fun isNull(column: Int): Boolean {
        return false
    }

    override fun getInt(column: Int): Int {
        return 0
    }

    override fun getString(column: Int): String {
        return ""
    }
}
