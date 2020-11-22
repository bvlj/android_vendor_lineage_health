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

package org.lineageos.mod.health.e2e.mindfulness

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.sdk.model.records.mindfulness.SleepRecord
import org.lineageos.mod.health.sdk.repo.MindfulnessRecordsRepo

@RunWith(AndroidJUnit4::class)
class SleepRecordTest {
    private lateinit var repo: MindfulnessRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = MindfulnessRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allSleepRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allSleepRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allSleepRecords.size)
    }

    @Test
    fun testInsert() {
        val a = SleepRecord(
            0L,
            System.currentTimeMillis(),
            1580000,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getSleepRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = SleepRecord(
            0L,
            System.currentTimeMillis(),
            1580000,
        )
        val b = SleepRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            6503011,
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getSleepRecord(idA))
        Assert.assertEquals(b, repo.getSleepRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = SleepRecord(
            0L,
            System.currentTimeMillis(),
            1580000,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getSleepRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            duration += 100000
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getSleepRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = SleepRecord(
            0L,
            System.currentTimeMillis(),
            1580000,
        )
        val initialSize = repo.allSleepRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allSleepRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getSleepRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allSleepRecords.size)
        Assert.assertNull(repo.getSleepRecord(idA))
    }

    @Test
    fun testValidator() {
        val a = SleepRecord(
            0L,
            -1L,
            -8L,
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getSleepRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertNotEquals(a.time, fromDb.time)
        Assert.assertEquals(0L, fromDb.duration)
    }
}
