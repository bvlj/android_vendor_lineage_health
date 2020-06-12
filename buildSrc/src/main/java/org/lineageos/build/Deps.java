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

package org.lineageos.build;

@SuppressWarnings("SpellCheckingInspection")
public final class Deps {

    // AndroidX - https://developer.android.com/jetpack/androidx/versions
    public static class AndroidX {

        // Core - KTX - https://developer.android.com/jetpack/androidx/releases/annotation
        public static final String annotation = "androidx.annotation:annotation:1.1.0";
    }

    // Kotlin - https://github.com/JetBrains/kotlin/releases
    public static class Kotlin {
        private static final String VERSION = "1.3.72";

        public static class StdLib {

            public static final String jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" + VERSION;
        }

        // Coroutines - https://github.com/Kotlin/kotlinx.coroutines/releases
        public static class Coroutines {
            private static final String VERSION = "1.3.4";

            public static final String jvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core:" + VERSION;
            public static final String android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" + VERSION;
        }
    }

    // Robolectric - https://mvnrepository.com/artifact/org.robolectric/android-all
    public static class Robolectric {

        public static final String androidAll = "org.robolectric:android-all:10-robolectric-5803371";
    }

    /**
     * Project libraries, modules in the `libraries` folder
     * and are shared across several Features modules
     */
    public static class Libraries {
        public static final String core = ":core";
        public static final String sdk = ":sdk";
        public static final String sdkKtx = ":sdk-ktx";
    }
}
