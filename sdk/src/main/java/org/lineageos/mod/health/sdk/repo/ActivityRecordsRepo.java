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

package org.lineageos.mod.health.sdk.repo;

import android.content.ContentResolver;
import android.database.Cursor;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.lineageos.mod.health.common.HealthStoreUri;
import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.sdk.model.records.activity.ActivityRecord;
import org.lineageos.mod.health.sdk.model.records.activity.CyclingRecord;
import org.lineageos.mod.health.sdk.model.records.activity.RunningRecord;
import org.lineageos.mod.health.sdk.model.records.activity.StepsRecord;
import org.lineageos.mod.health.sdk.model.records.activity.WorkoutRecord;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Activity records repository.
 *
 * This class allows you to retrieve, insert, update and delete
 * activity records.
 *
 * Supported records types:
 * <ul>
 *     <li>{@link CyclingRecord}</li>
 *     <li>{@link RunningRecord}</li>
 *     <li>{@link StepsRecord}</li>
 *     <li>{@link WorkoutRecord}</li>
 * </ul>
 *
 * Operations performed in this class should be executed outside of the
 * main / UI thread.
 */
@Keep
@SuppressWarnings("unused")
public final class ActivityRecordsRepo extends RecordsRepo<ActivityRecord> {
    private static final String DEFAULT_ORDER = RecordColumns.TIME + " DESC";

    @Nullable
    private static volatile ActivityRecordsRepo instance;

    private ActivityRecordsRepo(@NonNull ContentResolver contentResolver) {
        super(contentResolver, HealthStoreUri.ACTIVITY);
    }

    @NonNull
    public static synchronized ActivityRecordsRepo getInstance(
            @NonNull ContentResolver contentResolver) {
        final ActivityRecordsRepo currentInstance = instance;
        if (currentInstance != null) {
            return currentInstance;
        }

        final ActivityRecordsRepo newInstance = new ActivityRecordsRepo(contentResolver);
        instance = newInstance;
        return newInstance;
    }

    @NonNull
    public List<CyclingRecord> getAllCyclingRecords() {
        return getByMetric(Metric.CYCLING).stream()
                .map(CyclingRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<RunningRecord> getAllRunningRecords() {
        return getByMetric(Metric.RUNNING).stream()
                .map(RunningRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<StepsRecord> getAllStepsRecords() {
        return getByMetric(Metric.STEPS).stream()
                .map(StepsRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<WorkoutRecord> getAllWorkoutRecords() {
        return getByMetric(Metric.WORKOUT).stream()
                .map(WorkoutRecord.class::cast)
                .collect(Collectors.toList());
    }

    @Nullable
    public CyclingRecord getCyclingRecord(long id) {
        return (CyclingRecord) getById(Metric.CYCLING, id);
    }

    @Nullable
    public RunningRecord getRunningRecord(long id) {
        return (RunningRecord) getById(Metric.RUNNING, id);
    }

    @Nullable
    public StepsRecord getStepsRecord(long id) {
        return (StepsRecord) getById(Metric.STEPS, id);
    }

    @Nullable
    public WorkoutRecord getWorkoutRecord(long id) {
        return (WorkoutRecord) getById(Metric.WORKOUT, id);
    }

    @NonNull
    protected ActivityRecord parseRow(@NonNull Cursor cursor) {
        return new ActivityRecord(
                cursor.getLong(cursor.getColumnIndex(RecordColumns._ID)),
                cursor.getInt(cursor.getColumnIndex(RecordColumns._METRIC)),
                cursor.getLong(cursor.getColumnIndex(RecordColumns.TIME)),
                cursor.getLong(cursor.getColumnIndex(RecordColumns.DURATION)),
                cursor.getDouble(cursor.getColumnIndex(RecordColumns.AVG_SPEED)),
                cursor.getInt(cursor.getColumnIndex(RecordColumns.CALORIES)),
                cursor.getDouble(cursor.getColumnIndex(RecordColumns.DISTANCE)),
                cursor.getDouble(cursor.getColumnIndex(RecordColumns.ELEVATION_GAIN)),
                cursor.getString(cursor.getColumnIndex(RecordColumns.NOTES)),
                cursor.getLong(cursor.getColumnIndex(RecordColumns.STEPS))
        );
    }
}
