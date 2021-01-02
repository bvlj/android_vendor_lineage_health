/*
 * Copyright (C) 2021 The LineageOS Project
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

package org.lineageos.mod.health.e2e

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.lineageos.mod.health.sdk.model.records.Record
import org.lineageos.mod.health.sdk.repo.OperationResult
import org.lineageos.mod.health.sdk.repo.RecordsRepo
import org.lineageos.mod.health.validators.Validator

@Ignore("Abstract class")
abstract class RecordTest<CATEGORY : Record, METRIC : CATEGORY, REPO : RecordsRepo<CATEGORY>> {
    protected lateinit var repo: REPO

    protected abstract fun getRepo(context: Context): REPO

    protected abstract fun getById(id: Long): METRIC?

    protected abstract fun getAllInMetric(): List<METRIC>

    protected abstract fun testRecordA(): METRIC

    protected abstract fun testRecordB(): METRIC

    protected abstract fun invalidRecord(): METRIC

    protected abstract fun updateTestRecord(record: METRIC)

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repo = getRepo(context)

        getAllInMetric().forEach(repo::delete)
    }

    @After
    fun tearDown() {
        getAllInMetric().forEach(repo::delete)
        Assert.assertEquals(0, getAllInMetric().size)
    }

    @Test
    fun testInsert() {
        val record = testRecordA()
        val idA = (repo.insert(record) as OperationResult.Success<*>).result as Long
        Assert.assertNotEquals(-1L, idA)
        Assert.assertEquals(record, getById(idA))
    }

    @Test
    fun testMultipleInsert() {
        val a = testRecordA()
        val b = testRecordB()
        val idA = (repo.insert(a) as OperationResult.Success<*>).result as Long
        val idB = (repo.insert(b) as OperationResult.Success<*>).result as Long

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)
        Assert.assertNotEquals(idA, idB)

        Assert.assertEquals(a, getById(idA))
        Assert.assertEquals(b, getById(idB))
    }

    @Test
    fun testUpdate() {
        val record = testRecordA()
        val idA = (repo.insert(record) as OperationResult.Success<*>).result as Long
        Assert.assertNotEquals(-1L, idA)
        val fromDb = getById(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
            return
        }

        Assert.assertEquals(record, fromDb)
        updateTestRecord(record)

        Assert.assertNotEquals(record, fromDb)
        Assert.assertTrue(repo.update(fromDb) is OperationResult.Success<*>)
        Assert.assertEquals(fromDb, getById(idA))
    }

    @Test
    fun testDelete() {
        val record = testRecordA()
        val initialSize = getAllInMetric().size
        val idA = (repo.insert(record) as OperationResult.Success<*>).result as Long
        val finalSize = getAllInMetric().size

        Assert.assertNotEquals(-1L, idA)
        Assert.assertTrue(finalSize > initialSize)

        val fromDb = getById(idA)
        if (fromDb == null) {
            Assert.fail("fromDb == null")
        } else {
            repo.delete(fromDb)
        }

        Assert.assertEquals(finalSize - 1, getAllInMetric().size)
        Assert.assertNull(getById(idA))
    }

    @Test(expected = Validator.ValidationException::class)
    fun testValidator() {
        repo.insert(invalidRecord())
        Assert.fail()
    }
}
