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

package org.lineageos.mod.health.sdk.repo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.lineageos.mod.health.common.HealthStoreUri;
import org.lineageos.mod.health.common.db.MedicalProfileColumns;
import org.lineageos.mod.health.sdk.model.profile.MedicalProfile;

/**
 * Medical profile repository.
 * <p>
 * This class allows you to retrieve, insert, update and delete
 * medical profile information.
 * <p>
 * Operations performed in this class should be executed outside of the
 * main / UI thread.
 */
@Keep
@SuppressWarnings("unused")
public final class MedicalProfileRepo {
    private static final String[] DEFAULT_PROJECTION = {
            MedicalProfileColumns.ALLERGIES,
            MedicalProfileColumns.BLOOD_TYPE,
            MedicalProfileColumns.HEIGHT,
            MedicalProfileColumns.MEDICATIONS,
            MedicalProfileColumns.NOTES,
            MedicalProfileColumns.ORGAN_DONOR,
            MedicalProfileColumns.BIOLOGICAL_SEX,
    };

    @Nullable
    private volatile static MedicalProfileRepo instance;

    @NonNull
    private final ContentResolver contentResolver;

    private MedicalProfileRepo(@NonNull ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @NonNull
    public MedicalProfile get() {
        final Cursor cursor = contentResolver.query(HealthStoreUri.MEDICAL_PROFILE,
                DEFAULT_PROJECTION, null, null, null);
        if (cursor == null) {
            return new MedicalProfile();
        }

        final MedicalProfile medicalProfile;
        if (cursor.moveToFirst()) {
            medicalProfile = new MedicalProfile(
                    cursor.getString(cursor.getColumnIndex(MedicalProfileColumns.ALLERGIES)),
                    cursor.getInt(cursor.getColumnIndex(MedicalProfileColumns.BLOOD_TYPE)),
                    cursor.getFloat(cursor.getColumnIndex(MedicalProfileColumns.HEIGHT)),
                    cursor.getString(cursor.getColumnIndex(MedicalProfileColumns.MEDICATIONS)),
                    cursor.getString(cursor.getColumnIndex(MedicalProfileColumns.NOTES)),
                    cursor.getInt(cursor.getColumnIndex(MedicalProfileColumns.ORGAN_DONOR)),
                    cursor.getInt(cursor.getColumnIndex(MedicalProfileColumns.BIOLOGICAL_SEX))
            );
        } else {
            medicalProfile = new MedicalProfile();
        }

        cursor.close();
        return medicalProfile;
    }

    public boolean set(@NonNull MedicalProfile medicalProfile) {
        final ContentValues cv = medicalProfile.toContentValues();
        final Uri uri = contentResolver.insert(HealthStoreUri.MEDICAL_PROFILE, cv);
        return uri != null;
    }

    public boolean reset() {
        return contentResolver.delete(HealthStoreUri.MEDICAL_PROFILE, null, null) == 1;
    }

    @NonNull
    public static synchronized MedicalProfileRepo getInstance(
            @NonNull ContentResolver contentResolver) {
        final MedicalProfileRepo currentInstance = instance;
        if (currentInstance != null) {
            return currentInstance;
        }

        final MedicalProfileRepo newInstance = new MedicalProfileRepo(contentResolver);
        instance = newInstance;
        return newInstance;
    }
}
