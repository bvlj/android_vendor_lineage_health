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

package org.lineageos.mod.health.sdk.model.records.body;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

/**
 * Generic body record with a value of type double
 */
public class BaseBodyRecord extends BodyRecord<Double> {

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public BaseBodyRecord(long id, int metric, long time, @NonNull String notes,
                          int otherSymptoms, int physicalSymptoms, int sexualActivity,
                          double value) {
        super(id, metric, time, notes, otherSymptoms, physicalSymptoms, sexualActivity, value);
    }

    @Override
    protected double valueAsDouble() {
        return value;
    }
}
