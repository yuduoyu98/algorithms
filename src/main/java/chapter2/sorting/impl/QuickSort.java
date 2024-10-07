package chapter2.sorting.impl;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter2.sorting.ArrayTools;
import chapter2.sorting.SortTemplate;
import edu.princeton.cs.algs4.StdRandom;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Quick Sort
 *
 * basic idea
 * - Divide and Conquer
 * - partition:
 *   - picks an element as a pivot and
 *     partitions the given array around the picked pivot by placing the pivot in its correct position in the sorted array.
 * - terminate at 1-element subarray
 *
 * performance analysis
 * - Time complexity
 *   - best: O(nlogn) => the pivot always divides the array into two equal halves.
 *   - avg: O(nlogn)
 *   - worst: O(n^2) => the smallest or largest element is always chosen as the pivot
 * - evaluation
 *   - pros
 *     - fastest general purpose algorithm (fastest in practice)
 *       - fastest in practice
 *       - O(nlogn) probabilistic guarantee
 *     - in-place => low overhead
 *     - tail recursive => tail call optimization by compilers
 *       - the recursive call is the last statement that is executed by the function
 *   - cons
 *     - not stable
 *     - worst case O(n^2) => choose pivot wisely
 *     - not good for small array
 *       - cutoff to small size friendly sorting algorithms instead of recursively partition
 *         - trigger: end-start<=threshold
 *
 * algorithm variants
 * - 3-way partitioning
 *   - purpose: optimize performance with repeated elements
 *   - implementations
 *     - Dijkstra’s solution
 *     - Bentley-McIlroy's optimized solution
 *
 * work summary
 * - classic 2-way partitioning implemented at {@link QuickSort#sort1} (recursive) and {@link QuickSort#sort2} (non-recursive)
 *   - partition strategy 1 at {@link QuickSort#partition1} (implemented myself)
 *   - partition strategy 2 (an extremely concise approach) at {@link QuickSort#partition2} (enlightened by classes at PolyU)
 *   - partition test at {@link QuickSort#main}
 * - 3-way partitioning {@link QuickSort#sort3} (to improve performance under scenarios that exists lot of duplicate elements)
 *   - Dijkstra’s partition strategy solution implemented at {@link QuickSort#partition3}
 *   - Bentley-McIlroy's partition strategy implemented at {@link QuickSort#partition4}
 *
 * links
 * - <a href="https://www.geeksforgeeks.org/quick-sort-algorithm/</a>
 */
public class QuickSort<T extends Comparable<T>> implements SortTemplate<T> {
    @Override
    public void sort(T[] arr) {
//        sort1(arr, 0, arr.length); // 2-way partitioning (recursive)
//        sort2(arr); // 2-way partitioning (non-recursive)
        sort3(arr, 0, arr.length); // 3-way partitioning
    }

    /**
     * recursively partition the subarray into 2 part(smaller and larger) by a pivot
     * @param start included
     * @param end not included
     */
    @SuppressWarnings("unused")
    private void sort1(T[] arr, int start, int end) {
        if (end - start <= 1) return; // 1 element subarray
//        int pivot = partition1(arr, start, end);
        int pivot = partition2(arr, start, end); // a concise approach
        sort1(arr, start, pivot);
        sort1(arr, pivot + 1, end);
    }

    /**
     * strategy 1: Hoare’s Partition ()
     * - relatively faster? <a href="https://www.geeksforgeeks.org/hoares-vs-lomuto-partition-scheme-quicksort/</a>
     * - smaller part grow from start, larger part grow from end, and stop when meet at the middle
     * - introduce two pointer p1, p2, and move alternately
     *   - the stationary pointer point to the 'vacant' index for swapping
     *      - vacant created by a partition swap where the value at vacant index has been swapped to the other side
     *   - the moving pointer point to the next index to be partitioned
     *   - alternation between moving and stationary is triggered by a partition swap
     *   - the side selected as the pivot initially remains stationary (here arr[start] as pivot, so p1 sit still.
     * - for values same with pivot, it may be partitioned to both larger and smaller part, but it won't be a problem
     *   - e.g. arr: 7 7 9 4 10 3 7 1 and pivot is 7 at index 0
     *   - at first it will partition the other two 7 on both smaller and larger part: 1 7 3 4 7 10 7 9  (pivot at index 4)
     *   - but eventually: 1 3 4 7 7 7 9 10
     * @param start start index; pivot index
     * @param end end index (not included)
     * @return the pivot index
     */
    @SuppressWarnings("unused")
    private int partition1(T[] arr, int start, int end) {
        // arbitrarily choose the arr[start] to be the partitioning pivot
        T pivot = arr[start];
        // or choose a random index to be the pivot
//        int pivotIndex = StdRandom.uniformInt(start, end);
//        exchange(arr, pivotIndex, start);

        int p1 = start;
        int p2 = end - 1;
        // control which pointer move
        // initially p2 move, because p1 is pointing to the vacant index for swap (pivot)
        boolean direction = false;
        while (p1 < p2) { // p1,p2 at least one is pointing to the vacant index for swap
            if (!direction) {
                if (less(arr[p2], pivot)) { // arr[p2] < pivot
                    arr[p1++] = arr[p2];
                    direction = true;
                }
                else p2--; // arr[p2] >= pivot
            }
            else {
                if (less(pivot, arr[p1])) { // arr[p1] > pivot
                    arr[p2--] = arr[p1];
                    direction = false;
                }
                else p1++; // arr[p1] >= pivot
            }
        }
        arr[p1] = pivot;
        return p1;
    }

    /**
     * strategy 2: Lomuto partition (extremely concise)
     * - smaller part grow from start, larger part grow after that
     * - pointer i divide smaller part and larger part
     * - pointer j point to the index of the next partition value
     *   - [start,i] smaller part, [i+1,j-1] larger part
     * - only if the arr[j] belongs to the smaller side, there needs an adjustment (larger part grow as the j increases)
     *   - swap the first value of the bigger part arr[i+1] and arr[j] will do the job
     * @param start start index; pivot index
     * @param end end index (not included)
     * @return the pivot index
     */
    @SuppressWarnings("unused")
    private int partition2(T[] arr, int start, int end) {
        System.out.println("[" + start + "," + end + ")");
        T pivot = arr[end - 1]; // arbitrarily choose the arr[end-1] to be the partitioning pivot
        int i = start - 1;  // smaller part size 0
        for (int j = start; j < end - 1; j++)
            if (less(arr[j], pivot))  // j should be in the smaller part
                exchange(arr, j, ++i);
        exchange(arr, i + 1, end - 1);  // swap the first value index the larger part with the pivot
        return i + 1;
    }

    /**
     * non-recursive implementation of 2-way partitioning
     */
    @SuppressWarnings("unused")
    private void sort2(T[] arr) {
        Stack<Pair<Integer, Integer>> stack = new SimpleStack<>();
        stack.push(Pair.of(0, arr.length));
        while (!stack.isEmpty()) {
            Pair<Integer, Integer> range = stack.pop();
            Integer start = range.getLeft();
            Integer end = range.getRight();
            int pivot = partition2(arr, start, end);
            if (pivot > start) stack.push(Pair.of(start, pivot));
            if (pivot < end - 1) stack.push(Pair.of(pivot + 1, end));
        }
    }

    /**
     * recursively partition the array into 3 part(smaller equal and larger)
     * - to array that contains a lot of duplicate elements, 2-way partitioning's performance can be substantially improved
     * - in extreme cases: an array consists of sorely identical elements, 2-way partitioning still will recursively partition to the bottom
     * - to arrays without that much duplicate elements, it will lead to many more compares than 2-way partitioning
     */
    @SuppressWarnings("unused")
    private void sort3(T[] arr, int start, int end) {
        if (end - start <= 1) return;
//        Pair<Integer, Integer> equalRange = partition3(arr, start, end); // Dijkstra
        Pair<Integer, Integer> equalRange = partition4(arr, start, end); // Bentley-McIlroy
        sort3(arr, start, equalRange.getLeft());
        sort3(arr, equalRange.getRight(), end);
    }

    /**
     * Dijkstra’s solution
     * - semantic of variables
     *      smaller equal  unpartitioned   larger
     *    |--------|-----|---------------|--------|
     *  start     lt     i              gt     end-1
     * - partition
     *   - smaller part: [start, lt)
     *   - equal part: [lt, i)
     *   - larger part: [gt, end)
     *   - unpartitioned part: [i, gt)
     * - arr[start] as pivot
     * @return Pair of (lt, gt)
     */
    @SuppressWarnings("unused")
    private Pair<Integer, Integer> partition3(T[] arr, int start, int end) {
        T pivot = arr[start];
        int lt = start, gt = end;
        int i = start + 1;
        while (i < gt) {
            int cmp = arr[i].compareTo(pivot);
            if (cmp > 0) exchange(arr, --gt, i); // swap with the last value in the unpartitioned part
            else if (cmp < 0) exchange(arr, lt++, i++); // swap with the first value in the equals part
            else i++;
        }
        return Pair.of(lt, gt);
    }

    /**
     * Bentley-McIlroy's solution
     * - visualization: <a herf="https://learnforeverlearn.com/pivot_3way_music/" /a>
     * @return Pair.of(i, j)
     *         i -> 1st of equal part
     *         j -> (last of equal part + 1)
     */
    @SuppressWarnings("unused")
    private Pair<Integer, Integer> partition4(T[] arr, int start, int end) {
        T pivot = arr[start];
        int p = start + 1, i = start + 1; // p -> 1st of smaller part; i -> 1st of unpartitioned part
        int j = end - 1, q = end; // j -> last of unpartitioned part; q -> 1st of equal part(right)
        boolean direction = true;
        //  ################ partition phase 1 ##################
        //     equal  smaller   unpartitioned   larger   equal
        //   |------|---------|---------------|--------|-------|
        // start    p         i               j        q      end
        // ################ partition phase 1 ##################
        while (i < j) {
            if (direction) {
                int cmp = arr[i].compareTo(pivot);
                if (cmp == 0) exchange(arr, p++, i++);
                else if (cmp < 0) i++;
                else {
                    exchange(arr, j--, i);
                    direction = false;
                }
            }
            else {
                int cmp = arr[j].compareTo(pivot);
                if (cmp == 0) exchange(arr, --q, j--);
                else if (cmp > 0) j--;
                else {
                    exchange(arr, i++, j);
                    direction = true;
                }
            }
        }
        //  ########### partition phase 2 ############
        //      smaller   unpartitioned   larger
        //   |---------|---------------|--------|
        //             i               j
        // ########### partition phase 2 ############

        // partition the last one
        // ensure: i -> (last of smaller part + 1), j -> 1st of largest part
        int cmp = arr[i].compareTo(pivot);
        if (cmp == 0) j++;
        else if (cmp < 0) { // i,j point to the last of the smaller part
            i++;
            j++;
        }

        for (int k = end - 1; k >= q; k--)
            exchange(arr, k, j++);
        for (int k = start; k < p; k++)
            exchange(arr, k, --i);

        return Pair.of(i, j); // maintain the same meaning with method partition3
    }


    // partition test
    public static void main(String[] args) {
//        Integer[] arr = {1,2,3,4,5,6};
        Integer[] arr = {7, 7, 9, 4, 7, 7, 10, 7, 3, 7, 1};
//        StdRandom.shuffle(arr);
        ArrayTools.print(arr);
        QuickSort<Integer> quickSort = new QuickSort<>();
        System.out.println(quickSort.partition4(arr, 0, arr.length));
//        quickSort.sort(arr);
        ArrayTools.print(arr);
    }

}
