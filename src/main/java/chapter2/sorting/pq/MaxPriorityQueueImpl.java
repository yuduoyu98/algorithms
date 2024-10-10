package chapter2.sorting.pq;

import java.util.NoSuchElementException;

/**
 * max priority queue implemented by binary heap
 * - heap-ordered binary tree: parent's key no larger/smaller than children's keys
 */
public class MaxPriorityQueueImpl<T extends Comparable<T>> extends HeapBasedPriorityQueue<T> implements MaxPriorityQueue<T> {
    private static final boolean FLAG = true;

    @SuppressWarnings("unused")
    public MaxPriorityQueueImpl() {
        super();
    }

    @SuppressWarnings("unused")
    public MaxPriorityQueueImpl(int capacity) {
        super(capacity);
    }

    public MaxPriorityQueueImpl(T[] arr) {
        super(arr);
    }

    @Override
    public void insert(T element) {
        pq[++N] = element;
        swim(FLAG);
    }

    @Override
    public T max() {
        return isEmpty() ? null : pq[ROOT_OFFSET];
    }

    @Override
    public T deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("deleteMax from an empty max priority queue");

        T max = pq[ROOT_OFFSET];
        pq[ROOT_OFFSET] = pq[N];
        pq[N--] = null;
        sink(FLAG);
        return max;
    }

}
