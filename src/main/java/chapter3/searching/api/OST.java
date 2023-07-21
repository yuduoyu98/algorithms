package chapter3.searching.api;

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
     * 小于等于key的最大键
     */
    K floor(K key);

    /**
     * 大于等于等于key的最小键
     */
    K ceiling(K key);

    /**
     * 排名为 r 的键
     */
    K select(int r);

    /**
     * 键的排名：小于key的键的数量 (key可以不在符号表中)
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
