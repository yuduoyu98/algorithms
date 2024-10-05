package chapter2.sorting.pq;

public interface AbstractMaxPriorityQueue<T extends Comparable<T>> {

    void insert(T element);
    T max();

    T deleteMax();

    boolean isEmpty();

    int size();

}
