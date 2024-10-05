package chapter2.sorting.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter2.sorting.SortTemplate;

/**
 * Shell Sort
 * - an extension of Insertion Sort
 * Idea:
 * - in insertion sort:
 *    - if the smallest value happens to be at the end of the array, it needs N-1 exchanges to the right position
 *    - allow exchanges of array value that far apart to produce partially sorted array
 * - h-sorted:
 *    - h independent sorted subsequences interleaved together
 *    - h = 1 means the array is sorted
 * - increment sequence/gap sequence
 *    - a series of numbers used to determine the spacing between elements
 *    - decrease to 1 at the end
 */
public class ShellSort<T extends Comparable<T>> implements SortTemplate<T> {
    @Override
    public void sort(T[] arr) {
//        shellSort1(arr, genDecArithSeq(arr.length, 3));
        shellSort2(arr, genDecArithSeq(arr.length, 3));
    }

    /**
     * generate decreasing arithmetic sequence (end up at 1)
     * @param upper upper bound of the sequence
     * @param commonDiff common difference
     */
    private Iterable<Integer> genDecArithSeq(int upper, int commonDiff) {
        Queue<Integer> queue = new SimpleQueue<>();
        for (int i = upper; i > commonDiff; i -= commonDiff)
            queue.enqueue(i);
        queue.enqueue(1);
        return queue;
    }

    /**
     * shell sort with customized gap sequence
     * @param gapSequence gap sequence/increment sequence (a decrease sequence end up at 1)
     */
    @SuppressWarnings("unused")
    private void shellSort1(T[] arr, Iterable<Integer> gapSequence) {
        for (int gap : gapSequence)
            for (int offset = 0; offset < gap; offset++)
                subSeqSort(arr, gap, offset);
    }

    /**
     * sort subsequence with an interval of 'gap', starting from 'offset'
     */
    private void subSeqSort(T[] arr, int gap, int offset) {
        for (int i = gap + offset; i < arr.length; i += gap)
            for (int j = i; j > offset && less(arr[j], arr[j - gap]); j -= gap) // j>offset => j>=gap+offset
                exchange(arr, j, j - gap);
    }

    /**
     * offset is not necessary (do not have to treat each subsequence independently)
     * all need to insert into the right position in a subsequence with the same gap
     */
    @SuppressWarnings("unused")
    private void shellSort2(T[] arr, Iterable<Integer> gapSequence) {
        for (int gap : gapSequence)
            for (int i = gap; i < arr.length; i++)
                for (int j = i; j >= gap && less(arr[j], arr[j - gap]); j -= gap)
                    exchange(arr, j, j - gap);
    }
}
