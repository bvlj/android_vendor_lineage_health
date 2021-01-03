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

package org.lineageos.mod.health.e2e.breathing

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.breathing.BreathingRecord
import org.lineageos.mod.health.sdk.model.records.breathing.RespiratoryRateRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo

@RunWith(AndroidJUnit4::class)
class RespiratoryRateRecordTest :
    RecordTest<BreathingRecord, RespiratoryRateRecord, BreathingRecordsRepo>() {

    override fun getRepo(context: Context): BreathingRecordsRepo {
        return BreathingRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): RespiratoryRateRecord? {
        return repo.getRespiratoryRateRecord(id)
    }

    override fun getAllInMetric(): List<RespiratoryRateRecord> {
        return repo.allRespiratoryRateRecords
    }

    override fun testRecordA(): RespiratoryRateRecord {
        return RespiratoryRateRecord(
            0L,
            System.currentTimeMillis(),
            15.0,
        )
    }

    override fun testRecordB(): RespiratoryRateRecord {
        return RespiratoryRateRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            18.1,
        )
    }

    override fun invalidRecord(): RespiratoryRateRecord {
        return RespiratoryRateRecord(
            0L,
            System.currentTimeMillis() - 2000L,
            -2.0
        )
    }

    override fun updateTestRecord(record: RespiratoryRateRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value += 0.2
        }
    }
}
