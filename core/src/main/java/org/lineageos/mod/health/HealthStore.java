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

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public final class HealthStore {
    private static final String FEATURE_NAME = "org.lineageos.mod.health";

    private HealthStore() {
    }

    /**
     * Determine whether HealthStore is supported on this device
     */
    public static boolean isSupported(@NonNull Context context) {
        final PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(FEATURE_NAME);
    }

    /**
     * Get the HealthStore version supported on this device
     *
     * @return -1 if unsupported or the version declared on the healthstore
     * feature xml
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

    public static class Version {

        /**
         * Actinium (Ac)
         *
         * Released: November 2020.
         * Notes: Initial release. HealthStore Mod version
         * are defined by elements of the periodic table in
         * alphabetic order.
         *
         * Actinium is a silvery radioactive metallic element. Actinium glows in
         * the dark due to its intense radioactivity with a blue light.
         */
        public static final int ACTINIUM = 1;

        /**
         * The version of the Health mod against which
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
