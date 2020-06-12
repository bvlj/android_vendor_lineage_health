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

package org.lineageos.mod.health.sdk.model.records.activity;

import androidx.annotation.Keep;

import org.lineageos.mod.health.common.Metric;

/**
 * Steps count record.
 *
 * <a href="https://en.wikipedia.org/wiki/Walking">More info</a>
 */

@Keep
@SuppressWarnings("unused")
public final class StepsRecord extends ActivityRecord {

    public StepsRecord(long id, long time, long duration, long steps) {
        super(id, Metric.STEPS, time, duration, 0.0, 0,
                0.0, 0.0, "", steps);
    }

    @Override
    public long getSteps() {
        return super.getSteps();
    }

    @Override
    public void setSteps(long steps) {
        super.setSteps(steps);
    }
}
