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

package org.lineageos.mod.health.validators

import android.content.ContentValues
import org.lineageos.mod.health.HealthStore
import org.lineageos.mod.health.common.Metric
import org.lineageos.mod.health.common.db.AccessColumns
import org.lineageos.mod.health.common.values.Permission

object AccessValidator : Validator() {
    private val ALLOWED_PERMISSION_RANGE = Permission.NONE..Permission.ALL

    override fun pullVersion(cv: ContentValues): Int {
        return HealthStore.Version.CURRENT
    }

    override fun validateActinium(cv: ContentValues) {
        V1.validateMetric(cv)
        V1.validatePermissions(cv)
        V1.validatePkgName(cv)
    }

    private object V1 {

        fun validateMetric(cv: ContentValues) {
            val value = cv.getAsInteger(AccessColumns.METRIC)
            if (value == null || value <= Metric.UNKNOWN) {
                throw ValidationException("Invalid metric (was $value)")
            }
        }

        fun validatePkgName(cv: ContentValues) {
            val value = cv.getAsString(AccessColumns.PKG_NAME)
            if (value == null || !value.contains(".")) {
                throw ValidationException("Invalid package name (was $value)")
            }
        }

        fun validatePermissions(cv: ContentValues) {
            val value = cv.getAsInteger(AccessColumns.PERMISSIONS)
            if (value == null || value !in ALLOWED_PERMISSION_RANGE) {
                throw ValidationException("Invalid permissions (was $value)")
            }
        }
    }
}
