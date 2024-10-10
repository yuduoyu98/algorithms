package chapter2.sorting.pq;

/**
 *
 */
public interface IndexedPQ<T extends Comparable<T>> extends Iterable<Integer> {

    // insert value; associate it with ki(key index)
    void insert(int ki, T val);

    // change the value associated with ki(key index) to val
    void change(int ki, T val);

    // remove ki and its associated val
    void delete(int ki);

    // is ki associated with some val?
    boolean contains(int ki);

    T valOf(int ki);

    boolean isEmpty();

    int size();

}
