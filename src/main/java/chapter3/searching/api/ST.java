package chapter3.searching.api;

/**
 * Symbol Table
 * @param <K> key
 * @param <V> value
 */
public interface ST<K, V> {

    void put(K key, V val);

    V get(K key);

    /**
     * 如果key存在删除该key
     */
    void delete(K key);

    boolean contains(K key);

    boolean isEmpty();

    /**
     * 符号表键值对数量
     */
    int size();

    Iterable<K> keys();

}
