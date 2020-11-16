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

import org.lineageos.mod.health.sdk.model.values.MenstrualCycleOtherSymptoms;
import org.lineageos.mod.health.sdk.model.values.MenstrualCyclePhysicalSymptoms;
import org.lineageos.mod.health.sdk.model.values.SexualActivity;
import org.lineageos.mod.health.common.Metric;

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

    @Override
    @MenstrualCycleOtherSymptoms.Value
    public int getOtherSymptoms() {
        return super.getOtherSymptoms();
    }

    @Override
    public void setOtherSymptoms(@MenstrualCycleOtherSymptoms.Value int otherSymptoms) {
        super.setOtherSymptoms(otherSymptoms);
    }

    @Override
    @MenstrualCyclePhysicalSymptoms.Value
    public int getPhysicalSymptoms() {
        return super.getPhysicalSymptoms();
    }

    @Override
    public void setPhysicalSymptoms(@MenstrualCyclePhysicalSymptoms.Value int physicalSymptoms) {
        super.setPhysicalSymptoms(physicalSymptoms);
    }

    @Override
    @SexualActivity.Value
    public int getSexualActivity() {
        return super.getSexualActivity();
    }

    @Override
    public void setSexualActivity(@SexualActivity.Value int sexualActivity) {
        super.setSexualActivity(sexualActivity);
    }
}
