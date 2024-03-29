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

package org.lineageos.mod.health.common.values;

import androidx.annotation.IntDef;

import org.lineageos.mod.health.common.Metric;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Relation to a meal.
 *
 * @see Metric#GLUCOSE
 */
public final class MealRelation {

    private MealRelation() {
    }

    public static final int UNKNOWN = 0;
    public static final int BEFORE = 1;
    public static final int AFTER = 2;

    @IntDef({
            UNKNOWN,
            BEFORE,
            AFTER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Value {
    }
}
