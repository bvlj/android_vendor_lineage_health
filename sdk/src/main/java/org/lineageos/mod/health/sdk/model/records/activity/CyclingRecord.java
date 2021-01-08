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
 * Cycling activity.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Long} duration: duration in milliseconds (ms)</li>
 *     <li>{@link Double} distance: distance (default to km)</li>
 *     <li>{@link Double} avgSpeed: average speed (default to kilometers per hour - km/h)</li>
 *     <li>{@link Double} elevationGain: elevation gain (default to m)</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Cycling">More info</a>
 *
 * @see Metric#CYCLING
 */
@Keep
public final class CyclingRecord extends ActivityRecord {

    public CyclingRecord(long id, long time,
                         long duration,
                         @NonNull SpeedValue avgSpeed,
                         @NonNull LengthValue distance,
                         @NonNull LengthValue elevationGain) {
        super(id, Metric.CYCLING, time, duration, avgSpeed, 0,
                distance, elevationGain, "", 0);
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

    /**
     * @return Elevation gain
     */
    @NonNull
    @Override
    public LengthValue getElevationGain() {
        return super.getElevationGain();
    }

    /**
     * @param elevationGain Elevation gain in meters (m)
     */
    @Override
    public void setElevationGain(@NonNull LengthValue elevationGain) {
        super.setElevationGain(elevationGain);
    }
}
