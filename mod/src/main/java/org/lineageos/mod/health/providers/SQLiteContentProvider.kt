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

import android.content.ContentProvider
import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Binder
import android.os.Process
import android.util.Log
import androidx.annotation.CallSuper
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper
import net.sqlcipher.database.SQLiteTransactionListener
import org.lineageos.mod.health.db.SqlChecker
import java.util.ArrayList
import java.util.Locale

/**
 * General purpose [ContentProvider] base class that uses SQLiteDatabase for storage.
 */
abstract class SQLiteContentProvider : ContentProvider(), SQLiteTransactionListener {

    companion object {
        private const val TAG = "SQLiteContentProvider"
        private const val SLEEP_AFTER_YIELD_DELAY = 4000L
        private const val DISALLOW_SUB_QUERIES = true

        private const val QUERY_TABLES_VIEWS =
            "SELECT name FROM sqlite_master WHERE type in (\"table\", \"view\")"
    }

    protected var openHelper: SQLiteOpenHelper? = null
        private set
    private var db: SQLiteDatabase? = null
    private lateinit var cachedSqlChecker: SqlChecker

    @Volatile
    private var notifyChange = false

    private val _applyingBatch = ThreadLocal<Boolean>()
    protected var isApplyingBatch: Boolean
        get() = _applyingBatch.get() ?: false
        set(value) = _applyingBatch.set(value)

    /**
     * The package to most recently query(), not including further internally recursive calls
     */
    private val _cachedCallingPackage = ThreadLocal<String>()
    protected var cachedCallingPackage: String?
        get() = _cachedCallingPackage.get()
        set(value) = _cachedCallingPackage.set(value)

    /**
     * The calling Uid when a calling package is cached, so we know when the stack of any
     * recursive calls to clearCallingIdentity and restoreCallingIdentity is complete
     */
    private val _originalCallingUid = ThreadLocal<Int>()
    private var callingUid: Int?
        get() = _originalCallingUid.get()
        set(value) = _originalCallingUid.set(value)

    protected abstract fun getDatabaseHelper(context: Context): SQLiteOpenHelper

    protected abstract fun getReadableDatabase(): SQLiteDatabase

    protected abstract fun getWritableDatabase(): SQLiteDatabase

    /**
     * Equivalent of [insert], but within a transaction
     */
    protected abstract fun insertInTransaction(uri: Uri, values: ContentValues?): Uri?

    /**
     * Equivalent of [update], but within a transaction
     */
    protected abstract fun updateInTransaction(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int

    /**
     * Equivalent of [delete], but within a transaction
     */
    protected abstract fun deleteInTransaction(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int

    protected abstract fun notifyChange()

    @CallSuper
    override fun onCreate(): Boolean {
        val context = context ?: return false
        openHelper = getDatabaseHelper(context)
        return true
    }

    override fun shutdown() {
        val openHelper = this.openHelper
        if (openHelper != null) {
            openHelper.close()
            this.openHelper = null
            this.db = null
        }
    }

    @CallSuper
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        validateContentValues(cachedCallingPackage, values)

        if (isApplyingBatch) {
            val result = insertInTransaction(uri, values)
            if (result != null) {
                notifyChange = true
            }
            return result
        }

        val result: Uri?
        val db = getWritableDatabase().also { this.db = it }
        db.beginTransactionWithListener(this)
        val identity = clearCallingIdentityInternal()
        try {
            result = insertInTransaction(uri, values)
            if (result != null) {
                notifyChange = true
            }
            db.setTransactionSuccessful()
        } finally {
            restoreCallingIdentityInternal(identity)
            db.endTransaction()
        }

        onEndTransaction()
        return result
    }

    @CallSuper
    override fun bulkInsert(uri: Uri, values: Array<out ContentValues>): Int {
        val db = getWritableDatabase()
        db.beginTransactionWithListener(this)

        val identity = clearCallingIdentityInternal()
        try {
            values.forEach {
                val result = insertInTransaction(uri, it)
                if (result != null) {
                    notifyChange = true
                }
                db.yieldIfContendedSafely()
            }
            db.setTransactionSuccessful()
        } finally {
            restoreCallingIdentityInternal(identity)
            db.endTransaction()
        }

        onEndTransaction()
        return values.size
    }

    @CallSuper
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        validateContentValues(cachedCallingPackage, values)
        validateSql(cachedCallingPackage, selection)

        if (isApplyingBatch) {
            val count = updateInTransaction(uri, values, selection, selectionArgs)
            if (count > 0) {
                notifyChange = true
            }
            return count
        }

        val count: Int
        val db = getWritableDatabase().also { this.db = it }
        db.beginTransactionWithListener(this)
        val identity = clearCallingIdentityInternal()
        try {
            count = updateInTransaction(uri, values, selection, selectionArgs)
            if (count > 0) {
                notifyChange = true
            }
            db.setTransactionSuccessful()
        } finally {
            restoreCallingIdentityInternal(identity)
            db.endTransaction()
        }

        onEndTransaction()
        return count
    }

    @CallSuper
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        validateSql(cachedCallingPackage, selection)

        if (isApplyingBatch) {
            val count = deleteInTransaction(uri, selection, selectionArgs)
            if (count > 0) {
                notifyChange = true
            }
            return count
        }

