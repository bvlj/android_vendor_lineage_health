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

/**
 * Blood type
 */
public final class BloodType {

    private BloodType() {
    }

    public static final int UNKNOWN = 0;
    public static final int O_NEG = 1;
    public static final int O_POS = 2;
    public static final int A_NEG = 3;
    public static final int A_POS = 4;
    public static final int B_NEG = 5;
    public static final int B_POS = 6;
    public static final int AB_NEG = 7;
    public static final int AB_POS = 8;

    @IntDef({
            UNKNOWN,
            O_NEG,
            O_POS,
            A_NEG,
            A_POS,
            B_NEG,
            B_POS,
            AB_NEG,
            AB_POS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Value {
    }
}
