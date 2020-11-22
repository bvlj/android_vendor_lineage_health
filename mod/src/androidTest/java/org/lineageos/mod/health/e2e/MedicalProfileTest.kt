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

package org.lineageos.mod.health.e2e

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lineageos.mod.health.sdk.model.profile.MedicalProfile
import org.lineageos.mod.health.common.values.BiologicalSex
import org.lineageos.mod.health.common.values.BloodType
import org.lineageos.mod.health.common.values.OrganDonor
import org.lineageos.mod.health.sdk.repo.MedicalProfileRepo

@RunWith(AndroidJUnit4::class)
class MedicalProfileTest {
    private lateinit var repo: MedicalProfileRepo

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        repo = MedicalProfileRepo.getInstance(context.contentResolver).apply { reset() }
    }

    @After
    fun tearDown() {
        repo.reset()
    }

    @Test
    fun testDefault() {
        repo.reset()
        Assert.assertEquals(repo.get(), MedicalProfile())
    }

    @Test
    fun testInsert() {
        val a = MedicalProfile(
            "Allergies - test",
            BloodType.A_NEG,
            Float.MAX_VALUE,
            "Medications - test",
            "Notes - test",
            OrganDonor.NO,
            BiologicalSex.MALE
        )
        val b = MedicalProfile(
            "Allergies - test",
            BloodType.B_NEG,
            10f,
            "Medications - test",
            "Notes - test",
            OrganDonor.UNKNOWN,
            BiologicalSex.UNKNOWN
        )

        Assert.assertNotEquals(a, b)
        Assert.assertTrue(repo.set(a))
        Assert.assertEquals(a, repo.get())
        Assert.assertTrue(repo.set(b))
        Assert.assertNotEquals(a, repo.get())
        Assert.assertEquals(b, repo.get())
    }
}
