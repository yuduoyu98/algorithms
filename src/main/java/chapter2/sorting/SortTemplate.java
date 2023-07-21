package chapter2.sorting;

import edu.princeton.cs.algs4.StdOut;

public interface SortTemplate<T extends Comparable<T>> {
    default boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    default void exchange(T[] arr, int i, int j) {
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    default void print(T[] arr) {
        print(arr, null);
    }

    default void print(T[] arr, String prefix) {
        if (prefix != null) {
            StdOut.print(prefix + ": ");
        }
        for (T t : arr) {
            StdOut.print(t + " ");
        }
        StdOut.println();
    }

    default boolean isSorted(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (less(arr[i], arr[i - 1])) {
                return false;
            }
        }
        return true;
    }

    void sort(T[] arr);

}
