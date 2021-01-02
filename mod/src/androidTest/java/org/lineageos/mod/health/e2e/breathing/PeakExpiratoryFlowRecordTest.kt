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
import org.lineageos.mod.health.sdk.model.records.breathing.PeakExpiratoryFlowRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo

@RunWith(AndroidJUnit4::class)
class PeakExpiratoryFlowRecordTest :
    RecordTest<BreathingRecord, PeakExpiratoryFlowRecord, BreathingRecordsRepo>() {

    override fun getRepo(context: Context): BreathingRecordsRepo {
        return BreathingRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): PeakExpiratoryFlowRecord? {
        return repo.getPeakExpiratoryFlowRecord(id)
    }

    override fun getAllInMetric(): List<PeakExpiratoryFlowRecord> {
        return repo.allPeakExpiratoryFlowRecords
    }

    override fun testRecordA(): PeakExpiratoryFlowRecord {
        return PeakExpiratoryFlowRecord(
            0L,
            System.currentTimeMillis(),
            620.0,
        )
    }

    override fun testRecordB(): PeakExpiratoryFlowRecord {
        return PeakExpiratoryFlowRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            536.0,
        )
    }

    override fun invalidRecord(): PeakExpiratoryFlowRecord {
        return PeakExpiratoryFlowRecord(
            0L,
            -1L,
            -101.0
        )
    }

    override fun updateTestRecord(record: PeakExpiratoryFlowRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value -= 10
        }
    }
}
