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

package org.lineageos.mod.health.sdk.model.records.body;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.values.MenstrualCycleOtherSymptoms;
import org.lineageos.mod.health.common.values.MenstrualCyclePhysicalSymptoms;
import org.lineageos.mod.health.common.values.SexualActivity;
import org.lineageos.mod.health.common.Metric;

/**
 * Water intake record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Double} value: number of drank glasses of water</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Drinking_water">More info</a>
 *
 * @see Metric#WATER_INTAKE
 */
@Keep
public final class WaterIntakeRecord extends BaseBodyRecord {

    public WaterIntakeRecord(long id, long time, @NonNull String notes, double value) {
        super(id, Metric.WATER_INTAKE, time, notes,
                MenstrualCycleOtherSymptoms.NONE, MenstrualCyclePhysicalSymptoms.NONE,
                SexualActivity.NONE, value);
    }

    @NonNull
    @Override
    public String getNotes() {
        return super.getNotes();
    }

    @Override
    public void setNotes(@NonNull String notes) {
        super.setNotes(notes);
    }

    /**
     * @return Number of drank glasses of water
     */
    @NonNull
    @Override
    public Double getValue() {
        return super.getValue();
    }

    /**
     * @param value Number of drank glasses of water
     */
    @Override
    public void setValue(@NonNull Double value) {
        super.setValue(value);
    }
}
