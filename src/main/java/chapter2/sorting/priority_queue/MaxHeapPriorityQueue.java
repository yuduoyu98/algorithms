package chapter2.sorting.priority_queue;

/**
 * <h2>基于堆（大根堆）的优先队列实现</h2>
 */
public class MaxHeapPriorityQueue<T extends Comparable<T>> extends AbstractMaxPriorityQueue<T> {

    private T[] pq;     //完全二叉树的数组表示来表示堆
    private int N;
    public static final int DEFAULT_INIT_SIZE = 10;
    public static final int HEAD_ELEMENT_OFFSET = 1;    //表示完全二叉树数组的第一个元素不使用

    @SuppressWarnings({"unchecked", "unused"})
    public MaxHeapPriorityQueue() {
        this.pq = (T[]) new Comparable[DEFAULT_INIT_SIZE + HEAD_ELEMENT_OFFSET];
        this.N = 0;
    }

    @SuppressWarnings({"unchecked", "unused"})
    public MaxHeapPriorityQueue(int capacity) {
        this.pq = (T[]) new Comparable[capacity + HEAD_ELEMENT_OFFSET];
        this.N = 0;
    }

    @SuppressWarnings("unchecked")
    public MaxHeapPriorityQueue(T[] arr) {
        pq = (T[]) new Comparable[arr.length + HEAD_ELEMENT_OFFSET];
        for (T t : arr) {
            insert(t);
        }
        this.N = arr.length;
    }

    /**
     * <strong>上浮</strong>：添加新元素{@link MaxHeapPriorityQueue#insert insert}后进行，以维护堆得有序性
     */
    public void swim() {
        int sonIndex = N;
        int fatherIndex = sonIndex / 2;
        while (fatherIndex >= 1 && less(fatherIndex, sonIndex)) {
            exchange(fatherIndex, sonIndex);
            sonIndex = fatherIndex;
            fatherIndex = sonIndex / 2;
        }
    }

    /**
     * <strong>下沉</strong>：删除堆顶{@link MaxHeapPriorityQueue#deleteMax() deleteMax}后进行，以维护堆得有序性
     */
    public void sink() {
        int fatherIndex = HEAD_ELEMENT_OFFSET;
        int sonIndex = 2 * fatherIndex;
        while (sonIndex <= N) {
            //选择较大的子节点下标
            if (sonIndex + 1 <= N && less(sonIndex, sonIndex + 1)) {
                sonIndex++;
            }
            if (less(fatherIndex, sonIndex)) {
                exchange(fatherIndex, sonIndex);
                fatherIndex = sonIndex;
                sonIndex = 2 * fatherIndex;
            } else {
                break;
            }
        }
    }

    @Override
    public void insert(T element) {
        pq[++N] = element;
        swim();
    }

    @Override
    public T max() {
        if (N > 0) {
            return pq[HEAD_ELEMENT_OFFSET];
        } else {
            return null;
        }
    }

    @Override
    public T deleteMax() {
        if (N > 0) {
            T max = max();
            pq[HEAD_ELEMENT_OFFSET] = pq[N];
            pq[N--] = null;
            sink();
            return max;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exchange(int i, int j) {
        T t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

}
