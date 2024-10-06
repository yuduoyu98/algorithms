package chapter2.sorting;

import edu.princeton.cs.algs4.StdOut;

/**
 *
 */
public class ArrayTools {

    // print the arr
    public static <T extends Comparable<T>> void print(T[] arr) {
        print(arr, null);
    }

    // print the arr with index
    public static <T extends Comparable<T>> void print(T[] arr, String prefix) {
        if (prefix != null) StdOut.print(prefix + ": ");
        for (T t : arr) StdOut.print(t + " ");
        StdOut.println();
    }
}
