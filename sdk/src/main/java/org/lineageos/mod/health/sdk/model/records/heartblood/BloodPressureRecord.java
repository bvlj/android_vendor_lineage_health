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

package org.lineageos.mod.health.sdk.model.records.heartblood;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.values.MealRelation;
import org.lineageos.mod.health.sdk.model.values.PressureValue;

/**
 * Blood pressure record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Integer} systolic: systolic pressure</li>
 *     <li>{@link Integer} diastolic: diastolic pressure</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Blood_pressure">More info</a>
 *
 * @see Metric#BLOOD_PRESSURE
 */
@Keep
public final class BloodPressureRecord extends HeartBloodRecord<Object> {

    public BloodPressureRecord(long id, long time,
                               @NonNull PressureValue systolic,
                               @NonNull PressureValue diastolic) {
        super(id, Metric.BLOOD_PRESSURE, time, MealRelation.UNKNOWN,
                systolic, diastolic, new Object());
    }

    @NonNull
    @Override
    public PressureValue getSystolic() {
        return super.getSystolic();
    }

    @Override
    public void setSystolic(@NonNull PressureValue systolic) {
        super.setSystolic(systolic);
    }

    @NonNull
    @Override
    public PressureValue getDiastolic() {
        return super.getDiastolic();
    }

    @Override
    public void setDiastolic(@NonNull PressureValue diastolic) {
        super.setDiastolic(diastolic);
    }

    @Override
    protected double valueAsDouble() {
        return 0.0;
    }
}
