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
import org.lineageos.mod.health.sdk.ktx.MedicalProfileRepoKt
import org.lineageos.mod.health.sdk.model.profile.MedicalProfile
import org.lineageos.mod.health.sdk.model.values.BiologicalSex
import org.lineageos.mod.health.sdk.model.values.BloodType
import org.lineageos.mod.health.sdk.model.values.OrganDonor
import org.lineageos.mod.health.testsuite.tests.HsTest

class MedicalProfileTest : HsTest() {

    override val name = "Medical profile test"

    override suspend fun runTest(context: Context) {
        val repo = MedicalProfileRepoKt.getInstance(context.contentResolver)

        val testProfile1 = MedicalProfile(
            "Allergies - test",
            BloodType.A_NEG,
            Float.MAX_VALUE,
            "Medications - test",
            "Notes - test",
            OrganDonor.NO,
            BiologicalSex.MALE
        )
        val testProfile2 = MedicalProfile(
            "Allergies - test",
            BloodType.B_NEG,
            10f,
            "Medications - test",
            "Notes - test",
            OrganDonor.UNKNOWN,
            BiologicalSex.UNKNOWN
        )

        assert(testProfile1 != testProfile2, "Test profile 1 should not be equal to test profile 2")
        repo.reset()
        assert(repo.get() == MedicalProfile(), "Reset profile is not empty")
        assert(repo.set(testProfile1), "Failed to set test profile 1")
        assert(repo.get() == testProfile1, "Saved profile does not match test profile 1")
        assert(repo.set(testProfile2), "Failed to set test profile 2")
        assert(repo.get() != testProfile1, "Saved profile should not match test profile 1")
        assert(repo.get() == testProfile2, "Saved profile does not match test profile 2")
        assert(repo.reset(), "Failed to reset")
    }
}
