package chapter3.searching.api;

/**
 * Ordered Symbol Table
 *
 * @param <K> 实现 Comparable接口的泛型对象
 * @param <V>
 */
public interface OST<K extends Comparable<K>, V> extends ST<K, V> {

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
    default void deleteMin() {
        delete(min());
    }

    /**
     * 删除最大的键
     */
    default void deleteMax() {
        delete(max());
    }

    /**
     * [lo, hi]之间键的数量
     */
    default int size(K lo, K hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        /**
         * rank(k) 表示 比k小的key有多少个
         * -> rank(hi)-rank(lo) -> 比hi小的key数-比lo小的key数
         * -> 边界情况：
         *        1.hi本身就是key时 hi包含在区间内 但未被包含在比hi小的key数内 所以需要 +1
         *        2.lo本身就是key时 lo包含在区间内 不会算在比lo小的key数中 并未被减掉
         */
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }

    /**
     * [lo, hi]之间的所有键 （已排序）
     */
    Iterable<K> keys(K lo, K hi);
}
