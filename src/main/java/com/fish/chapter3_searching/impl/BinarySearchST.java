package com.fish.chapter3_searching.impl;

import com.fish.chapter3_searching.api.OST;
import edu.princeton.cs.algs4.Queue;

import java.util.LinkedList;

/**
 * @author Yuyi-YuShaoyu
 * @Description
 * @create 2023-04-03 17:22
 * @Modified By
 */
public class BinarySearchST<K extends Comparable<K>, V> implements OST<K, V> {

    private K[] keys;
    private V[] vals;
    private int N;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        //Java 不支持直接创建泛型数组
        //可参考：https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
        keys = (K[]) new Comparable[capacity];
        vals = (V[]) new Object[capacity];
        N = 0;
    }

    public BinarySearchST() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public V get(K key) {
        int rank = rank(key);
        return rank < N && keys[rank].compareTo(key) == 0 ? vals[rank] : null;
    }

    @Override
    public void put(K key, V val) {

    }

    @Override
    public K select(int rank) {
        return keys[rank];
    }

    @Override
    public K min() {
        return keys[0];
    }

    @Override
    public K max() {
        return keys[N - 1];
    }

    @Override
    public int rank(K key) {
        return 0;
    }

    @Override
    public K floor(K key) {
        int rank = rank(key);
        if (rank == N){

        }
        return rank == 0 ? null : keys[rank];
    }

    @Override
    public K ceiling(K key) {
        int rank = rank(key);
        return rank == N ? null : keys[rank];
    }

    @Override
    public void deleteMin() {
        for (int i = 1; i < N; i++) {
            keys[i - 1] = keys[i];
            vals[i - 1] = vals[i];
        }
        N -= 1;
    }

    @Override
    public void deleteMax() {
        keys[N - 1] = null;
        vals[N - 1] = null;
        N -= 1;
    }

    @Override
    public void delete(K key) {
        int rank = rank(key);

    }

    @Override
    public boolean contains(K key) {
        int rank = rank(key);
        return keys[rank - 1].equals(key);
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public int size(K lo, K hi) {
        return 0;
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
        for (int i = 0; i < N; i++) {
            q.enqueue(keys[i]);
        }
        return q;
    }
}
