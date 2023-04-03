package com.fish.chapter3_searching.api;

import com.fish.chapter3_searching.api.ST;

/**
 * Ordered Symbol Table
 * @param <K> 实现 Comparable接口的泛型对象
 * @param <V>
 */
public interface OST<K extends Comparable<K>, V> extends ST<K , V> {

    /**
     * min key of ST
     */
    K min();

    /**
     * max key of ST
     */
    K max();

    /**
     * max key lte the input key
     */
    K floor(K key);

    /**
     * min key gte the input key
     */
    K ceiling(K key);

    /**
     * 排名为 r 的键
     */
    K select(int r);

    /**
     * 键的排名：小于key的键的数量
     */
    int rank(K key);

    /**
     * 删除最小的键
     */
    void deleteMin();

    /**
     * 删除最大的键
     */
    void deleteMax();

    /**
     * [lo, hi]之间键的数量
     */
    int size(K lo, K hi);

    /**
     * [lo, hi]之间的所有键 （已排序）
     */
    Iterable<K> keys(K lo, K hi);
}
