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

package org.lineageos.mod.health.validators

import android.content.ContentValues
import org.lineageos.mod.health.CareCache

abstract class Validator {

    fun validate(cv: ContentValues?) {
        if (cv == null) {
            throw ValidationException("ContentValues cannot be null")
        }

        val version = pullVersion(cv)

        // Sort by higher-than decreasing to maximize compatibility
        when {
            // version >= CareCache.Version.BISMUTH -> validateBismuth(cv)
            version >= CareCache.Version.ACTINIUM -> validateActinium(cv)
            else -> throw ValidationException("Unsupported SDK version $version")
        }
    }

    protected abstract fun pullVersion(cv: ContentValues): Int

    protected abstract fun validateActinium(cv: ContentValues)

    class ValidationException(msg: String) : Exception(msg)
}
