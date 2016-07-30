package com.intellectualcrafters.plot.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.stream;

public class Common {

    public static <T> void arrayForEach(T[] array, Consumer<T> consumer) {
        if (array == null || consumer == null) {
            return;
        }
        stream(array).forEach(consumer);
    }

    public static <T> Optional<T> findFirst(T[] array, Predicate<T> predicate) {
        if (array == null || predicate == null) {
            return Optional.empty();
        }
        return Arrays.stream(array).filter(predicate).findFirst();
    }

}
