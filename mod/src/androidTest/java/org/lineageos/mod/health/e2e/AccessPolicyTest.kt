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

package org.lineageos.mod.health.e2e

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.common.HealthStoreUri
import org.lineageos.mod.health.common.Metric
import org.lineageos.mod.health.common.db.AccessColumns
import org.lineageos.mod.health.common.values.Permission
import org.lineageos.mod.health.sdk.model.records.body.BodyRecord
import org.lineageos.mod.health.sdk.model.records.body.BodyTemperatureRecord
import org.lineageos.mod.health.sdk.model.records.body.WeightRecord
import org.lineageos.mod.health.sdk.repo.BodyRecordsRepo
import org.lineageos.mod.health.sdk.repo.OperationResult

@RunWith(AndroidJUnit4::class)
class AccessPolicyTest {
    private lateinit var cr: ContentResolver
    private lateinit var bodyRepo: BodyRecordsRepo
    private lateinit var myPkgName: String

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        cr = context.contentResolver
        bodyRepo = BodyRecordsRepo.getInstance(cr)
        myPkgName = context.packageName
    }

    @After
    fun tearDown() {
        cr.delete(
            Uri.withAppendedPath(HealthStoreUri.ACCESS, "all"),
            null,
            null
        )
    }

    @Test
    fun blockRead() {
        // Make weight write-only
        cr.insert(
            HealthStoreUri.ACCESS,
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, myPkgName)
                put(AccessColumns.METRIC, Metric.WEIGHT)
                put(AccessColumns.PERMISSIONS, Permission.WRITE)
            }
        )

        // Should be able to insert…
        val idA = insert(
            WeightRecord(0L, System.currentTimeMillis(), 60.0)
        )
        val idB = insert(
            BodyTemperatureRecord(0L, System.currentTimeMillis(), 36.2)
        )

        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)

        // …and update…
        val updatedRecord = WeightRecord(idA, System.currentTimeMillis(), 67.0)
        Assert.assertTrue(bodyRepo.update(updatedRecord) is OperationResult.Success<*>)

        // …but not to read weight records (only)
        Assert.assertNull(bodyRepo.getWeightRecord(idA))
        Assert.assertNotNull(bodyRepo.getBodyTemperatureRecord(idB))
        Assert.assertTrue(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())

        // Grant permission again
        val path = Uri.withAppendedPath(HealthStoreUri.ACCESS, "$myPkgName/${Metric.WEIGHT}")
        val updated = cr.update(
            path,
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, myPkgName)
                put(AccessColumns.METRIC, Metric.WEIGHT)
                put(AccessColumns.PERMISSIONS, Permission.ALL)
            },
            null, null
        )
        Assert.assertTrue(updated > 0)

        Assert.assertFalse(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())

        updatedRecord.value -= 2.1
        Assert.assertTrue(bodyRepo.update(updatedRecord) is OperationResult.Success<*>)

        Assert.assertEquals(updatedRecord, bodyRepo.getWeightRecord(idA))

        bodyRepo.deleteAll()
        Assert.assertTrue(bodyRepo.all.isEmpty())
        cr.delete(Uri.withAppendedPath(HealthStoreUri.ACCESS, "all"), null, null)
    }

    @Test
    fun blockWrite() {
        // Should be able to insert…
        val idA = insert(
            WeightRecord(0L, System.currentTimeMillis(), 60.0)
        )
        val idB = insert(
            BodyTemperatureRecord(0L, System.currentTimeMillis(), 36.2)
        )
        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)

        // Make weight read-only
        cr.insert(
            HealthStoreUri.ACCESS,
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, myPkgName)
                put(AccessColumns.METRIC, Metric.WEIGHT)
                put(AccessColumns.PERMISSIONS, Permission.READ)
            }
        )

        // Should be able to read…
        val a = bodyRepo.getWeightRecord(idA)
        val b = bodyRepo.getBodyTemperatureRecord(idB)
        if (a == null || b == null) {
            Assert.fail("Failed to insert test values")
            return
        }
        Assert.assertFalse(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())

        // …but not to insert or update weight records…
        a.value += 3.1

        val c = WeightRecord(0L, System.currentTimeMillis(), 88.2)
        Assert.assertTrue(bodyRepo.insert(c) is OperationResult.PolicyError)
        Assert.assertTrue(bodyRepo.update(a) is OperationResult.PolicyError)

        // …but it should be possible to update temperature records
        b.value -= 0.2
        Assert.assertTrue(bodyRepo.update(b) is OperationResult.Success<*>)

        // Grant permission again
        val path = Uri.withAppendedPath(HealthStoreUri.ACCESS, "$myPkgName/${Metric.WEIGHT}")
        val updated = cr.update(
            path,
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, myPkgName)
                put(AccessColumns.METRIC, Metric.WEIGHT)
                put(AccessColumns.PERMISSIONS, Permission.ALL)
            },
            null, null
        )
        Assert.assertTrue(updated > 0)

        // It should be possible to write again
        val idC = insert(c)

        b.value -= 1.0

        Assert.assertNotEquals(-1L, idC)
        Assert.assertTrue(bodyRepo.update(a) is OperationResult.Success<*>)
        Assert.assertTrue(bodyRepo.update(b) is OperationResult.Success<*>)

        // and reading ability should be intact
        Assert.assertFalse(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())
        Assert.assertEquals(c, bodyRepo.getWeightRecord(idC))

        bodyRepo.deleteAll()
        Assert.assertTrue(bodyRepo.all.isEmpty())
        cr.delete(Uri.withAppendedPath(HealthStoreUri.ACCESS, "all"), null, null)
    }

    @Test
    fun blockAll() {
        // Should be able to insert…
        val idA = insert(
            WeightRecord(0L, System.currentTimeMillis(), 60.0)
        )
        val idB = insert(
            BodyTemperatureRecord(0L, System.currentTimeMillis(), 36.2)
        )
        Assert.assertNotEquals(-1L, idA)
        Assert.assertNotEquals(-1L, idB)

        // …and read
        val a = bodyRepo.getWeightRecord(idA)
        val b = bodyRepo.getBodyTemperatureRecord(idB)
        if (a == null || b == null) {
            Assert.fail("Failed to insert test values")
            return
        }
        Assert.assertFalse(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())

        // Revoke weight access
        cr.insert(
            HealthStoreUri.ACCESS,
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, myPkgName)
                put(AccessColumns.METRIC, Metric.WEIGHT)
                put(AccessColumns.PERMISSIONS, Permission.NONE)
            }
        )

        // Shouldn't be able to read weight records…
        Assert.assertNull(bodyRepo.getWeightRecord(idA))
        Assert.assertTrue(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertNotNull(bodyRepo.getBodyTemperatureRecord(idB))
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())

        // …to insert or update weight records…
        a.value += 3.1

        val c = WeightRecord(0L, System.currentTimeMillis(), 88.2)
        Assert.assertTrue(bodyRepo.insert(c) is OperationResult.PolicyError)
        Assert.assertTrue(bodyRepo.update(a) is OperationResult.PolicyError)

        // …but it should be possible to update temperature records…
        b.value -= 0.2
        Assert.assertTrue(bodyRepo.update(b) is OperationResult.Success<*>)

        // Grant permission again
        val path = Uri.withAppendedPath(HealthStoreUri.ACCESS, "$myPkgName/${Metric.WEIGHT}")
        val updated = cr.update(
            path,
            ContentValues().apply {
                put(AccessColumns.PKG_NAME, myPkgName)
                put(AccessColumns.METRIC, Metric.WEIGHT)
                put(AccessColumns.PERMISSIONS, Permission.ALL)
            },
            null, null
        )
        Assert.assertTrue(updated > 0)

        // It should be possible to write…
        val idC = insert(c)

        b.value -= 1.0

        Assert.assertNotEquals(-1L, idC)
        Assert.assertTrue(bodyRepo.update(a) is OperationResult.Success<*>)
        Assert.assertTrue(bodyRepo.update(b) is OperationResult.Success<*>)

        // …and reading again
        Assert.assertFalse(bodyRepo.allWeightRecords.isEmpty())
        Assert.assertFalse(bodyRepo.allBodyTemperatureRecords.isEmpty())
        Assert.assertEquals(c, bodyRepo.getWeightRecord(idC))

        bodyRepo.deleteAll()
        Assert.assertTrue(bodyRepo.all.isEmpty())
        cr.delete(Uri.withAppendedPath(HealthStoreUri.ACCESS, "all"), null, null)
    }

    private fun insert(record: BodyRecord): Long {
        return (bodyRepo.insert(record) as OperationResult.Success<*>).result as Long
    }
}
