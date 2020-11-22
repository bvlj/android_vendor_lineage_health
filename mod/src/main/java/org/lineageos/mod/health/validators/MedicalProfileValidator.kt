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
import android.util.Log
import org.lineageos.mod.health.HealthStore
import org.lineageos.mod.health.common.db.MedicalProfileColumns

internal object MedicalProfileValidator : Validator() {
    private const val TAG = "MedicalProfileValidator"

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
                Log.w(TAG, "Removing invalid blood type value $value")
                cv.remove(MedicalProfileColumns.BLOOD_TYPE)
            }
        }

        fun validateHeight(cv: ContentValues) {
            val value = cv.getAsFloat(MedicalProfileColumns.HEIGHT) ?: 0f
            if (value < 0f) {
                Log.w(TAG, "Removing invalid height ${value}m")
                cv.remove(MedicalProfileColumns.HEIGHT)
            }
        }

        fun validateOrganDonor(cv: ContentValues) {
            val value = cv.getAsInteger(MedicalProfileColumns.ORGAN_DONOR)
            if (value == null || value < 0 || value > 2) {
                Log.w(TAG, "Removing invalid organ donor value $value")
                cv.remove(MedicalProfileColumns.ORGAN_DONOR)
            }
        }

        fun validateBiologicalSex(cv: ContentValues) {
            val value = cv.getAsInteger(MedicalProfileColumns.BIOLOGICAL_SEX)
            if (value == null || value < 0 || value > 2) {
                Log.w(TAG, "Removing invalid biological sex value $value")
                cv.remove(MedicalProfileColumns.BIOLOGICAL_SEX)
            }
        }
    }
}
