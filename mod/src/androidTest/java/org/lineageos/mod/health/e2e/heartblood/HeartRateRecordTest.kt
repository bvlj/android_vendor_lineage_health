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

package org.lineageos.mod.health.e2e.heartblood

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartBloodRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartRateRecord
import org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo

@RunWith(AndroidJUnit4::class)
class HeartRateRecordTest : RecordTest<HeartBloodRecord, HeartRateRecord, HeartBloodRecordsRepo>() {

    override fun getRepo(context: Context): HeartBloodRecordsRepo {
        return HeartBloodRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): HeartRateRecord? {
        return repo.getHeartRateRecord(id)
    }

    override fun getAllInMetric(): List<HeartRateRecord> {
        return repo.allHeartRateRecords
    }

    override fun testRecordA(): HeartRateRecord {
        return HeartRateRecord(
            0L,
            System.currentTimeMillis(),
            77.0,
        )
    }

    override fun testRecordB(): HeartRateRecord {
        return HeartRateRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            104.0,
        )
    }

    override fun invalidRecord(): HeartRateRecord {
        return HeartRateRecord(
            0L,
            System.currentTimeMillis(),
            -12.0,
        )
    }

    override fun updateTestRecord(record: HeartRateRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value += 12.0
        }
    }
}
