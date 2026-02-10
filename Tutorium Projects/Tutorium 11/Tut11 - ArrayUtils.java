package tut11;

import java.util.function.Function;

public final class ArrayUtils {

    private ArrayUtils() {
        throw new UnsupportedOperationException("Utility classes cannot be instantiated");
    }

    /**
     * This method will search for an element in an array
     * @param array The array to be searched
     * @param elem The element that will be searched for
     * @return The first index at which the element can be found. If there is no element in the array, return -1
     */
    public static int findFirst(int[] array, int elem) {
        for (int i = 1; i <= array.length; i++) {
            if (array[i-1] == elem) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method finds every occurrence of an element in an array.
     * @param array The element to be searched
     * @param elem The Element that will be searched for
     * @return An array with every index the element can be found at. If there is no element in the array, return null.
     */
    public static int[] findAll(int[] array, int elem) {
        // Count occurrences of 'elem' to be able to create a new array with every index
        int occurrences = 0;
        for (int j : array) {
            if (j == elem) {
                occurrences++;
            }
        }

        int[] indexes = new int[occurrences];
        int currentIndex = 0; //this variable keeps track where to insert the next found index

        // Insert every found index into the array
        for (int i = 1; i < array.length; i++) {
            if (array[i] == elem) {
                indexes[currentIndex] = i;
                currentIndex++;
            }
        }
        return indexes;
    }

    /**
     * Increments every element of the array by the given value
     * @param array The array which will be incremented
     * @param increment The amount the elements will be incremented
     * @return The array with the incremented values
     */
    public static int[] incrementAll(int[] array, int increment) {
        for (int i = 0; i < array.length; i++) {
            array[i] += increment;
        }
        return array;
    }

    /**
     * Inserts a new element at the end of the array.
     * @param array The base array
     * @param newElement The element that will be inserted
     * @return The array with the appended element
     */
    public static int[] insert(int[] array, int newElement) {
        // Copy Array
        int[] newArray = new int[array.length + 1];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        // Insert 'newElement' at the end
        newArray[array.length] = newElement;
        return newArray;
    }

    /**
     * Joins two sorted arrays together. The result will still be sorted.
     * @param first The first array.
     * @param second The second array.
     * @return A new sorted array with all elements together.
     */
    static int[] sortedJoin(int[] first, int[] second) {
        int[] newArray = new int[first.length + second.length];

        int firstArrayIndex = 0;
        int secondArrayIndex = 0;

        while (firstArrayIndex < first.length && secondArrayIndex < second.length) {

            if (first[firstArrayIndex] < second[secondArrayIndex]) {
                // Insert from first array since that element is smaller
                newArray[firstArrayIndex + secondArrayIndex] = first[firstArrayIndex];
                firstArrayIndex++;

            } else if (first[firstArrayIndex] > second[secondArrayIndex]) {
                // Insert from second array since that element is smaller
                newArray[firstArrayIndex + secondArrayIndex] = second[secondArrayIndex];
                secondArrayIndex++;
            }
        }
        //At this point exactly one array should be copied completely
        assert (firstArrayIndex == first.length) ^ (secondArrayIndex == second.length);

        while (firstArrayIndex < first.length) {
            // Insert from the first array since only those elements remain
            newArray[firstArrayIndex + secondArrayIndex] = first[firstArrayIndex];
            firstArrayIndex++;
        }
        while (secondArrayIndex < second.length) {
            // Insert from the second array since only those elements remain
            newArray[firstArrayIndex + secondArrayIndex] = second[secondArrayIndex];
            secondArrayIndex++;
        }

        return newArray;
    }

    /**
     * Advanced exercise:
     * This method takes and array of integers and a function (int → int) and applies said function on
     * every element of the array in place.
     * @param array The array with the elements that will be mapped
     * @param map The function that will be applied to every element of the array
     * @return The array, but with the mapped values.
     */
    public static int[] map(int[] array, Function<Integer, Integer> map) {
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = map.apply(array[i]);
        }
        return array;
    }
}
