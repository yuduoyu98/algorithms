package chapter2.sorting.pq;

/**
 *
 */
public interface IndexedMinPQ<T extends Comparable<T>> extends IndexedPQ<T> {
    // remove a minimal item and return its key index
    int delMin();

    // return a minimal item’s index
    int minIndex();
}
