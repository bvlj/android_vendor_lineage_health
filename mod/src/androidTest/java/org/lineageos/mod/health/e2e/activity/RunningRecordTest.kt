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
import org.lineageos.mod.health.sdk.model.records.activity.RunningRecord
import org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo

@RunWith(AndroidJUnit4::class)
class RunningRecordTest : RecordTest<ActivityRecord, RunningRecord, ActivityRecordsRepo>() {

    override fun getRepo(context: Context): ActivityRecordsRepo {
        return ActivityRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): RunningRecord? {
        return repo.getRunningRecord(id)
    }

    override fun getAllInMetric(): List<RunningRecord> {
        return repo.allRunningRecords
    }

    override fun testRecordA(): RunningRecord {
        return RunningRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            6.5,
            50.0,
        )
    }

    override fun testRecordB(): RunningRecord {
        return RunningRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            45L,
            1.47,
            0.08
        )
    }

    override fun invalidRecord(): RunningRecord {
        return RunningRecord(
            0L,
            System.currentTimeMillis(),
            4L,
            -88.2,
            -33.3,
        )
    }

    override fun updateTestRecord(record: RunningRecord) {
        record.apply {
            time = System.currentTimeMillis() - 90L
            avgSpeed -= 0.4
        }
    }
}
