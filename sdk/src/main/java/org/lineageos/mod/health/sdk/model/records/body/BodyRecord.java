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

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.lineageos.mod.health.HealthStore;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.common.values.MenstrualCycleOtherSymptoms;
import org.lineageos.mod.health.common.values.MenstrualCyclePhysicalSymptoms;
import org.lineageos.mod.health.common.values.SexualActivity;
import org.lineageos.mod.health.common.values.annotations.BodyMetric;
import org.lineageos.mod.health.sdk.model.records.Record;

import java.util.Objects;

/**
 * Generic Body record.
 *
 * @see BodyMetric
 */
@Keep
public abstract class BodyRecord<T> extends Record {

    @NonNull
    private String notes;
    @MenstrualCycleOtherSymptoms.Value
    private int otherSymptoms;
    @MenstrualCyclePhysicalSymptoms.Value
    private int physicalSymptoms;
    @SexualActivity.Value
    private int sexualActivity;
    @NonNull
    protected T value;

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public BodyRecord(long id, @BodyMetric int metric, long time, @NonNull String notes,
                      @MenstrualCycleOtherSymptoms.Value int otherSymptoms,
                      @MenstrualCyclePhysicalSymptoms.Value int physicalSymptoms,
                      @SexualActivity.Value int sexualActivity,
                      @NonNull T value) {
        super(id, metric, time);
        this.notes = notes;
        this.otherSymptoms = otherSymptoms;
        this.physicalSymptoms = physicalSymptoms;
        this.sexualActivity = sexualActivity;
        this.value = value;
    }

    @NonNull
    protected String getNotes() {
        return notes;
    }

    protected void setNotes(@NonNull String notes) {
        this.notes = notes;
    }

    @MenstrualCycleOtherSymptoms.Value
    protected int getOtherSymptoms() {
        return otherSymptoms;
    }

    protected void setOtherSymptoms(@MenstrualCycleOtherSymptoms.Value int otherSymptoms) {
        this.otherSymptoms = otherSymptoms;
    }

    @MenstrualCyclePhysicalSymptoms.Value
    protected int getPhysicalSymptoms() {
        return physicalSymptoms;
    }

    protected void setPhysicalSymptoms(@MenstrualCyclePhysicalSymptoms.Value int physicalSymptoms) {
        this.physicalSymptoms = physicalSymptoms;
    }

    @SexualActivity.Value
    protected int getSexualActivity() {
        return sexualActivity;
    }

    protected void setSexualActivity(@SexualActivity.Value int sexualActivity) {
        this.sexualActivity = sexualActivity;
    }

    @NonNull
    public T getValue() {
        return value;
    }

    public void setValue(@NonNull T value) {
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
        cv.put(RecordColumns.NOTES, notes);
        cv.put(RecordColumns.SYMPTOMS_OTHER, otherSymptoms);
        cv.put(RecordColumns.SYMPTOMS_PHYSICAL, physicalSymptoms);
        cv.put(RecordColumns.SEXUAL_ACTIVITY, sexualActivity);
        cv.put(RecordColumns.VALUE, valueAsDouble());
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BodyRecord)) return false;
        if (!super.equals(o)) return false;
        final BodyRecord<?> that = (BodyRecord<?>) o;
        return otherSymptoms == that.otherSymptoms &&
                physicalSymptoms == that.physicalSymptoms &&
                sexualActivity == that.sexualActivity &&
                Double.compare(that.valueAsDouble(), valueAsDouble()) == 0 &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), notes, otherSymptoms,
                physicalSymptoms, sexualActivity, valueAsDouble());
    }
}
