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

package org.lineageos.mod.health.sdk.model.records.mindfulness;

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.common.values.MoodLevel;
import org.lineageos.mod.health.common.values.annotations.MindfulnessMetric;
import org.lineageos.mod.health.sdk.model.records.Record;

import java.util.Objects;

@Keep
public class MindfulnessRecord extends Record {

    private long duration;
    @MoodLevel.Value
    private int moodLevel;
    @NonNull
    private String notes;

    public MindfulnessRecord(long id, @MindfulnessMetric int metric, long time, long duration,
                             @MoodLevel.Value int moodLevel, @NonNull String notes) {
        super(id, metric, time);
        this.duration = duration;
        this.moodLevel = moodLevel;
        this.notes = notes;
    }

    protected long getDuration() {
        return duration;
    }

    protected void setDuration(long duration) {
        this.duration = duration;
    }

    @MoodLevel.Value
    protected int getMoodLevel() {
        return moodLevel;
    }

    protected void setMoodLevel(@MoodLevel.Value int moodLevel) {
        this.moodLevel = moodLevel;
    }

    @NonNull
    protected String getNotes() {
        return notes;
    }

    protected void setNotes(@NonNull String notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public final ContentValues toContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(RecordColumns._ID, id);
        cv.put(RecordColumns._METRIC, metric);
        cv.put(RecordColumns.TIME, time);
        cv.put(RecordColumns.DURATION, duration);
        cv.put(RecordColumns.MOOD, moodLevel);
        cv.put(RecordColumns.NOTES, notes);
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MindfulnessRecord)) return false;
        if (!super.equals(o)) return false;
        final MindfulnessRecord that = (MindfulnessRecord) o;
        return duration == that.duration &&
                moodLevel == that.moodLevel &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration, moodLevel, notes);
    }
}
