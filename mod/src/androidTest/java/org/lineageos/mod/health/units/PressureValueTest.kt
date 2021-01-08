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
import org.lineageos.mod.health.sdk.model.values.PressureValue

@RunWith(JUnit4::class)
class PressureValueTest {

    companion object {
        private const val DELTA = 0.0001
    }

    @Test
    fun atmospheres() {
        val value = PressureValue.atmospheres(924.1)
        Assert.assertEquals(924.1, value.atmospheres(), DELTA)
        Assert.assertEquals(936.3443, value.bars(), DELTA)
        Assert.assertEquals(27650.2212, value.inHg(), DELTA)
        Assert.assertEquals(702317.9407, value.mmHg(), DELTA)
        Assert.assertEquals(93634432.5, value.pascals(), DELTA)
    }

    @Test
    fun bars() {
        val value = PressureValue.bars(924.1)
        Assert.assertEquals(912.0157, value.atmospheres(), DELTA)
        Assert.assertEquals(924.1, value.bars(), DELTA)
        Assert.assertEquals(27288.6466, value.inHg(), DELTA)
        Assert.assertEquals(693133.9163, value.mmHg(), DELTA)
        Assert.assertEquals(92410000.0, value.pascals(), DELTA)
    }

    @Test
    fun inHg() {
        val value = PressureValue.inHg(924.1)
        Assert.assertEquals(30.8844, value.atmospheres(), DELTA)
        Assert.assertEquals(31.2936, value.bars(), DELTA)
        Assert.assertEquals(924.1, value.inHg(), DELTA)
        // Float math rounding fest
        Assert.assertEquals(23472.14, value.mmHg(), 0.1)
        Assert.assertEquals(3129362.8, value.pascals(), 1.0)
    }

    @Test
    fun mmHg() {
        val value = PressureValue.mmHg(924.1)
        Assert.assertEquals(1.2159, value.atmospheres(), DELTA)
        Assert.assertEquals(1.2320, value.bars(), DELTA)
        Assert.assertEquals(36.3818, value.inHg(), DELTA)
        Assert.assertEquals(924.1, value.mmHg(), DELTA)
        Assert.assertEquals(123202.8602, value.pascals(), DELTA)
    }

    @Test
    fun pascals() {
        val value = PressureValue.pascals(924.1)
        Assert.assertEquals(0.009120, value.atmospheres(), DELTA)
        Assert.assertEquals(0.009241, value.bars(), DELTA)
        Assert.assertEquals(0.2728, value.inHg(), DELTA)
        Assert.assertEquals(6.9313, value.mmHg(), DELTA)
        Assert.assertEquals(924.1, value.pascals(), DELTA)
    }
}
