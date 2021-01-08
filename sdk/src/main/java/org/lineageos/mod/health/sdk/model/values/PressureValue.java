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
 * Representation of a pressure value in various units.
 */
public final class PressureValue implements UnitValue<PressureValue> {

    public static final PressureValue ZERO = new PressureValue(0.0);

    private static final double ATMOSPHERES_TO_PASCALS = 101325.0;
    private static final double BARS_TO_PASCALS = 100000;
    private static final double IN_HG_TO_PASCALS = 3386.39;
    private static final double MM_HG_TO_PASCALS = 133.322;

    private final double value;

    @NonNull
    public static PressureValue atmospheres(double value) {
        return new PressureValue(value * ATMOSPHERES_TO_PASCALS);
    }

    @NonNull
    public static PressureValue bars(double value) {
        return new PressureValue(value * BARS_TO_PASCALS);
    }

    @NonNull
    public static PressureValue inHg(double value) {
        return new PressureValue(value * IN_HG_TO_PASCALS);
    }

    @NonNull
    public static PressureValue mmHg(double value) {
        return new PressureValue(value * MM_HG_TO_PASCALS);
    }

    @NonNull
    public static PressureValue pascals(double value) {
        return new PressureValue(value);
    }

    private PressureValue(double value) {
        this.value = value;
    }

    public double atmospheres() {
        return value / ATMOSPHERES_TO_PASCALS;
    }

    public double bars() {
        return value / BARS_TO_PASCALS;
    }

    public double inHg() {
        return value / IN_HG_TO_PASCALS;
    }

    public double mmHg() {
        return value / MM_HG_TO_PASCALS;
    }

    public double pascals() {
        return value;
    }

    @NonNull
    @Override
    public PressureValue plus(@NonNull PressureValue other) {
        return new PressureValue(value + other.value);
    }

    @NonNull
    @Override
    public PressureValue minus(@NonNull PressureValue other) {
        return new PressureValue(value - other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PressureValue)) return false;
        final PressureValue that = (PressureValue) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
