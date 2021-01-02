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

package org.lineageos.mod.health.e2e.mindfulness

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.mindfulness.MeditationRecord
import org.lineageos.mod.health.sdk.model.records.mindfulness.MindfulnessRecord
import org.lineageos.mod.health.sdk.repo.MindfulnessRecordsRepo

@RunWith(AndroidJUnit4::class)
class MeditationRecordTest :
    RecordTest<MindfulnessRecord, MeditationRecord, MindfulnessRecordsRepo>() {

    override fun getRepo(context: Context): MindfulnessRecordsRepo {
        return MindfulnessRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): MeditationRecord? {
        return repo.getMeditationRecord(id)
    }

    override fun getAllInMetric(): List<MeditationRecord> {
        return repo.allMeditationRecords
    }

    override fun testRecordA(): MeditationRecord {
        return MeditationRecord(
            0L,
            System.currentTimeMillis(),
            1580000,
        )
    }

    override fun testRecordB(): MeditationRecord {
        return MeditationRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            6503011,
        )
    }

    override fun invalidRecord(): MeditationRecord {
        return MeditationRecord(
            0L,
            System.currentTimeMillis(),
            -8L
        )
    }

    override fun updateTestRecord(record: MeditationRecord) {
        record.apply {
            time = System.currentTimeMillis()
            duration += 100000
        }
    }
}
