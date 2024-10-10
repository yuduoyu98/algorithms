package chapter2.sorting.pq;

/**
 * indexed min priority queue implementation based on binary heap
 */
public class IndexedMinPQImpl<T extends Comparable<T>> extends HeapBasedIndexedPQ<T> implements IndexedMinPQ<T>{

    public IndexedMinPQImpl(int capacity) {
        super(capacity, false);
    }

    @Override
    public int delMin() {
        return delTop();
    }

    @Override
    public int minIndex() {
        return topIndex();
    }

}
