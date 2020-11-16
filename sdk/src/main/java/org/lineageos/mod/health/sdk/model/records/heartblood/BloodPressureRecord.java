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

import org.lineageos.mod.health.sdk.model.values.MealRelation;
import org.lineageos.mod.health.common.Metric;

@Keep
public final class BloodPressureRecord extends HeartBloodRecord {

    public BloodPressureRecord(long id, long time, long systolic, long diastolic) {
        super(id, Metric.BLOOD_PRESSURE, time, MealRelation.UNKNOWN, systolic, diastolic, 0.0);
    }

    @Override
    public long getSystolic() {
        return super.getSystolic();
    }

    @Override
    public void setSystolic(long systolic) {
        super.setSystolic(systolic);
    }

    @Override
    public long getDiastolic() {
        return super.getDiastolic();
    }

    @Override
    public void setDiastolic(long diastolic) {
        super.setDiastolic(diastolic);
    }
}
