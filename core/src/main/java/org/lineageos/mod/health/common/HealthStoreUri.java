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

package org.lineageos.mod.health.common;

import android.net.Uri;

import androidx.annotation.NonNull;

public final class HealthStoreUri {

    private HealthStoreUri() {
    }

    @NonNull
    public static final Uri ACCESS = Uri.parse(Authority.ACCESS);

    @NonNull
    public static final Uri ACTIVITY = Uri.parse(Authority.ACTIVITY);
    @NonNull
    public static final Uri BODY = Uri.parse(Authority.BODY);
    @NonNull
    public static final Uri BREATHING = Uri.parse(Authority.BREATHING);
    @NonNull
    public static final Uri HEART_BLOOD = Uri.parse(Authority.HEART_BLOOD);
    @NonNull
    public static final Uri MINDFULNESS = Uri.parse(Authority.MINDFULNESS);

    @NonNull
    public static final Uri MEDICAL_PROFILE = Uri.parse(Authority.MEDICAL_PROFILE);

    public static final class Authority {
        private Authority() {
        }

        @NonNull
        private static final String BASE = "org.lineageos.mod.health";

        @NonNull
        public static final String ACCESS = "content://" + BASE + ".access";
        @NonNull
        public static final String ACTIVITY = "content://" + BASE + ".activity";
        @NonNull
        public static final String BODY = "content://" + BASE + ".body";
        @NonNull
        public static final String BREATHING = "content://" + BASE + ".breathing";
        @NonNull
        public static final String HEART_BLOOD = "content://" + BASE + ".heart";
        @NonNull
        public static final String MINDFULNESS = "content://" + BASE + ".mindfulness";
        @NonNull
        public static final String MEDICAL_PROFILE = "content://" + BASE + ".profile";
    }
}
