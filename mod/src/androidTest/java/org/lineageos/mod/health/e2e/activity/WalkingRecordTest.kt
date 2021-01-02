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
import org.lineageos.mod.health.sdk.model.records.activity.WalkingRecord
import org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo

@RunWith(AndroidJUnit4::class)
class WalkingRecordTest : RecordTest<ActivityRecord, WalkingRecord, ActivityRecordsRepo>() {

    override fun getRepo(context: Context): ActivityRecordsRepo {
        return ActivityRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): WalkingRecord? {
        return repo.getWalkingRecord(id)
    }

    override fun getAllInMetric(): List<WalkingRecord> {
        return repo.allWalkingRecords
    }

    override fun testRecordA(): WalkingRecord {
        return WalkingRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            6.5,
            500,
        )
    }

    override fun testRecordB(): WalkingRecord {
        return WalkingRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            45L,
            1.47,
            891,
        )
    }

    override fun invalidRecord(): WalkingRecord {
        return WalkingRecord(
            0L,
            System.currentTimeMillis(),
            4L,
            88.2,
            -44,
        )
    }

    override fun updateTestRecord(record: WalkingRecord) {
        record.apply {
            time = System.currentTimeMillis() - 90L
            steps -= 89
        }
    }
}
