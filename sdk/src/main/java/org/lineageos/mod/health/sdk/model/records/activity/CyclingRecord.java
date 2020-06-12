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
 * Cycling activity.
 *
 * <ul>
 *     <li>Duration in minutes</li>
 *     <li>Distance in km</li>
 *     <li>Elevation gain in m</li>
 *     <li>Average speed in km/h</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Cycling">More info</a>
 */
@Keep
@SuppressWarnings("unused")
public final class CyclingRecord extends ActivityRecord {

    public CyclingRecord(long id, long time,
                         long duration, double avgSpeed,
                         double distance, double elevationGain) {
        super(id, Metric.CYCLING, time, duration, avgSpeed, 0,
                distance, elevationGain, "", 0);
    }

    @Override
    public double getAvgSpeed() {
        return super.getAvgSpeed();
    }

    @Override
    public void setAvgSpeed(double avgSpeed) {
        super.setAvgSpeed(avgSpeed);
    }

    @Override
    public double getDistance() {
        return super.getDistance();
    }

    @Override
    public void setDistance(double distance) {
        super.setDistance(distance);
    }

    @Override
    public double getElevationGain() {
        return super.getElevationGain();
    }

    @Override
    public void setElevationGain(double elevationGain) {
        super.setElevationGain(elevationGain);
    }
}
