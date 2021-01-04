/*
 * Copyright (C) 2021 The LineageOS Project
 * Copyright (C) 2016 The Android Open Source Project
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

package org.lineageos.mod.health.db

import androidx.annotation.VisibleForTesting
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Simple SQL validator to detect uses of hidden tables / columns as well as invalid SQLs.
 */
class SqlChecker(
    invalidTokens: List<String>
) {

    companion object {
        private const val PRIVATE_PREFIX = "x_" // MUST BE LOWERCASE

        @VisibleForTesting
        const val OPTION_NONE = 0

        @VisibleForTesting
        const val OPTION_TOKEN_ONLY = 1
    }

    private val invalidTokens = invalidTokens.map(String::toLowerCase)

    /**
     * Ensure [sql] is valid and doesn't contain invalid tokens.
     */
    fun ensureNoInvalidTokens(sql: String) {
        findTokens(sql, OPTION_NONE) { token -> throwIfContainsToken(token, sql) }
    }

    /**
     * Ensure [sql] only contains a single, valid token. Use to validate column names
     * in [android.content.ContentValues].
     */
    fun ensureSingleTokenOnly(sql: String) {
        val tokenFound = AtomicBoolean(false)
        findTokens(sql, OPTION_TOKEN_ONLY) { token ->
            if (tokenFound.get()) {
                throw InvalidSqlException("Multiple tokens detected", sql)
            }
            tokenFound.set(true)
            throwIfContainsToken(token, sql)
        }
        if (!tokenFound.get()) {
            throw InvalidSqlException("Token not found", sql)
        }
    }

    /**
     * SQL Tokenizer specialized to extract tokens from SQL (snippets).
     *
     * Based on sqlite3GetToken() in tokenzie.c in SQLite.
     *
     * Source for v3.8.6 (which android uses): http://www.sqlite.org/src/artifact/ae45399d6252b4d7
     * (Latest source as of now: http://www.sqlite.org/src/artifact/78c8085bc7af1922)
     *
     * Also draft spec: http://www.sqlite.org/draft/tokenreq.html
     */
    @VisibleForTesting
    fun findTokens(sql: String, _options: Int, checker: (String) -> Unit) {
        var options = _options
        var pos = 0
        val len = sql.length
        while (pos < len) {
            val ch = sql.peek(pos)
            when {
                // Regular token
                ch.isAlpha() -> {
                    val start = pos++
                    while (sql.peek(pos).isAlNum()) {
                        pos++
                    }
                    val end = pos
                    val token = sql.substring(start, end)
                    checker(token)
                }
                // Handle quoted tokens
                ch.isAnyOf("'\"`") -> {
                    val quoteStart = pos++
                    while (true) {
                        pos = sql.indexOf(ch, pos)
                        if (pos < 0) {
                            throw InvalidSqlException("Unterminated quote", sql)
                        }
                        if (sql.peek(pos + 1) != ch) {
                            break
                        }
                        // Quoted quote char -- e.g. "abc""def" is a single string.
                        pos += 2
                    }
                    val quoteEnd = pos++
                    if (ch != '\'') {
                        // Extract the token
                        val tokenUnquoted = sql.substring(quoteStart + 1, quoteEnd - 1)
                        // Unquote if needed. i.e. "aa""bb" -> aa"bb
                        val token = if (tokenUnquoted.indexOf(ch) < 0)
                            tokenUnquoted
                        else
                            tokenUnquoted.replace("$ch$ch", ch.toString())
                        checker(token)
                    } else {
                        options = options and OPTION_TOKEN_ONLY
                        if (options != 0) {
                            throw InvalidSqlException("Non-token detected", sql)
                        }
                    }
                }
                // Handle tokens enclosed in [...]
                ch == '[' -> {
                    val quoteStart = pos++
                    pos = sql.indexOf(']', pos)
                    if (pos < 0) {
                        throw InvalidSqlException("Unterminated quote", sql)
                    }
                    val quoteEnd = pos++
                    val token = sql.substring(quoteStart + 1, quoteEnd)
                    checker(token)
                }
                // Detect comments
                ch == '-' && sql.peek(pos + 1) == '-' -> {
                    pos += 2
                    pos = sql.indexOf('\n', pos)
                    if (pos < 0) {
                        // We disallow strings ending in an inline comment
                        throw InvalidSqlException("Unterminated comment", sql)
                    }
                    pos++
                }
                ch == '/' && sql.peek(pos + 1) == '*' -> {
                    pos += 2
                    pos = sql.indexOf("*/", pos)
                    if (pos < 0) {
                        throw InvalidSqlException("Unterminated comment", sql)
                    }
                    pos += 2
                }
                // Semicolon is never allowed
                ch == ';' -> throw InvalidSqlException("Semicolon is not allowed", sql)
                // For this purpose, we can simply ignore other characters.
                // (Note it doesn't handle the X'' literal properly and reports this X as a
                // token, but that should be fine...)
                else -> pos++
            }
        }
    }

    private fun throwIfContainsToken(token: String, sql: String) {
        val lower = token.toLowerCase(Locale.ROOT)
        if (lower in invalidTokens || lower.startsWith(PRIVATE_PREFIX)) {
            throw InvalidSqlException("Detected disallowed token: $token", sql)
        }
    }

    private fun Char.isAlNum(): Boolean {
        return isNum() || isAlpha()
    }

    private fun Char.isAlpha(): Boolean {
        // Also allow '_' since we're using it in several places
        return this in 'a'..'z' || this in 'A'..'Z' || this == '_'
    }

    private fun Char.isNum(): Boolean {
        return this in '0'..'9'
    }

    private fun Char.isAnyOf(set: String): Boolean {
        return set.indexOf(this) >= 0
    }

    private fun String.peek(index: Int): Char {
        return if (index < length) get(index) else '\u0000'
    }

    /**
     * Exception for invalid queries
     */
    @VisibleForTesting
    class InvalidSqlException(message: String, sql: String) :
        IllegalArgumentException("$message in $sql")
}
