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

import android.content.res.Resources

class Partner(private val info: ApkInfo) {

    companion object {
        private const val RES_DEFAULT_ACCESS_POLICY = "access_policy"
    }

    fun getDefaultAccessPolicy(): List<AccessPolicy> {
        val defaultAccessPolicy =
            info.res.getIdentifier(RES_DEFAULT_ACCESS_POLICY, "xml", info.pkgName)
        if (defaultAccessPolicy == 0) {
            return emptyList()
        }

        return try {
            DefaultPolicyParser.parse(info.res.getXml(defaultAccessPolicy))
        } catch (e: DefaultPolicyParser.UnsupportedVersionException) {
            e.printStackTrace()
            emptyList()
        }
    }

    data class ApkInfo(
        val pkgName: String,
        val res: Resources
    )
}
