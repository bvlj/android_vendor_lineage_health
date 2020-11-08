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

package org.lineageos.mod.health.providers

import android.content.ContentProvider
import android.os.Build
import android.util.Log

fun <T> ContentProvider.withMyId(
    block: ContentProvider.() -> T
): T {
    if (Build.VERSION.SDK_INT < 29) {
        // TODO: decide what to do here. SELinux will kill the calling app
        //       if we attempt to access the db without clearing the calling identity
        Log.w("ContentProviderExt", "Running with original CallerId")
        return block()
    }

    val token = clearCallingIdentity()
    val result = block()
    restoreCallingIdentity(token)
    return result
}
