package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;

/**
 * <h2>Selection Sort</h2>
 * <strong>idea</strong> <p> Find the smallest item and swap with the first item of the array that needs to be sorted...</p>
 * <strong>traits</strong>
 * <ol>
 *     <li> Running time is insensitive to input
 *     <li> Data movement is minimal
 * </ol>
 */
public class SelectionSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    public void sort(T[] arr) {
        //1. 依次找出第i小的元素
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            //2.[i,arr.length)找出最小元素下标
            for (int j = i + 1; j < arr.length; j++) {
                if (less(arr[j], arr[min])) {
                    min = j;
                }
            }
            //3.交换
            exchange(arr, i, min);
        }

    }

}