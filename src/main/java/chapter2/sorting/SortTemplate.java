package chapter2.sorting;

import edu.princeton.cs.algs4.StdOut;

/**
 * sort implementation template
 * - using less() and exchange() to implement sort()
 */
public interface SortTemplate<T extends Comparable<T>> {

    void sort(T[] arr);

    // whether a < b ?
    default boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    // exchange value indexed at i and j
    default void exchange(T[] arr, int i, int j) {
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // print the arr
    default void print(T[] arr) {
        print(arr, null);
    }

    // print the arr with index
    default void print(T[] arr, String prefix) {
        if (prefix != null) StdOut.print(prefix + ": ");
        for (T t : arr) StdOut.print(t + " ");
        StdOut.println();
    }

    // check: ascending order
    default boolean isSorted(T[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (less(arr[i], arr[i - 1]))
                return false;
        return true;
    }

    default boolean isSorted(T[] arr, int start, int end) {
        for (int i = start + 1; i < end; i++)
            if (less(arr[i], arr[i - 1]))
                return false;
        return true;
    }

}
