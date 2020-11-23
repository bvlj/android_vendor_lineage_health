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

/**
 * Vital capacity record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Double} value: vital capacity in centimeters cube (cm^3)</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Vital_capacity">More info</a>
 *
 * @see Metric#VITAL_CAPACITY
 */
@Keep
public final class VitalCapacityRecord extends BreathingRecord {

    public VitalCapacityRecord(long id, long time, double value) {
        super(id, Metric.VITAL_CAPACITY, time, "", value);
    }

    /**
     * @return Vital capacity in centimeters cube (cm^3)
     */
    @Override
    public double getValue() {
        return super.getValue();
    }

    /**
     * @param value Vital capacity in centimeters cube (cm^3)
     */
    @Override
    public void setValue(double value) {
        super.setValue(value);
    }
}
