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

import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log

object PartnerProvider {
    private const val TAG = "PartnerProvider"
    private const val ACTION_PARTNER_CUSTOMIZATION =
        "org.lineageos.mod.health.action.PARTNER_CUSTOMIZATION"

    @Synchronized
    fun get(pm: PackageManager): Partner? {
        val apkInfo = findPartnerApk(pm)
        return if (apkInfo == null) null else Partner(apkInfo)
    }

    /**
     * Finds a system apk which had a broadcast receiver listening to the customization action.
     */
    private fun findPartnerApk(pm: PackageManager): Partner.ApkInfo? {
        val intent = Intent(ACTION_PARTNER_CUSTOMIZATION)
        pm.queryBroadcastReceivers(intent, PackageManager.MATCH_SYSTEM_ONLY).forEach { info ->
            val pkgName = info.activityInfo.packageName
            try {
                val res = pm.getResourcesForApplication(pkgName)
                return Partner.ApkInfo(pkgName, res)
            } catch (e: PackageManager.NameNotFoundException) {
                Log.w(TAG, "Failed to find resources for $pkgName")
            }
        }

        return null
    }
}
