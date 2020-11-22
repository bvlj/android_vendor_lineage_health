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
import org.lineageos.mod.health.sdk.model.records.body.BodyTemperatureRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo

@RunWith(AndroidJUnit4::class)
class BodyRecordsTest {
    private lateinit var cr: ContentResolver
    private lateinit var repo: BodyRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        cr = context.contentResolver
        repo = BodyRecordsRepo.getInstance(cr)
    }

    @Test
    fun invalidGet() {
        val a = BodyTemperatureRecord(
            0L,
            System.currentTimeMillis(),
            35.2,
        )
        val idA = repo.insert(a)
        Assert.assertNull(repo.getAbdominalCircumferenceRecord(idA))
        val fromDb = repo.getBodyTemperatureRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }
        Assert.assertTrue(repo.delete(fromDb))
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidInsert() {
        val cv = ContentValues().apply {
            put(RecordColumns._METRIC, Metric.WATER_INTAKE)
            put(RecordColumns.TIME, System.currentTimeMillis())
        }

        val invalidUri = Uri.withAppendedPath(HealthStoreUri.BODY, "${Metric.LEAN_BODY_MASS}")
        cr.insert(invalidUri, cv)
        Assert.fail("Did not throw IllegalArgumentException")
    }
}
