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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.lineageos.mod.health.sdk.model.records.heartblood.BloodAlcoholConcentrationRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.BloodPressureRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.GlucoseRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartBloodRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartRateRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.PerfusionIndexRecord
import org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo
import org.lineageos.mod.util.SingletonHolder

/**
 * Wrapper for [HeartBloodRecordsRepo]
 * that makes use of coroutines
 */
@Keep
@Suppress("unused")
class HeartBloodRecordsRepoKt private constructor(
    private val _repo: HeartBloodRecordsRepo
) {
    private val dispatcher = Dispatchers.IO + CoroutineName("hearthBlood")

    suspend fun all(): List<HeartBloodRecord> =
        withContext(dispatcher) { _repo.all }

    suspend fun allBloodAlcoholConcentrationRecord(): List<BloodAlcoholConcentrationRecord> =
        withContext(dispatcher) { _repo.allBloodAlcoholConcentrationRecords }

    suspend fun allBloodPressureRecords(): List<BloodPressureRecord> =
        withContext(dispatcher) { _repo.allBloodPressureRecords }

    suspend fun allGlucoseRecords(): List<GlucoseRecord> =
        withContext(dispatcher) { _repo.allGlucoseRecords }

    suspend fun allHeartRateRecords(): List<HeartRateRecord> =
        withContext(dispatcher) { _repo.allHeartRateRecords }

    suspend fun allPerfusionIndexRecords(): List<PerfusionIndexRecord> =
        withContext(dispatcher) { _repo.allPerfusionIndexRecords }

    suspend fun bloodAlcoholConcentrationRecord(id: Long): BloodAlcoholConcentrationRecord? =
        withContext(dispatcher) { _repo.getBloodAlcoholConcentrationRecord(id) }

    suspend fun bloodPressureRecord(id: Long): BloodPressureRecord? =
        withContext(dispatcher) { _repo.getBloodPressureRecord(id) }

    suspend fun glucoseRecord(id: Long): GlucoseRecord? =
        withContext(dispatcher) { _repo.getGlucoseRecord(id) }

    suspend fun heartRateRecord(id: Long): HeartRateRecord? =
        withContext(dispatcher) { _repo.getHeartRateRecord(id) }

    suspend fun perfusionIndexRecord(id: Long): PerfusionIndexRecord? =
        withContext(dispatcher) { _repo.getPerfusionIndexRecord(id) }

    suspend fun insert(record: HeartBloodRecord): Boolean =
        withContext(dispatcher) { _repo.insert(record) }

    suspend fun update(record: HeartBloodRecord): Boolean =
        withContext(dispatcher) { _repo.update(record) }

    suspend fun delete(record: HeartBloodRecord): Boolean =
        withContext(dispatcher) { _repo.delete(record) }

    companion object : SingletonHolder<HeartBloodRecordsRepoKt, ContentResolver>({
        HeartBloodRecordsRepoKt(HeartBloodRecordsRepo.getInstance(it))
    })
}
