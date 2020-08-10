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

package org.lineageos.mod.health.sdk;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
@SuppressWarnings("unused")
public final class HealthStore {

    private HealthStore() {
    }

    /**
     * Determine whether HealthStore is supported on this device
     */
    public static boolean isSupported(@NonNull Context context) {
        final PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature("org.lineageos.mod.health");
    }

    /**
     * Determine whether a particular HealthStore version is supported on this device
     */
    public static boolean isSupported(@NonNull Context context, int version) {
        final PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature("org.lineageos.mod.health", version);
    }
}
