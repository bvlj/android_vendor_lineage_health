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

package org.lineageos.mod.health.sdk.model.profile;

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.HealthStore;
import org.lineageos.mod.health.common.db.MedicalProfileColumns;
import org.lineageos.mod.health.common.values.BiologicalSex;
import org.lineageos.mod.health.common.values.BloodType;
import org.lineageos.mod.health.common.values.OrganDonor;

import java.util.Objects;

/**
 * Medical Profile data class.
 *
 * <ul>
 *     <li>{@link String} allergies</li>
 *     <li>{@link Integer} bloodType: {@link BloodType}</li>
 *     <li>{@link Float} height: height in centimeters (cm)</li>
 *     <li>{@link String} medication</li>
 *     <li>{@link String} notes</li>
 *     <li>{@link Integer} organDonor: {@link OrganDonor}</li>
 *     <li>{@link Integer} biologicalSex: {@link BiologicalSex}</li>
 * </ul>
 */
@Keep
public final class MedicalProfile {

    @NonNull
    private String allergies;

    @BloodType.Value
    private int bloodType;

    private float height;

    @NonNull
    private String medications;

    @NonNull
    private String notes;

    @OrganDonor.Value
    private int organDonor;

    @BiologicalSex.Value
    private int biologicalSex;

    public MedicalProfile() {
        this.allergies = "";
        this.bloodType = BloodType.UNKNOWN;
        this.height = 0f;
        this.medications = "";
        this.notes = "";
        this.organDonor = OrganDonor.UNKNOWN;
        this.biologicalSex = BiologicalSex.UNKNOWN;
    }

    /**
     * @param bloodType {@link BloodType}
     * @param height Height in centimeters (cm)
     * @param organDonor {@link OrganDonor}
     * @param biologicalSex {@link BiologicalSex}
     */
    public MedicalProfile(@NonNull String allergies, @BloodType.Value int bloodType,
                          float height, @NonNull String medications,
                          @NonNull String notes, @OrganDonor.Value int organDonor,
                          @BiologicalSex.Value int biologicalSex) {
        this.allergies = allergies;
        this.bloodType = bloodType;
        this.height = height;
        this.medications = medications;
        this.notes = notes;
        this.organDonor = organDonor;
        this.biologicalSex = biologicalSex;
    }

    @NonNull
    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(@NonNull String allergies) {
        this.allergies = allergies;
    }

    /**
     * @see BloodType
     */
    @BloodType.Value
    public int getBloodType() {
        return bloodType;
    }

    /**
     * @see BloodType
     */
    public void setBloodType(@BloodType.Value int bloodType) {
        this.bloodType = bloodType;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @NonNull
    public String getMedications() {
        return medications;
    }

    public void setMedications(@NonNull String medications) {
        this.medications = medications;
    }

    @NonNull
    public String getNotes() {
        return notes;
    }

    public void setNotes(@NonNull String notes) {
        this.notes = notes;
    }

    /**
     * @see OrganDonor
     */
    @OrganDonor.Value
    public int getOrganDonor() {
        return organDonor;
    }

    /**
     * @see OrganDonor
     */
    public void setOrganDonor(@OrganDonor.Value int organDonor) {
        this.organDonor = organDonor;
    }

    /**
     * @see BiologicalSex
     */
    @BiologicalSex.Value
    public int getBiologicalSex() {
        return biologicalSex;
    }

    /**
     * @see BiologicalSex
     */
    public void setBiologicalSex(@BiologicalSex.Value int biologicalSex) {
        this.biologicalSex = biologicalSex;
    }

    /**
     * @return ContentValues to be used by the ContentProvider
     */
    @NonNull
    public ContentValues toContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(MedicalProfileColumns._VERSION, HealthStore.Version.CURRENT);
        cv.put(MedicalProfileColumns.ALLERGIES, allergies);
        cv.put(MedicalProfileColumns.BLOOD_TYPE, bloodType);
        cv.put(MedicalProfileColumns.HEIGHT, height);
        cv.put(MedicalProfileColumns.MEDICATIONS, medications);
        cv.put(MedicalProfileColumns.NOTES, notes);
        cv.put(MedicalProfileColumns.ORGAN_DONOR, organDonor);
        cv.put(MedicalProfileColumns.BIOLOGICAL_SEX, biologicalSex);
        return cv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalProfile)) return false;
        final MedicalProfile that = (MedicalProfile) o;
        return bloodType == that.bloodType &&
                Float.compare(that.height, height) == 0 &&
                organDonor == that.organDonor &&
                biologicalSex == that.biologicalSex &&
                allergies.equals(that.allergies) &&
                medications.equals(that.medications) &&
                notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allergies, bloodType, height, medications, notes, organDonor, biologicalSex);
    }
}
