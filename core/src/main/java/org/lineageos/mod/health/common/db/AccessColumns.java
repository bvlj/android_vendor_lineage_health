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

package org.lineageos.mod.health.common.db;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import org.lineageos.mod.health.common.Metric;
import org.lineageos.mod.health.common.values.Permission;

/**
 * Access policy table columns.
 */
public interface AccessColumns extends BaseColumns {

    /**
     * Invoking package name.
     *
     * {@link String} must not be empty / null.
     */
    @NonNull
    String PKG_NAME = "pkg_name";

    /**
     * Target metric.
     *
     * {@link Integer}: One of {@link Metric} values
     *
     * @see Metric
     */
    @NonNull
    String METRIC = "metric";

    /**
     * Permissions granted to the corresponding {@link AccessColumns#PKG_NAME} over the
     * {@link AccessColumns#METRIC}.
     *
     * {@link Integer}: Defaults to {@link Permission#ALL}.
     *
     * @see Permission
     */
    @NonNull
    String PERMISSIONS = "permissions";
}
