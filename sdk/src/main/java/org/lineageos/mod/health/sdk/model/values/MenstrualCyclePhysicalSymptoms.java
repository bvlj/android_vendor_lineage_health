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

public final class MenstrualCyclePhysicalSymptoms {

    private MenstrualCyclePhysicalSymptoms() {
    }

    public static final int NONE = 0;
    public static final int ACNE = 1;
    public static final int BLOATING = 1 << 1;
    public static final int CRAMPS = 1 << 2;
    public static final int CONSTIPATION = 1 << 3;
    public static final int FATIGUE = 1 << 4;
    public static final int HEADACHE = 1 << 5;
    public static final int JOINT_MUSCLE_PAIN = 1 << 6;
    public static final int SPOTTING = 1 << 7;
    public static final int TENDER_BREASTS = 1 << 8;

    @IntDef(flag = true,
            value = {
                    NONE,
                    ACNE,
                    BLOATING,
                    CRAMPS,
                    CONSTIPATION,
                    FATIGUE,
                    HEADACHE,
                    JOINT_MUSCLE_PAIN,
                    SPOTTING,
                    TENDER_BREASTS,
            }
    )
    @Retention(RetentionPolicy.SOURCE)
    public @interface Value {
    }
}
