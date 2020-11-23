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

package org.lineageos.mod.health.sdk.ktx

import android.content.ContentResolver
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.lineageos.mod.health.sdk.model.records.body.AbdominalCircumferenceRecord
import org.lineageos.mod.health.sdk.model.records.body.BodyMassIndexRecord
import org.lineageos.mod.health.sdk.model.records.body.BodyRecord
import org.lineageos.mod.health.sdk.model.records.body.BodyTemperatureRecord
import org.lineageos.mod.health.sdk.model.records.body.LeanBodyMassRecord
import org.lineageos.mod.health.sdk.model.records.body.MenstrualCycleRecord
import org.lineageos.mod.health.sdk.model.records.body.UvIndexRecord
import org.lineageos.mod.health.sdk.model.records.body.WaterIntakeRecord
import org.lineageos.mod.health.sdk.model.records.body.WeightRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo
import org.lineageos.mod.util.SingletonHolder

/**
 * Wrapper for [BodyRecordsRepo]
 * that makes use of coroutines
 */
@Keep
@Suppress("unused")
class BodyRecordsRepoKt private constructor(
    private val _repo: BodyRecordsRepo
) {
    private val dispatcher = IO + CoroutineName("body")

    suspend fun all(): List<BodyRecord> =
        withContext(dispatcher) { _repo.all }

    suspend fun allCyclingRecords(): List<AbdominalCircumferenceRecord> =
        withContext(dispatcher) { _repo.allAbdominalCircumferenceRecords }

    suspend fun allBodyMassIndexRecords(): List<BodyMassIndexRecord> =
        withContext(dispatcher) { _repo.allBodyMassIndexRecords }

    suspend fun allBodyTemperatureRecords(): List<BodyTemperatureRecord> =
        withContext(dispatcher) { _repo.allBodyTemperatureRecords }

    suspend fun allLeanBodyMassRecords(): List<LeanBodyMassRecord> =
        withContext(dispatcher) { _repo.allLeanBodyMassRecords }

    suspend fun allMenstrualCycleRecords(): List<MenstrualCycleRecord> =
        withContext(dispatcher) { _repo.allMenstrualCycleRecords }

    suspend fun allUvIndexRecords(): List<UvIndexRecord> =
        withContext(dispatcher) { _repo.allUvIndexRecords }

    suspend fun allWaterIntakeRecords(): List<WaterIntakeRecord> =
        withContext(dispatcher) { _repo.allWaterIntakeRecords }

    suspend fun allWeightRecords(): List<WeightRecord> =
        withContext(dispatcher) { _repo.allWeightRecords }

    suspend fun abdominalCircumferenceRecord(id: Long): AbdominalCircumferenceRecord? =
        withContext(dispatcher) { _repo.getAbdominalCircumferenceRecord(id) }

    suspend fun bodyMassIndexRecord(id: Long): BodyMassIndexRecord? =
        withContext(dispatcher) { _repo.getBodyMassIndexRecord(id) }

    suspend fun bodyTemperatureRecord(id: Long): BodyTemperatureRecord? =
        withContext(dispatcher) { _repo.getBodyTemperatureRecord(id) }

    suspend fun leanBodyMassRecord(id: Long): LeanBodyMassRecord? =
        withContext(dispatcher) { _repo.getLeanBodyMassRecord(id) }

    suspend fun menstrualCycleRecord(id: Long): MenstrualCycleRecord? =
        withContext(dispatcher) { _repo.getMenstrualCycleRecord(id) }

    suspend fun uvIndexRecord(id: Long): UvIndexRecord? =
        withContext(dispatcher) { _repo.getUvIndexRecord(id) }

    suspend fun waterIntakeRecord(id: Long): WaterIntakeRecord? =
        withContext(dispatcher) { _repo.getWaterIntakeRecord(id) }

    suspend fun weightRecord(id: Long): WeightRecord? =
        withContext(dispatcher) { _repo.getWeightRecord(id) }

    suspend fun insert(record: BodyRecord): Long =
        withContext(dispatcher) { _repo.insert(record) }

    suspend fun update(record: BodyRecord): Boolean =
        withContext(dispatcher) { _repo.update(record) }

    suspend fun delete(record: BodyRecord): Boolean =
        withContext(dispatcher) { _repo.delete(record) }

    companion object : SingletonHolder<BodyRecordsRepoKt, ContentResolver>({
        BodyRecordsRepoKt(BodyRecordsRepo.getInstance(it))
    })
}
