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

package org.lineageos.mod.health.sdk.repo;

import androidx.annotation.NonNull;

/**
 * Repo operation results.
 */
public class OperationResult {

    private OperationResult() {}

    /**
     * Return value of a successful operation.
     *
     * @param <T> The held value of the operation
     */
    public static final class Success<T> extends OperationResult {

        @NonNull
        public final T result;

        /**
         * @hide
         */
        Success(@NonNull T result) {
            this.result = result;
        }
    }

    /**
     * Return value of a failed operation.
     */
    public static final class Failure extends OperationResult {
        /**
         * @hide
         */
        static final Failure INSTANCE = new Failure();

        private Failure() {
        }
    }

    /**
     * Return value of an operation blocked by a policy.
     */
    public static final class PolicyError extends OperationResult {
        /**
         * @hide
         */
        static final PolicyError INSTANCE = new PolicyError();

        private PolicyError() {
        }
    }
}
