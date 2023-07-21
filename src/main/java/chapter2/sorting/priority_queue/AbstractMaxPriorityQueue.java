package chapter2.sorting.priority_queue;

public abstract class AbstractMaxPriorityQueue<T extends Comparable<T>> {

    public abstract void insert(T element);
    public abstract T max();

    public abstract T deleteMax();

    public abstract boolean isEmpty();

    public abstract int size();

}
