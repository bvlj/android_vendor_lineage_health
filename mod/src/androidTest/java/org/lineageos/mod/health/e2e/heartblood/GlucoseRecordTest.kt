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
import org.lineageos.mod.health.common.values.MealRelation
import org.lineageos.mod.health.e2e.RecordTest
import org.lineageos.mod.health.sdk.model.records.heartblood.GlucoseRecord
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartBloodRecord
import org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo

@RunWith(AndroidJUnit4::class)
class GlucoseRecordTest : RecordTest<HeartBloodRecord, GlucoseRecord, HeartBloodRecordsRepo>() {

    override fun getRepo(context: Context): HeartBloodRecordsRepo {
        return HeartBloodRecordsRepo.getInstance(context.contentResolver)
    }

    override fun getById(id: Long): GlucoseRecord? {
        return repo.getGlucoseRecord(id)
    }

    override fun getAllInMetric(): List<GlucoseRecord> {
        return repo.allGlucoseRecords
    }

    override fun testRecordA(): GlucoseRecord {
        return GlucoseRecord(
            0L,
            System.currentTimeMillis(),
            MealRelation.BEFORE,
            123.0
        )
    }

    override fun testRecordB(): GlucoseRecord {
        return GlucoseRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            MealRelation.AFTER,
            224.0
        )
    }

    override fun invalidRecord(): GlucoseRecord {
        return GlucoseRecord(
            0L,
            System.currentTimeMillis(),
            1 shl 7,
            -81.0
        )
    }

    override fun updateTestRecord(record: GlucoseRecord) {
        record.apply {
            time = System.currentTimeMillis()
            value += 50.0
        }
    }
}
