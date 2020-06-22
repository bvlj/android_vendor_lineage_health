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
import org.lineageos.mod.health.sdk.model.records.activity.ActivityRecord
import org.lineageos.mod.health.sdk.model.records.activity.CyclingRecord
import org.lineageos.mod.health.sdk.model.records.activity.RunningRecord
import org.lineageos.mod.health.sdk.model.records.activity.WalkingRecord
import org.lineageos.mod.health.sdk.model.records.activity.WorkoutRecord
import org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo
import org.lineageos.mod.util.SingletonHolder

/**
 * Wrapper for [ActivityRecordsRepo]
 * that makes use of coroutines
 */
@Keep
@Suppress("unused")
class ActivityRecordsRepoKt private constructor(
    private val _repo: ActivityRecordsRepo
) {
    private val dispatcher = IO + CoroutineName("activity")

    suspend fun all(): List<ActivityRecord> =
        withContext(dispatcher) { _repo.all }

    suspend fun allCyclingRecords(): List<CyclingRecord> =
        withContext(dispatcher) { _repo.allCyclingRecords }

    suspend fun allRunningRecords(): List<RunningRecord> =
        withContext(dispatcher) { _repo.allRunningRecords }

    suspend fun allWalkingRecords(): List<WalkingRecord> =
        withContext(dispatcher) { _repo.allWalkingRecords }

    suspend fun allWorkoutRecords(): List<WorkoutRecord> =
        withContext(dispatcher) { _repo.allWorkoutRecords }

    suspend fun cyclingRecord(id: Long): CyclingRecord? =
        withContext(dispatcher) { _repo.getCyclingRecord(id) }

    suspend fun runningRecord(id: Long): RunningRecord? =
        withContext(dispatcher) { _repo.getRunningRecord(id) }

    suspend fun walkingRecord(id: Long): WalkingRecord? =
        withContext(dispatcher) { _repo.getWalkingRecord(id) }

    suspend fun workoutRecord(id: Long): WorkoutRecord? =
        withContext(dispatcher) { _repo.getWorkoutRecord(id) }

    suspend fun insert(record: ActivityRecord): Boolean =
        withContext(dispatcher) { _repo.insert(record) }

    suspend fun update(record: ActivityRecord): Boolean =
        withContext(dispatcher) { _repo.update(record) }

    suspend fun delete(record: ActivityRecord): Boolean =
        withContext(dispatcher) { _repo.delete(record) }

    companion object : SingletonHolder<ActivityRecordsRepoKt, ContentResolver>({
        ActivityRecordsRepoKt(ActivityRecordsRepo.getInstance(it))
    })
}
