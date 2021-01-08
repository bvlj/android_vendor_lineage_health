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

import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.values.MenstrualCycleOtherSymptoms;
import org.lineageos.mod.health.common.values.MenstrualCyclePhysicalSymptoms;
import org.lineageos.mod.health.common.values.SexualActivity;
import org.lineageos.mod.health.sdk.model.values.LengthValue;

/**
 * Abdominal circumference record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Double} value: abdominal circumference value (default to cm)</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Abdominal_obesity">More info</a>
 *
 * @see Metric#ABDOMINAL_CIRCUMFERENCE
 */
@Keep
public final class AbdominalCircumferenceRecord extends BodyRecord<LengthValue> {

    public AbdominalCircumferenceRecord(long id, long time, @NonNull LengthValue value) {
        super(id, Metric.ABDOMINAL_CIRCUMFERENCE, time, "",
                MenstrualCycleOtherSymptoms.NONE, MenstrualCyclePhysicalSymptoms.NONE,
                SexualActivity.NONE, value);
    }

    @Override
    protected double valueAsDouble() {
        return value.centimeters();
    }
}
