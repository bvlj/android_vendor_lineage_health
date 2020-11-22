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
import org.lineageos.mod.health.sdk.model.records.breathing.InhalerUsageRecord
import org.lineageos.mod.health.sdk.repo.BreathingRecordsRepo

@RunWith(AndroidJUnit4::class)
class InhalerUsageRecordTest {
    private lateinit var repo: BreathingRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = BreathingRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allInhalerUsageRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allInhalerUsageRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allInhalerUsageRecords.size)
    }

    @Test
    fun testInsert() {
        val a = InhalerUsageRecord(
            0L,
            System.currentTimeMillis(),
            "A note",
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getInhalerUsageRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = InhalerUsageRecord(
            0L,
            System.currentTimeMillis(),
            "A note",
        )
        val b = InhalerUsageRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            "Another note",
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getInhalerUsageRecord(idA))
        Assert.assertEquals(b, repo.getInhalerUsageRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = InhalerUsageRecord(
            0L,
            System.currentTimeMillis(),
            "A note",
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getInhalerUsageRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            notes = "A better note"
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getInhalerUsageRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = InhalerUsageRecord(
            0L,
            System.currentTimeMillis(),
            "A note",
        )
        val initialSize = repo.allInhalerUsageRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allInhalerUsageRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getInhalerUsageRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allInhalerUsageRecords.size)
        Assert.assertNull(repo.getInhalerUsageRecord(idA))
    }

    @Test
    fun testValidator() {
        val a = InhalerUsageRecord(
            0L,
            -1L,
            "Valid note",
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getInhalerUsageRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertNotEquals(a.time, fromDb.time)
        Assert.assertEquals(a.notes, fromDb.notes)
    }
}
