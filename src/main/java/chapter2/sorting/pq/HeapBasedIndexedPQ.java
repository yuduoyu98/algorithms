package chapter2.sorting.pq;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * indexed priority queue implemented by binary heap
 * suitable for k-v pair (k is integer and is mapped into[0,capacity])
 *
 * Note:
 * - vars:
 *   - ti: tree index; fti: father tree index; sti: son tree index
 *   - ki: key index
 * - all the input ki need to be mapped into [0, capacity)
 *   - or won't pass {@link HeapBasedIndexedPQ#validateIndex}
 */
public class HeapBasedIndexedPQ<T extends Comparable<T>> implements IndexedPQ<T> {

    private T[] index; // key index -> value
    private int[] pq;  // heap-ordered complete binary tree: tree index -> key index
    private int[] qp;  // key index -> tree index (dynamic adjust the tree using key index)
    private int N; // heap size (1-base excluding pq[0])
    private static final int ROOT_OFFSET = 1;    // offset (pq[0] unused)
    private static final int NOT_IN_PQ = -1;
    private int capacity;
    private boolean isMaxPQ;  // is max or min priority queue

    @SuppressWarnings("unchecked")
    public HeapBasedIndexedPQ(int capacity, boolean isMaxPQ) {
        this.index = (T[]) new Comparable[capacity];
        this.capacity = capacity;
        this.isMaxPQ = isMaxPQ;
        this.pq = new int[capacity + ROOT_OFFSET];
        Arrays.fill(pq, NOT_IN_PQ);
        this.qp = new int[capacity + ROOT_OFFSET];
        Arrays.fill(qp, NOT_IN_PQ);
        this.N = 0;
    }

    /***************************************************************************
     * helper functions.
     ***************************************************************************/

    /**
     * adjustments 'swim' from ti up
     * @param ti tree index
     */
    private void swim(int ti) {
        while (ti > 1 && disorder(ti / 2, ti)) {
            exchange(ti, ti / 2);
            ti = ti / 2;
        }
    }

    /**
     * adjustments 'sink' from ti down
     * @param ti tree index
     */
    private void sink(int ti) {
        while (ti * 2 <= N) {
            int sti = chooseChild(ti);
            if (!disorder(ti, sti)) break; // father <= son
            exchange(sti, ti);
            ti = sti;
        }
    }

    /**
     * choose one of fti's children to compare with fti in swim/sink
     * - maxPQ: choose the bigger one
     * - minPQ: choose the smaller one
     * @param fti father tree index
     * @return chosen child's tree index
     */
    private int chooseChild(int fti) {
        int child = fti * 2;
        assert child <= N; // ensure there at least one child
        if (child + 1 <= N) {
            boolean maxPQchooseRight = isMaxPQ && less(child, child + 1);
            boolean minPQchooseRight = !isMaxPQ && less(child + 1, child);
            // choose the right child
            if (maxPQchooseRight || minPQchooseRight) child++;
        }
        return child;
    }

    /**
     * heap disorder between father and son:
     * - maxPQ: father < son
     * - minPQ: father > son
     * @param fti father (tree node) index
     * @param sti son (tree node) index
     * @return whether there is a heap disorder between father and son
     */
    private boolean disorder(int fti, int sti) {
        if (isMaxPQ) return less(fti, sti);
        else return less(sti, fti);
    }

    // pq[ti1] less than pq[ti2]
    private boolean less(int ti1, int ti2) {
        return index[pq[ti1]].compareTo(index[pq[ti2]]) < 0;
    }

    /**
     * exchange pq value indexed at ti1 and ti2
     * maintain qp as well
     * @param ti1 tree index 1
     * @param ti2 tree index 2
     */
    private void exchange(int ti1, int ti2) {
        int ki1 = pq[ti1];
        int ki2 = pq[ti2];
        pq[ti1] = ki2;
        pq[ti2] = ki1;
        qp[ki2] = ti1;
        qp[ki1] = ti2;
    }

    /***************************************************************************
     * check functions.
     ***************************************************************************/

    /**
     * make sure key index has been mapped into [0, capacity)
     * @param ki key index
     */
    private void validateIndex(int ki) {
        if (ki < 0) throw new IllegalArgumentException("index is negative: " + ki);
        if (ki >= capacity) throw new IllegalArgumentException("index >= capacity: " + ki);
    }

    /***************************************************************************
     * api functions.
     ***************************************************************************/

    @Override
    public void insert(int ki, T val) {
        validateIndex(ki);
        if (contains(ki)) throw new IllegalArgumentException("index is already in the priority queue");
        index[ki] = val;
        pq[++N] = ki;
        qp[ki] = N;
        swim(N);
    }

    /**
     * dynamic change the heap element's value
     * @param ki key index
     * @param newVal new value
     */
    @Override
    public void change(int ki, T newVal) {
        validateIndex(ki);
        if (!contains(ki)) throw new NoSuchElementException("index is not in the priority queue");
        index[ki] = newVal;
        swim(qp[ki]);
        sink(qp[ki]);
    }

    @Override
    public boolean contains(int ki) {
        validateIndex(ki);
        return qp[ki] != NOT_IN_PQ;
    }

    @Override
    public T valOf(int ki) {
        validateIndex(ki);
        if (!contains(ki)) throw new NoSuchElementException("index is not in the priority queue");
        return index[ki];
    }

    /**
     * remove ki and its associated value from PQ
     * @param ki key index
     */
    @Override
    public void delete(int ki) {
        validateIndex(ki);
        if (!contains(ki)) throw new NoSuchElementException("index is not in the priority queue");
        int ti = qp[ki];
        exchange(ti, N--);
        qp[ki] = NOT_IN_PQ;
        pq[N + 1] = NOT_IN_PQ;
        index[ki] = null; // help with garbage clean
        // the PQ tail swapped to ti does not necessarily in heap order with ti's father
        swim(ti);
        sink(ti);
    }

    /**
     * retrieve and remove the minimum/maximum value's key index from the top of the heap
     * @return the top key index of the heap
     */
    protected int delTop() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        int topKi = pq[ROOT_OFFSET];
        exchange(ROOT_OFFSET, N--);
        pq[N + 1] = NOT_IN_PQ;
        qp[topKi] = NOT_IN_PQ;
        index[topKi] = null; // help with garbage clean
        sink(ROOT_OFFSET);
        return topKi;
    }

    public int topIndex() {
        if (isEmpty()) throw new NoSuchElementException();
        return pq[ROOT_OFFSET];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    /**
     * returns an iterator that iterates over the keys on the priority queue in ascending order
     */
    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {

        private HeapBasedIndexedPQ<T> copy;

        public HeapIterator() {
            copy = new HeapBasedIndexedPQ<>(capacity, isMaxPQ);
            for (int ti = 1; ti <= N; ti++)
                copy.insert(pq[ti], index[pq[ti]]);
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delTop();
        }
    }

}
