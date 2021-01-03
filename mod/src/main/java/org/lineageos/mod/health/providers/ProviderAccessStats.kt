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

package org.lineageos.mod.health.providers

import android.os.SystemClock
import android.util.SparseBooleanArray
import android.util.SparseLongArray
import java.io.PrintWriter
import kotlin.math.max

/**
 * Kotlin translation of com.android.internal.util.ProviderAccessStats
 */
class ProviderAccessStats {

    private val lock = Object()

    private val startupTime = SystemClock.uptimeMillis()

    private val allCallingUids = SparseBooleanArray()
    private val queryStats = SparseLongArray(16)
    private val batchStats = SparseLongArray(0)
    private val insertStats = SparseLongArray(0)
    private val updateStats = SparseLongArray(0)
    private val deleteStats = SparseLongArray(0)
    private val insertInBatchStats = SparseLongArray(0)
    private val updateInBatchStats = SparseLongArray(0)
    private val deleteInBatchStats = SparseLongArray(0)

    private val operationDurationMillis = SparseLongArray(16)

    private val threadLocal = ThreadLocal.withInitial { PerThreadData() }

    fun incrementInsertStats(callingUid: Int, inBatch: Boolean) {
        incrementStats(callingUid, if (inBatch) insertInBatchStats else insertStats)
    }

    fun incrementUpdateStats(callingUid: Int, inBatch: Boolean) {
        incrementStats(callingUid, if (inBatch) updateInBatchStats else updateStats)
    }

    fun incrementDeleteStats(callingUid: Int, inBatch: Boolean) {
        incrementStats(callingUid, if (inBatch) deleteInBatchStats else deleteStats)
    }

    fun incrementQueryStats(callingUid: Int) {
        incrementStats(callingUid, queryStats)
    }

    fun incrementBatchStats(callingUid: Int) {
        incrementStats(callingUid, batchStats)
    }

    fun finishOperation(callingUid: Int) {
        threadLocal.get()?.let { data ->
            data.nestCount--
            if (data.nestCount == 0) {
                // Because we only have millisecond granularity, let's always attribute at least 1ms
                // for each operation.
                val duration = max(1L, SystemClock.uptimeMillis() - data.startupTimeMillis)
                synchronized(lock) {
                    operationDurationMillis.put(
                        callingUid,
                        operationDurationMillis[callingUid] + duration
                    )
                }
            }
        }
    }

    fun dump(pw: PrintWriter, prefix: String) {
        synchronized(lock) {
            pw.print("  Process uptime: ")
            pw.print((SystemClock.uptimeMillis() - startupTime) / (60 * 1000))
            pw.println(" minutes")
            pw.println()

            pw.print(prefix)
            pw.println("Client activities:")
            pw.print(prefix)
            pw.println(
                "  UID        Query  Insert Update Delete   Batch Insert Update Delete          Sec"
            )

            var i = 0
            val n = allCallingUids.size()
            while (i < n) {
                val uid = allCallingUids.keyAt(i++)
                pw.print(prefix)
                pw.println(
                    "  %-9d %6d  %6d %6d %6d  %6d %6d %6d %6d %12.3f".format(
                        uid,
                        queryStats[uid],
                        insertStats[uid],
                        updateStats[uid],
                        deleteStats[uid],
                        batchStats[uid],
                        insertInBatchStats[uid],
                        updateInBatchStats[uid],
                        deleteInBatchStats[uid],
                        (operationDurationMillis[uid] / 1000.0)
                    )
                )
            }
            pw.println()
        }
    }

    private fun incrementStats(callingUid: Int, stats: SparseLongArray) {
        synchronized(lock) {
            stats.put(callingUid, stats[callingUid] + 1)
            allCallingUids.put(callingUid, true)
        }

        threadLocal.get()?.let { data ->
            data.nestCount++
            if (data.nestCount == 1) {
                data.startupTimeMillis = System.currentTimeMillis()
            }
        }
    }

    private data class PerThreadData(
        var nestCount: Int = 0,
        var startupTimeMillis: Long = 0L
    )
}
