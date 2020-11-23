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
import org.lineageos.mod.health.sdk.model.records.breathing.OxygenSaturationRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo
import org.lineageos.mod.health.validators.Validator

@RunWith(AndroidJUnit4::class)
class OxygenSaturationRecordTest {
    private lateinit var repo: BreathingRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = BreathingRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allOxygenSaturationRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allOxygenSaturationRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allOxygenSaturationRecords.size)
    }

    @Test
    fun testInsert() {
        val a = OxygenSaturationRecord(
            0L,
            System.currentTimeMillis(),
            0.96,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getOxygenSaturationRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = OxygenSaturationRecord(
            0L,
            System.currentTimeMillis(),
            0.96,
        )
        val b = OxygenSaturationRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            0.929,
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getOxygenSaturationRecord(idA))
        Assert.assertEquals(b, repo.getOxygenSaturationRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = OxygenSaturationRecord(
            0L,
            System.currentTimeMillis(),
            0.96,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getOxygenSaturationRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            value += 0.04
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getOxygenSaturationRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = OxygenSaturationRecord(
            0L,
            System.currentTimeMillis(),
            0.96,
        )
        val initialSize = repo.allOxygenSaturationRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allOxygenSaturationRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getOxygenSaturationRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allOxygenSaturationRecords.size)
        Assert.assertNull(repo.getOxygenSaturationRecord(idA))
    }

    @Test(expected = Validator.ValidationException::class)
    fun testValidator() {
        repo.insert(
            OxygenSaturationRecord(
                0L,
                System.currentTimeMillis(),
                1.1
            )
        )
        Assert.fail()
    }
}
