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

package org.lineageos.mod.health.testsuite.tests.impl

import android.content.Context
import org.lineageos.mod.health.sdk.ktx.ActivityRecordsRepoKt
import org.lineageos.mod.health.sdk.model.records.activity.CyclingRecord
import org.lineageos.mod.health.testsuite.tests.HsTest

class CyclingRecordTest : HsTest() {

    override val name = "Cycling records test"

    override suspend fun runTest(context: Context) {
        val repo = ActivityRecordsRepoKt.getInstance(context.contentResolver)

        // Cleanup
        repo.allCyclingRecords().forEach { repo.delete(it) }
        assert(repo.allCyclingRecords().isEmpty(), "Failed to clean up")

        val idA = repo.insert(
            CyclingRecord(
                0L,
                System.currentTimeMillis(),
                1000L,
                12.0,
                50.0,
                5.0
            )
        )
        assert(idA != -1L, "Failed to insert first record")

        val idB = repo.insert(
            CyclingRecord(
                0L,
                System.currentTimeMillis() - 1000L,
                60L,
                1.0,
                9.1,
                88.4
            )
        )
        assert(idB != -1L, "Failed to insert second record")

        val records = repo.allCyclingRecords()
        assert(
            records.size == 2,
            "Retrieved records do not match expected size (was ${records.size})"
        )

        val a = records[0]
        val b = records[1]
        assert(a != b, "Retrieved records are not different")
        a.distance += 800.0
        assert(repo.update(a), "Failed to update record")
        assert(a == repo.cyclingRecord(a.id), "Retrieved record does not match")

        records.forEach { repo.delete(it) }
        assert(repo.allCyclingRecords().isEmpty(), "Failed to cleanup")
    }
}
