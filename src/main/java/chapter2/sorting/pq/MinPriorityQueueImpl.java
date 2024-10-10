package chapter2.sorting.pq;

import java.util.NoSuchElementException;

/**
 * min priority queue implemented by binary heap
 * - heap-ordered binary tree: parent's key no larger/smaller than children's keys
 */
public class MinPriorityQueueImpl<T extends Comparable<T>> extends HeapBasedPriorityQueue<T> implements MinPriorityQueue<T> {
    private static final boolean FLAG = false;

    @SuppressWarnings("unused")
    public MinPriorityQueueImpl() {
        super();
    }

    @SuppressWarnings("unused")
    public MinPriorityQueueImpl(int capacity) {
        super(capacity);
    }

    public MinPriorityQueueImpl(T[] arr) {
        super(arr);
    }

    @Override
    public void insert(T element) {
        pq[++N] = element;
        swim(FLAG);
    }

    @Override
    public T min() {
        return isEmpty() ? null : pq[ROOT_OFFSET];
    }

    @Override
    public T deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("deleteMin from an empty min priority queue");

        T min = pq[ROOT_OFFSET];
        pq[ROOT_OFFSET] = pq[N];
        pq[N--] = null;
        sink(FLAG);
        return min;
    }
}
