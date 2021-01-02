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

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.common.Metric
import org.lineageos.mod.health.common.db.RecordColumns
import org.lineageos.mod.health.sdk.model.records.breathing.InhalerUsageRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo
import org.lineageos.mod.health.sdk.repo.OperationResult

@RunWith(AndroidJUnit4::class)
class BreathingRecordsTest {
    private lateinit var cr: ContentResolver
    private lateinit var repo: BreathingRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        cr = context.contentResolver
        repo = BreathingRecordsRepo.getInstance(cr)
    }

    @Test
    fun invalidGet() {
        val a = InhalerUsageRecord(
            0L,
            System.currentTimeMillis(),
            "A note",
        )
        val idA = (repo.insert(a) as OperationResult.Success<*>).result as Long
        Assert.assertNull(repo.getOxygenSaturationRecord(idA))
        val fromDb = repo.getInhalerUsageRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }
        Assert.assertTrue(repo.delete(fromDb) is OperationResult.Success<*>)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidInsert() {
        val cv = ContentValues().apply {
            put(RecordColumns._METRIC, Metric.VITAL_CAPACITY)
            put(RecordColumns.TIME, System.currentTimeMillis())
        }

        val invalidUri = Uri.withAppendedPath(
            HealthStoreUri.BREATHING,
            "${Metric.OXYGEN_SATURATION}"
        )
        cr.insert(invalidUri, cv)
        Assert.fail("Did not throw IllegalArgumentException")
    }
}
