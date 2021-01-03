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

package org.lineageos.mod.health.sdk.util;

/**
 * HealthStore runtime permissions constants
 */
public final class HsRuntimePermission {

    /**
     * Activity records permission.
     *
     * @see org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo
     */
    public static final String ACTIVITY = "lineageos.permission.HEALTH_ACTIVITY";

    /**
     * Body records permission.
     *
     * @see org.lineageos.mod.health.sdk.repo.BodyRecordsRepo
     */
    public static final String BODY = "lineageos.permission.HEALTH_BODY";

    /**
     * Breathing records permission.
     *
     * @see org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo
     */
    public static final String BREATHING = "lineageos.permission.HEALTH_BREATHING";

    /**
     * Heart &amp; blood records permission.
     *
     * @see org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo
     */
    public static final String HEART_BLOOD = "lineageos.permission.HEALTH_HEART_BLOOD";

    /**
     * Mindfulness records permission.
     *
     * @see org.lineageos.mod.health.sdk.repo.MindfulnessRecordsRepo
     */
    public static final String MINDFULNESS = "lineageos.permission.HEALTH_MINDFULNESS";

    /**
     * Medical profile permission.
     *
     * @see org.lineageos.mod.health.sdk.repo.MedicalProfileRepo
     */
    public static final String MEDICAL_PROFILE = "lineageos.permission.HEALTH_MEDICAL_PROFILE";

    private HsRuntimePermission() {
    }
}
