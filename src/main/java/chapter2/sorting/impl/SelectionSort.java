package chapter2.sorting.impl;

import chapter2.sorting.SortTemplate;

/**
 * <h2>选择排序</h2>
 * <strong>基本思想</strong>：依次找到最小元素与指定位置元素进行交换
 * <strong>特点</strong>:
 * <ol>
 *     <li> 运行时间和输入无关：比较次数和交换次数固定
 *     <li> 数据移动是最少的
 * </ol>
 */
public class SelectionSort<T extends Comparable<T>> implements SortTemplate<T> {

    @Override
    public void sort(T[] arr) {
        //1. 依次找出第i小的元素
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            //2.[i,arr.length)找出最小元素下标
            for (int j = i + 1; j < arr.length; j++) {
                if (less(arr[j], arr[min])) {
                    min = j;
                }
            }
            //3.交换
            exchange(arr, i, min);
        }

    }

}