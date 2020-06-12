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

package org.lineageos.mod.health.sdk.model.values;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MenstrualCycleOtherSymptoms {

    private MenstrualCycleOtherSymptoms() {
    }

    public static final int NONE = 0;
    public static final int ANXIETY = 1;
    public static final int CRYING_SPELLS = 1 << 1;
    public static final int DEPRESSION = 1 << 2;
    public static final int HIGH_SEX_DRIVE = 1 << 3;
    public static final int INSOMNIA = 1 << 4;
    public static final int MOOD_SWINGS = 1 << 5;
    public static final int POOR_CONCENTRATION = 1 << 6;
    public static final int SOCIAL_WITHDRAWAL = 1 << 7;

    @IntDef(flag = true,
            value = {
                    NONE,
                    ANXIETY,
                    CRYING_SPELLS,
                    DEPRESSION,
                    HIGH_SEX_DRIVE,
                    INSOMNIA,
                    MOOD_SWINGS,
                    POOR_CONCENTRATION,
                    SOCIAL_WITHDRAWAL,
            }
    )
    @Retention(RetentionPolicy.SOURCE)
    public @interface Value {
    }
}
