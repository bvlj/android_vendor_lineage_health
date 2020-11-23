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

import org.lineageos.mod.health.HealthStore.Version;
import org.lineageos.mod.health.common.values.BiologicalSex;
import org.lineageos.mod.health.common.values.BloodType;
import org.lineageos.mod.health.common.values.OrganDonor;

/**
 * Medical profile table columns.
 */
public interface MedicalProfileColumns extends BaseColumns {

    /**
     * @see Version
     */
    @NonNull
    String _VERSION = "_version";

    /**
     * Information about user's allergies.
     *
     * {@link String} (default to <pre>""</pre>)
     */
    @NonNull
    String ALLERGIES = "allergies";

    /**
     * Information about user's blood type.
     *
     * {@link Integer} (default to {@link BloodType#UNKNOWN})
     *
     * @see BloodType
     */
    @NonNull
    String BLOOD_TYPE = "blood_type";

    /**
     * Information about user's height in centimeters (cm).
     *
     * {@link Float} (default to <pre>0f</pre>)
     */
    @NonNull
    String HEIGHT = "height";

    /**
     * Information about user's medications.
     *
     * {@link String} (default to <pre>""</pre>)
     */
    @NonNull
    String MEDICATIONS = "medications";

    /**
     * Notes about the user.
     *
     * {@link String} (default to <pre>""</pre>)
     */
    @NonNull
    String NOTES = "notes";

    /**
     * Information about whether the user is an organ donor.
     *
     * {@link Integer} (default to {@link OrganDonor#UNKNOWN})
     *
     * @see OrganDonor
     */
    @NonNull
    String ORGAN_DONOR = "organ_donor";

    /**
     * Information about the user's biological sex.
     *
     * {@link Integer} (default to {@link BiologicalSex#UNKNOWN})
     *
     * @see BiologicalSex
     */
    @NonNull
    String BIOLOGICAL_SEX = "sex";
}
