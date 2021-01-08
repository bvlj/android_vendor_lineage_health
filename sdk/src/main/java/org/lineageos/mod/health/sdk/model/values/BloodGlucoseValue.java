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

package org.lineageos.mod.health.sdk.model.values;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Representation of a blood glucose concentration reading value in various units.
 */
public final class BloodGlucoseValue implements UnitValue<BloodGlucoseValue> {

    private static final double MMOL_L_TO_MG_DL = 18.0;

    private final long value;

    @NonNull
    public static BloodGlucoseValue mgdL(long value) {
        return new BloodGlucoseValue(value);
    }

    @NonNull
    public static BloodGlucoseValue mmolL(double value) {
        return new BloodGlucoseValue(Math.round(value * MMOL_L_TO_MG_DL));
    }

    private BloodGlucoseValue(long value) {
        this.value = value;
    }

    public long mgdL() {
        return value;
    }

    public double mmolL() {
        return value / MMOL_L_TO_MG_DL;
    }

    @NonNull
    @Override
    public BloodGlucoseValue plus(@NonNull BloodGlucoseValue other) {
        return new BloodGlucoseValue(value + other.value);
    }

    @NonNull
    @Override
    public BloodGlucoseValue minus(@NonNull BloodGlucoseValue other) {
        return new BloodGlucoseValue(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BloodGlucoseValue)) return false;
        final BloodGlucoseValue that = (BloodGlucoseValue) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
