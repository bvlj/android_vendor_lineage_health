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
import org.lineageos.mod.health.common.Metric
import org.lineageos.mod.health.common.db.MedicalProfileColumns
import org.lineageos.mod.health.common.db.RecordColumns

object RecordValidator : Validator() {

    override fun pullVersion(cv: ContentValues): Int {
        val version = cv.getAsInteger(RecordColumns._VERSION)
            ?: CareCache.Version.MIN

        // Now that we've read it, remove it
        cv.remove(MedicalProfileColumns._VERSION)

        if (version < CareCache.Version.MIN) {
            // Unknown version, assume minimum supported
            return CareCache.Version.MIN
        }
        return version
    }

    override fun validateActinium(cv: ContentValues) {
        val metric = cv.getAsInteger(RecordColumns._METRIC)
        V1.validateId(cv)
        V1.validateTime(cv)

        when (metric) {
            Metric.ABDOMINAL_CIRCUMFERENCE,
            Metric.BODY_TEMPERATURE,
            Metric.UV_INDEX,
            Metric.WATER_INTAKE,
            Metric.WEIGHT,
            Metric.PEAK_EXPIRATORY_FLOW,
            Metric.RESPIRATORY_RATE,
            Metric.VITAL_CAPACITY,
            Metric.HEART_RATE -> V1.validateValueNonNeg(cv)
            Metric.LEAN_BODY_MASS,
            Metric.OXYGEN_SATURATION,
            Metric.BLOOD_ALCOHOL_CONCENTRATION,
            Metric.PERFUSION_INDEX -> V1.validateValuePercent(cv)
            Metric.CYCLING,
            Metric.RUNNING,
            Metric.WALKING,
            Metric.WORKOUT -> {
                V1.validateAvgSpeed(cv)
                V1.validateCalories(cv)
                V1.validateDistance(cv)
                V1.validateSteps(cv)
            }
            Metric.MENSTRUAL_CYCLE -> {
                V1.validateValueNonNeg(cv)
                V1.validateSexualActivity(cv)
                V1.validateSymptomsOther(cv)
                V1.validateSymptomsPhysical(cv)
                V1.validateValueNonNeg(cv)
            }
            Metric.BLOOD_PRESSURE -> {
                V1.validatePressure(cv)
            }
            Metric.GLUCOSE -> {
                V1.validateValueNonNeg(cv)
                V1.validateMealRelation(cv)
            }
            Metric.MOOD -> {
                V1.validateMoodLevel(cv)
            }
            // No validations gang
            Metric.BODY_MASS_INDEX,
            Metric.INHALER_USAGE,
            Metric.MEDITATION,
            Metric.SLEEP -> {
            }
            else -> throw ValidationException("Unsupported metric $metric")
        }
    }

    private object V1 {

        fun validateAvgSpeed(cv: ContentValues) {
            val value = cv.getAsDouble(RecordColumns.AVG_SPEED) ?: 0.0
            if (value < 0.0 || value > 2.998e8) {
                throw ValidationException("Invalid speed (was $value)")
            }
        }

        fun validateCalories(cv: ContentValues) {
            val value = cv.getAsInteger(RecordColumns.CALORIES) ?: 0
            if (value < 0) {
                throw ValidationException("Invalid negative calories (was $value)")
            }
        }

        fun validateDistance(cv: ContentValues) {
            val value = cv.getAsDouble(RecordColumns.DISTANCE) ?: 0.0
            if (value < 0.0) {
                throw ValidationException("Invalid negative distance (was $value)")
            }
        }

        fun validateId(cv: ContentValues) {
            val value = cv.getAsLong(RecordColumns._ID) ?: -1L
            if (value < 1L) {
                // New record
                cv.remove(RecordColumns._ID)
            }
        }

        fun validateMealRelation(cv: ContentValues) {
            val value = cv.getAsInteger(RecordColumns.MEAL_RELATION)
            if (value < 0 || value > 2) {
                throw ValidationException("Invalid meal relation (was $value)")
            }
        }

        fun validateMoodLevel(cv: ContentValues) {
            val value = cv.getAsInteger(RecordColumns.MOOD)
            if (value == null || value < 0L || value >= 1 shl 11) {
                throw ValidationException("Invalid mood level (was $value)")
            }
        }

        fun validatePressure(cv: ContentValues) {
            val systolic = cv.getAsInteger(RecordColumns.PRESSURE_SYSTOLIC) ?: 0
            val diastolic = cv.getAsInteger(RecordColumns.PRESSURE_DIASTOLIC) ?: 0
            if (systolic < 0 || diastolic < 0) {
                throw ValidationException(
                    "Invalid negative pressure (was sys: $systolic, dia: $diastolic)"
                )
            }
        }

        fun validateSexualActivity(cv: ContentValues) {
            val value = cv.getAsInteger(RecordColumns.SEXUAL_ACTIVITY) ?: 0
            if (value < 0 || value >= 1 shl 4) {
                throw ValidationException("Invalid sexual activity (was $value)")
            }
        }

        fun validateSteps(cv: ContentValues) {
            val value = cv.getAsLong(RecordColumns.STEPS) ?: -1L
            if (value < 0L) {
                throw ValidationException("Invalid steps count (was $value)")
            }
        }

        fun validateSymptomsPhysical(cv: ContentValues) {
            val value = cv.getAsInteger(RecordColumns.SYMPTOMS_PHYSICAL) ?: 0
            if (value < 0 || value >= 1 shl 9) {
                throw ValidationException("Invalid physical symptoms (was $value)")
            }
        }

        fun validateSymptomsOther(cv: ContentValues) {
            val value = cv.getAsInteger(RecordColumns.SYMPTOMS_OTHER)
            if (value < 0 || value >= 1 shl 8) {
                throw ValidationException("Invalid physical symptoms (was $value)")
            }
        }

        fun validateTime(cv: ContentValues) {
            val value = cv.getAsLong(RecordColumns.TIME) ?: -1L
            val duration = cv.getAsLong(RecordColumns.DURATION) ?: 0L
            if (value < 0L) {
                throw ValidationException("Invalid negative timestamp (was $value)")
            } else if (duration < 0L) {
                throw ValidationException("Invalid negative duration (was $value)")
            }
        }

        fun validateValueNonNeg(cv: ContentValues) {
            val value = cv.getAsDouble(RecordColumns.VALUE) ?: 0.0
            if (value < 0.0) {
                throw ValidationException("Invalid negative value of `value` (was $value)")
            }
        }

        fun validateValuePercent(cv: ContentValues) {
            val value = cv.getAsDouble(RecordColumns.VALUE) ?: 0.0
            if (value < 0.0 || value > 1.0) {
                throw ValidationException("Out-of-range value of `value` (was $value)")
            }
        }
    }
}
