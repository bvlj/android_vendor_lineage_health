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

package org.lineageos.mod.health.testsuite.tests.impl

import android.content.Context
import org.lineageos.mod.health.sdk.HealthStore
import org.lineageos.mod.health.testsuite.tests.HsTest

class ModTest : HsTest() {

    override val name = "Mod base test"

    override suspend fun runTest(context: Context) {
        val apps = context.packageManager.getInstalledApplications(0)

        assert(
            apps.find { it.packageName == "org.lineageos.mod.health" } != null,
            "Mod app is not installed"
        )
        assert(HealthStore.isSupported(context), "System does not declare feature support")
    }
}
