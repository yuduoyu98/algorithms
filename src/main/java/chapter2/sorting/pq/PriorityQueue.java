package chapter2.sorting.pq;

/**
 *
 */
public interface PriorityQueue<T extends Comparable<T>> {
    void insert(T element);
    boolean isEmpty();
    int size();
}
