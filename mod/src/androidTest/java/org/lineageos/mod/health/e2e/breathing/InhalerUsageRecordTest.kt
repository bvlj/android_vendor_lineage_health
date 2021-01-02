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
import org.lineageos.mod.health.sdk.model.records.breathing.InhalerUsageRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo

@RunWith(AndroidJUnit4::class)
class InhalerUsageRecordTest :
    RecordTest<BreathingRecord, InhalerUsageRecord, BreathingRecordsRepo>() {

    override fun getRepo(context: Context): BreathingRecordsRepo {
        return BreathingRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): InhalerUsageRecord? {
        return repo.getInhalerUsageRecord(id)
    }

    override fun getAllInMetric(): List<InhalerUsageRecord> {
        return repo.allInhalerUsageRecords
    }

    override fun testRecordA(): InhalerUsageRecord {
        return InhalerUsageRecord(
            0L,
            System.currentTimeMillis(),
            "A note",
        )
    }

    override fun testRecordB(): InhalerUsageRecord {
        return InhalerUsageRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            "Another note",
        )
    }

    override fun invalidRecord(): InhalerUsageRecord {
        return InhalerUsageRecord(
            0L,
            -1L,
            "Valid note",
        )
    }

    override fun updateTestRecord(record: InhalerUsageRecord) {
        record.apply {
            time = System.currentTimeMillis()
            notes = "A better note"
        }
    }
}
