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

package org.lineageos.mod.health.testsuite.tests

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lineageos.mod.health.testsuite.tests.impl.CyclingRecordTest
import org.lineageos.mod.health.testsuite.tests.impl.MedicalProfileTest
import org.lineageos.mod.health.testsuite.tests.impl.ModTest

class TestRunner(
    private val context: Context
) {
    private val tests = arrayOf(
        ModTest(),
        CyclingRecordTest(),
        MedicalProfileTest(),
    )

    fun runTests(): Flow<Array<TestInfo>> = flow {
        val results = Array(tests.size) { i ->
            TestInfo(tests[i].name, TestStatus.ToRun)
        }
        emit(results)

        tests.forEachIndexed { i, test ->
            // Notify test is running
            results[i] = results[i].copy(status = TestStatus.Running)
            emit(results)

            val status = try {
                test.runTest(context)
                TestStatus.Pass
            } catch (e: Exception) {
                if (e !is HsTestFailure) e.printStackTrace()
                TestStatus.Fail(e.message ?: "???")
            }
            results[i] = results[i].copy(status = status)
            emit(results)
        }
    }
}
