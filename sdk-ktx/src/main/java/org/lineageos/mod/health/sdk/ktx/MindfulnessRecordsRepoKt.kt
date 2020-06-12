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
import org.lineageos.mod.health.sdk.model.records.mindfulness.MeditationRecord
import org.lineageos.mod.health.sdk.model.records.mindfulness.MindfulnessRecord
import org.lineageos.mod.health.sdk.model.records.mindfulness.MoodRecord
import org.lineageos.mod.health.sdk.model.records.mindfulness.SleepRecord
import org.lineageos.mod.health.sdk.repo.MindfulnessRecordsRepo
import org.lineageos.mod.util.SingletonHolder

/**
 * Wrapper for [MindfulnessRecordsRepo]
 * that makes use of coroutines
 */
@Keep
@Suppress("unused")
class MindfulnessRecordsRepoKt private constructor(
    private val _repo: MindfulnessRecordsRepo
) {
    private val dispatcher = Dispatchers.IO + CoroutineName("mindfulness")

    suspend fun all(): List<MindfulnessRecord> =
        withContext(dispatcher) { _repo.all }

    suspend fun allMeditationRecords(): List<MeditationRecord> =
        withContext(dispatcher) { _repo.allMeditationRecords }

    suspend fun allMoodRecords(): List<MoodRecord> =
        withContext(dispatcher) { _repo.allMoodRecords }

    suspend fun allSleepRecord(): List<SleepRecord> =
        withContext(dispatcher) { _repo.allSleepRecords }

    suspend fun meditationRecord(id: Long): MeditationRecord? =
        withContext(dispatcher) { _repo.getMeditationRecord(id) }

    suspend fun moodRecord(id: Long): MoodRecord? =
        withContext(dispatcher) { _repo.getMoodRecord(id) }

    suspend fun sleepRecord(id: Long): SleepRecord? =
        withContext(dispatcher) { _repo.getSleepRecord(id) }

    suspend fun insert(record: MindfulnessRecord): Boolean =
        withContext(dispatcher) { _repo.insert(record) }

    suspend fun update(record: MindfulnessRecord): Boolean =
        withContext(dispatcher) { _repo.update(record) }

    suspend fun delete(record: MindfulnessRecord): Boolean =
        withContext(dispatcher) { _repo.delete(record) }

    companion object : SingletonHolder<MindfulnessRecordsRepoKt, ContentResolver>({
        MindfulnessRecordsRepoKt(MindfulnessRecordsRepo.getInstance(it))
    })
}
