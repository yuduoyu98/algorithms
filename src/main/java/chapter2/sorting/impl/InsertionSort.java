package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;

/**
 * Insertion Sort
 * idea
 * - sort cards, consider a 'card' at a time, insert it into the right position
 * traits
 * - relatively efficient for partially ordered or non-random arrays:
 *      - the initial orderliness of the array can greatly enhance the efficiency of insertion sort.
 * - inversions: a pair of elements with the reverse order
 */
public class InsertionSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    public void sort(T[] arr) {
        sort3(arr);
    }

    // bottom up insertion
    @SuppressWarnings("unused")
    private void sort1(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            T insertVal = arr[i];
            for (int j = 0; j < i; j++)
                if (less(insertVal, arr[j])) {
                    // move room for insert value
                    for (int k = i; k > j; k--)
                        arr[k] = arr[k - 1];
                    // insert
                    arr[j] = insertVal;
                    break;
                }
        }
    }


    // top down insertion (swap with the neighbour until in right position)
    // similar to bubble sort
    // same exchange count, less count not sure
    @SuppressWarnings("unused")
    private void sort2(T[] arr) {
        for (int i = 1; i < arr.length; i++)
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) // put condition into the for loop -> omit the 'break'
                exchange(arr, j, j - 1);
    }

    // sort2 improved version (cutting half the array access)
    @SuppressWarnings("unused")
    private void sort3(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            T insertVal = arr[i];
            int j = i;
            for (; j > 0 && less(insertVal, arr[j - 1]); j--)
                // avoid actual exchange until find the right position
                arr[j] = arr[j - 1];
            // j+1 > j >= j-1 (origin arr[j] has exchanged to arr[j+1])
            arr[j] = insertVal;
        }
    }

}
