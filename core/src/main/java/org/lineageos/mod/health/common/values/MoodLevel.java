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
 * Mood level.
 *
 * @see Metric#MOOD
 */
public final class MoodLevel {

    private MoodLevel() {
    }

    public static final int UNKNOWN = 0;
    public static final int AMAZING = 1;
    public static final int HAPPY = 1 << 1;
    public static final int EXCITED = 1 << 2;
    public static final int STRESSED = 1 << 3;
    public static final int FOCUSED = 1 << 4;
    public static final int TIRED = 1 << 5;
    public static final int SAD = 1 << 6;
    public static final int SICK = 1 << 7;
    public static final int EXHAUSTED = 1 << 8;
    public static final int NERVOUS = 1 << 9;
    public static final int ANGRY = 1 << 10;

    @IntDef(flag = true,
            value = {
                    UNKNOWN,
                    AMAZING,
                    HAPPY,
                    EXCITED,
                    STRESSED,
                    FOCUSED,
                    TIRED,
                    SAD,
                    SICK,
                    EXHAUSTED,
                    NERVOUS,
                    ANGRY,
            }
    )
    @Retention(RetentionPolicy.SOURCE)
    public @interface Value {
    }
}
