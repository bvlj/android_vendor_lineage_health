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

package org.lineageos.mod.health.sdk.model.records.breathing;

import androidx.annotation.Keep;

import org.lineageos.mod.health.common.Metric;

@Keep
public final class VitalCapacityRecord extends BreathingRecord {

    public VitalCapacityRecord(long id, long time, double value) {
        super(id, Metric.VITAL_CAPACITY, time, "", value);
    }

    @Override
    public double getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(double value) {
        super.setValue(value);
    }
}
