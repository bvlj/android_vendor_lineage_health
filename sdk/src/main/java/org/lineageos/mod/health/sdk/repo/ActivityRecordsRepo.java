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

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.lineageos.mod.health.common.HealthStoreUri;
import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.sdk.model.records.activity.ActivityRecord;
import org.lineageos.mod.health.sdk.model.records.activity.CyclingRecord;
import org.lineageos.mod.health.sdk.model.records.activity.RunningRecord;
import org.lineageos.mod.health.sdk.model.records.activity.WalkingRecord;
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
 *     <li>{@link WalkingRecord}</li>
 *     <li>{@link WorkoutRecord}</li>
 * </ul>
 *
 * Operations performed in this class should be executed outside of the
 * main / UI thread.
 */
@Keep
public final class ActivityRecordsRepo extends RecordsRepo<ActivityRecord> {

    @Nullable
    private static volatile ActivityRecordsRepo instance;
    @NonNull
    private static final Object instanceLock = new Object();

    private ActivityRecordsRepo(@NonNull ContentResolver contentResolver) {
        super(contentResolver, HealthStoreUri.ACTIVITY);
    }

    @NonNull
    public static ActivityRecordsRepo getInstance(
            @NonNull ContentResolver contentResolver) {
        ActivityRecordsRepo currentInstance = instance;
        // use double-checked locking
        // (https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java)
        if (currentInstance == null) {
            synchronized (instanceLock) {
                currentInstance = instance;
                if (currentInstance == null) {
                    currentInstance = new ActivityRecordsRepo(contentResolver);
                    instance = currentInstance;
                }
            }
        }

        return currentInstance;
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
    public List<WalkingRecord> getAllWalkingRecords() {
        return getByMetric(Metric.WALKING).stream()
                .map(WalkingRecord.class::cast)
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
    public WalkingRecord getWalkingRecord(long id) {
        return (WalkingRecord) getById(Metric.WALKING, id);
    }

    @Nullable
    public WorkoutRecord getWorkoutRecord(long id) {
        return (WorkoutRecord) getById(Metric.WORKOUT, id);
    }

    @NonNull
    protected ActivityRecord parseRow(@NonNull Cursor cursor) {
        final long id = cursor.getLong(cursor.getColumnIndex(RecordColumns._ID));
        final int metric = cursor.getInt(cursor.getColumnIndex(RecordColumns._METRIC));
        final long time = cursor.getLong(cursor.getColumnIndex(RecordColumns.TIME));
        final long duration = cursor.getLong(cursor.getColumnIndex(RecordColumns.DURATION));
        final double avgSpeed = cursor.getDouble(cursor.getColumnIndex(RecordColumns.AVG_SPEED));
        final int calories = cursor.getInt(cursor.getColumnIndex(RecordColumns.CALORIES));
        final double distance = cursor.getDouble(cursor.getColumnIndex(RecordColumns.DISTANCE));
        final double elevationGain = cursor.getDouble(cursor.getColumnIndex(
                RecordColumns.ELEVATION_GAIN));
        final String notes = cursor.getString(cursor.getColumnIndex(RecordColumns.NOTES));
        final long steps = cursor.getLong(cursor.getColumnIndex(RecordColumns.STEPS));

        switch (metric) {
            case Metric.CYCLING:
                return new CyclingRecord(id, time, duration, avgSpeed, distance, elevationGain);
            case Metric.RUNNING:
                return new RunningRecord(id, time, duration, avgSpeed, distance);
            case Metric.WALKING:
                return new WalkingRecord(id, time, duration, distance, steps);
            case Metric.WORKOUT:
                return new WorkoutRecord(id, time, duration, calories, notes);
            default:
                return new ActivityRecord(id, metric, time, duration, avgSpeed, calories,
                        distance, elevationGain, notes, steps);
        }

    }
}
