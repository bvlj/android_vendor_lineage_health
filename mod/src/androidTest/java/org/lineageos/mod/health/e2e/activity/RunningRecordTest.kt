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

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.sdk.model.records.activity.RunningRecord
import org.lineageos.mod.health.sdk.repo.ActivityRecordsRepo
import org.lineageos.mod.health.validators.Validator

@RunWith(AndroidJUnit4::class)
class RunningRecordTest {
    private lateinit var repo: ActivityRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = ActivityRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allRunningRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allRunningRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allRunningRecords.size)
    }

    @Test
    fun testInsert() {
        val a = RunningRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            6.5,
            50.0,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getRunningRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = RunningRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            6.5,
            50.0,
        )
        val b = RunningRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            45L,
            1.47,
            0.08
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getRunningRecord(idA))
        Assert.assertEquals(b, repo.getRunningRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = RunningRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            6.5,
            50.0,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getRunningRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis() - 90L
            avgSpeed -= 0.4
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getRunningRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = RunningRecord(
            0L,
            System.currentTimeMillis(),
            1000L,
            6.5,
            50.0,
        )
        val initialSize = repo.allRunningRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allRunningRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getRunningRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allRunningRecords.size)
        Assert.assertNull(repo.getRunningRecord(idA))
    }

    @Test(expected = Validator.ValidationException::class)
    fun testValidator() {
        repo.insert(RunningRecord(
            0L,
            System.currentTimeMillis(),
            4L,
            -88.2,
            -33.3,
        ))
        Assert.fail()
    }
}
