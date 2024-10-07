package chapter2.sorting.pq;

public interface MinPriorityQueue<T extends Comparable<T>> extends PriorityQueue<T> {
    T min();
    T deleteMin();
}
