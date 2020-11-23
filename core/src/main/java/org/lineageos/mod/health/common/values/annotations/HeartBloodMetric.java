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
 *     <li>{@link Metric#BLOOD_ALCOHOL_CONCENTRATION}</li>
 *     <li>{@link Metric#BLOOD_PRESSURE}</li>
 *     <li>{@link Metric#GLUCOSE}</li>
 *     <li>{@link Metric#HEART_RATE}</li>
 *     <li>{@link Metric#PERFUSION_INDEX}</li>
 * </ul>
 */
@IntDef({
        Metric.BLOOD_ALCOHOL_CONCENTRATION,
        Metric.BLOOD_PRESSURE,
        Metric.GLUCOSE,
        Metric.HEART_RATE,
        Metric.PERFUSION_INDEX,
})
@Retention(RetentionPolicy.SOURCE)
public @interface HeartBloodMetric {
}
