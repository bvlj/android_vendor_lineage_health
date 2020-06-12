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

public final class Metric {

    private Metric() {
    }

    private static final int ACTIVITY = 0;
    private static final int BODY = 1000;
    private static final int BREATHING = 2000;
    private static final int HEARTH_BLOOD = 3000;
    private static final int MINDFULNESS = 4000;

    public static final int UNKNOWN = -1;

    public static final int CYCLING = ACTIVITY + 1;
    public static final int RUNNING = ACTIVITY + 2;
    public static final int STEPS = ACTIVITY + 3;
    public static final int WORKOUT = ACTIVITY + 4;

    public static final int ABDOMINAL_CIRCUMFERENCE = BODY + 1;
    public static final int BODY_MASS_INDEX = BODY + 2;
    public static final int BODY_TEMPERATURE = BODY + 3;
    public static final int LEAN_BODY_MASS = BODY + 4;
    public static final int MENSTRUAL_CYCLE = BODY + 5;
    public static final int UV_INDEX = BODY + 6;
    public static final int WATER_INTAKE = BODY + 7;
    public static final int WEIGHT = BODY + 8;

    public static final int INHALER_USAGE = BREATHING + 1;
    public static final int OXYGEN_SATURATION = BREATHING + 2;
    public static final int PEAK_EXPIRATORY_FLOW = BREATHING + 3;
    public static final int RESPIRATORY_RATE = BREATHING + 4;
    public static final int VITAL_CAPACITY = BREATHING + 5;

    public static final int BLOOD_ALCOHOL_CONCENTRATION =  HEARTH_BLOOD + 1;
    public static final int BLOOD_PRESSURE =  HEARTH_BLOOD + 2;
    public static final int GLUCOSE =  HEARTH_BLOOD + 3;
    public static final int HEART_RATE =  HEARTH_BLOOD + 4;
    public static final int PERFUSION_INDEX =  HEARTH_BLOOD + 5;

    public static final int MEDITATION = MINDFULNESS + 1;
    public static final int MOOD = MINDFULNESS + 2;
    public static final int SLEEP = MINDFULNESS + 3;
}
