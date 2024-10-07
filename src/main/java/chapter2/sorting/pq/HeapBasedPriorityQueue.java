package chapter2.sorting.pq;

/**
 * priority queue implementation
 * - underlying data structure: binary heap (logical representations)
 * - heap-ordered binary tree: parent's key no larger/smaller than children's keys
 *   - physical representations: complete tree represented by an array
 */
public abstract class HeapBasedPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {

    protected T[] pq;     // array representation of a heap-ordered complete binary tree
    // pq[0] unused; pq[k]'s children pq[2k] & pq[2k+1]
    // a tree of size N needs an array with size N+1
    protected int N;      // tree size
    protected static final int DEFAULT_INIT_SIZE = 10;
    protected static final int ROOT_OFFSET = 1;    // offset (pq[0] unused)

    @SuppressWarnings({"unchecked", "unused"})
    public HeapBasedPriorityQueue() {
        this(DEFAULT_INIT_SIZE);
    }

    @SuppressWarnings({"unchecked", "unused"})
    public HeapBasedPriorityQueue(int capacity) {
        this.pq = (T[]) new Comparable[capacity + ROOT_OFFSET];
        this.N = 0;
    }

    @SuppressWarnings("unchecked")
    public HeapBasedPriorityQueue(T[] arr) {
        pq = (T[]) new Comparable[arr.length + ROOT_OFFSET];
        for (T t : arr)
            insert(t);
    }

    /**
     * if pq[child] and pq[father] not conform to heap order
     * @param flag max heap or min heap (max heap = true)
     */
    protected boolean disorder(int child, int father, boolean flag) {
        if (flag) return less(father, child); // max heap disorder: pq[father] < pq[child]
        else return less(child, father); // min heap disorder: pq[father] > pq[child]
    }

    /**
     * return whether pq[i] is less than pq[j]
     */
    protected boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    protected void exchange(int i, int j) {
        T t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    /**
     * adjustment after insertion
     */
    public void swim(boolean flag) {
        int child = N;
        int father = child / 2;
        while (father >= 1 && disorder(child, father, flag)) {
            exchange(father, child);
            child = father;
            father = child / 2;
        }
    }

    /**
     * adjustment after replace the last node to the root (deleteMax/Min)
     */
    public void sink(boolean flag) {
        int father = ROOT_OFFSET;
        int leftChild = 2 * father; // left child by default
        while (leftChild <= N) {
            int rightChild = leftChild + 1;
            int child = leftChild;
            if (rightChild <= N) { // right child exist
                // flag=true max heap: find the larger child
                boolean condition2 = flag && less(leftChild, rightChild);
                // flag=false min heap: find the smaller child
                boolean condition1 = !flag && less(rightChild, leftChild);
                // choose the right child
                if (condition1 || condition2) child = rightChild;
            }

            if (disorder(child, father, flag)) {
                exchange(father, child);
                father = child;
                leftChild = 2 * father;
            }
            else break;
        }
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }
}
