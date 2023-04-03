package com.fish.chapter3_searching.impl;

import com.fish.chapter3_searching.api.ST;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于无序链表的符号表实现
 * @param <K>
 * @param <V>
 */
public class SequentialSearchST<K, V> implements ST<K, V> {

    private Node first;

    private class Node {
        K key;
        V val;
        Node next;

        public Node(K key, V val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    @Override
    public void put(K key, V val) {
        Node cur = first;
        while (cur != null) {
            if (cur.key.equals(key)) {
                cur.val = val;
                return;
            }
            cur = cur.next;
        }
        first = new Node(key, val, first);
    }

    @Override
    public V get(K key) {
        Node cur = first;
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur.val;
            }
            cur = cur.next;
        }
        return null;
    }

    @Override
    public void delete(K key) {
        Node cur = first;
        Node last = null;
        while (cur != null) {
            if (cur.key.equals(key)) {
                //如果需要删除第一个节点
                if (last == null) {
                    first = cur.next;
                } else {
                    last.next = cur.next;
                }
                return;
            }
            last = cur;
            cur = cur.next;
        }
    }

    @Override
    public boolean contains(K key) {
        Node cur = first;
        while (cur != null) {
            if (cur.key.equals(key)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        int cnt = 0;
        Node cur = first;
        while (cur != null) {
            cnt++;
            cur = cur.next;
        }
        return cnt;
    }

    @Override
    public Iterable<K> keys() {
        Node cur = first;
        List<K> keys = new ArrayList<>();
        while (cur != null) {
            keys.add(cur.key);
            cur = cur.next;
        }
        return keys;
    }

}
