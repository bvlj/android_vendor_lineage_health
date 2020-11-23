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

import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.values.MenstrualCycleOtherSymptoms;
import org.lineageos.mod.health.common.values.MenstrualCyclePhysicalSymptoms;
import org.lineageos.mod.health.common.values.SexualActivity;

/**
 * Menustral cycle record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Integer} sexualActivity: {@link SexualActivity}</li>
 *     <li>{@link Integer} physicalSymptoms: {@link MenstrualCyclePhysicalSymptoms}</li>
 *     <li>{@link Integer} otherSymptoms: {@link MenstrualCycleOtherSymptoms}</li>
 *     <li>{@link Double} value: menstrual flow</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Menstrual_cycle">More info</a>
 *
 * @see Metric#MENSTRUAL_CYCLE
 */
@Keep
public final class MenstrualCycleRecord extends BodyRecord {

    public MenstrualCycleRecord(long id, long time,
                                @MenstrualCycleOtherSymptoms.Value int otherSymptoms,
                                @MenstrualCyclePhysicalSymptoms.Value int physicalSymptoms,
                                @SexualActivity.Value int sexualActivity,
                                double value) {
        super(id, Metric.MENSTRUAL_CYCLE, time, "", otherSymptoms, physicalSymptoms,
                sexualActivity, value);
    }

    /**
     * @see MenstrualCycleOtherSymptoms
     */
    @Override
    @MenstrualCycleOtherSymptoms.Value
    public int getOtherSymptoms() {
        return super.getOtherSymptoms();
    }

    /**
     * @see MenstrualCycleOtherSymptoms
     */
    @Override
    public void setOtherSymptoms(@MenstrualCycleOtherSymptoms.Value int otherSymptoms) {
        super.setOtherSymptoms(otherSymptoms);
    }

    /**
     * @see MenstrualCyclePhysicalSymptoms
     */
    @Override
    @MenstrualCyclePhysicalSymptoms.Value
    public int getPhysicalSymptoms() {
        return super.getPhysicalSymptoms();
    }

    /**
     * @see MenstrualCyclePhysicalSymptoms
     */
    @Override
    public void setPhysicalSymptoms(@MenstrualCyclePhysicalSymptoms.Value int physicalSymptoms) {
        super.setPhysicalSymptoms(physicalSymptoms);
    }

    /**
     * @see SexualActivity
     */
    @Override
    @SexualActivity.Value
    public int getSexualActivity() {
        return super.getSexualActivity();
    }

    /**
     * @see SexualActivity
     */
    @Override
    public void setSexualActivity(@SexualActivity.Value int sexualActivity) {
        super.setSexualActivity(sexualActivity);
    }

    /**
     * @return Menstrual flow
     */
    @Override
    public double getValue() {
        return super.getValue();
    }

    /**
     * @param value Menstrual flow
     */
    @Override
    public void setValue(double value) {
        super.setValue(value);
    }
}
