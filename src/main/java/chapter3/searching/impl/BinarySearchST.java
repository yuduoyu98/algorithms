package chapter3.searching.impl;

import chapter3.searching.api.OST;
import edu.princeton.cs.algs4.Queue;

/**
 * 基于二分查找+有序数组的符号表实现
 * @param <K>
 * @param <V>
 */
public class BinarySearchST<K extends Comparable<K>, V> implements OST<K, V> {

    private K[] keys;
    private V[] vals;
    private int n;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        //Java 不支持直接创建泛型数组
        //可参考：https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
        keys = (K[]) new Comparable[capacity];
        vals = (V[]) new Object[capacity];
        n = 0;
    }

    public BinarySearchST() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public V get(K key) {
        int rank = rank(key);
        return rank < n && keys[rank].compareTo(key) == 0 ? vals[rank] : null;
    }

    @Override
    public void put(K key, V val) {
        int rank = rank(key);
        //当为符号表为空时 rank = 0 = n
        if (rank < n && keys[rank].compareTo(key) == 0) vals[rank] = val;
        else {
            //key 在 (keys[rank-1],keys[rank]) 开区间内 -> put key到keys[rank]
            for (int i = n - 1; i >= rank; i--) {
                keys[i + 1] = keys[i];
                vals[i + 1] = vals[i];
            }
            keys[rank] = key;
            vals[rank] = val;
            n += 1;
        }
    }

    @Override
    public K select(int rank) {
        return keys[rank - 1];
    }

    @Override
    public K min() {
        return keys[0];
    }

    @Override
    public K max() {
        return keys[n - 1];
    }

    @Override
    public int rank(K key) {
        return rank(key, 0, n - 1);
    }

    /**
     * [lo,hi]的子区间内定位 key
     * @param key 待排位的key值
     * @param lo 数组下界下标
     * @param hi 数组上界下标
     * @return 符号表内小于输入key值的键数
     */
    private int rank(K key, int lo, int hi) {
        //防止空符号表 keys[lo]空指针
        if (isEmpty()) return 0;
        int mid = (lo + hi) / 2;
        //区间递归到[lo, hi] 考虑lo是否应包含在内
        if (lo >= hi) return keys[lo].compareTo(key) == 0 ? lo : lo + 1;
        if (keys[mid].compareTo(key) < 0) return rank(key, mid + 1, hi);
        else if (keys[mid].compareTo(key) > 0) return rank(key, lo, mid - 1);
        else return mid;
    }

    @Override
    public K floor(K key) {
        /**
         * rank -> 符号表中小于输入key值的key数
         * floor -> key的定义为小于等于输入key值key
         *
         * 分析：
         *      1. key=keys[n]           keys[0~n-1] < key   rank=n    floor应返回keys[n]
         *      2. keys[n-1]<key<keys[n] keys[0~n-1] < key   rank=n    floor应返回keys[n-1]
         *
         * 处理逻辑：
         *      rank=n时 如果key=keys[n] 返回keys[n] 否则返回keys[n-1]
         *      由于rank取值范围为0~N: 当rank为0或者N时 都需要单独讨论 因为可能会发生数组越界
         */
        int rank = rank(key);
        if (rank == n) {
            return keys[n - 1];
        } else if (rank == 0) {
            return keys[0].compareTo(key) == 0 ? keys[0] : null;
        } else {
            return keys[rank].compareTo(key) == 0 ? keys[rank] : keys[rank - 1];
        }
    }

    @Override
    public K ceiling(K key) {
        /**
         * 分析：
         *      1. key=keys[n]           keys[0~n-1] < key   rank=n    ceiling应返回keys[n]
         *      2. keys[n-1]<key<keys[n] keys[0~n-1] < key   rank=n    ceiling应返回keys[n]
         * 只需要单独讨论rank=N的特殊情况即可
         */
        int rank = rank(key);
        return rank == n ? null : keys[rank];
    }

    @Override
    public void deleteMin() {
        for (int i = 1; i < n; i++) {
            keys[i - 1] = keys[i];
            vals[i - 1] = vals[i];
        }
        keys[n] = null;
        vals[n] = null;
        n -= 1;
    }

    @Override
    public void deleteMax() {
        keys[n - 1] = null;
        vals[n - 1] = null;
        n -= 1;
    }

    @Override
    public void delete(K key) {
        int rank = rank(key);
        //符号表中存在key
        if (rank < n && keys[rank].compareTo(key) == 0) {
            for (int i = rank + 1; i < n; i++) {
                keys[i - 1] = keys[i];
                vals[i - 1] = vals[i];
            }
            keys[n] = null;
            vals[n] = null;
            n -= 1;
        } else {
            System.out.println("key does not exists, nothing deleted");
        }
    }

    @Override
    public boolean contains(K key) {
        int rank = rank(key);
        return rank < n && keys[rank].equals(key);
    }

    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public int size(K lo, K hi) {
        if (lo.compareTo(hi) > 0) return 0;
            //包含hi时 会少算hi这个点
        else if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        Queue<K> q = new Queue<>();
        for (int i = rank(lo); i < rank(hi); i++) {
            q.enqueue(keys[i]);
        }
        return q;
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> q = new Queue<>();
        for (int i = 0; i < n; i++) {
            q.enqueue(keys[i]);
        }
        return q;
    }
}
