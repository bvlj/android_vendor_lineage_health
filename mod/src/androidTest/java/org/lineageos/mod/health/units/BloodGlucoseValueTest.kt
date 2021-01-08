/*
 * Copyright (C) 2021 The LineageOS Project
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

package org.lineageos.mod.health.units

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.lineageos.mod.health.sdk.model.values.BloodGlucoseValue

@RunWith(JUnit4::class)
class BloodGlucoseValueTest {

    // We want error margin down to 0.1 for mmol/L
    // but we can tolerate errors up to 1.0 for mg/dL

    @Test
    fun mgdL() {
        val mgdL = BloodGlucoseValue.mgdL(100)
        Assert.assertEquals(100.0, mgdL.mgdL().toDouble(), 1.0)
        Assert.assertEquals(5.55, mgdL.mmolL(), 0.1)
    }

    @Test
    fun mmolL() {
        val mmolL = BloodGlucoseValue.mmolL(6.7)
        Assert.assertEquals(6.7, mmolL.mmolL(), 0.1)
        Assert.assertEquals(120.0, mmolL.mgdL().toDouble(), 1.0)
    }
}
