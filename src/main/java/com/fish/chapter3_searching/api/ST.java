package com.fish.chapter3_searching.api;

/**
 * Symbol Table
 * @param <K> key
 * @param <V> value
 */
public interface ST<K, V> {

    void put(K key, V val);

    V get(K key);

    void delete(K key);

    boolean contains(K key);

    boolean isEmpty();

    /**
     * 符号表键值对数量
     */
    int size();

    Iterable<K> keys();

}
