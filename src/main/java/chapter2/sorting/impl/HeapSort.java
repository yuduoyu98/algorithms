package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;
import chapter2.sorting.pq.MaxPriorityQueueImpl;
import chapter2.sorting.pq.MinPriorityQueueImpl;
import chapter2.sorting.pq.MaxPriorityQueue;
import chapter2.sorting.pq.MinPriorityQueue;

public class HeapSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    public void sort(T[] arr) {
//        maxHeapSort(arr);
        minHeapSort(arr);
    }

    @SuppressWarnings("unused")
    private void maxHeapSort(T[] arr) {
        MaxPriorityQueue<T> pq = new MaxPriorityQueueImpl<>(arr);
        for (int i = arr.length - 1; i >= 0; i--)
            arr[i] = pq.deleteMax();
    }

    @SuppressWarnings("unused")
    private void minHeapSort(T[] arr) {
        MinPriorityQueue<T> pq = new MinPriorityQueueImpl<>(arr);
        for (int i = 0; i < arr.length; i++)
            arr[i] = pq.deleteMin();
    }
}
