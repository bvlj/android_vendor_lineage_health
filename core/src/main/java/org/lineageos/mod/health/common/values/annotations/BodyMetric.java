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

package org.lineageos.mod.health.common.values.annotations;

import androidx.annotation.IntDef;

import org.lineageos.mod.health.common.Metric;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * {@link IntDef} annotation to mark that a value
 * needs to be one of the following:
 *
 * <ul>
 *     <li>{@link Metric#ABDOMINAL_CIRCUMFERENCE}</li>
 *     <li>{@link Metric#BODY_MASS_INDEX}</li>
 *     <li>{@link Metric#BODY_TEMPERATURE}</li>
 *     <li>{@link Metric#LEAN_BODY_MASS}</li>
 *     <li>{@link Metric#MENSTRUAL_CYCLE}</li>
 *     <li>{@link Metric#UV_INDEX}</li>
 *     <li>{@link Metric#LEAN_BODY_MASS}</li>
 *     <li>{@link Metric#WATER_INTAKE}</li>
 *     <li>{@link Metric#WEIGHT}</li>
 * </ul>
 */
@IntDef({
        Metric.ABDOMINAL_CIRCUMFERENCE,
        Metric.BODY_MASS_INDEX,
        Metric.BODY_TEMPERATURE,
        Metric.LEAN_BODY_MASS,
        Metric.MENSTRUAL_CYCLE,
        Metric.UV_INDEX,
        Metric.WATER_INTAKE,
        Metric.WEIGHT,
})
@Retention(RetentionPolicy.SOURCE)
public @interface BodyMetric {
}
