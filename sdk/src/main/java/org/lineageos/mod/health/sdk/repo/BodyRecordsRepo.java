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
import org.lineageos.mod.health.sdk.model.records.body.AbdominalCircumferenceRecord;
import org.lineageos.mod.health.sdk.model.records.body.BaseBodyRecord;
import org.lineageos.mod.health.sdk.model.records.body.BodyMassIndexRecord;
import org.lineageos.mod.health.sdk.model.records.body.BodyRecord;
import org.lineageos.mod.health.sdk.model.records.body.BodyTemperatureRecord;
import org.lineageos.mod.health.sdk.model.records.body.LeanBodyMassRecord;
import org.lineageos.mod.health.sdk.model.records.body.MenstrualCycleRecord;
import org.lineageos.mod.health.sdk.model.records.body.UvIndexRecord;
import org.lineageos.mod.health.sdk.model.records.body.WaterIntakeRecord;
import org.lineageos.mod.health.sdk.model.records.body.WeightRecord;
import org.lineageos.mod.health.sdk.model.values.LengthValue;
import org.lineageos.mod.health.sdk.model.values.MassValue;
import org.lineageos.mod.health.sdk.model.values.TemperatureValue;
import org.lineageos.mod.health.sdk.util.HsRuntimePermission;
import org.lineageos.mod.health.sdk.util.RecordTimeComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Body records repository.
 * <p>
 * This class allows you to retrieve, insert, update and delete
 * body records.
 * <p>
 * Supported records types:
 * <ul>
 *     <li>{@link AbdominalCircumferenceRecord}</li>
 *     <li>{@link BodyMassIndexRecord}</li>
 *     <li>{@link BodyTemperatureRecord}</li>
 *     <li>{@link LeanBodyMassRecord}</li>
 *     <li>{@link MenstrualCycleRecord}</li>
 *     <li>{@link UvIndexRecord}</li>
 *     <li>{@link WaterIntakeRecord}</li>
 *     <li>{@link WeightRecord}</li>
 * </ul>
 * <p>
 * Operations performed in this class should be executed outside of the
 * main / UI thread.
 *
 * @see BodyRecord
 * @see AbdominalCircumferenceRecord
 * @see BodyMassIndexRecord
 * @see BodyTemperatureRecord
 * @see LeanBodyMassRecord
 * @see MenstrualCycleRecord
 * @see UvIndexRecord
 * @see WaterIntakeRecord
 * @see WeightRecord
 */
@Keep
public final class BodyRecordsRepo extends RecordsRepo<BodyRecord<?>> {

    @Nullable
    private static volatile BodyRecordsRepo instance;
    @NonNull
    private static final Object instanceLock = new Object();

    private BodyRecordsRepo(@NonNull ContentResolver contentResolver) {
        super(contentResolver, HealthStoreUri.BODY);
    }

    @NonNull
    public static BodyRecordsRepo getInstance(
            @NonNull ContentResolver contentResolver) {
        BodyRecordsRepo currentInstance = instance;
        // use double-checked locking
        // (https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java)
        if (currentInstance == null) {
            synchronized (instanceLock) {
                currentInstance = instance;
                if (currentInstance == null) {
                    currentInstance = new BodyRecordsRepo(contentResolver);
                    instance = currentInstance;
                }
            }
        }

        return currentInstance;
    }

