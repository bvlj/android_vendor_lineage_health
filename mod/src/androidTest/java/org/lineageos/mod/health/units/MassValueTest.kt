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
import org.lineageos.mod.health.sdk.model.values.MassValue

@RunWith(JUnit4::class)
class MassValueTest {

    companion object {
        private const val DELTA = 0.0001
    }

    @Test
    fun grams() {
        val value = MassValue.grams(426.8)
        Assert.assertEquals(426.8, value.grams(), DELTA)
        Assert.assertEquals(0.4268, value.kilograms(), DELTA)
        Assert.assertEquals(15.0549, value.ounces(), DELTA)
        Assert.assertEquals(0.9409, value.pounds(), DELTA)
    }

    @Test
    fun kilograms() {
        val value = MassValue.kilograms(426.8)
        Assert.assertEquals(426800.0, value.grams(), DELTA)
        Assert.assertEquals(426.8, value.kilograms(), DELTA)
        Assert.assertEquals(15054.9286, value.ounces(), DELTA)
        Assert.assertEquals(940.9329, value.pounds(), DELTA)
    }

    @Test
    fun ounces() {
        val value = MassValue.ounces(426.8)
        Assert.assertEquals(12099.5751, value.grams(), DELTA)
        Assert.assertEquals(12.0995, value.kilograms(), DELTA)
        Assert.assertEquals(426.8, value.ounces(), DELTA)
        Assert.assertEquals(26.675, value.pounds(), DELTA)
    }

    @Test
    fun pounds() {
        val value = MassValue.pounds(426.8)
        Assert.assertEquals(193593.2235, value.grams(), DELTA)
        Assert.assertEquals(193.5932, value.kilograms(), DELTA)
        Assert.assertEquals(6828.8007, value.ounces(), DELTA)
        Assert.assertEquals(426.8, value.pounds(), DELTA)
    }
}
