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

package org.lineageos.build

// AndroidX - https://developer.android.com/jetpack/androidx/versions
object AndroidX {
    const val gradlePlugin = "com.android.tools.build:gradle:4.1.0"

    // Core - KTX - https://developer.android.com/jetpack/androidx/releases/annotation
    const val annotationX = "androidx.annotation:annotation:1.1.0"
}

// Kotlin - https://github.com/JetBrains/kotlin/releases
object Kotlin {
    private const val VERSION = "1.4.10"

    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"

    object StdLib {

        const val jvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$VERSION"
    }

    // Coroutines - https://github.com/Kotlin/kotlinx.coroutines/releases
    object Coroutines {
        private const val VERSION = "1.4.1"

        const val jvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"
    }
}

/**
 * Project modules
 */
object Libraries {
    const val core = ":core"
    const val sdk = ":sdk"
    const val sdkKtx = ":sdk-ktx"
}

