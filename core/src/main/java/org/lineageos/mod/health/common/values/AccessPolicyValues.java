/*
 * Copyright (C) 2021 The LineageOS Project
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

package org.lineageos.mod.health.common.values;

import android.net.Uri;

import org.lineageos.mod.health.common.db.AccessColumns;

/**
 * Content access constant values that might be returned from the content
 * provider due to some policy.
 *
 * @see AccessColumns
 * @see Permission
 */
public final class AccessPolicyValues {

    private AccessPolicyValues() {
    }

    /**
     * Count returned from denied update &amp; delete operations
     */
    public static final int DENIED_RESULT = -2;

    /**
     * Uri returned from denied insert operations
     */
    public static final Uri DENIED_URI = Uri.EMPTY;
}
