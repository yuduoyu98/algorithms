package chapter3.searching.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter3.searching.api.AutoCheck;
import chapter3.searching.api.OST;
import chapter3.searching.api.Resizable;

import java.util.NoSuchElementException;

/**
 * Implementation of a Symbol Table based on Binary Search and Ordered Array
 *
 * @param <K>
 * @param <V>
 */
public class BinarySearchST<K extends Comparable<K>, V> implements OST<K, V>, AutoCheck, Resizable {

    private K[] keys;
    private V[] vals;
    private int N;
    private static final int DEFAULT_CAPACITY = 10;
    //是否需要开启内部自动检查
    private static final boolean INTERNAL_CHECK = true;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        //Java 不支持直接创建泛型数组
        //可参考：https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
        keys = (K[]) new Comparable[capacity];
        vals = (V[]) new Object[capacity];
        N = 0;
    }

    @SuppressWarnings("unused")
    public BinarySearchST() {
        this(DEFAULT_CAPACITY);
    }


    /**
     * 扩容
     */
    @SuppressWarnings("unchecked")
    public void resize(int capacity) {
        K[] tempK = (K[]) new Comparable[capacity];
        V[] tempV = (V[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempK[i] = keys[i];
            tempV[i] = vals[i];
        }
        keys = tempK;
        vals = tempV;
    }


    @Override
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("key can not be null");
            // val置为null -> 删除
        else if (val == null) delete(key);
        else {
            //无需考虑OST Empty的边界情况 index=0 index > N-1 会走else逻辑进行添加
            int index = rank(key);
            //put重复key -> 更新val
            if (index <= N - 1 && key.compareTo(keys[index]) == 0) {
                vals[index] = val;
            }
            else {
                if (N == keys.length) resize(2 * keys.length);
                //put非重复key
                for (int i = N; i > index; i--) {
                    //右移
                    keys[i] = keys[i - 1];
                    vals[i] = vals[i - 1];
                }
                keys[index] = key;
                vals[index] = val;
                N++;
            }
        }

        assert check();
    }

    @Override
    public boolean check() {
        return !INTERNAL_CHECK || isSorted() && rankCheck();
    }

    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i - 1]) < 0) return false;
        return true;
    }

    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        else if (isEmpty()) return null;
        else {
            int index = rank(key);
            //使用keys[]取数的时候需要注意不能数组越界
            if (index < N && key.compareTo(keys[index]) == 0) return vals[index];
            else return null;
        }
    }

    /**
     * eager delete
     */
    @Override
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        else if (isEmpty()) return;
        int index = rank(key);
        // key not in the table
        if (index < N && keys[index].compareTo(key) != 0) return;
        // key in the table
        for (int i = index; i < N - 1; i++) {
            keys[i] = keys[i + 1];
            vals[i] = vals[i + 1];
        }
        N--;
        keys[N] = null;
        vals[N] = null;

        assert check();
    }

    @Override
    public K select(int r) {
        if (r >= 0 && r < size()) {
            return this.keys[r];
        }
        else {
            throw new IllegalArgumentException("called select() with invalid argument: " + r);
        }
    }

    /**
     * rank值 比key小的键的个数 <=> 大于等于key值的最小键下标
     * 如果OST contains key，rank值 <=> key在数组中的下标(index)
     * 如果需 insert key rank值 <=> 待插入数组的下标(index)
     * array index <=> rank(keys[index])  比index处key小的有index个
     */
    @Override
    public int rank(K key) {
        return rank(key, 0, N - 1);
    }

    /**
     * [lo, hi]subset中的rank值
     */
    public int rank(K key, int lo, int hi) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        else if (isEmpty()) return 0;
        else return recursiveRank(key, lo, hi);
//        else return nonRecursiveRank(key, lo, hi);
    }

    private int recursiveRank(K key, int lo, int hi) {
        if (lo > hi) return 0;
        int mid = (lo + hi) / 2;
        int cmp = key.compareTo(keys[mid]);
        // key > mid
        if (cmp > 0) return mid - lo + 1 + rank(key, mid + 1, hi);
            // key <= mid
        else if (cmp < 0) return rank(key, lo, mid - 1);
            // key == mid
        else return mid - lo;
    }

    /**
     * 落在[lo,mid]
     */
    @SuppressWarnings("unused")
    private int nonRecursiveRank(K key, int lo, int hi) {
        int rank = 0;
        //循环条件必须包括lo==hi的情况
        //  1.不包含的话 无法判断lo&hi重合键和key的位置关系 如果key>重合键 则回出错
        //  2.不会产生死循环 迭代时lo或者hi会朝key落在的半区多偏移一个
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            // key > mid
            if (key.compareTo(keys[mid]) > 0) {
                rank += mid - lo + 1;
                lo = mid + 1;
            }
            // key < mid
            else if (key.compareTo(keys[mid]) < 0) {
                hi = mid - 1;
            }
            // key == mid
            else {
                return rank + mid - lo;
            }
        }
        return rank;
    }

    @Override
    public K min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        else return keys[0];
    }

    @Override
    public K max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        else return keys[N - 1];
    }

    @Override
    public K floor(K key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        else {
            int index = rank(key);
            //key已存在
            if (index < N && keys[index].compareTo(key) == 0) {
                return key;
            }
            //key不存在 且 key <= keys <=> key < keys
            else if (index == 0) {
                return null;
            }
            //key不存在 且落在keys的范围内
            else return keys[index - 1];
        }
    }

    @Override
    public K ceiling(K key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        else {
            //key > keys
            if (key.compareTo(max()) > 0) return null;
            else return keys[rank(key)];
        }
    }

    @Override
    public int size() {
        return N;
    }

    /**
     * 从rank值表示比key小的键数的角度看
     * lo无论是否已经存在 小的键数统计中都不会包含lo点 作为被减数 等价于已经包含了lo点
     * hi不存在时 包含hi点 ; 已经存在时 不包含hi点 需+1
     */
    @Override
    public int size(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        else if (hi == null) throw new IllegalArgumentException("second argument to size() is null");
        else if (lo.compareTo(hi) > 0) return 0;
        else {
            //both lo&hi inclusive
            int loRank = rank(lo);
            int hiRank = rank(hi);
            //如果hi已存在
            if (contains(hi)) return hiRank - loRank + 1;
            else return hiRank - loRank;
        }
    }


    @Override
    public Iterable<K> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        else if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
        else {
            Queue<K> queue = new SimpleQueue<>();
            if (lo.compareTo(hi) > 0) return queue;
            // both hi&lo inclusive
            // i = rank(lo) -> 从第一个不比lo小的index开始
            // i < rank(hi) 注意没有等于
            for (int i = rank(lo); i < rank(hi); i++) {
                queue.enqueue(keys[i]);
            }
            return queue;
        }
    }

}
