package chapter3.searching.api;

/**
 * abstract hash table
 */
public abstract class AbstractResizingHashTable<K, V> implements ST<K, V> {

    protected int N; // number of kv pairs
    protected int M; // hash table size

    @Override
    public int size() {
        return N;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // default hash function
    protected int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M; // mask off the sign bit
    }

    protected abstract void resize(int size);

}