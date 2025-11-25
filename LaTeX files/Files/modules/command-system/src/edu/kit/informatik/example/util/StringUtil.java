package edu.kit.informatik.example.util;

import java.util.StringJoiner;

/**
 * The {@code StringUtil} utility class provide methods for string
 * manipulations.
 *
 * @author Aurelia Huell
 * @version 1.0
 *
 * @see StringJoiner
 */

public final class StringUtil {

    /**
     * Private Constructor to prevent a object creation.
     */
    private StringUtil() {
    }

    /**
     * Place round brackets around a string.
     *
     * <p>Example: {@code Hello World} to {@code (Hello World)}</p>
     *
     * @param input string to process
     * @return string with round brackets
     */
    public static String bracket(final String input) {
        return "(" + input + ")";
    }

    /**
     * Concatenate every element of a specified iterable, separated by a
     * specified seperator.
     *
     * @param <T>       the contained type of the iterable
     * @param iterable  the specied iterable
     * @param separator the specified separator
     * @return the described string
     */
    public static <T> String toString(final Iterable<T> iterable,
            final String separator) {
        final StringJoiner representation = new StringJoiner(separator);

        for (final T element : iterable) {
            representation.add(element.toString());
        }

        return representation.toString();
    }
}