        val count: Int
        val db = getWritableDatabase().also { this.db = it }
        db.beginTransactionWithListener(this)
        val identity = clearCallingIdentityInternal()
        try {
            count = deleteInTransaction(uri, selection, selectionArgs)
            if (count > 0) {
                notifyChange = true
            }
            db.setTransactionSuccessful()
        } finally {
            restoreCallingIdentityInternal(identity)
            db.endTransaction()
        }
        return count
    }

    @CallSuper
    override fun applyBatch(operations: ArrayList<ContentProviderOperation>): Array<ContentProviderResult> {
        if (operations.isEmpty()) {
            return arrayOf()
        }

        val db = getWritableDatabase().also { this.db = it }
        db.beginTransactionWithListener(this)
        val identity = clearCallingIdentityInternal()
        try {
            isApplyingBatch = true
            val results = Array(operations.size) { ContentProviderResult(0) }
            var i = 0
            while (i < results.size) {
                val operation = operations[i]
                if (i > 0 && operation.isYieldAllowed) {
                    db.yieldIfContendedSafely(SLEEP_AFTER_YIELD_DELAY)
                }
                operation.apply(this, results, i++)
            }

            db.setTransactionSuccessful()
            return results
        } finally {
            isApplyingBatch = false
            db.endTransaction()
            onEndTransaction()
            restoreCallingIdentityInternal(identity)
        }
    }

    override fun onBegin() {
        onBeginTransaction()
    }

    override fun onCommit() {
        beforeTransactionCommit()
    }

    override fun onRollback() {
        // Unused
    }

    protected open fun onBeginTransaction() {
    }

    protected open fun beforeTransactionCommit() {
    }

    @CallSuper
    protected open fun onEndTransaction() {
        if (notifyChange) {
            notifyChange = false
            notifyChange()
        }
    }

    /**
     * Call [Binder.clearCallingIdentity], while caching the calling package
     * name, so that it can be saved if this is part of an event mutation.
     */
    protected fun clearCallingIdentityInternal(): Long {
        val uid = Process.myUid()
        val callingUid = Binder.getCallingUid()
        if (uid != callingUid) {
            try {
                this.callingUid = callingUid
                cachedCallingPackage = callingPackage
            } catch (e: SecurityException) {
                Log.e(TAG, "Error getting the calling package", e)
            }
        }

        return Binder.clearCallingIdentity()
    }

    /**
     * Call [Binder.restoreCallingIdentity].
     *
     * If this is the last restore on the stack of calls to
     * [Binder.clearCallingIdentity], then the cached calling package will also
     * be cleared.
     */
    protected fun restoreCallingIdentityInternal(identity: Long) {
        Binder.restoreCallingIdentity(identity)

        val callingUid = Binder.getCallingUid()
        if (callingUid == this.callingUid) {
            cachedCallingPackage = null
            this.callingUid = null
        }
    }

    /**
     * Ensure a piece of SQL is valid and doesn't contain disallowed tokens
     */
    protected fun validateSql(callerPackage: String?, sqlPiece: String?) {
        if (sqlPiece == null) {
            return
        }
        runSqlValidation(callerPackage) {
            getSqlChecker().ensureNoInvalidTokens(sqlPiece)
        }
    }

    /**
     * Ensure all column names in [projection] are valid (i.e. they're all single token)
     */
    protected fun validateProjection(callerPackage: String?, projection: Array<out String>?) {
        if (projection == null) {
            return
        }

        runSqlValidation(callerPackage) {
            val checker = getSqlChecker()
            projection.forEach(checker::ensureSingleTokenOnly)
        }
    }

    /**
     * Ensure all keys in [values] are valid (i.e. they're all single token)
     */
    private fun validateContentValues(callerPackage: String?, values: ContentValues?) {
        if (values == null) {
            return
        }
        runSqlValidation(callerPackage) {
            val checker = getSqlChecker()
            values.keySet().forEach(checker::ensureSingleTokenOnly)
        }
    }

    private fun runSqlValidation(callerPackage: String?, validation: () -> Unit) {
        try {
            validation()
        } catch (e: SqlChecker.InvalidSqlException) {
            Log.e(TAG, "${e.message} caller=$callerPackage")
            throw e
        }
    }

    private fun getSqlChecker(): SqlChecker {
        // No need for synchronization on mCachedSqlChecker, because worst-case we'll just
        // initialize it twice.
        if (::cachedSqlChecker.isInitialized) {
            return cachedSqlChecker
        }

        val invalidTokens = mutableListOf<String>()
        if (DISALLOW_SUB_QUERIES) {
            // Disallow referring to tables and views. However, we exempt tables whose names are
            // also used as column names of any tables.
            invalidTokens.addAll(findTableAndViews(getReadableDatabase()))
            // Disallow toke "select" to disallow sub-queries
            invalidTokens.add("select")
        }

        return SqlChecker(invalidTokens).also { cachedSqlChecker = it }
    }

    /**
     * Return all table / view names that clients shouldn't use in their queries -- basically the
     * result contains all table / view names, except for the names that are column names of any
     * tables.
     */
    private fun findTableAndViewsAllowingColumns(db: SQLiteDatabase): List<String> {
        val tables = findTableAndViews(db)
        val ret = mutableListOf<String>()
        ret.addAll(tables)
        tables.forEach { name ->
            findColumns(db, name).forEach { column ->
                ret.remove(column)
            }
        }
        return ret
    }

    /**
     * Find and return all table / view names in a db.
     */
    private fun findTableAndViews(db: SQLiteDatabase): List<String> {
        val ret = mutableListOf<String>()
        db.rawQuery(QUERY_TABLES_VIEWS, null)?.use {
            while (it.moveToNext()) {
                ret.add(it.getString(0).toLowerCase(Locale.ROOT))
            }
        }
        return ret
    }

    /**
     * Find all columns in a table / view.
     */
    private fun findColumns(db: SQLiteDatabase, table: String): List<String> {
        val ret = mutableListOf<String>()
        db.rawQuery("SELECT * FROM $table WHERE 0 LIMIT 0", null)?.use {
            var i = 0
            val n = it.columnCount
            while (i < n) {
                ret.add(it.getColumnName(i++).toLowerCase(Locale.ROOT))
            }
        }
        return ret
    }
}
