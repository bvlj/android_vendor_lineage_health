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

package org.lineageos.mod.health.common.db;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;

public interface MedicalProfileColumns extends BaseColumns {

    @NonNull
    String _VERSION = "_version";

    @NonNull
    String ALLERGIES = "allergies";

    @NonNull
    String BLOOD_TYPE = "blood_type";

    @NonNull
    String HEIGHT = "height";

    @NonNull
    String MEDICATIONS = "medications";

    @NonNull
    String NOTES = "notes";

    @NonNull
    String ORGAN_DONOR = "organ_donor";

    @NonNull
    String BIOLOGICAL_SEX = "sex";
}
