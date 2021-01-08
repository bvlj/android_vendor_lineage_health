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
import org.lineageos.mod.health.sdk.model.records.body.LeanBodyMassRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo

@RunWith(AndroidJUnit4::class)
class LeanBodyMassRecordTest :
    RecordTest<BodyRecord<*>, LeanBodyMassRecord, BodyRecordsRepo>() {

    override fun getRepo(context: Context): BodyRecordsRepo {
        return BodyRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): LeanBodyMassRecord? {
        return repo.getLeanBodyMassRecord(id)
    }

    override fun getAllInMetric(): List<LeanBodyMassRecord> {
        return repo.allLeanBodyMassRecords
    }

    override fun testRecordA(): LeanBodyMassRecord {
        return LeanBodyMassRecord(
            0L,
            System.currentTimeMillis(),
            0.8,
        )
    }

    override fun testRecordB(): LeanBodyMassRecord {
        return LeanBodyMassRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            0.74,
        )
    }

    override fun invalidRecord(): LeanBodyMassRecord {
        return LeanBodyMassRecord(
            0L,
            System.currentTimeMillis(),
            11.8,
        )
    }

    override fun updateTestRecord(record: LeanBodyMassRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value += 0.03
        }
    }
}
