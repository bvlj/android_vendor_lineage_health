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
 * Heart rate record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Double} value: beats per minute (BPM)</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Heart_rate">More info</a>
 *
 * @see Metric#HEART_RATE
 */
@Keep
public final class HeartRateRecord extends BaseHeartBloodRecord {

    public HeartRateRecord(long id, long time, double value) {
        super(id, Metric.HEART_RATE, time,
                MealRelation.UNKNOWN, PressureValue.ZERO, PressureValue.ZERO, value);
    }

    /**
     * @return Beats per minute (BPM)
     */
    @NonNull
    @Override
    public Double getValue() {
        return super.getValue();
    }

    /**
     * @param value Beats per minute (BPM)
     */
    @Override
    public void setValue(@NonNull Double value) {
        super.setValue(value);
    }
}
