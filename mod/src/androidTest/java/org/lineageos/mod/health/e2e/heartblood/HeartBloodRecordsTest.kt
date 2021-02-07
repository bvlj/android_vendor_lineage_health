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

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.common.CareCacheUri
import org.lineageos.mod.health.common.Metric
import org.lineageos.mod.health.common.db.RecordColumns
import org.lineageos.mod.health.sdk.model.records.heartblood.HeartRateRecord
import org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo
import org.lineageos.mod.health.sdk.repo.OperationResult

@RunWith(AndroidJUnit4::class)
class HeartBloodRecordsTest {
    private lateinit var cr: ContentResolver
    private lateinit var repo: HeartBloodRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        cr = context.contentResolver
        repo = HeartBloodRecordsRepo.getInstance(cr)
    }

    @Test
    fun invalidGet() {
        val a = HeartRateRecord(
            0L,
            System.currentTimeMillis(),
            77.0,
        )
        val idA = (repo.insert(a) as OperationResult.Success<*>).result as Long
        Assert.assertNull(repo.getGlucoseRecord(idA))
        val fromDb = repo.getHeartRateRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }
        Assert.assertTrue(repo.delete(fromDb) is OperationResult.Success<*>)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidInsert() {
        val cv = ContentValues().apply {
            put(RecordColumns._METRIC, Metric.BLOOD_ALCOHOL_CONCENTRATION)
            put(RecordColumns.TIME, System.currentTimeMillis())
        }

        val invalidUri = Uri.withAppendedPath(
            CareCacheUri.HEART_BLOOD,
            "${Metric.PERFUSION_INDEX}"
        )
        cr.insert(invalidUri, cv)
        Assert.fail("Did not throw IllegalArgumentException")
    }
}
