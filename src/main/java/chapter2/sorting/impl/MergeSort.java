package chapter2.sorting.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter2.sorting.SortTemplate;

/**
 * Merge Sort
 *
 * performance analysis
 * - O(nlogn) guarantee (best/worst/avg)
 * - stable
 * - not in-place
 *
 * work summary
 * - recursive merge sort implementations for arrays
 *   - Top-down recursion
 *      - self implemented {@link MergeSort#sortTD(Comparable[], int, int)} with {@link MergeSort#merge(Comparable[], int, int, int)}
 *      - improved version {@link MergeSort#sortTD(Comparable[], Comparable[], int, int)} with {@link MergeSort#merge(Comparable[], Comparable[], int, int, int)}
 *          - using shared space (clone[]) instead of recursively new a container queue
 *   - Bottom-up recursion
 *      - self implemented {@link MergeSort#sortBU(Comparable[], Comparable[])} with {@link MergeSort#merge(Comparable[], Comparable[], int, int, int)} reused
 */
public class MergeSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    @SuppressWarnings("unchecked")
    public void sort(T[] arr) {
        T[] clone = (T[]) new Comparable[arr.length];
        // ================== TD: top-down ==================
//        sortTD(arr, 0, arr.length);
        // improved version: shared extra space
//        sortTD(arr, clone, 0, arr.length);
        // ================== BU: bottom-up ==================
        sortBU(arr, clone);
    }

    /**
     * Top-down merge sort the subarray [start, end)
     * @param start included
     * @param end not included
     */
    @SuppressWarnings("unused")
    private void sortTD(T[] arr, int start, int end) {
        if (end - start <= 1) return;
        int mid = (start + end) / 2;
        sortTD(arr, start, mid);
        sortTD(arr, mid, end);
        merge(arr, start, mid, end);
    }


    /**
     * merge sort the subarray [start,end) through merging 2 sorted subarray [start,mid) & [mid, end)
     * use extra space: the cost of creating a new queue to hold the output every time that we do a merge is problematic
     * @param start sorted subarray 1 start index
     * @param mid sorted subarray 2 start index / sorted subarray 1 end point(not included)
     * @param end sorted subarray 2 end index (not included)
     */
    private void merge(T[] arr, int start, int mid, int end) {
        assert isSorted(arr, start, mid);
        assert isSorted(arr, mid, end);

        Queue<T> queue = new SimpleQueue<>();
        int p1 = start; // sorted subarray 1 pointer
        int p2 = mid;   // sorted subarray 2 pointer
        while (p1 < mid && p2 < end) {
            if (less(arr[p2], arr[p1])) queue.enqueue(arr[p2++]);
            else queue.enqueue(arr[p1++]);
        }
        if (p1 >= mid)
            for (; p2 < end; p2++)
                queue.enqueue(arr[p2]);
        else
            for (; p1 < mid; p1++)
                queue.enqueue(arr[p1]);

        for (int i = start; i < end; i++)
            arr[i] = queue.dequeue();
    }

    /**
     * Top-down merge sort the subarray [start, end) using shared extra space
     * @param start included
     * @param end not included
     */
    @SuppressWarnings("unused")
    private void sortTD(T[] arr, T[] clone, int start, int end) {
        if (end - start <= 1) return;
        int mid = (start + end) / 2;
        sortTD(arr, clone, start, mid);
        sortTD(arr, clone, mid, end);
        merge(arr, clone, start, mid, end);
    }


    /**
     * shared extra space (clone[])
     * @param arr sorted arr
     * @param clone clone of the origin array
     * @param start same
     * @param mid same
     * @param end same
     */
    private void merge(T[] arr, T[] clone, int start, int mid, int end) {
        assert isSorted(arr, start, mid);
        assert isSorted(arr, mid, end);

        for (int k = start; k < end; k++)
            clone[k] = arr[k];

        int i = start;
        int j = mid;

        for (int k = start; k < end; k++) {
            if (i >= mid) arr[k] = clone[j++];
            else if (j >= end) arr[k] = clone[i++];
            else if (less(clone[j], clone[i])) arr[k] = clone[j++];
            else arr[k] = clone[i++];
        }
    }

    /**
     * Bottom-up merge sort the subarray [start, end)
     */
    private void sortBU(T[] arr, T[] clone) {
        for (int sz = 1; sz < arr.length; sz <<= 1) // sz: the length of the sorted subarray
            for (int start = 0; start < arr.length - sz; start += sz << 1) // two in a pair, merge into a larger sorted array (2*sz)
                // ensure the right subarray at least 1 element (mid on the left side of end: mid+sz<arr.length)
                merge(arr, clone, start, start + sz, Math.min(start + (sz << 1), arr.length));
    }
}
