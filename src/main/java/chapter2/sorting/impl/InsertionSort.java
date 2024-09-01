package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;

/**
 * <h2>Insertion Sort</h2>
 * <strong>idea</strong> <p> sort cards(抓牌), consider a 'card' at a time, insert it into the right position </p>
 * <strong>traits</strong>
 * <ol>
 *     <li> 适合非随机数组/部分有序数组：初始数组的有序性会大大提升插入排序的效率
 *     <li> exchange次数 = 逆序数(inversions)
 *     <li> compare次数 最少=逆序数(inversions) ;最多=逆序数(inversions)+数组长度-1（除了交换对应的一次比较外 每一轮额外一次比较）
 * </ol>
 */
public class InsertionSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    public void sort(T[] arr) {
//        sort1(arr);
        sort2(arr);
    }

    private void sort1(T[] arr) {
        //下标i左边确保有序
        for (int i = 1; i < arr.length; i++) {
            T t = arr[i]; //待插入值
            for (int j = 0; j < i; j++) {
                if (less(t, arr[j])) { // arr[i] < arr[j]
                    //右移 -> 相对sort2交换的方式 减少数组访问次数
                    for (int k = i; k > j; k--) {
                        arr[k] = arr[k - 1];
                    }
                    //插入
                    arr[j] = t;
                    break;
                }
            }
        }
    }

    private void sort2(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            //1."冒泡"的方式实现 2.放在for循环中 省略break
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) {
                exchange(arr, j, j - 1);
            }
        }
    }

}
