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

package org.lineageos.mod.health.sdk.model.records.breathing;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.Metric;

@Keep
public final class InhalerUsageRecord extends BreathingRecord {

    public InhalerUsageRecord(long id, long time, @NonNull String notes) {
        super(id, Metric.INHALER_USAGE, time, notes, 0.0);
    }

    @NonNull
    @Override
    public String getNotes() {
        return super.getNotes();
    }

    @Override
    public void setNotes(@NonNull String notes) {
        super.setNotes(notes);
    }
}
