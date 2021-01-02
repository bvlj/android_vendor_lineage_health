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
import org.lineageos.mod.health.sdk.model.records.heartblood.BloodAlcoholConcentrationRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartBloodRecord
import org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo

@RunWith(AndroidJUnit4::class)
class BloodAlcoholConcentrationRecordTest :
    RecordTest<HeartBloodRecord, BloodAlcoholConcentrationRecord, HeartBloodRecordsRepo>() {

    override fun getRepo(context: Context): HeartBloodRecordsRepo {
        return HeartBloodRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): BloodAlcoholConcentrationRecord? {
        return repo.getBloodAlcoholConcentrationRecord(id)
    }

    override fun getAllInMetric(): List<BloodAlcoholConcentrationRecord> {
        return repo.allBloodAlcoholConcentrationRecords
    }

    override fun testRecordA(): BloodAlcoholConcentrationRecord {
        return BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis(),
            0.05,
        )
    }

    override fun testRecordB(): BloodAlcoholConcentrationRecord {
        return BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            0.12,
        )
    }

    override fun invalidRecord(): BloodAlcoholConcentrationRecord {
        return BloodAlcoholConcentrationRecord(
            0L,
            -1L,
            1.1
        )
    }

    override fun updateTestRecord(record: BloodAlcoholConcentrationRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value += 0.9
        }
    }
}
