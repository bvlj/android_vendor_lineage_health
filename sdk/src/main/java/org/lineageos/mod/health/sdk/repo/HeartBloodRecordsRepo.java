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
import org.lineageos.mod.health.sdk.model.records.heartblood.BloodAlcoholConcentrationRecord;
import org.lineageos.mod.health.sdk.model.records.heartblood.BloodPressureRecord;
import org.lineageos.mod.health.sdk.model.records.heartblood.GlucoseRecord;
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartBloodRecord;
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartRateRecord;
import org.lineageos.mod.health.sdk.model.records.heartblood.PerfusionIndexRecord;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Breathing records repository.
 * <p>
 * This class allows you to retrieve, insert, update and delete
 * hearth and blood records.
 * <p>
 * Supported records types:
 * <ul>
 *     <li>{@link BloodAlcoholConcentrationRecord}</li>
 *     <li>{@link BloodPressureRecord}</li>
 *     <li>{@link GlucoseRecord}</li>
 *     <li>{@link HeartBloodRecord}</li>
 *     <li>{@link PerfusionIndexRecord}</li>
 * </ul>
 * <p>
 * Operations performed in this class should be executed outside of the
 * main / UI thread.
 */
@Keep
@SuppressWarnings("unused")
public final class HeartBloodRecordsRepo extends RecordsRepo<HeartBloodRecord> {

    @Nullable
    private static volatile HeartBloodRecordsRepo instance;
    @NonNull
    private static final Object instanceLock = new Object();

    private HeartBloodRecordsRepo(@NonNull ContentResolver contentResolver) {
        super(contentResolver, HealthStoreUri.HEART_BLOOD);
    }

    @NonNull
    public static HeartBloodRecordsRepo getInstance(
            @NonNull ContentResolver contentResolver) {
        HeartBloodRecordsRepo currentInstance = instance;
        // use double-checked locking
        // (https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java)
        if (currentInstance == null) {
            synchronized (instanceLock) {
                currentInstance = instance;
                if (currentInstance == null) {
                    currentInstance = new HeartBloodRecordsRepo(contentResolver);
                    instance = currentInstance;
                }
            }
        }

        return currentInstance;
    }

    @NonNull
    public List<BloodAlcoholConcentrationRecord> getAllBloodAlcoholConcentrationRecords() {
        return getByMetric(Metric.BLOOD_ALCOHOL_CONCENTRATION).parallelStream()
                .map(BloodAlcoholConcentrationRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<BloodPressureRecord> getAllBloodPressureRecords() {
        return getByMetric(Metric.BLOOD_PRESSURE).parallelStream()
                .map(BloodPressureRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<GlucoseRecord> getAllGlucoseRecords() {
        return getByMetric(Metric.GLUCOSE).parallelStream()
                .map(GlucoseRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<HeartRateRecord> getAllHeartRateRecords() {
        return getByMetric(Metric.HEART_RATE).parallelStream()
                .map(HeartRateRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    public List<PerfusionIndexRecord> getAllPerfusionIndexRecords() {
        return getByMetric(Metric.PERFUSION_INDEX).parallelStream()
                .map(PerfusionIndexRecord.class::cast)
                .collect(Collectors.toList());
    }

    @Nullable
    public BloodAlcoholConcentrationRecord getBloodAlcoholConcentrationRecord(long id) {
        return (BloodAlcoholConcentrationRecord) getById(Metric.BLOOD_ALCOHOL_CONCENTRATION, id);
    }

    @Nullable
    public BloodPressureRecord getBloodPressureRecord(long id) {
        return (BloodPressureRecord) getById(Metric.BLOOD_PRESSURE, id);
    }

    @Nullable
    public GlucoseRecord getGlucoseRecord(long id) {
        return (GlucoseRecord) getById(Metric.GLUCOSE, id);
    }

    @Nullable
    public HeartRateRecord getHeartRateRecord(long id) {
        return (HeartRateRecord) getById(Metric.HEART_RATE, id);
    }

    @Nullable
    public PerfusionIndexRecord getPerfusionIndexRecord(long id) {
        return (PerfusionIndexRecord) getById(Metric.PERFUSION_INDEX, id);
    }

    @NonNull
    @Override
    protected HeartBloodRecord parseRow(@NonNull Cursor cursor) {
        return new HeartBloodRecord(
                cursor.getLong(cursor.getColumnIndex(RecordColumns._ID)),
                cursor.getInt(cursor.getColumnIndex(RecordColumns._METRIC)),
                cursor.getLong(cursor.getColumnIndex(RecordColumns.TIME)),
                cursor.getInt(cursor.getColumnIndex(RecordColumns.BEFORE_MEAL)),
                cursor.getInt(cursor.getColumnIndex(RecordColumns.PRESSURE_SYSTOLIC)),
                cursor.getInt(cursor.getColumnIndex(RecordColumns.PRESSURE_DIASTOLIC)),
                cursor.getDouble(cursor.getColumnIndex(RecordColumns.VALUE))
        );
    }
}
