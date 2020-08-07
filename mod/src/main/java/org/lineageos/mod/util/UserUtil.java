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

package org.lineageos.mod.util;

import android.annotation.SuppressLint;
import android.os.Process;
import android.os.UserHandle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * This class must remain in java to allow us to access
 * "int.class" that we can't reference from kotlin
 */
public final class UserUtil {

    private UserUtil() {
    }

    @SuppressLint("DiscouragedPrivateApi")
    @SuppressWarnings("JavaReflectionMemberAccess")
    public static int getCurrentUserId() {
        try {
             final Method getUserId = UserHandle.class.getDeclaredMethod(
                    "getUserId", int.class);
            final Object result = getUserId.invoke(null, Process.myUid());
            if (result instanceof Integer) {
                return (Integer) result;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
