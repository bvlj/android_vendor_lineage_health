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

import org.lineageos.mod.health.sdk.model.values.MealRelation;
import org.lineageos.mod.health.sdk.model.records.Record;
import org.lineageos.mod.health.common.db.RecordColumns;

import java.util.Objects;

@Keep
@SuppressWarnings("unused")
public class HeartBloodRecord extends Record {

    @MealRelation.Value
    private int beforeMeal;
    private long systolic;
    private long diastolic;
    private double value;

    public HeartBloodRecord(long id, int metric, long time,
                            @MealRelation.Value int beforeMeal,
                            long systolic, long diastolic,
                            double value) {
        super(id, metric, time);
        this.beforeMeal = beforeMeal;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.value = value;
    }

    @MealRelation.Value
    protected int getBeforeMeal() {
        return beforeMeal;
    }

    protected void setBeforeMeal(@MealRelation.Value int beforeMeal) {
        this.beforeMeal = beforeMeal;
    }

    protected long getSystolic() {
        return systolic;
    }

    protected void setSystolic(long systolic) {
        this.systolic = systolic;
    }

    protected long getDiastolic() {
        return diastolic;
    }

    protected void setDiastolic(long diastolic) {
        this.diastolic = diastolic;
    }

    protected double getValue() {
        return value;
    }

    protected void setValue(double value) {
        this.value = value;
    }

    @NonNull
    @Override
    public final ContentValues toContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(RecordColumns._ID, id);
        cv.put(RecordColumns._METRIC, metric);
        cv.put(RecordColumns.TIME, time);
        cv.put(RecordColumns.BEFORE_MEAL, beforeMeal);
        cv.put(RecordColumns.PRESSURE_SYSTOLIC, systolic);
        cv.put(RecordColumns.PRESSURE_DIASTOLIC, diastolic);
        cv.put(RecordColumns.VALUE, value);
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeartBloodRecord)) return false;
        if (!super.equals(o)) return false;
        final HeartBloodRecord that = (HeartBloodRecord) o;
        return beforeMeal == that.beforeMeal &&
                systolic == that.systolic &&
                diastolic == that.diastolic &&
                Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), beforeMeal, systolic, diastolic, value);
    }
}
