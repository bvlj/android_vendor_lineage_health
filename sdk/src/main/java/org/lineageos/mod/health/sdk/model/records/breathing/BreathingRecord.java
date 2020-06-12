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

package org.lineageos.mod.health.sdk.model.records.breathing;

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.sdk.model.records.Record;
import org.lineageos.mod.health.common.db.RecordColumns;

import java.util.Objects;

@Keep
@SuppressWarnings("unused")
public class BreathingRecord extends Record {

    @NonNull
    private String notes;
    private double value;

    public BreathingRecord(long id, int metric, long time,
                           @NonNull String notes, double value) {
        super(id, metric, time);
        this.notes = notes;
        this.value = value;
    }

    @NonNull
    protected String getNotes() {
        return notes;
    }

    protected void setNotes(@NonNull String notes) {
        this.notes = notes;
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
        cv.put(RecordColumns.NOTES, notes);
        cv.put(RecordColumns.VALUE, value);
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BreathingRecord)) return false;
        if (!super.equals(o)) return false;
        final BreathingRecord that = (BreathingRecord) o;
        return Double.compare(that.value, value) == 0 &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), notes, value);
    }
}
