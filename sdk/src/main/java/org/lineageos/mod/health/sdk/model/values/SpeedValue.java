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

public final class SpeedValue implements UnitValue<SpeedValue> {

    public static final SpeedValue ZERO = new SpeedValue(0.0);

    private static final double M_S_TO_KM_H = 3.6;
    private static final double MS_TO_MP_H = 2.237;

    @NonNull
    public static SpeedValue kilometersPerHour(double value) {
        return new SpeedValue(value / M_S_TO_KM_H);
    }

    @NonNull
    public static SpeedValue metersPerSecond(double value) {
        return new SpeedValue(value);
    }

    @NonNull
    public static SpeedValue milesPerHour(double value) {
        return new SpeedValue(value / MS_TO_MP_H);
    }

    private final double value;

    private SpeedValue(double value) {
        this.value = value;
    }

    public double kilometersPerHour() {
        return value * M_S_TO_KM_H;
    }

    public double metersPerSecond() {
        return value;
    }

    public double milesPerHour() {
        return value * MS_TO_MP_H;
    }

    @NonNull
    @Override
    public SpeedValue plus(@NonNull SpeedValue other) {
        return new SpeedValue(value + other.value);
    }

    @NonNull
    @Override
    public SpeedValue minus(@NonNull SpeedValue other) {
        return new SpeedValue(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpeedValue)) return false;
        final SpeedValue that = (SpeedValue) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
