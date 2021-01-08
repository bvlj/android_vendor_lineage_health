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
 * Representation of a mass value in various units.
 */
public final class MassValue implements UnitValue<MassValue> {

    private static final double GRAMS_TO_KILOGRAMS = 0.001;
    private static final double OUNCES_TO_KILOGRAMS = 0.02834952;
    private static final double POUNDS_TO_KILOGRAMS = 0.45359237;

    private final double value;

    @NonNull
    public static MassValue grams(double value) {
        return new MassValue(value * GRAMS_TO_KILOGRAMS);
    }

    @NonNull
    public static MassValue kilograms(double value) {
        return new MassValue(value);
    }

    @NonNull
    public static MassValue ounces(double value) {
        return new MassValue(value * OUNCES_TO_KILOGRAMS);
    }

    @NonNull
    public static MassValue pounds(double value) {
        return new MassValue(value * POUNDS_TO_KILOGRAMS);
    }

    private MassValue(double value) {
        this.value = value;
    }

    public double kilograms() {
        return value;
    }

    public double grams() {
        return value / GRAMS_TO_KILOGRAMS;
    }

    public double ounces() {
        return value / OUNCES_TO_KILOGRAMS;
    }

    public double pounds() {
        return value / POUNDS_TO_KILOGRAMS;
    }

    @NonNull
    @Override
    public MassValue plus(@NonNull MassValue other) {
        return new MassValue(value + other.value);
    }

    @NonNull
    @Override
    public MassValue minus(@NonNull MassValue other) {
        return new MassValue(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MassValue)) return false;
        final MassValue massValue = (MassValue) o;
        return Double.compare(massValue.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
