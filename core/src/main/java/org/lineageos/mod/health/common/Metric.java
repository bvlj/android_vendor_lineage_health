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

package org.lineageos.mod.health.common;

import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.common.values.MealRelation;
import org.lineageos.mod.health.common.values.MenstrualCycleOtherSymptoms;
import org.lineageos.mod.health.common.values.MenstrualCyclePhysicalSymptoms;
import org.lineageos.mod.health.common.values.MoodLevel;
import org.lineageos.mod.health.common.values.SexualActivity;

/**
 * Numerical identifiers for each metric and information about each metric's fields.
 *
 * <br>
 * Please always refer to each metric documentation, especially if you're
 * not using the official SDK library.
 * <br>
 * The HealthStore Mod is built with data protection mechanisms that might
 * restrict the data your app may be able to push, in particular:
 *
 * <ol>
 *     <li>
 *         Always check the expected units of the data that's being pushed
 *         (Usually the international system of units (SI) picks are respected).
 *         Refer to each metric documentation for specific information. <br>
 *         This is important to keep data from multiple sources comparable to the user.
 *     </li>
 *     <li>
 *         Always check the possible flag values as stated in the metric documentation.
 *     </li>
 *     <li>
 *         Invalid data (eg. speed higher than 2.99e8 km/s or invalid flag values) will be
 *         rejected and overwritten with a default value (eg. 0).
 *     </li>
 *     <li>
 *         The access control may prevent one or more specific apps to access one or more
 *         specific metrics. <br>
 *         Apps that have been restricted from reading will always obtain empty lists for
 *         "list queries" or null results for "entry queries".
 *         Apps that have been restricted from writing will always obtain a failure in
 *         insert and update operations. <br>
 *     </li>
 * </ol>
 */
public final class Metric {

    private Metric() {
    }

    private static final int ACTIVITY = 0;
    private static final int BODY = 1000;
    private static final int BREATHING = 2000;
    private static final int HEARTH_BLOOD = 3000;
    private static final int MINDFULNESS = 4000;

    /**
     * Unknown metric
     */
    public static final int UNKNOWN = -1;

    /* ***** Activity metrics ***** */

    /**
     * Cycling metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Long} {@link RecordColumns#DURATION}: duration in milliseconds</li>
     *     <li>{@link Double} {@link RecordColumns#DISTANCE}: distance in kilometers</li>
     *     <li>{@link Double} {@link RecordColumns#ELEVATION_GAIN}: elevation gain in meters</li>
     *     <li>{@link Double} {@link RecordColumns#AVG_SPEED}: average speed in kilometers per hour</li>
     * </ul>
     */
    public static final int CYCLING = ACTIVITY + 1;

    /**
     * Running metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Long} {@link RecordColumns#DURATION}: duration in milliseconds</li>
     *     <li>{@link Double} {@link RecordColumns#DISTANCE}: distance in kilometers</li>
     *     <li>{@link Double} {@link RecordColumns#AVG_SPEED}: average speed in kilometers per hour</li>
     * </ul>
     */
    public static final int RUNNING = ACTIVITY + 2;

    /**
     * Walking metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Long} {@link RecordColumns#DURATION}: duration in milliseconds</li>
     *     <li>{@link Double} {@link RecordColumns#DISTANCE}: distance in kilometers</li>
     *     <li>{@link Double} {@link RecordColumns#STEPS}: number of steps</li>
     * </ul>
     */
    public static final int WALKING = ACTIVITY + 3;

    /**
     * Workout metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Long} {@link RecordColumns#DURATION}: duration in milliseconds</li>
     *     <li>{@link Integer} {@link RecordColumns#CALORIES}: burned calories in cal</li>
     *     <li>{@link String} {@link RecordColumns#NOTES}: workout notes</li>
     * </ul>
     */
    public static final int WORKOUT = ACTIVITY + 4;

    /* ***** Activity metrics ***** */

    /**
     * Abdominal circumference metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value in cm</li>
     * </ul>
     */
    public static final int ABDOMINAL_CIRCUMFERENCE = BODY + 1;

    /**
     * Body Mass Index metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value (BMI)</li>
     * </ul>
     */
    public static final int BODY_MASS_INDEX = BODY + 2;

    /**
     * Body temperature metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value in Celsius degrees (ºC)</li>
     * </ul>
     */
    public static final int BODY_TEMPERATURE = BODY + 3;

    /**
     * Lean body mass metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: percent value (range 0.00 .. 1.00)</li>
     * </ul>
     */
    public static final int LEAN_BODY_MASS = BODY + 4;

