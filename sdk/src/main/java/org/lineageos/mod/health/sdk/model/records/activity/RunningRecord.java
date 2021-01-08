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
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.sdk.model.values.LengthValue;
import org.lineageos.mod.health.sdk.model.values.SpeedValue;

/**
 * Running activity.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Long} duration: duration in milliseconds (ms)</li>
 *     <li>{@link Double} distance: distance in kilometers (km)</li>
 *     <li>{@link Double} avg_speed: average speed in kilometers per hour (km/h)</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Running">More info</a>
 *
 * @see Metric#RUNNING
 */
@Keep
public final class RunningRecord extends ActivityRecord {

    public RunningRecord(long id, long time, long duration,
                         @NonNull SpeedValue avgSpeed,
                         @NonNull LengthValue distance) {
        super(id, Metric.RUNNING, time, duration, avgSpeed, 0,
                distance, LengthValue.ZERO, "", 0);
    }

    @NonNull
    @Override
    public SpeedValue getAvgSpeed() {
        return super.getAvgSpeed();
    }

    @Override
    public void setAvgSpeed(@NonNull SpeedValue avgSpeed) {
        super.setAvgSpeed(avgSpeed);
    }

    @NonNull
    @Override
    public LengthValue getDistance() {
        return super.getDistance();
    }

    @Override
    public void setDistance(@NonNull LengthValue distance) {
        super.setDistance(distance);
    }
}
