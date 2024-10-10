package chapter2.sorting.pq;

/**
 *
 */
public interface IndexedMaxPQ<T extends Comparable<T>> extends IndexedPQ<T> {
    // remove a minimal item and return its key index
    int delMax();

    // return a minimal item’s index
    int maxIndex();
}
