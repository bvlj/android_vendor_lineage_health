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
import org.lineageos.mod.health.sdk.model.values.LengthValue

@RunWith(JUnit4::class)
class LengthValueTest {

    companion object {
        private const val DELTA = 0.0001
    }

    @Test
    fun centimeters() {
        val value = LengthValue.centimeters(124.5)
        Assert.assertEquals(124.5, value.centimeters(), DELTA)
        Assert.assertEquals(4.084646, value.feet(), DELTA)
        Assert.assertEquals(49.01575, value.inches(), DELTA)
        Assert.assertEquals(0.001245, value.kilometers(), DELTA)
        Assert.assertEquals(1.245, value.meters(), DELTA)
        Assert.assertEquals(1245.0, value.millimeters(), DELTA)
        Assert.assertEquals(1.361549, value.yards(), DELTA)
    }

    @Test
    fun feet() {
        val value = LengthValue.feet(124.5)
        Assert.assertEquals(3794.76, value.centimeters(), DELTA)
        Assert.assertEquals(124.5, value.feet(), DELTA)
        Assert.assertEquals(1494.0, value.inches(), DELTA)
        Assert.assertEquals(0.0379476, value.kilometers(), DELTA)
        Assert.assertEquals(37.9476, value.meters(), DELTA)
        Assert.assertEquals(37947.6, value.millimeters(), DELTA)
        Assert.assertEquals(41.5, value.yards(), DELTA)
    }

    @Test
    fun inches() {
        val value = LengthValue.inches(124.5)
        Assert.assertEquals(316.23, value.centimeters(), DELTA)
        Assert.assertEquals(10.375, value.feet(), DELTA)
        Assert.assertEquals(124.5, value.inches(), DELTA)
        Assert.assertEquals(0.0031623, value.kilometers(), DELTA)
        Assert.assertEquals(3.1623, value.meters(), DELTA)
        Assert.assertEquals(3162.3, value.millimeters(), DELTA)
        Assert.assertEquals(3.458333, value.yards(), DELTA)
    }

    @Test
    fun kilometers() {
        val value = LengthValue.kilometers(124.5)
        Assert.assertEquals(1.245e7, value.centimeters(), DELTA)
        Assert.assertEquals(408464.5669, value.feet(), DELTA)
        Assert.assertEquals(4901574.8031, value.inches(), DELTA)
        Assert.assertEquals(124.5, value.kilometers(), DELTA)
        Assert.assertEquals(124500.0, value.meters(), DELTA)
        Assert.assertEquals(1.245e8, value.millimeters(), DELTA)
        Assert.assertEquals(136154.8556, value.yards(), DELTA)
    }

    @Test
    fun meters() {
        val value = LengthValue.meters(124.5)
        Assert.assertEquals(12450.0, value.centimeters(), DELTA)
        Assert.assertEquals(408.4646, value.feet(), DELTA)
        Assert.assertEquals(4901.5748, value.inches(), DELTA)
        Assert.assertEquals(0.1245, value.kilometers(), DELTA)
        Assert.assertEquals(124.5, value.meters(), DELTA)
        Assert.assertEquals(124500.0, value.millimeters(), DELTA)
        Assert.assertEquals(136.1549, value.yards(), DELTA)
    }

    @Test
    fun millimeters() {
        val value = LengthValue.millimeters(124.5)
        Assert.assertEquals(12.45, value.centimeters(), DELTA)
        Assert.assertEquals(0.4084646, value.feet(), DELTA)
        Assert.assertEquals(4.901575, value.inches(), DELTA)
        Assert.assertEquals(0.0001245, value.kilometers(), DELTA)
        Assert.assertEquals(0.1245, value.meters(), DELTA)
        Assert.assertEquals(124.5, value.millimeters(), DELTA)
        Assert.assertEquals(0.1361549, value.yards(), DELTA)
    }

    @Test
    fun yards() {
        val value = LengthValue.yards(124.5)
        Assert.assertEquals(11384.28, value.centimeters(), DELTA)
        Assert.assertEquals(373.5, value.feet(), DELTA)
        Assert.assertEquals(4482.0, value.inches(), DELTA)
        Assert.assertEquals(0.1138428, value.kilometers(), DELTA)
        Assert.assertEquals(113.8428, value.meters(), DELTA)
        Assert.assertEquals(113842.8, value.millimeters(), DELTA)
        Assert.assertEquals(124.5, value.yards(), DELTA)
    }
}
