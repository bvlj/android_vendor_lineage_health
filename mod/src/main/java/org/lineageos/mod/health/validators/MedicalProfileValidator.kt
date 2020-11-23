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
import org.lineageos.mod.health.HealthStore
import org.lineageos.mod.health.common.db.MedicalProfileColumns

internal object MedicalProfileValidator : Validator() {

    override fun pullVersion(cv: ContentValues): Int {
        val version = cv.getAsInteger(MedicalProfileColumns._VERSION)
            ?: HealthStore.Version.MIN

        // Now that we've read it, remove it
        cv.remove(MedicalProfileColumns._VERSION)

        if (version < HealthStore.Version.MIN) {
            // Unknown version, assume minimum supported
            return HealthStore.Version.MIN
        }
        return version
    }

    override fun validateActinium(cv: ContentValues) {
        V1.validateBloodType(cv)
        V1.validateHeight(cv)
        V1.validateOrganDonor(cv)
        V1.validateBiologicalSex(cv)
    }

    private object V1 {

        fun validateBloodType(cv: ContentValues) {
            val value = cv.getAsInteger(MedicalProfileColumns.BLOOD_TYPE)
            if (value == null || value < 0 || value > 9) {
                throw ValidationException("Invalid blood type value (was $value)")
            }
        }

        fun validateHeight(cv: ContentValues) {
            val value = cv.getAsFloat(MedicalProfileColumns.HEIGHT) ?: 0f
            if (value < 0f || value > 300f) {
                throw ValidationException("Invalid height (was $value cm)")
            }
        }

        fun validateOrganDonor(cv: ContentValues) {
            val value = cv.getAsInteger(MedicalProfileColumns.ORGAN_DONOR)
            if (value == null || value < 0 || value > 2) {
                throw ValidationException("Invalid organ donor value (was $value)")
            }
        }

        fun validateBiologicalSex(cv: ContentValues) {
            val value = cv.getAsInteger(MedicalProfileColumns.BIOLOGICAL_SEX)
            if (value == null || value < 0 || value > 2) {
                throw ValidationException("Invalid biological sex value (was $value)")
            }
        }
    }
}
