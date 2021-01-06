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
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.common.db.AccessColumns
import org.lineageos.mod.health.db.SqlCipherLoader
import org.lineageos.mod.health.partner.PartnerProvider

/**
 * The purpose of this class is to initialize
 * the sql cipher libraries
 */
class InitProvider : ContentProvider() {

    companion object {
        private const val PREFS_NAME = "partner_prefs"
        private const val PREFS_KEY_PARTNER_LOADED = "key_partner_loaded"
    }

    override fun onCreate(): Boolean {
        SqlCipherLoader.loadLibs(context!!)
        loadPartnerConfig(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException()
    }

    private fun loadPartnerConfig(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(PREFS_KEY_PARTNER_LOADED, false)) {
            return
        }
        val partner = PartnerProvider.get(context.packageManager) ?: return
        val defaultPolicies = partner.getDefaultAccessPolicy()
        val values = Array(defaultPolicies.size) { i ->
            val it = defaultPolicies[i]
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, it.pkgName)
                put(AccessColumns.METRIC, it.metric)
                put(AccessColumns.PERMISSIONS, it.permissions)
            }
        }
        val inserted = context.contentResolver.bulkInsert(HealthStoreUri.ACCESS, values)
        if (inserted == defaultPolicies.size) {
            prefs.edit()
                .putBoolean(PREFS_KEY_PARTNER_LOADED, true)
                .apply()
        }
    }
}
