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
import org.lineageos.mod.health.common.values.MoodLevel
import org.lineageos.mod.health.sdk.model.records.mindfulness.MoodRecord
import org.lineageos.mod.health.sdk.repo.MindfulnessRecordsRepo

@RunWith(AndroidJUnit4::class)
class MoodRecordTest {
    private lateinit var repo: MindfulnessRecordsRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = MindfulnessRecordsRepo.getInstance(context.contentResolver)

        // Cleanup
        repo.allMoodRecords.forEach { repo.delete(it) }
    }

    @After
    fun tearDown() {
        repo.allMoodRecords.forEach { repo.delete(it) }
        Assert.assertEquals(0, repo.allMoodRecords.size)
    }

    @Test
    fun testInsert() {
        val a = MoodRecord(
            0L,
            System.currentTimeMillis(),
            MoodLevel.EXCITED or MoodLevel.HAPPY,
            "Excited and happy now"
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(a, repo.getMoodRecord(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = MoodRecord(
            0L,
            System.currentTimeMillis(),
            MoodLevel.EXCITED or MoodLevel.HAPPY,
            "Excited and happy now"
        )
        val b = MoodRecord(
            0L,
            System.currentTimeMillis() - 1000L,
            MoodLevel.NERVOUS or MoodLevel.TIRED or MoodLevel.STRESSED,
            "Feeling bad now"
        )
        val idA = repo.insert(a)
        val idB = repo.insert(b)

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, repo.getMoodRecord(idA))
        Assert.assertEquals(b, repo.getMoodRecord(idB))
    }

    @Test
    fun testUpdate() {
        val a = MoodRecord(
            0L,
            System.currentTimeMillis(),
            MoodLevel.EXCITED or MoodLevel.HAPPY,
            "Excited and happy now"
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getMoodRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(a, fromDb)
        fromDb.apply {
            time = System.currentTimeMillis()
            moodLevel = MoodLevel.ANGRY
            notes = "It didn't go as expected"
        }
        Assert.assertNotEquals(a, fromDb)
        Assert.assertTrue(repo.update(fromDb))
        Assert.assertEquals(fromDb, repo.getMoodRecord(idA))
    }

    @Test
    fun testDelete() {
        val a = MoodRecord(
            0L,
            System.currentTimeMillis(),
            MoodLevel.EXCITED or MoodLevel.HAPPY,
            "Excited and happy now"
        )
        val initialSize = repo.allMoodRecords.size
        val idA = repo.insert(a)
        val finalSize = repo.allMoodRecords.size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = repo.getMoodRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, repo.allMoodRecords.size)
        Assert.assertNull(repo.getMoodRecord(idA))
    }

    @Test
    fun testValidator() {
        val a = MoodRecord(
            0L,
            -1L,
            1 shl 11 or 1 shl 8,
            "Valid note"
        )
        val idA = repo.insert(a)
        Assert.assertNotEquals(-1L, idA)
        val fromDb = repo.getMoodRecord(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertNotEquals(a.time, fromDb.time)
        Assert.assertEquals(0, fromDb.moodLevel)
        Assert.assertEquals(a.notes, fromDb.notes)
    }
}
