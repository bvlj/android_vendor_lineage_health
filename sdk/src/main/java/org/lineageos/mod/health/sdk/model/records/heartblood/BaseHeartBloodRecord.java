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

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.lineageos.mod.health.common.values.MealRelation;
import org.lineageos.mod.health.common.values.annotations.HeartBloodMetric;
import org.lineageos.mod.health.sdk.model.values.PressureValue;

/**
 * Generic heart and blood record with a value of type double
 */
public class BaseHeartBloodRecord extends HeartBloodRecord<Double> {

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public BaseHeartBloodRecord(long id,
                                @HeartBloodMetric int metric,
                                long time,
                                @MealRelation.Value int mealRelation,
                                @NonNull PressureValue systolic,
                                @NonNull PressureValue diastolic,
                                double value) {
        super(id, metric, time, mealRelation, systolic, diastolic, value);
    }


    @Override
    protected double valueAsDouble() {
        return value;
    }
}