    @NonNull
    @Override
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<BodyRecord<?>> getAll() {
        final List<BodyRecord<?>> list = new ArrayList<>();
        list.addAll(getAllAbdominalCircumferenceRecords());
        list.addAll(getAllBodyMassIndexRecords());
        list.addAll(getAllBodyTemperatureRecords());
        list.addAll(getAllLeanBodyMassRecords());
        list.addAll(getAllMenstrualCycleRecords());
        list.addAll(getAllUvIndexRecords());
        list.addAll(getAllWaterIntakeRecords());
        list.addAll(getAllWeightRecords());
        list.sort(new RecordTimeComparator());
        return list;
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<AbdominalCircumferenceRecord> getAllAbdominalCircumferenceRecords() {
        return getByMetric(Metric.ABDOMINAL_CIRCUMFERENCE).parallelStream()
                .map(AbdominalCircumferenceRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<BodyMassIndexRecord> getAllBodyMassIndexRecords() {
        return getByMetric(Metric.BODY_MASS_INDEX).parallelStream()
                .map(BodyMassIndexRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<BodyTemperatureRecord> getAllBodyTemperatureRecords() {
        return getByMetric(Metric.BODY_TEMPERATURE).parallelStream()
                .map(BodyTemperatureRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<LeanBodyMassRecord> getAllLeanBodyMassRecords() {
        return getByMetric(Metric.LEAN_BODY_MASS).parallelStream()
                .map(LeanBodyMassRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<MenstrualCycleRecord> getAllMenstrualCycleRecords() {
        return getByMetric(Metric.MENSTRUAL_CYCLE).parallelStream()
                .map(MenstrualCycleRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<UvIndexRecord> getAllUvIndexRecords() {
        return getByMetric(Metric.UV_INDEX).parallelStream()
                .map(UvIndexRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<WaterIntakeRecord> getAllWaterIntakeRecords() {
        return getByMetric(Metric.WATER_INTAKE).parallelStream()
                .map(WaterIntakeRecord.class::cast)
                .collect(Collectors.toList());
    }

    @NonNull
    @RequiresPermission(HsRuntimePermission.BODY)
    public List<WeightRecord> getAllWeightRecords() {
        return getByMetric(Metric.WEIGHT).parallelStream()
                .map(WeightRecord.class::cast)
                .collect(Collectors.toList());
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public AbdominalCircumferenceRecord getAbdominalCircumferenceRecord(long id) {
        return (AbdominalCircumferenceRecord) getById(Metric.ABDOMINAL_CIRCUMFERENCE, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public BodyMassIndexRecord getBodyMassIndexRecord(long id) {
        return (BodyMassIndexRecord) getById(Metric.BODY_MASS_INDEX, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public BodyTemperatureRecord getBodyTemperatureRecord(long id) {
        return (BodyTemperatureRecord) getById(Metric.BODY_TEMPERATURE, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public LeanBodyMassRecord getLeanBodyMassRecord(long id) {
        return (LeanBodyMassRecord) getById(Metric.LEAN_BODY_MASS, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public MenstrualCycleRecord getMenstrualCycleRecord(long id) {
        return (MenstrualCycleRecord) getById(Metric.MENSTRUAL_CYCLE, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public UvIndexRecord getUvIndexRecord(long id) {
        return (UvIndexRecord) getById(Metric.UV_INDEX, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public WaterIntakeRecord getWaterIntakeRecord(long id) {
        return (WaterIntakeRecord) getById(Metric.WATER_INTAKE, id);
    }

    @Nullable
    @RequiresPermission(HsRuntimePermission.BODY)
    public WeightRecord getWeightRecord(long id) {
        return (WeightRecord) getById(Metric.WEIGHT, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequiresPermission(HsRuntimePermission.BODY)
    public OperationResult insert(@NonNull BodyRecord<?> record) {
        return super.insert(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequiresPermission(HsRuntimePermission.BODY)
    public OperationResult update(@NonNull BodyRecord<?> record) {
        return super.update(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequiresPermission(HsRuntimePermission.BODY)
    public OperationResult delete(@NonNull BodyRecord<?> record) {
        return super.delete(record);
    }

    @NonNull
    @Override
    protected BodyRecord<?> parseRow(@NonNull Cursor cursor) {
        final long id = cursor.getLong(cursor.getColumnIndex(RecordColumns._ID));
        final int metric = cursor.getInt(cursor.getColumnIndex(RecordColumns._METRIC));
        final long time = cursor.getLong(cursor.getColumnIndex(RecordColumns.TIME));
        final String notes = cursor.getString(cursor.getColumnIndex(RecordColumns.NOTES));
        final int otherSymptoms = cursor.getInt(cursor.getColumnIndex(
                RecordColumns.SYMPTOMS_OTHER));
        final int physicalSymptoms = cursor.getInt(cursor.getColumnIndex(
                RecordColumns.SYMPTOMS_PHYSICAL));
        final int sexualActivity = cursor.getInt(cursor.getColumnIndex(
                RecordColumns.SEXUAL_ACTIVITY));
        final double value = cursor.getDouble(cursor.getColumnIndex(RecordColumns.VALUE));

        switch (metric) {
            case Metric.ABDOMINAL_CIRCUMFERENCE:
                return new AbdominalCircumferenceRecord(id, time, LengthValue.centimeters(value));
            case Metric.BODY_MASS_INDEX:
                return new BodyMassIndexRecord(id, time, value);
            case Metric.BODY_TEMPERATURE:
                return new BodyTemperatureRecord(id, time, TemperatureValue.celsius(value));
            case Metric.LEAN_BODY_MASS:
                return new LeanBodyMassRecord(id, time, value);
            case Metric.MENSTRUAL_CYCLE:
                return new MenstrualCycleRecord(id, time, otherSymptoms, physicalSymptoms,
                        sexualActivity, value);
            case Metric.UV_INDEX:
                return new UvIndexRecord(id, time, value);
            case Metric.WATER_INTAKE:
                return new WaterIntakeRecord(id, time, notes, value);
            case Metric.WEIGHT:
                return new WeightRecord(id, time, MassValue.kilograms(value));
            default:
                return new BaseBodyRecord(id, metric, time, notes, otherSymptoms,
                        physicalSymptoms, sexualActivity, value);
        }
    }
}
