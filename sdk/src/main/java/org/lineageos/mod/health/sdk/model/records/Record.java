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

package org.lineageos.mod.health.sdk.model.records;

import android.content.ContentValues;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.annotations.MetricType;

import java.util.Objects;

@Keep
@SuppressWarnings("unused")
public abstract class Record {

    protected final long id;
    @MetricType
    protected final int metric;
    protected long time;

    public Record(long id, @MetricType int metric, long time) {
        this.id = id;
        this.metric = metric;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    @MetricType
    public int getMetric() {
        return metric;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @NonNull
    public abstract ContentValues toContentValues();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        final Record record = (Record) o;
        return id == record.id &&
                metric == record.metric &&
                time == record.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metric, time);
    }
}
