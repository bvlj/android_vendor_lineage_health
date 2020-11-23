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

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.sdk.model.records.heartblood.BloodAlcoholConcentrationRecord
import org.lineageos.mod.health.sdk.repo.HeartBloodRecordsRepo
import org.lineageos.mod.health.validators.Validator

@RunWith(AndroidJUnit4::class)
class BloodAlcoholConcentrationRecordTest {
    private lateinit var repo: HeartBloodRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = HeartBloodRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allBloodAlcoholConcentrationRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allBloodAlcoholConcentrationRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allBloodAlcoholConcentrationRecords.size)
    }

    @Test
    fun testInsert() {
        val a = BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis(),
            0.05,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getBloodAlcoholConcentrationRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis(),
            0.05,
        )
        val b = BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            0.12,
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getBloodAlcoholConcentrationRecord(idA))
        Assert.assertEquals(b, repo.getBloodAlcoholConcentrationRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis(),
            0.05,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getBloodAlcoholConcentrationRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            value += 0.9
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getBloodAlcoholConcentrationRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = BloodAlcoholConcentrationRecord(
            0L,
            System.currentTimeMillis(),
            0.05,
        )
        val initialSize = repo.allBloodAlcoholConcentrationRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allBloodAlcoholConcentrationRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getBloodAlcoholConcentrationRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allBloodAlcoholConcentrationRecords.size)
        Assert.assertNull(repo.getBloodAlcoholConcentrationRecord(idA))
    }

    @Test(expected = Validator.ValidationException::class)
    fun testValidator() {
        repo.insert(
            BloodAlcoholConcentrationRecord(
                0L,
                -1L,
                1.1
            )
        )
        Assert.fail()
    }
}
