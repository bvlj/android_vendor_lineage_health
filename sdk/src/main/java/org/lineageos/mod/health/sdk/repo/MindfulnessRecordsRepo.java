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
import androidx.annotation.RequiresPermission;

import org.lineageos.mod.health.common.HealthStoreUri;
import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.db.RecordColumns;
import org.lineageos.mod.health.sdk.model.records.mindfulness.MeditationRecord;
import org.lineageos.mod.health.sdk.model.records.mindfulness.MindfulnessRecord;
import org.lineageos.mod.health.sdk.model.records.mindfulness.MoodRecord;
import org.lineageos.mod.health.sdk.model.records.mindfulness.SleepRecord;
import org.lineageos.mod.health.sdk.util.HsRuntimePermission;
import org.lineageos.mod.health.sdk.util.RecordTimeComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mindfulness records repository.
 * <p>
 * This class allows you to retrieve, insert, update and delete
 * mindfulness records.
 * <p>
 * Supported records types:
 * <ul>
 *     <li>{@link MeditationRecord}</li>
 *     <li>{@link MoodRecord}</li>
 *     <li>{@link SleepRecord}</li>
 * </ul>
 * <p>
 * Operations performed in this class should be executed outside of the
 * main / UI thread.
 *
 * @see MindfulnessRecord
 * @see MeditationRecord
 * @see MoodRecord
 * @see SleepRecord
 */
@Keep
public final class MindfulnessRecordsRepo extends RecordsRepo<MindfulnessRecord> {

    @Nullable
    private static volatile MindfulnessRecordsRepo instance;
    @NonNull
    private static final Object instanceLock = new Object();

    private MindfulnessRecordsRepo(@NonNull ContentResolver contentResolver) {
        super(contentResolver, HealthStoreUri.MINDFULNESS);
    }

    @NonNull
    public static MindfulnessRecordsRepo getInstance(
            @NonNull ContentResolver contentResolver) {
        MindfulnessRecordsRepo currentInstance = instance;
        // use double-checked locking
        // (https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java)
        if (currentInstance == null) {
            synchronized (instanceLock) {
                currentInstance = instance;
                if (currentInstance == null) {
                    currentInstance = new MindfulnessRecordsRepo(contentResolver);
                    instance = currentInstance;
                }
            }
        }

        return currentInstance;
    }

    @NonNull
    @Override
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public List<MindfulnessRecord> getAll() {
        final List<MindfulnessRecord> list = new ArrayList<>();
        list.addAll(getAllMeditationRecords());
        list.addAll(getAllMoodRecords());
        list.addAll(getAllSleepRecords());
        list.sort(new RecordTimeComparator());
        return list;
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public List<MeditationRecord> getAllMeditationRecords() {
        return getByMetric(Metric.MEDITATION).parallelStream()
                .map(MeditationRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public List<MoodRecord> getAllMoodRecords() {
        return getByMetric(Metric.MOOD).parallelStream()
                .map(MoodRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public List<SleepRecord> getAllSleepRecords() {
        return getByMetric(Metric.SLEEP).parallelStream()
                .map(SleepRecord.class::cast)
                .collect(Collectors.toList());
    }


    @Nullable
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public MeditationRecord getMeditationRecord(long id) {
        return (MeditationRecord) getById(Metric.MEDITATION, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public MoodRecord getMoodRecord(long id) {
        return (MoodRecord) getById(Metric.MOOD, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public SleepRecord getSleepRecord(long id) {
        return (SleepRecord) getById(Metric.SLEEP, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public OperationResult insert(@NonNull MindfulnessRecord record) {
        return super.insert(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public OperationResult update(@NonNull MindfulnessRecord record) {
        return super.update(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequiresPermission(HsRuntimePermission.MINDFULNESS)
    public OperationResult delete(@NonNull MindfulnessRecord record) {
        return super.delete(record);
    }

    @NonNull
    @Override
    protected MindfulnessRecord parseRow(@NonNull Cursor cursor) {
        final long id = cursor.getLong(cursor.getColumnIndex(RecordColumns._ID));
        final int metric = cursor.getInt(cursor.getColumnIndex(RecordColumns._METRIC));
        final long time = cursor.getLong(cursor.getColumnIndex(RecordColumns.TIME));
        final long duration = cursor.getLong(cursor.getColumnIndex(RecordColumns.DURATION));
        final int moodLevel = cursor.getInt(cursor.getColumnIndex(RecordColumns.MOOD));
        final String notes = cursor.getString(cursor.getColumnIndex(RecordColumns.NOTES));

        switch (metric) {
            case Metric.MEDITATION:
                return new MeditationRecord(id, time, duration);
            case Metric.MOOD:
                return new MoodRecord(id, time, moodLevel, notes);
            case Metric.SLEEP:
                return new SleepRecord(id, time, duration);
            default:
                return new MindfulnessRecord(id, metric, time, duration, moodLevel, notes);
        }
    }
}
