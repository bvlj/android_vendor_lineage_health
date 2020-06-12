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

package org.lineageos.mod.health.access

import android.content.ContentProvider

internal fun ContentProvider.canRead(
    accessManager: AccessManager,
    metric: String
): Boolean {
    val callingPkg = callingPackage ?: "[unknown package]"
    val metricInt = metric.toIntOrNull()
    return metricInt != null && !accessManager.canRead(callingPkg, metricInt)
}

internal fun ContentProvider.canWrite(
    accessManager: AccessManager,
    metric: String
): Boolean {
    val callingPkg = callingPackage ?: "[unknown package]"
    val metricInt = metric.toIntOrNull()
    return metricInt != null && !accessManager.canWrite(callingPkg, metricInt)
}
