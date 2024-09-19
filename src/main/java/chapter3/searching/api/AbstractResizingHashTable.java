package chapter3.searching.api;

/**
 * abstract hash table
 */
public abstract class AbstractResizingHashTable<K, V> implements ST<K, V>, Resizable {

    protected int N; // number of kv pairs
    protected int M; // hash table size

    @Override
    public int size() {
        return N;
    }

    // default hash function
    protected int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M; // mask off the sign bit
    }

}
