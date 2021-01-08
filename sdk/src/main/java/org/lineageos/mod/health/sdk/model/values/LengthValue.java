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
 * Representation of a length value in various units.
 */
public final class LengthValue implements UnitValue<LengthValue> {

    public static final LengthValue ZERO = new LengthValue(0.0);

    private static final double CENTIMETERS_TO_METERS = 0.01;
    private static final double FEET_TO_METERS = 0.3048;
    private static final double INCH_TO_METERS = 0.0254;
    private static final double KILOMETERS_TO_METERS = 1000.0;
    private static final double MILES_TO_METERS = 1609.344;
    private static final double MILLIMETERS_TO_METERS = 0.001;
    private static final double YARDS_TO_METERS = 0.9144;

    private final double value;

    @NonNull
    public static LengthValue centimeters(double value) {
        return new LengthValue(value * CENTIMETERS_TO_METERS);
    }

    @NonNull
    public static LengthValue feet(double value) {
        return new LengthValue(value * FEET_TO_METERS);
    }

    @NonNull
    public static LengthValue inches(double value) {
        return new LengthValue(value * INCH_TO_METERS);
    }

    @NonNull
    public static LengthValue kilometers(double value) {
        return new LengthValue(value * KILOMETERS_TO_METERS);
    }

    @NonNull
    public static LengthValue meters(double value) {
        return new LengthValue(value);
    }

    @NonNull
    public static LengthValue miles(double value) {
        return new LengthValue(value * MILES_TO_METERS);
    }

    @NonNull
    public static LengthValue millimeters(double value) {
        return new LengthValue(value * MILLIMETERS_TO_METERS);
    }

    @NonNull
    public static LengthValue yards(double value) {
        return new LengthValue(value * YARDS_TO_METERS);
    }

    private LengthValue(double value) {
        this.value = value;
    }

    public double centimeters() {
        return value / CENTIMETERS_TO_METERS;
    }

    public double feet() {
        return value / FEET_TO_METERS;
    }

    public double kilometers() {
        return value / KILOMETERS_TO_METERS;
    }

    public double inches() {
        return value / INCH_TO_METERS;
    }

    public double meters() {
        return value;
    }

    public double miles() {
        return value / MILES_TO_METERS;
    }

    public double millimeters() {
        return value / MILLIMETERS_TO_METERS;
    }

    public double yards() {
        return value / YARDS_TO_METERS;
    }

    @NonNull
    @Override
    public LengthValue plus(@NonNull LengthValue other) {
        return new LengthValue(value + other.value);
    }

    @NonNull
    @Override
    public LengthValue minus(@NonNull LengthValue other) {
        return new LengthValue(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LengthValue)) return false;
        final LengthValue that = (LengthValue) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
