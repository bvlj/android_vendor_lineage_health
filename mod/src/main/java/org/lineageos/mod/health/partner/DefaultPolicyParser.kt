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

package org.lineageos.mod.health.partner

import android.content.res.XmlResourceParser
import org.lineageos.mod.health.common.Metric
import org.lineageos.mod.health.common.values.Permission
import org.xmlpull.v1.XmlPullParser

/**
 * Example expected document format:
 *
 * ```
 * <policies version="1">
 *     <package name="com.example.app">
 *         <policy metric="1002"
 *                 permissions="1" />
 *         <policy metric="1003"
 *                 permissions="0" />
 *         <policy metric="1004" />
 *     </package>
 * </policies>
 * ```
 *
 * - <policies>: Root tag, requires version (int) attribute. Must match [EXPECTED_POLICY_VERSION]
 *   for the document to be considered valid
 * - <package>: Policy target package, requires name (string) attribute
 * - <policy>: Policy specifications, requires metric (int) attribute (see [Metric]) and
 *   an optional permissions (int) attribute (see [Permission], defaults to [Permission.NONE])
 */
object DefaultPolicyParser {
    private const val EXPECTED_POLICY_VERSION = 1

    private const val KEY_ROOT = "policies"
    private const val KEY_PACKAGE = "package"
    private const val KEY_POLICY = "policy"
    private const val ATTR_METRIC = "metric"
    private const val ATTR_NAME = "name"
    private const val ATTR_PERMISSIONS = "permissions"
    private const val ATTR_VERSION = "version"

    fun parse(xpp: XmlResourceParser): List<AccessPolicy> {
        val policies = mutableListOf<AccessPolicy>()
        var hasEncounteredRoot = false
        var currentPackage = ""

        var eventType = xpp.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = xpp.name
            if (eventType != XmlPullParser.START_TAG) {
                eventType = xpp.next()
                continue
            }

            when (tagName) {
                KEY_ROOT -> {
                    if (hasEncounteredRoot) {
                        throw DocumentFormatException(
                            "Config can't have more than one <policies> tags"
                        )
                    }
                    hasEncounteredRoot = true
                    val version = xpp.getAttributeIntValue(null, ATTR_VERSION, 0)
                    if (version != EXPECTED_POLICY_VERSION) {
                        throw UnsupportedVersionException(version)
                    }
                }
                KEY_PACKAGE -> {
                    currentPackage = xpp.getAttributeValue(null, ATTR_NAME)
                        ?: throw DocumentFormatException("Missing name attribute for package")
                }
                KEY_POLICY -> {
                    if (currentPackage == "") {
                        throw DocumentFormatException("Found <policy> outside a <package> tag")
                    }
                    val metric = xpp.getAttributeIntValue(
                        null,
                        ATTR_METRIC,
                        Metric.UNKNOWN
                    )
                    val permissions = xpp.getAttributeIntValue(
                        null,
                        ATTR_PERMISSIONS,
                        Permission.NONE
                    )
                    policies.add(AccessPolicy(currentPackage, metric, permissions))
                }
            }
            eventType = xpp.next()
        }

        return policies
    }

    class UnsupportedVersionException(version: Int) : Exception(
        "Unsupported access policy config version $version, expected $EXPECTED_POLICY_VERSION"
    )

    class DocumentFormatException(message: String) : Exception(message)
}