    /**
     * Menstrual cycle metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Integer} {@link RecordColumns#SEXUAL_ACTIVITY}: {@link SexualActivity}</li>
     *     <li>{@link Integer} {@link RecordColumns#SYMPTOMS_PHYSICAL}: {@link MenstrualCyclePhysicalSymptoms}</li>
     *     <li>{@link Integer} {@link RecordColumns#SYMPTOMS_OTHER}: {@link MenstrualCycleOtherSymptoms}</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: flow</li>
     * </ul>
     *
     * @see MenstrualCycleOtherSymptoms
     * @see MenstrualCyclePhysicalSymptoms
     * @see SexualActivity
     */
    public static final int MENSTRUAL_CYCLE = BODY + 5;

    /**
     * Uv index metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value</li>
     * </ul>
     */
    public static final int UV_INDEX = BODY + 6;

    /**
     * Water intake metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: drunk glasses of water</li>
     *     <li>{@link String} {@link RecordColumns#NOTES}: notes</li>
     * </ul>
     */
    public static final int WATER_INTAKE = BODY + 7;

    /**
     * Weight metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: mass in kilograms (Kg)</li>
     * </ul>
     */
    public static final int WEIGHT = BODY + 8;

    /* ***** Activity metrics ***** */

    /**
     * Inhaler usage metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link String} {@link RecordColumns#NOTES}: notes</li>
     * </ul>
     */
    public static final int INHALER_USAGE = BREATHING + 1;

    /**
     * Oxygen saturation metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: percent value (range 0.00 .. 1.00)</li>
     * </ul>
     */
    public static final int OXYGEN_SATURATION = BREATHING + 2;

    /**
     * Peak expiratory flow metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value in liter per minute (L/min)</li>
     * </ul>
     */
    public static final int PEAK_EXPIRATORY_FLOW = BREATHING + 3;

    /**
     * Respiratory rate metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value in breaths per minute</li>
     * </ul>
     */
    public static final int RESPIRATORY_RATE = BREATHING + 4;

    /**
     * Vital capacity metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value</li>
     * </ul>
     */
    public static final int VITAL_CAPACITY = BREATHING + 5;

    /* ***** Activity metrics ***** */

    /**
     * Blood alcohol metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: percent value (range 0.00 .. 1.00)</li>
     * </ul>
     */
    public static final int BLOOD_ALCOHOL_CONCENTRATION = HEARTH_BLOOD + 1;

    /**
     * Blood pressure metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Integer} {@link RecordColumns#PRESSURE_SYSTOLIC}: systolic pressure in millimeters of mercury (mmHg)</li>
     *     <li>{@link Integer} {@link RecordColumns#PRESSURE_DIASTOLIC}: diastolic pressure in millimeters of mercury (mmHg)</li>
     * </ul>
     */
    public static final int BLOOD_PRESSURE = HEARTH_BLOOD + 2;

    /**
     * Glucose metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#MEAL_RELATION}: {@link MealRelation}</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value in milligrams per deciliter (mg/dL)</li>
     * </ul>
     *
     * @see MealRelation
     */
    public static final int GLUCOSE = HEARTH_BLOOD + 3;

    /**
     * Heart rate metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: value in beats per minute (BPM)</li>
     * </ul>
     */
    public static final int HEART_RATE = HEARTH_BLOOD + 4;

    /**
     * Perfusion index metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Double} {@link RecordColumns#VALUE}: percent value (range 0.00 .. 1.00)</li>
     * </ul>
     */
    public static final int PERFUSION_INDEX = HEARTH_BLOOD + 5;

    /* ***** Activity metrics ***** */

    /**
     * Meditation metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Long} {@link RecordColumns#DURATION}: duration in milliseconds</li>
     * </ul>
     */
    public static final int MEDITATION = MINDFULNESS + 1;

    /**
     * Mood metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Integer} {@link RecordColumns#MOOD}: {@link MoodLevel}</li>
     *     <li>{@link String} {@link RecordColumns#NOTES}: notes</li>
     * </ul>
     *
     * @see MoodLevel
     */
    public static final int MOOD = MINDFULNESS + 2;

    /**
     * Sleep metric.
     *
     * <ul>
     *     <li>{@link Long} {@link RecordColumns#_ID}: db identifier (default to <code>0L</code>)</li>
     *     <li>{@link Long} {@link RecordColumns#TIME}: timestamp ({@link System#currentTimeMillis()})</li>
     *     <li>{@link Long} {@link RecordColumns#DURATION}: duration in milliseconds</li>
     * </ul>
     */
    public static final int SLEEP = MINDFULNESS + 3;
}
