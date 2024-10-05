package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;

/**
 * Selection Sort
 * idea
 * - find the smallest item and swap with the first item of the array that needs to be sorted...
 * traits
 * - insensitive to input: all case O(n^2)
 * - data movement is minimal
 * - not stable: may change the relative order of equal elements
 */
public class SelectionSort<T extends Comparable<T>> implements SortTemplate<T> {
    @Override
    public void sort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            // find the smallest value index from [i, arr.length)
            int min = i;
            for (int j = i + 1; j < arr.length; j++)
                if (less(arr[j], arr[min]))
                    min = j;
            // swap
            exchange(arr, i, min);
        }
    }
}