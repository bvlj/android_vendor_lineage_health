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

package org.lineageos.mod.health.e2e.activity

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
import org.lineageos.mod.health.sdk.model.records.activity.CyclingRecord
import org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo
import org.lineageos.mod.health.sdk.repo.OperationResult
import java.lang.IllegalArgumentException

@RunWith(AndroidJUnit4::class)
class ActivityRecordsTest {
    private lateinit var cr: ContentResolver
    private lateinit var repo: ActivityRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        cr = context.contentResolver
        repo = ActivityRecordsRepo.getInstance(cr)
    }

    @Test
    fun invalidGet() {
        val a = CyclingRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            12.0,
            50.0,
            5.0
        )
        val insertResult = repo.insert(a)
        val idA = (insertResult as OperationResult.Success<*>).result as Long
        Assert.assertNull(repo.getRunningRecord(idA))
        val fromDb = repo.getCyclingRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }
        Assert.assertTrue(repo.delete(fromDb) is OperationResult.Success<*>)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidInsert() {
        val cv = ContentValues().apply {
            put(RecordColumns._METRIC, Metric.WORKOUT)
            put(RecordColumns.TIME, System.currentTimeMillis())
        }

        val invalidUri = Uri.withAppendedPath(HealthStoreUri.HEART_BLOOD, "${Metric.CYCLING}")
        cr.insert(invalidUri, cv)
        Assert.fail("Did not throw IllegalArgumentException")
    }
}
