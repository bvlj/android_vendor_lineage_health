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
import org.lineageos.mod.health.common.values.MoodLevel
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.mindfulness.MindfulnessRecord
import org.lineageos.mod.health.sdk.model.records.mindfulness.MoodRecord
import org.lineageos.mod.health.sdk.repo.MindfulnessRecordsRepo

@RunWith(AndroidJUnit4::class)
class MoodRecordTest : RecordTest<MindfulnessRecord, MoodRecord, MindfulnessRecordsRepo>() {

    override fun getRepo(context: Context): MindfulnessRecordsRepo {
        return MindfulnessRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): MoodRecord? {
        return repo.getMoodRecord(id)
    }

    override fun getAllInMetric(): List<MoodRecord> {
        return repo.allMoodRecords
    }

    override fun testRecordA(): MoodRecord {
        return MoodRecord(
            0L,
            System.currentTimeMillis(),
            MoodLevel.EXCITED or MoodLevel.HAPPY,
            "Excited and happy now"
        )
    }

    override fun testRecordB(): MoodRecord {
        return MoodRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            MoodLevel.NERVOUS or MoodLevel.TIRED or MoodLevel.STRESSED,
            "Feeling bad now"
        )
    }

    override fun invalidRecord(): MoodRecord {
        return MoodRecord(
            0L,
            System.currentTimeMillis(),
            1 shl 11 or 1 shl 8,
            "Valid note"
        )
    }

    override fun updateTestRecord(record: MoodRecord) {
        record.apply {
            time = System.currentTimeMillis()
            moodLevel = MoodLevel.ANGRY
            notes = "It didn't go as expected"
        }
    }
}
