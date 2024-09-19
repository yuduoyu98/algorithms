package chapter3.searching.impl;

import chapter1.fundamentals.impl.SimpleQueue;
import chapter3.searching.api.AbstractResizingHashTable;
import chapter3.searching.api.AutoCheck;

/**
 * Resizing hash table implementation based on linear probing
 * - resize the st to ensure the load factor is between 1/8 ~ 1/2
 */
public class LinearProbingHashST<K, V> extends AbstractResizingHashTable<K, V> {

    private static final int INIT_CAPACITY = 4;

    private K[] keys;
    private V[] vals;

    @SuppressWarnings("unchecked")
    public LinearProbingHashST(int M) {
        this.M = M;
        keys = (K[]) new Object[M];
        vals = (V[]) new Object[M];
    }

    @SuppressWarnings("unused")
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        // double size if load factor >= 1/2
        if (M <= 2 * N) resize(2 * M);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }

        keys[i] = key;
        vals[i] = val;
        N++;
    }

    @Override
    public V get(K key) {
        AutoCheck.keyNotNull(key, "get");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (key.equals(keys[i])) {
                return vals[i];
            }
        }
        return null;
    }

    @Override
    public void delete(K key) {
        AutoCheck.keyNotNull(key, "delete");
        if (!contains(key)) return;

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (key.equals(keys[i])) break;
        }
        keys[i] = null;
        vals[i] = null;
        N--;

        // re-insert
        i = (i + 1) % M;
        while (keys[i] != null) {
            K rKey = keys[i];
            V rVal = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(rKey, rVal);
            i = (i + 1) % M;
        }

        // halves size if load factor below 1/8
        if (N > INIT_CAPACITY && 8 * N <= M) resize(M / 2);
    }

    @Override
    public void resize(int capacity) {
        LinearProbingHashST<K, V> temp = new LinearProbingHashST<>(capacity);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        M = temp.M;
    }

    @Override
    public Iterable<K> keys() {
        SimpleQueue<K> queue = new SimpleQueue<>();
        for (K key : keys)
            if (key != null) queue.enqueue(key);
        return queue;
    }
}
