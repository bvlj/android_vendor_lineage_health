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

public interface RecordColumns extends BaseColumns {

    @NonNull
    String _METRIC = "_metric";

    @NonNull
    String AVG_SPEED = "avg_speed";

    @NonNull
    String BEFORE_MEAL = "before_meal";

    @NonNull
    String CALORIES = "calories";

    @NonNull
    String DISTANCE = "distance";

    @NonNull
    String ELEVATION_GAIN = "elevation_gain";

    @NonNull
    String DURATION = "duration";

    @NonNull
    String PRESSURE_SYSTOLIC = "pressure_systolic";

    @NonNull
    String PRESSURE_DIASTOLIC = "pressure_diastolic";

    @NonNull
    String MOOD = "mood";

    @NonNull
    String NOTES = "notes";

    @NonNull
    String SEXUAL_ACTIVITY = "sexual_activity";

    @NonNull
    String STEPS = "steps";

    @NonNull
    String SYMPTOMS_OTHER = "symptoms_other";

    @NonNull
    String SYMPTOMS_PHYSICAL = "symptoms_physical";

    @NonNull
    String TIME = "time";

    @NonNull
    String VALUE = "value";
}
