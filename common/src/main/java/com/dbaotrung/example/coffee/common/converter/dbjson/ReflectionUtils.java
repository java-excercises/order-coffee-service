package com.dbaotrung.example.coffee.common.converter.dbjson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

    private static final String GETTER_PREFIX = "get";

    private static final String SETTER_PREFIX = "set";

    /**
     * Prevent any instantiation.
     */
    private ReflectionUtils() {
        throw new UnsupportedOperationException("The " + getClass() + " is not instantiable!");
    }

    /**
     * Get the {@link Method} with the given signature (name and parameter types) belonging to the
     * provided Java {@link Object}.
     *
     * @param target target {@link Object}
     * @param methodName method name
     * @param parameterTypes method parameter types
     * @return return {@link Method} matching the provided signature
     */
    public static Method getMethod(Object target, String methodName,Class<?> ... parameterTypes) {
        return getMethod(target.getClass(), methodName, parameterTypes);
    }

    /**
     * Get the {@link Method} with the given signature (name and parameter types) belonging to the
     * provided Java {@link Class}.
     *
     * @param targetClass target {@link Class}
     * @param methodName method name
     * @param parameterTypes method parameter types
     * @return the {@link Method} matching the provided signature
     */
    public static Method getMethod(Class<?> targetClass, String methodName,Class<?> ... parameterTypes) {
        try {
            return targetClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            try {
                return targetClass.getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException ignore) {
            }

            if (!targetClass.getSuperclass().equals(Object.class)) {
                return getMethod(targetClass.getSuperclass(), methodName, parameterTypes);
            } else {
                throw handleException(e);
            }
        }
    }

    /**
     * Get the {@link Method} with the given signature (name and parameter types) belonging to the
     * provided Java {@link Class}, excluding inherited ones, or {@code null} if no {@link Method} was
     * found.
     *
     * @param targetClass target {@link Class}
     * @param methodName method name
     * @param parameterTypes method parameter types
     * @return return {@link Method} matching the provided signature or {@code null}
     */
    public static Method getDeclaredMethodOrNull(Class<?> targetClass, String methodName,Class<?> ... parameterTypes) {
        try {
            return targetClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Get the property setter {@link Method} with the given signature (name and parameter types)
     * belonging to the provided Java {@link Object}.
     *
     * @param target target {@link Object}
     * @param propertyName property name
     * @param parameterType setter property type
     * @return the setter {@link Method} matching the provided signature
     */
    public static Method getSetter(Object target, String propertyName, Class<?> parameterType) {
        String setterMethodName = SETTER_PREFIX + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method setter = getMethod(target, setterMethodName, parameterType);
        setter.setAccessible(true);
        return setter;
    }

    /**
     * Get the property getter {@link Method} with the given name belonging to the provided Java
     * {@link Object}.
     *
     * @param target target {@link Object}
     * @param propertyName property name
     * @return the getter {@link Method} matching the provided name
     */
    public static Method getGetter(Object target, String propertyName) {
        String getterMethodName = GETTER_PREFIX + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method getter = getMethod(target, getterMethodName);
        getter.setAccessible(true);
        return getter;
    }

    /**
     * Invoke the property getter with the provided name on the given Java {@link Object}.
     *
     * @param target target {@link Object} whose property getter we are invoking
     * @param propertyName property name whose getter we are invoking
     * @param <T> return value object type
     * @return the value return by the getter invocation
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeGetter(Object target, String propertyName) {
        Method setter = getGetter(target, propertyName);
        try {
            return (T) setter.invoke(target);
        } catch (IllegalAccessException e) {
            throw handleException(e);
        } catch (InvocationTargetException e) {
            throw handleException(e);
        }
    }

    /**
     * Handle the {@link NoSuchMethodException} by rethrowing it as an {@link IllegalArgumentException}.
     *
     * @param e the original {@link NoSuchMethodException}
     * @return the {@link IllegalArgumentException} wrapping exception
     */
    private static IllegalArgumentException handleException(NoSuchMethodException e) {
        return new IllegalArgumentException(e);
    }

    /**
     * Handle the {@link IllegalAccessException} by rethrowing it as an
     * {@link IllegalArgumentException}.
     *
     * @param e the original {@link IllegalAccessException}
     * @return the {@link IllegalArgumentException} wrapping exception
     */
    private static IllegalArgumentException handleException(IllegalAccessException e) {
        return new IllegalArgumentException(e);
    }

    /**
     * Handle the {@link InvocationTargetException} by rethrowing it as an
     * {@link IllegalArgumentException}.
     *
     * @param e the original {@link InvocationTargetException}
     * @return the {@link IllegalArgumentException} wrapping exception
     */
    private static IllegalArgumentException handleException(InvocationTargetException e) {
        return new IllegalArgumentException(e);
    }
    
}
