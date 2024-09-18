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

    default boolean contains(K key) {
        AutoCheck.keyNotNull(key, "contains");
        return get(key) == null;
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 符号表键值对数量
     */
    int size();

    Iterable<K> keys();

}
