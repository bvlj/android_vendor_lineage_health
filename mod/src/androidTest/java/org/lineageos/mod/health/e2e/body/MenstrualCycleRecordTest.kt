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

package org.lineageos.mod.health.e2e.body

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import org.lineageos.mod.health.common.values.MenstrualCycleOtherSymptoms
import org.lineageos.mod.health.common.values.MenstrualCyclePhysicalSymptoms
import org.lineageos.mod.health.common.values.SexualActivity
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.body.BodyRecord
import org.lineageos.mod.health.sdk.model.records.body.MenstrualCycleRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo

@RunWith(AndroidJUnit4::class)
class MenstrualCycleRecordTest :
    RecordTest<BodyRecord<*>, MenstrualCycleRecord, BodyRecordsRepo>() {

    override fun getRepo(context: Context): BodyRecordsRepo {
        return BodyRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): MenstrualCycleRecord? {
        return repo.getMenstrualCycleRecord(id)
    }

    override fun getAllInMetric(): List<MenstrualCycleRecord> {
        return repo.allMenstrualCycleRecords
    }

    override fun testRecordA(): MenstrualCycleRecord {
        return MenstrualCycleRecord(
            0L,
            System.currentTimeMillis(),
            MenstrualCycleOtherSymptoms.MOOD_SWINGS or
                MenstrualCycleOtherSymptoms.POOR_CONCENTRATION,
            MenstrualCyclePhysicalSymptoms.FATIGUE,
            SexualActivity.NONE,
            2.0
        )
    }

    override fun testRecordB(): MenstrualCycleRecord {
        return MenstrualCycleRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            MenstrualCycleOtherSymptoms.HIGH_SEX_DRIVE,
            MenstrualCyclePhysicalSymptoms.CRAMPS,
            SexualActivity.SEX,
            3.0
        )
    }

    override fun invalidRecord(): MenstrualCycleRecord {
        return MenstrualCycleRecord(
            0L,
            -1L,
            1 shl 11 or 1 shl 3,
            1 shl 10 or 1 shl 5,
            1 shl 6,
            -2.0
        )
    }

    override fun updateTestRecord(record: MenstrualCycleRecord) {
        record.apply {
            otherSymptoms = otherSymptoms or MenstrualCycleOtherSymptoms.ANXIETY
            value += 0.03
        }
    }
}
