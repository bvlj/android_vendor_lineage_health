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

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.sdk.model.records.breathing.VitalCapacityRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo

@RunWith(AndroidJUnit4::class)
class VitalCapacityRecordTest {
    private lateinit var repo: BreathingRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = BreathingRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allVitalCapacityRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allVitalCapacityRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allVitalCapacityRecords.size)
    }

    @Test
    fun testInsert() {
        val a = VitalCapacityRecord(
            0L,
            System.currentTimeMillis(),
            3225.0,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getVitalCapacityRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = VitalCapacityRecord(
            0L,
            System.currentTimeMillis(),
            3225.0,
        )
        val b = VitalCapacityRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            3500.0,
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getVitalCapacityRecord(idA))
        Assert.assertEquals(b, repo.getVitalCapacityRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = VitalCapacityRecord(
            0L,
            System.currentTimeMillis(),
            3225.0,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getVitalCapacityRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            value -= 100
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getVitalCapacityRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = VitalCapacityRecord(
            0L,
            System.currentTimeMillis(),
            3225.0,
        )
        val initialSize = repo.allVitalCapacityRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allVitalCapacityRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getVitalCapacityRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allVitalCapacityRecords.size)
        Assert.assertNull(repo.getVitalCapacityRecord(idA))
    }

    @Test
    fun testValidator() {
        val a = VitalCapacityRecord(
            0L,
            -1L,
            -82.4
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getVitalCapacityRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertNotEquals(a.time, fromDb.time)
        Assert.assertEquals(0.0, fromDb.value, 0.0)
    }
}
