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

package org.lineageos.mod.health.sdk.ktx

import android.content.ContentResolver
import androidx.annotation.Keep
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.lineageos.mod.health.sdk.model.profile.MedicalProfile
import org.lineageos.mod.health.sdk.repo.MedicalProfileRepo
import org.lineageos.mod.util.SingletonHolder

/**
 * Wrapper for [MedicalProfileRepo]
 * that makes use of coroutines
 */
@Keep
@Suppress("unused")
class MedicalProfileRepoKt private constructor(
    private val _repo: MedicalProfileRepo
) {
    private val dispatcher = Dispatchers.IO + CoroutineName("profile")

    suspend fun get(): MedicalProfile =
        withContext(dispatcher) { _repo.get() }

    suspend fun set(medicalProfile: MedicalProfile): Boolean =
        withContext(dispatcher) { _repo.set(medicalProfile) }

    suspend fun reset(): Boolean =
        withContext(dispatcher) { _repo.reset() }

    companion object : SingletonHolder<MedicalProfileRepoKt, ContentResolver>({
        MedicalProfileRepoKt(MedicalProfileRepo.getInstance(it))
    })
}
