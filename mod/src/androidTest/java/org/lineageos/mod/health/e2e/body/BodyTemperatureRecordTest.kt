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
import org.lineageos.mod.health.sdk.model.records.body.BodyTemperatureRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo
import org.lineageos.mod.health.validators.Validator

@RunWith(AndroidJUnit4::class)
class BodyTemperatureRecordTest {
    private lateinit var repo: BodyRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = BodyRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allBodyTemperatureRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allBodyTemperatureRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allBodyTemperatureRecords.size)
    }

    @Test
    fun testInsert() {
        val a = BodyTemperatureRecord(
            0L,
            System.currentTimeMillis(),
            35.2,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getBodyTemperatureRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = BodyTemperatureRecord(
            0L,
            System.currentTimeMillis(),
            35.2,
        )
        val b = BodyTemperatureRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            37.4,
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getBodyTemperatureRecord(idA))
        Assert.assertEquals(b, repo.getBodyTemperatureRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = BodyTemperatureRecord(
            0L,
            System.currentTimeMillis(),
            35.2,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getBodyTemperatureRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            value += 1.51
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getBodyTemperatureRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = BodyTemperatureRecord(
            0L,
            System.currentTimeMillis(),
            35.2,
        )
        val initialSize = repo.allBodyTemperatureRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allBodyTemperatureRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getBodyTemperatureRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allBodyTemperatureRecords.size)
        Assert.assertNull(repo.getBodyTemperatureRecord(idA))
    }

    @Test(expected = Validator.ValidationException::class)
    fun testValidator() {
        repo.insert(
            BodyTemperatureRecord(
                0L,
                System.currentTimeMillis(),
                -6.8,
            )
        )
        Assert.fail()
    }
}
