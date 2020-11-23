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

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.sdk.model.records.body.WaterIntakeRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo
import org.lineageos.mod.health.validators.Validator

@RunWith(AndroidJUnit4::class)
class WaterIntakeRecordTest {
    private lateinit var repo: BodyRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = BodyRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allWaterIntakeRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allWaterIntakeRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allWaterIntakeRecords.size)
    }

    @Test
    fun testInsert() {
        val a = WaterIntakeRecord(
            0L,
            System.currentTimeMillis(),
            "Half glass of water",
            0.5
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getWaterIntakeRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = WaterIntakeRecord(
            0L,
            System.currentTimeMillis(),
            "Half glass of water",
            0.5
        )
        val b = WaterIntakeRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            "Two glasses of water",
            2.0
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getWaterIntakeRecord(idA))
        Assert.assertEquals(b, repo.getWaterIntakeRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = WaterIntakeRecord(
            0L,
            System.currentTimeMillis(),
            "Half glass of water",
            0.5
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getWaterIntakeRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            notes = "A glass of water"
            value = 1.0
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getWaterIntakeRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = WaterIntakeRecord(
            0L,
            System.currentTimeMillis(),
            "Half glass of water",
            0.5
        )
        val initialSize = repo.allWaterIntakeRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allWaterIntakeRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getWaterIntakeRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allWaterIntakeRecords.size)
        Assert.assertNull(repo.getWaterIntakeRecord(idA))
    }

    @Test(expected = Validator.ValidationException::class)
    fun testValidator() {
        repo.insert(
            WaterIntakeRecord(
                0L,
                System.currentTimeMillis(),
                "Peed half glass of water",
                -0.5
            )
        )
        Assert.fail()
    }
}
