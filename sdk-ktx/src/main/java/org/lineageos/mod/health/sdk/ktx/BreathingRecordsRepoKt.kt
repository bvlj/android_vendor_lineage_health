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

import android.content.ContentProviderResult
import android.content.ContentResolver
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.sdk.ktx.batch.HsBatchOperationBuilder
import org.lineageos.mod.health.sdk.model.records.breathing.BreathingRecord
import org.lineageos.mod.health.sdk.model.records.breathing.InhalerUsageRecord
import org.lineageos.mod.health.sdk.model.records.breathing.OxygenSaturationRecord
import org.lineageos.mod.health.sdk.model.records.breathing.PeakExpiratoryFlowRecord
import org.lineageos.mod.health.sdk.model.records.breathing.RespiratoryRateRecord
import org.lineageos.mod.health.sdk.model.records.breathing.VitalCapacityRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo
import org.lineageos.mod.util.SingletonHolder

/**
 * Wrapper for [BreathingRecordsRepo]
 * that makes use of coroutines
 */
@Keep
@Suppress("unused")
class BreathingRecordsRepoKt private constructor(
    private val _repo: BreathingRecordsRepo
) {
    private val dispatcher = Dispatchers.IO + CoroutineName("breathing")

    suspend fun all(): List<BreathingRecord> =
        withContext(dispatcher) { _repo.all }

    suspend fun allInhalerUsageRecords(): List<InhalerUsageRecord> =
        withContext(dispatcher) { _repo.allInhalerUsageRecords }

    suspend fun allOxygenSaturationRecords(): List<OxygenSaturationRecord> =
        withContext(dispatcher) { _repo.allOxygenSaturationRecords }

    suspend fun allPeakExpiratoryFlowRecords(): List<PeakExpiratoryFlowRecord> =
        withContext(dispatcher) { _repo.allPeakExpiratoryFlowRecords }

    suspend fun allRespiratoryRateRecords(): List<RespiratoryRateRecord> =
        withContext(dispatcher) { _repo.allRespiratoryRateRecords }

    suspend fun allVitalCapacityRecords(): List<VitalCapacityRecord> =
        withContext(dispatcher) { _repo.allVitalCapacityRecords }

    suspend fun inhalerUsageRecord(id: Long): InhalerUsageRecord? =
        withContext(dispatcher) { _repo.getInhalerUsageRecord(id) }

    suspend fun oxygenSaturationRecords(id: Long): OxygenSaturationRecord? =
        withContext(dispatcher) { _repo.getOxygenSaturationRecord(id) }

    suspend fun peakExpiratoryFlowRecord(id: Long): PeakExpiratoryFlowRecord? =
        withContext(dispatcher) { _repo.getPeakExpiratoryFlowRecord(id) }

    suspend fun respiratoryRateRecord(id: Long): RespiratoryRateRecord? =
        withContext(dispatcher) { _repo.getRespiratoryRateRecord(id) }

    suspend fun vitalCapacityRecord(id: Long): VitalCapacityRecord? =
        withContext(dispatcher) { _repo.getVitalCapacityRecord(id) }

    suspend fun insert(record: BreathingRecord): Long =
        withContext(dispatcher) { _repo.insert(record) }

    suspend fun update(record: BreathingRecord): Boolean =
        withContext(dispatcher) { _repo.update(record) }

    suspend fun delete(record: BreathingRecord): Boolean =
        withContext(dispatcher) { _repo.delete(record) }

    suspend fun batchOperation(
        buildBlock: HsBatchOperationBuilder<BreathingRecord>.() -> Unit
    ): Array<ContentProviderResult> = withContext(dispatcher) {
        val opBuilder = HsBatchOperationBuilder<BreathingRecord>(HealthStoreUri.ACTIVITY)
        opBuilder.buildBlock()
        val ops = opBuilder.build()
        _repo.executeBatch(ops)
    }

    companion object : SingletonHolder<BreathingRecordsRepoKt, ContentResolver>({
        BreathingRecordsRepoKt(BreathingRecordsRepo.getInstance(it))
    })
}
