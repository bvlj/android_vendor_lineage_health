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
 * Representation of a temperature value in various units.
 */
public final class TemperatureValue implements UnitValue<TemperatureValue> {

    public static final TemperatureValue ZERO = new TemperatureValue(0.0);

    private static final double ABS_ZERO  = 273.15;

    private final double value;

    @NonNull
    public static TemperatureValue celsius(double value) {
        return new TemperatureValue(value);
    }

    @NonNull
    public static TemperatureValue fahrenheit(double value) {
        return new TemperatureValue((value - 32.0) * 5.0 / 9.0);
    }

    @NonNull
    public static TemperatureValue kevin(double value) {
        return new TemperatureValue(value - ABS_ZERO);
    }

    private TemperatureValue(double value) {
        this.value = value;
    }

    public double celsius() {
        return value;
    }

    public double fahrenheit() {
        return (value * 9.0 / 5.0) + 32.0;
    }

    public double kevin() {
        return value + ABS_ZERO;
    }

    @NonNull
    @Override
    public TemperatureValue plus(@NonNull TemperatureValue other) {
        return new TemperatureValue(value + other.value);
    }

    @NonNull
    @Override
    public TemperatureValue minus(@NonNull TemperatureValue other) {
        return new TemperatureValue(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemperatureValue)) return false;
        final TemperatureValue that = (TemperatureValue) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
