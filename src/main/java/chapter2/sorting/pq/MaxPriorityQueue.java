package chapter2.sorting.pq;

public interface MaxPriorityQueue<T extends Comparable<T>> extends PriorityQueue<T> {
    T max();
    T deleteMax();
}
