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
    public static final String AUTHORITY = "org.lineageos.mod.health";

    @NonNull
    public static final Uri ACCESS = Uri.parse("content://" + AUTHORITY + "/access");

    @NonNull
    public static final Uri ACTIVITY = Uri.parse("content://" + AUTHORITY + "/activity");
    @NonNull
    public static final Uri BODY = Uri.parse("content://" + AUTHORITY + "/body");
    @NonNull
    public static final Uri BREATHING = Uri.parse("content://" + AUTHORITY + "/breathing");
    @NonNull
    public static final Uri HEART_BLOOD = Uri.parse("content://" + AUTHORITY + "/blood");
    @NonNull
    public static final Uri MINDFULNESS = Uri.parse("content://" + AUTHORITY + "/mindfulness");

    @NonNull
    public static final Uri MEDICAL_PROFILE = Uri.parse("content://" + AUTHORITY + "/profile");

}
