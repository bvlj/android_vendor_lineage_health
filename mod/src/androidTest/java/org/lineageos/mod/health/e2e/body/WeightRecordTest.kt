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
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.body.BodyRecord
import org.lineageos.mod.health.sdk.model.records.body.WeightRecord
import org.lineageos.mod.health.sdk.model.values.MassValue
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo

@RunWith(AndroidJUnit4::class)
class WeightRecordTest : RecordTest<BodyRecord<*>, WeightRecord, BodyRecordsRepo>() {

    override fun getRepo(context: Context): BodyRecordsRepo {
        return BodyRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): WeightRecord? {
        return repo.getWeightRecord(id)
    }

    override fun getAllInMetric(): List<WeightRecord> {
        return repo.allWeightRecords
    }

    override fun testRecordA(): WeightRecord {
        return WeightRecord(
            0L,
            System.currentTimeMillis(),
            MassValue.kilograms(71.3)
        )
    }

    override fun testRecordB(): WeightRecord {
        return WeightRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            MassValue.kilograms(87.6)
        )
    }

    override fun invalidRecord(): WeightRecord {
        return WeightRecord(
            0L,
            System.currentTimeMillis(),
            MassValue.kilograms(-6.8)
        )
    }

    override fun updateTestRecord(record: WeightRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value += MassValue.kilograms(2.01)
        }
    }
}
