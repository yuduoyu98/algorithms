package chapter2.sorting.pq;

/**
 * indexed min priority queue implementation based on binary heap
 */
public class IndexedMaxPQImpl<T extends Comparable<T>> extends HeapBasedIndexedPQ<T> implements IndexedMaxPQ<T>{

    public IndexedMaxPQImpl(int capacity) {
        super(capacity, true);
    }

    @Override
    public int delMax() {
        return delTop();
    }

    @Override
    public int maxIndex() {
        return topIndex();
    }
}
