package cn.ilpanda.arch.java.base;

import java.util.Collection;

public final class Preconditions {


    private Preconditions() {
        // Utility class.
    }

    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> T checkNotNull(T arg) {
        return checkNotNull(arg, "Argument must not be null");
    }

    public static <T> T checkNotNull(T arg, String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }

    public static String checkNotEmpty(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }

    public static <T extends Collection<Y>, Y> T checkNotEmpty(T collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Must not be empty.");
        }
        return collection;
    }

}
