/*
 * Original work Copyright (c) 2005-2008, The Android Open Source Project
 * Modified work Copyright (c) 2013, rovo89 and Tungstwenty
 * Modified work Copyright (c) 2015, Alibaba Mobile Infrastructure (Android) Team
 * Modified work Copyright (c) 2017, weishu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.robv.android.xposed;

import android.util.Log;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public final class DexposedBridge {

    private static final String TAG = "DexposedBridge";

    /**
     * Writes a message to BASE_DIR/log/debug.log (needs to have chmod 777)
     *
     * @param text log message
     */
    public synchronized static void log(String text) {

    }

    /**
     * Log the stack trace
     *
     * @param t The Throwable object for the stacktrace
     * @see DexposedBridge#log(String)
     */
    public synchronized static void log(Throwable t) {
        log(Log.getStackTraceString(t));
    }

    /**
     * Hook any method with the specified callback
     *
     * @param hookMethod The method to be hooked
     * @param callback
     */
    public static XC_MethodHook.Unhook hookMethod(Member hookMethod, XC_MethodHook callback) {
        return callback.new Unhook(hookMethod);
    }

    /**
     * Removes the callback for a hooked method
     *
     * @param hookMethod The method for which the callback should be removed
     * @param callback   The reference to the callback as specified in {@link #hookMethod}
     */
    public static void unhookMethod(Member hookMethod, XC_MethodHook callback) {

    }

    public static Set<XC_MethodHook.Unhook> hookAllMethods(Class<?> hookClass, String methodName, XC_MethodHook callback) {
        return new HashSet<>();
    }

    public static XC_MethodHook.Unhook findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        return null;
    }

    public static void unhookAllMethods() {

    }

    public static Set<XC_MethodHook.Unhook> hookAllConstructors(Class<?> hookClass, XC_MethodHook callback) {
        return new HashSet<>();
    }


    public static Object handleHookedArtMethod(Object artMethodObject, Object thisObject, Object[] args) {
        return null;
    }

    /**
     * Just for throw an checked exception without check
     *
     * @param exception The checked exception.
     * @param dummy     dummy.
     * @param <T>       fake type
     * @throws T the checked exception.
     */
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwNoCheck(Throwable exception, Object dummy) throws T {
        throw (T) exception;
    }

    /**
     * This method is called as a replacement for hooked methods.
     */
    private static Object handleHookedMethod(Member method, int originalMethodId, Object additionalInfoObj,
                                             Object thisObject, Object[] args) throws Throwable {
        return null;
    }

    public static Object invokeSuper(Object obj, Member method, Object... args) throws NoSuchFieldException {
        return null;
    }


    /**
     * Basically the same as {@link Method#invoke}, but calls the original method
     * as it was before the interception by Xposed. Also, access permissions are not checked.
     *
     * @param method     Method to be called
     * @param thisObject For non-static calls, the "this" pointer
     * @param args       Arguments for the method call as Object[] array
     * @return The result returned from the invoked method
     * @throws NullPointerException      if {@code receiver == null} for a non-static method
     * @throws IllegalAccessException    if this method is not accessible (see {@link AccessibleObject})
     * @throws IllegalArgumentException  if the number of arguments doesn't match the number of parameters, the receiver
     *                                   is incompatible with the declaring class, or an argument could not be unboxed
     *                                   or converted by a widening conversion to the corresponding parameter type
     * @throws InvocationTargetException if an exception was thrown by the invoked method
     */
    public static Object invokeOriginalMethod(Member method, Object thisObject, Object[] args) {
        return null;
    }

}
