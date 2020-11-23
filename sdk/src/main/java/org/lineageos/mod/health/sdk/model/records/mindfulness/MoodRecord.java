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

package org.lineageos.mod.health.sdk.model.records.mindfulness;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.values.MoodLevel;

/**
 * Meditation record.
 *
 * <ul>
 *     <li>{@link Long} id: db identifier (default to <code>0L</code>)</li>
 *     <li>{@link Long} time: timestamp ({@link System#currentTimeMillis()})</li>
 *     <li>{@link Integer} mood level: {@link MoodLevel}</li>
 *     <li>{@link String} notes: notes about the mood</li>
 * </ul>
 *
 * <a href="https://en.wikipedia.org/wiki/Mood_(psychology)">More info</a>
 *
 * @see Metric#MOOD
 */
@Keep
public final class MoodRecord extends MindfulnessRecord {

    public MoodRecord(long id, long time, @MoodLevel.Value int moodLevel, @NonNull String notes) {
        super(id, Metric.MOOD, time, 0, moodLevel, notes);
    }

    /**
     * @see MoodLevel
     */
    @MoodLevel.Value
    @Override
    public int getMoodLevel() {
        return super.getMoodLevel();
    }

    /**
     * @see MoodLevel
     */
    @Override
    public void setMoodLevel(@MoodLevel.Value int moodLevel) {
        super.setMoodLevel(moodLevel);
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
