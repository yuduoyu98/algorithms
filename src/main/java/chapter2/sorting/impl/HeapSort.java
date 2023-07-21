package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;
import chapter2.sorting.priority_queue.MaxHeapPriorityQueue;

public class HeapSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    public void sort(T[] arr) {
        MaxHeapPriorityQueue<T> pq = new MaxHeapPriorityQueue<>(arr);
        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i] = pq.deleteMax();
        }
    }
}
