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

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.Metric;

/**
 * Workout exercise record.
 *
 * <ul>
 *     <li>Duration in minutes</li>
 *     <li>Burned calories in cal</li>
 *     <li>Notes</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Exercise">More info</a>
 */
@Keep
@SuppressWarnings("unused")
public final class WorkoutRecord extends ActivityRecord {

    public WorkoutRecord(long id, long time, long duration,
                         int calories, @NonNull String notes) {
        super(id, Metric.WORKOUT, time, duration, 0.0, calories,
                0.0, 0.0, notes, 0);
    }

    @Override
    public int getCalories() {
        return super.getCalories();
    }

    @Override
    public void setCalories(int calories) {
        super.setCalories(calories);
    }

    @NonNull
    @Override
    public String getNotes() {
        return super.getNotes();
    }

    @Override
    public void setNotes(@NonNull String notes) {
        super.setNotes(notes);
    }
}
