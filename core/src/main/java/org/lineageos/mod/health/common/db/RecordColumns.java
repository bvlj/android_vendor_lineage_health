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

package org.lineageos.mod.health.common.db;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import org.lineageos.mod.health.HealthStore.Version;
import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.values.*;

/**
 * Record table columns.
 * <br>
 * Please always refer to the {@link Metric} documentation.
 *
 * @see Metric
 */
public interface RecordColumns extends BaseColumns {

    /**
     * The metric id of this record.
     *
     * @see Metric
     */
    @NonNull
    String _METRIC = "_metric";

    /**
     * @see Version
     */
    @NonNull
    String _VERSION = "_version";

    /**
     * Average speed in kilometers per hour (Km/h).
     *
     * @see Metric#CYCLING
     * @see Metric#RUNNING
     */
    @NonNull
    String AVG_SPEED = "avg_speed";

    /**
     * Calories count in calories (cal).
     *
     * @see Metric#WORKOUT
     */
    @NonNull
    String CALORIES = "calories";

    /**
     * Distance in kilometers (km).
     *
     * @see Metric#CYCLING
     * @see Metric#RUNNING
     */
    @NonNull
    String DISTANCE = "distance";

    /**
     * Elevation gain in meters (m).
     *
     * @see Metric#CYCLING
     */
    @NonNull
    String ELEVATION_GAIN = "elevation_gain";

    /**
     * Duration in milliseconds (ms).
     *
     * @see Metric#CYCLING
     * @see Metric#RUNNING
     * @see Metric#WALKING
     * @see Metric#WORKOUT
     * @see Metric#MEDITATION
     * @see Metric#SLEEP
     */
    @NonNull
    String DURATION = "duration";

    /**
     * Systolic pressure in millimeters of mercury (mmHg).
     *
     * @see Metric#BLOOD_PRESSURE
     */
    @NonNull
    String PRESSURE_SYSTOLIC = "pressure_systolic";

    /**
     * Diastolic pressure in millimeters of mercury (mmHg).
     *
     * @see Metric#BLOOD_PRESSURE
     */
    @NonNull
    String PRESSURE_DIASTOLIC = "pressure_diastolic";

    /**
     * Meal relation.
     *
     * One of:
     * <ul>
     *     <li>{@link MealRelation#UNKNOWN}</li>
     *     <li>{@link MealRelation#BEFORE}</li>
     *     <li>{@link MealRelation#AFTER}</li>
     * </ul>
     *
     * @see MealRelation
     * @see Metric#GLUCOSE
     */
    @NonNull
    String MEAL_RELATION = "meal_relation";

    /**
     * Mood.
     *
     * Flag value of:
     * <ul>
     *     <li>{@link MoodLevel#UNKNOWN}</li>
     *     <li>{@link MoodLevel#AMAZING}</li>
     *     <li>{@link MoodLevel#HAPPY}</li>
     *     <li>{@link MoodLevel#EXCITED}</li>
     *     <li>{@link MoodLevel#STRESSED}</li>
     *     <li>{@link MoodLevel#TIRED}</li>
     *     <li>{@link MoodLevel#SAD}</li>
     *     <li>{@link MoodLevel#SICK}</li>
     *     <li>{@link MoodLevel#EXHAUSTED}</li>
     *     <li>{@link MoodLevel#NERVOUS}</li>
     *     <li>{@link MoodLevel#ANGRY}</li>
     * </ul>
     *
     * @see MoodLevel
     * @see Metric#MOOD
     */
    @NonNull
    String MOOD = "mood";

    /**
     * Notes about the record.
     *
     * @see Metric#WORKOUT
     * @see Metric#WATER_INTAKE
     * @see Metric#INHALER_USAGE
     * @see Metric#MOOD
     */
    @NonNull
    String NOTES = "notes";

    /**
     * Sexual activity.
     *
     * Flag value of:
     * <ul>
     *     <li>{@link SexualActivity#NONE}</li>
     *     <li>{@link SexualActivity#MASTURBATION}</li>
     *     <li>{@link SexualActivity#NO_SEX}</li>
     *     <li>{@link SexualActivity#PROTECTED_SEX}</li>
     *     <li>{@link SexualActivity#SEX}</li>
     * </ul>
     *
     * @see SexualActivity
     * @see Metric#MENSTRUAL_CYCLE
     */
    @NonNull
    String SEXUAL_ACTIVITY = "sexual_activity";

    /**
     * Number of steps.
     *
     * @see Metric#WALKING
     */
    @NonNull
    String STEPS = "steps";

    /**
     * Other menstrual cycle symptoms.
     *
     * Flag value of:
     * <ul>
     *     <li>{@link MenstrualCycleOtherSymptoms#NONE}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#ANXIETY}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#CRYING_SPELLS}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#DEPRESSION}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#HIGH_SEX_DRIVE}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#INSOMNIA}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#MOOD_SWINGS}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#POOR_CONCENTRATION}</li>
     *     <li>{@link MenstrualCycleOtherSymptoms#SOCIAL_WITHDRAWAL}</li>
     * </ul>
     *
     * @see MenstrualCycleOtherSymptoms
     * @see Metric#MENSTRUAL_CYCLE
     */
    @NonNull
    String SYMPTOMS_OTHER = "symptoms_other";

    /**
     * Physical menstrual cycle symptoms.
     *
     * Flag value of:
     * <ul>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#NONE}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#ACNE}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#BLOATING}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#CRAMPS}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#CONSTIPATION}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#FATIGUE}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#HEADACHE}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#JOINT_MUSCLE_PAIN}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#SPOTTING}</li>
     *     <li>{@link MenstrualCyclePhysicalSymptoms#TENDER_BREASTS}</li>
     * </ul>
     *
     * @see MenstrualCyclePhysicalSymptoms
     * @see Metric#MENSTRUAL_CYCLE
     */
    @NonNull
    String SYMPTOMS_PHYSICAL = "symptoms_physical";

    /**
     * Time in milliseconds since epoch.
     *
     * @see System#currentTimeMillis()
     * @see android.os.SystemClock
     */
    @NonNull
    String TIME = "time";

    /**
     * Numerical value.
     *
     * Please refer to each metric documentation
     * for specific information about the format
     * of the value.
     *
     * @see Metric#ABDOMINAL_CIRCUMFERENCE
     * @see Metric#BODY_MASS_INDEX
     * @see Metric#BODY_TEMPERATURE
     * @see Metric#LEAN_BODY_MASS
     * @see Metric#MENSTRUAL_CYCLE
     * @see Metric#UV_INDEX
     * @see Metric#WATER_INTAKE
     * @see Metric#WEIGHT
     * @see Metric#OXYGEN_SATURATION
     * @see Metric#PEAK_EXPIRATORY_FLOW
     * @see Metric#RESPIRATORY_RATE
     * @see Metric#VITAL_CAPACITY
     * @see Metric#BLOOD_ALCOHOL_CONCENTRATION
     * @see Metric#GLUCOSE
     * @see Metric#HEART_RATE
     * @see Metric#PERFUSION_INDEX
     */
    @NonNull
    String VALUE = "value";
}
