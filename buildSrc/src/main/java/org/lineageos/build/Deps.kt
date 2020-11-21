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
@file:Suppress("unused")

package org.lineageos.build

// AndroidX - https://developer.android.com/jetpack/androidx/versions
object AndroidX {
    const val gradlePlugin = "com.android.tools.build:gradle:4.1.1"

    // Core - KTX - https://developer.android.com/jetpack/androidx/releases/annotation
    const val annotationX = "androidx.annotation:annotation:1.1.0"

    // Core - KTX - https://developer.android.com/jetpack/androidx/releases/sqlite
    const val sqLite = "androidx.sqlite:sqlite:2.1.0"

    object Test {
        private const val VERSION = "1.2.0"

        const val core = "androidx.test:core:$VERSION"
        const val rules = "androidx.test:rules:$VERSION"

        const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"

        object Ext {
            private const val VERSION = "1.1.2-rc01"
            const val junit = "androidx.test.ext:junit-ktx:$VERSION"
        }
    }
}

// Kotlin - https://github.com/JetBrains/kotlin/releases
object Kotlin {
    private const val VERSION = "1.4.20"

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

object SqlCipher {
    private const val VERSION = "4.4.1"

    const val android = "net.zetetic:android-database-sqlcipher:$VERSION"
}

/**
 * Project modules
 */
object Libraries {
    const val core = ":core"
    const val sdk = ":sdk"
    const val sdkKtx = ":sdk-ktx"
}

