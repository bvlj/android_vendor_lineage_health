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

package org.lineageos.mod.health.security

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import org.lineageos.mod.util.SingletonHolder
import java.security.KeyStore
import java.util.Base64
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

internal class KeyMaster private constructor(
    private val prefs: SharedPreferences
) {
    companion object : SingletonHolder<KeyMaster, Context>({
        val context = it.applicationContext ?: it
        KeyMaster(context.getSharedPreferences(KeyMaster.TAG, Context.MODE_PRIVATE))
    }) {
        private const val TAG = "KeyMaster"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_ALIAS = "healthy_axolotl"
        private const val KEY_IV = "iv"
        private const val KEY_SECRET = "secret"
    }

    private var pwd: String? = null

    fun initialize() {
        var entry = retrieveKey()
        if (entry != null && prefs[KEY_SECRET].isNotEmpty()) {
            return
        }

        // Not yet initialized
        deleteOldKey()
        generateKey()
        entry = retrieveKey()
            ?: throw SecurityException("Failed to retrieve generated key")
        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, entry.secretKey)
        }

        prefs[KEY_IV] = cipher.iv
        val secret = UUID.randomUUID()
            .toString()
            .toByteArray(Charsets.UTF_8)
        prefs[KEY_SECRET] = cipher.doFinal(secret)
    }

    fun getDbKey(): String {
        pwd?.let { return it }

        val iv = prefs[KEY_IV]
        val encrypted = prefs[KEY_SECRET]

        if (iv.isEmpty() || encrypted.isEmpty()) {
            initialize()
            return getDbKey()
        }

        val entry = retrieveKey()
            ?: throw SecurityException("Failed to retrieve generated key")
        val cipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, entry.secretKey, GCMParameterSpec(128, iv))
        }
        // Return and cache the value
        return String(cipher.doFinal(encrypted), Charsets.UTF_8).also { pwd = it }
    }

    private fun deleteOldKey() {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        if (ks.getEntry(KEY_ALIAS, null) != null) {
            ks.deleteEntry(KEY_ALIAS)
        }
    }

    private fun generateKey() {
        val kpg = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        val parameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        kpg.init(parameterSpec)
        kpg.generateKey()
    }

    private fun retrieveKey(): KeyStore.SecretKeyEntry? {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val entry = ks.getEntry(KEY_ALIAS, null)
        return if (entry is KeyStore.SecretKeyEntry) entry else null
    }

    private operator fun SharedPreferences.set(key: String, value: ByteArray) {
        val str = Base64.getEncoder().encodeToString(value)
        edit()
            .putString(key, str)
            .apply()
    }

    private operator fun SharedPreferences.get(key: String): ByteArray {
        val str = getString(key, null) ?: return byteArrayOf()
        return Base64.getDecoder().decode(str)
    }
}
