package chapter3.searching.impl;

import chapter3.searching.api.OST;
import edu.princeton.cs.algs4.Queue;

/**
 * Implementation of a Symbol Table based on Binary Search and Ordered Array
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
    public void put(K key, V val) {

    }

    @Override
    public V get(K key) {
        int index = rank(key);
        if(index == n) {

        }
        return null;
    }

    /**
     * 比key小的键的个数 <=> key如果插入有序符号表的下标
     */
    @Override
    public int rank(K key) {
        return 0;
    }

    /**
     * [lo, hi]范围内rank值
     */
    public int rank(K key, int lo, int hi){
        return 0;
    }

    @Override
    public K min() {
        return null;
    }

    @Override
    public K max() {
        return null;
    }

    @Override
    public K floor(K key) {
        return null;
    }

    @Override
    public K ceiling(K key) {
        return null;
    }

    @Override
    public K select(int r) {
        return null;
    }

    @Override
    public int size(K lo, K hi) {
        return 0;
    }

    @Override
    public void delete(K key) {

    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterable<K> keys() {
        return null;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        return null;
    }
}
