package chapter3.searching.impl;

import chapter1.fundamentals.impl.SimpleQueue;
import chapter3.searching.api.AbstractResizingHashTable;
import chapter3.searching.api.AutoCheck;

/**
 * Resizing hash table implementation based on separate chaining
 * - resize strategy:
 *      - double when average length of chain >= 10
 *      - halves when average length of chain <= 2 (minimum=INIT_CAPACITY)
 */
public class SeparateChainingHashST<K, V> extends AbstractResizingHashTable<K, V> {

    private static final int INIT_CAPACITY = 4;

    private SequentialSearchST<K, V>[] st; // array of ST

    public SeparateChainingHashST() {this(INIT_CAPACITY);}

    @SuppressWarnings("unchecked")
    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<K, V>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++)
            st[i] = new SequentialSearchST<>();
    }

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        if (val == null) {
            delete(key);
            return;
        }
        SequentialSearchST<K, V> chain = st[hash(key)];
        if (N >= 10 * M) resize(2 * M);
        if (!chain.contains(key)) N++;
        chain.put(key, val);
    }

    @Override
    public V get(K key) {
        AutoCheck.keyNotNull(key, "get");
        return st[hash(key)].get(key);
    }

    @Override
    public void delete(K key) {
        AutoCheck.keyNotNull(key, "delete");
        SequentialSearchST<K, V> chain = st[hash(key)];
        if (chain.contains(key)) {
            chain.delete(key);
            N--;
            if (N > INIT_CAPACITY && N <= 2 * M) resize(M / 2);
        }
    }

    @Override
    public Iterable<K> keys() {
        SimpleQueue<K> queue = new SimpleQueue<>();
        for (SequentialSearchST<K, V> s : st) {
            for (K key : s.keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

    @Override
    protected void resize(int size) {
        SeparateChainingHashST<K, V> temp = new SeparateChainingHashST<>(size);
        for (SequentialSearchST<K, V> s : this.st) {
            for (K key : s.keys()) {
                temp.put(key, s.get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }
}
