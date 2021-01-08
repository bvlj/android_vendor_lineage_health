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

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.lineageos.mod.health.HealthStore;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.common.values.MealRelation;
import org.lineageos.mod.health.common.values.annotations.HeartBloodMetric;
import org.lineageos.mod.health.sdk.model.records.Record;
import org.lineageos.mod.health.sdk.model.values.PressureValue;

import java.util.Objects;

/**
 * Generic Heart &amp; Blood record.
 *
 * @see HeartBloodMetric
 * @see BloodAlcoholConcentrationRecord
 * @see BloodPressureRecord
 * @see GlucoseRecord
 * @see HeartRateRecord
 * @see PerfusionIndexRecord
 */
@Keep
public abstract class HeartBloodRecord<T> extends Record {

    @MealRelation.Value
    private int mealRelation;
    @NonNull
    private PressureValue systolic;
    @NonNull
    private PressureValue diastolic;
    @NonNull
    protected T value;

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public HeartBloodRecord(long id, @HeartBloodMetric int metric, long time,
                            @MealRelation.Value int mealRelation,
                            @NonNull PressureValue systolic,
                            @NonNull PressureValue diastolic,
                            @NonNull T value) {
        super(id, metric, time);
        this.mealRelation = mealRelation;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.value = value;
    }

    @MealRelation.Value
    protected int getMealRelation() {
        return mealRelation;
    }

    protected void setMealRelation(@MealRelation.Value int mealRelation) {
        this.mealRelation = mealRelation;
    }

    @NonNull
    protected PressureValue getSystolic() {
        return systolic;
    }

    protected void setSystolic(@NonNull PressureValue systolic) {
        this.systolic = systolic;
    }

    @NonNull
    protected PressureValue getDiastolic() {
        return diastolic;
    }

    protected void setDiastolic(@NonNull PressureValue diastolic) {
        this.diastolic = diastolic;
    }

    @NonNull
    protected T getValue() {
        return value;
    }

    protected void setValue(@NonNull T value) {
        this.value = value;
    }

    protected abstract double valueAsDouble();

    @NonNull
    @Override
    public final ContentValues toContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(RecordColumns._VERSION, HealthStore.Version.CURRENT);
        cv.put(RecordColumns._ID, id);
        cv.put(RecordColumns._METRIC, metric);
        cv.put(RecordColumns.TIME, time);
        cv.put(RecordColumns.MEAL_RELATION, mealRelation);
        cv.put(RecordColumns.PRESSURE_SYSTOLIC, systolic.mmHg());
        cv.put(RecordColumns.PRESSURE_DIASTOLIC, diastolic.mmHg());
        cv.put(RecordColumns.VALUE, valueAsDouble());
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeartBloodRecord)) return false;
        if (!super.equals(o)) return false;
        final HeartBloodRecord<?> that = (HeartBloodRecord<?>) o;
        return mealRelation == that.mealRelation &&
                systolic.equals(that.systolic) &&
                diastolic.equals(that.diastolic) &&
                Double.compare(that.valueAsDouble(), valueAsDouble()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mealRelation, systolic, diastolic, valueAsDouble());
    }
}
