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

package org.lineageos.mod.health;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

/**
 * General information about the CareCache
 */
@Keep
public final class CareCache {
    private static final String FEATURE_NAME = "org.lineageos.mod.health";

    private CareCache() {
    }

    /**
     * Determine whether CareCache is supported on this device
     */
    public static boolean isSupported(@NonNull Context context) {
        final PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(FEATURE_NAME);
    }

    /**
     * Get the CareCache version supported on this device.
     *
     * @return -1 if unsupported or the version declared on the CareCache
     * feature xml
     * @see Version
     */
    public static int getSupportedVersion(@NonNull Context context) {
        final PackageManager pm = context.getPackageManager();
        final FeatureInfo[] features = pm.getSystemAvailableFeatures();
        for (final FeatureInfo f : features) {
            if (FEATURE_NAME.equals(f.name)) {
                return f.version;
            }
        }

        return -1;
    }

    /**
     * Version information used to understand what format the client app
     * is using (if any). By inserting a <code>"_version"</code> field in
     * the {@link ContentValues} sent to the {@link ContentProvider},
     * will allow for graceful conversion of the data to the installed mod
     * version on the mod side.
     *
     * If no <code>"_version"</code> is specified, the mod will assume
     * the {@link Version#MIN} value and will attempt to upgrade the
     * values to the {@link Version#CURRENT} format.
     *
     * When "upgrading" {@link ContentValues}, it is possible
     * some data might be dropped or changed, make sure to look
     * at the logcat for warnings printed by the mod component
     * for more specific information.
     *
     * If the official SDK is being used, the versioning will be
     * managed internally by the SDK itself with no additional
     * effort required by the SDK user's end.
     */
    public static class Version {

        /**
         * Actinium (Ac)
         * <br>
         * Released: November 2020.
         * Notes: Initial release. CareCache Mod version
         * are defined by elements of the periodic table in
         * alphabetic order.
         * <br>
         * Actinium is a silvery radioactive metallic element. Actinium glows in
         * the dark due to its intense radioactivity with a blue light.
         */
        public static final int ACTINIUM = 1;

        /**
         * The version of the CareCache module against which
         * this sdk was compiled.
         */
        public static final int CURRENT = ACTINIUM;

        /**
         * Minimum supported version
         */
        public static final int MIN = ACTINIUM;

        private Version() {}
    }
}
