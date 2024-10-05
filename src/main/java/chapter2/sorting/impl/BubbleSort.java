package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;

/**
 * Bubble Sort
 * Idea
 * - compares adjacent elements, and swaps them if they are in the wrong order
 */
public class BubbleSort<T extends Comparable<T>> implements SortTemplate<T> {
    @Override
    public void sort(T[] arr) {
        for (int i = arr.length; i > 0; i--)
            // i: the upper bound of the unsorted sub array
            for (int j = 1; j < i; j++)
                // bubble the max to the end of the array
                if (less(arr[j], arr[j - 1]))
                    exchange(arr, j, j - 1);

    }
}
