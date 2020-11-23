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
                         double avgSpeed, double distance) {
        super(id, Metric.RUNNING, time, duration, avgSpeed, 0,
                distance, 0, "", 0);
    }

    /**
     * @return Average speed in kilometers per hour (km/h)
     */
    @Override
    public double getAvgSpeed() {
        return super.getAvgSpeed();
    }

    /**
     * @param avgSpeed Average speed in kilometers per hour (km/h)
     */
    @Override
    public void setAvgSpeed(double avgSpeed) {
        super.setAvgSpeed(avgSpeed);
    }

    /**
     * @return Distance in kilometers (km)
     */
    @Override
    public double getDistance() {
        return super.getDistance();
    }

    /**
     * @param distance Distance in kilometers (km)
     */
    @Override
    public void setDistance(double distance) {
        super.setDistance(distance);
    }
}
