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

import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Binder
import androidx.annotation.CallSuper
import org.lineageos.mod.health.security.KeyMaster
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.ArrayList

/**
 * Base class for all the HealthStore ContentProviders
 * that store user data in a SQLite database.
 *
 * Supports printing usage stats when using:
 * `adb shell dumpsys activity providers`
 */
abstract class BaseHealthStoreContentProvider : SQLiteContentProvider() {

    private lateinit var keyMaster: KeyMaster

    private val stats = ProviderAccessStats()
    private val _callingUid = ThreadLocal<Int>()
    private var callingUid: Int
        get() = _callingUid.get()!!
        set(value) = _callingUid.set(value)

    /**
     * Perform an insertion within a SQL transaction.
     * The given [values] are ensured to be safe for usage.
     *
     * @return The [Uri] of the newly inserted item
     *
     * @see [insert]
     */
    protected abstract fun insertInTransactionImpl(uri: Uri, values: ContentValues): Uri?

    /**
     * Perform an update within a SQL transaction.
     * The given [values] are ensured to be safe for usage.
     *
     * @return The number of updated items
     *
     * @see [update]
     */
    protected abstract fun updateInTransactionImpl(uri: Uri, values: ContentValues): Int

    /**
     * Perform a deletion within a SQL transaction.
     *
     * @return The number of deleted items
     *
     * @see [delete]
     */
    protected abstract fun deleteInTransactionImpl(uri: Uri): Int

    /**
     * Perform a query.
     * The given [selection] and [selectionArgs] are ensured to be safe for usage.
     *
     * @see [query]
     */
    protected abstract fun queryImpl(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor?

    /**
     * Verify the arguments of an operation.
     * A verification failure should throw a
     * [org.lineageos.mod.health.validators.Validator.ValidationException]
     * or an [IllegalArgumentException].
     *
     * @param type Operation type
     * @param values ContentValues given for the operation
     * @param selection Selection query for the operation
     * @param selectionArgs Selection query args
     *
     * @see [org.lineageos.mod.health.validators.Validator]
     */
    protected abstract fun verifyTransactionAllowed(
        type: Type,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    )

    @CallSuper
    override fun onCreate(): Boolean {
        val context = context ?: return false
        keyMaster = KeyMaster.getInstance(context)
        return super.onCreate()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (!isApplyingBatch) {
            callingUid = Binder.getCallingUid()
        }
        return super.insert(uri, values)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (!isApplyingBatch) {
            callingUid = Binder.getCallingUid()
        }
        return super.update(uri, values, selection, selectionArgs)
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (!isApplyingBatch) {
            callingUid = Binder.getCallingUid()
        }
        return super.delete(uri, selection, selectionArgs)
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        // Note: don't use this.callingUid here. That's only used by mutation functions
        val callingUid = Binder.getCallingUid()

        stats.incrementQueryStats(callingUid)
        val identity = clearCallingIdentityInternal()
        try {
            verifyTransactionAllowed(Type.QUERY, null, selection, selectionArgs)
            return queryImpl(uri, projection, selection, selectionArgs, sortOrder)
        } finally {
            restoreCallingIdentityInternal(identity)
            stats.finishOperation(callingUid)
        }
    }

    override fun bulkInsert(uri: Uri, values: Array<out ContentValues>): Int {
        callingUid = Binder.getCallingUid()
        stats.incrementBatchStats(callingUid)
        try {
            return super.bulkInsert(uri, values)
        } finally {
            stats.finishOperation(callingUid)
        }
    }

    override fun applyBatch(
        operations: ArrayList<ContentProviderOperation>
    ): Array<ContentProviderResult> {
        callingUid = Binder.getCallingUid()
        stats.incrementBatchStats(callingUid)
        try {
            return super.applyBatch(operations)
        } finally {
            stats.finishOperation(callingUid)
        }
    }

    override fun insertInTransaction(uri: Uri, values: ContentValues?): Uri? {
        stats.incrementInsertStats(callingUid, isApplyingBatch)
        try {
            verifyTransactionAllowed(Type.INSERT, values, null, null)
            return if (values == null) null else insertInTransactionImpl(uri, values)
        } finally {
            stats.finishOperation(callingUid)
        }
    }

    override fun updateInTransaction(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        stats.incrementUpdateStats(callingUid, isApplyingBatch)
        try {
            verifyTransactionAllowed(Type.UPDATE, values, selection, selectionArgs)
            return if (values == null) 0 else updateInTransactionImpl(uri, values)
        } finally {
            stats.finishOperation(callingUid)
        }
    }

    override fun deleteInTransaction(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        stats.incrementDeleteStats(callingUid, isApplyingBatch)
        try {
            verifyTransactionAllowed(Type.DELETE, null, selection, selectionArgs)
            return deleteInTransactionImpl(uri)
        } finally {
            stats.finishOperation(callingUid)
        }
    }

    override fun dump(fd: FileDescriptor?, writer: PrintWriter?, args: Array<out String>?) {
        if (writer == null) {
            return
        }
        stats.dump(writer, "  ")
    }

    protected enum class Type {
        QUERY,
        INSERT,
        UPDATE,
        DELETE
    }
}
