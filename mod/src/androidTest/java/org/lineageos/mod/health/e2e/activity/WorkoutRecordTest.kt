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

package org.lineageos.mod.health.e2e.activity

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.activity.ActivityRecord
import org.lineageos.mod.health.sdk.model.records.activity.WorkoutRecord
import org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo

@RunWith(AndroidJUnit4::class)
class WorkoutRecordTest : RecordTest<ActivityRecord, WorkoutRecord, ActivityRecordsRepo>() {

    override fun getRepo(context: Context): ActivityRecordsRepo {
        return ActivityRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): WorkoutRecord? {
        return repo.getWorkoutRecord(id)
    }

    override fun getAllInMetric(): List<WorkoutRecord> {
        return repo.allWorkoutRecords
    }

    override fun testRecordA(): WorkoutRecord {
        return WorkoutRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            65,
            "Intensive workout",
        )
    }

    override fun testRecordB(): WorkoutRecord {
        return WorkoutRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            45L,
            80,
            "Very intensive workout",
        )
    }

    override fun invalidRecord(): WorkoutRecord {
        return WorkoutRecord(
            0L,
            -1L,
            4L,
            88,
            "Valid notes"
        )
    }

    override fun updateTestRecord(record: WorkoutRecord) {
        record.apply {
            time -= 90L
            notes = "Shorter workout"
        }
    }
}
