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

package org.lineageos.mod.health.sdk.model.records.activity;

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import org.lineageos.mod.health.HealthStore;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.common.values.annotations.ActivityMetric;
import org.lineageos.mod.health.sdk.model.records.Record;
import org.lineageos.mod.health.sdk.model.values.LengthValue;
import org.lineageos.mod.health.sdk.model.values.SpeedValue;

import java.util.Objects;

/**
 * Generic activity record.
 *
 * @see ActivityMetric
 */
@Keep
public class ActivityRecord extends Record {

    private long duration;
    @NonNull
    private SpeedValue avgSpeed;
    private int calories;
    @NonNull
    private LengthValue distance;
    @NonNull
    private LengthValue elevationGain;
    @NonNull
    private String notes;
    private long steps;

    /**
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public ActivityRecord(long id, @ActivityMetric int metric,
                          long time, long duration,
                          @NonNull SpeedValue avgSpeed,
                          int calories,
                          @NonNull LengthValue distance,
                          @NonNull LengthValue elevationGain,
                          @NonNull String notes, long steps) {
        super(id, metric, time);
        this.duration = duration;
        this.avgSpeed = avgSpeed;
        this.calories = calories;
        this.distance = distance;
        this.elevationGain = elevationGain;
        this.notes = notes;
        this.steps = steps;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @NonNull
    protected SpeedValue getAvgSpeed() {
        return avgSpeed;
    }

    protected void setAvgSpeed(@NonNull SpeedValue avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    protected int getCalories() {
        return calories;
    }

    protected void setCalories(int calories) {
        this.calories = calories;
    }

    @NonNull
    protected LengthValue getDistance() {
        return distance;
    }

    protected void setDistance(@NonNull LengthValue distance) {
        this.distance = distance;
    }

    @NonNull
    protected LengthValue getElevationGain() {
        return elevationGain;
    }

    protected void setElevationGain(@NonNull LengthValue elevationGain) {
        this.elevationGain = elevationGain;
    }

    @NonNull
    protected String getNotes() {
        return notes;
    }

    protected void setNotes(@NonNull String notes) {
        this.notes = notes;
    }

    protected long getSteps() {
        return steps;
    }

    protected void setSteps(long steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public final ContentValues toContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(RecordColumns._VERSION, HealthStore.Version.CURRENT);
        cv.put(RecordColumns._ID, id);
        cv.put(RecordColumns._METRIC, metric);
        cv.put(RecordColumns.TIME, time);
        cv.put(RecordColumns.DURATION, duration);
        cv.put(RecordColumns.AVG_SPEED, avgSpeed.kilometersPerHour());
        cv.put(RecordColumns.CALORIES, calories);
        cv.put(RecordColumns.DISTANCE, distance.kilometers());
        cv.put(RecordColumns.ELEVATION_GAIN, elevationGain.meters());
        cv.put(RecordColumns.NOTES, notes);
        cv.put(RecordColumns.STEPS, steps);
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityRecord)) return false;
        if (!super.equals(o)) return false;
        final ActivityRecord that = (ActivityRecord) o;
        return duration == that.duration &&
                avgSpeed.equals(that.avgSpeed) &&
                calories == that.calories &&
                distance.equals(that.distance) &&
                elevationGain.equals(that.elevationGain) &&
                steps == that.steps &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration, avgSpeed, calories,
                distance, elevationGain, notes, steps);
    }
}
