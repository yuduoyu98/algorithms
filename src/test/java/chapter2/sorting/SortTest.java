package chapter2.sorting;

import chapter2.sorting.impl.HeapSort;
import chapter2.sorting.impl.SelectionSort;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

public class SortTest {

    public static Integer[] generateIntegerArr(int size, int bound) {
        if (bound < size) {
            throw new IllegalArgumentException("随机数生成上界应不小于随机数组元素个数");
        }
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = StdRandom.uniformInt(bound);
        }
        return arr;
    }

    public static void test(SortTemplate<Integer> sortImpl, Integer[] arr) {
        sortImpl.print(arr, "排序前");
        sortImpl.sort(arr);
        sortImpl.print(arr, "排序");
        assert sortImpl.isSorted(arr) : "排序结果错误";
    }

    @Test
    public void selectionSortTest() {
        System.out.println("------------------------ 选择排序测试 ------------------------");
        SelectionSort<Integer> sortAlg = new SelectionSort<>();
        test(sortAlg, generateIntegerArr(1, 100));
        test(sortAlg, generateIntegerArr(50, 100));
    }

    @Test
    public void heapSortTest() {
        System.out.println("------------------------ 堆排序测试 ------------------------");
        HeapSort<Integer> sortAlg = new HeapSort<>();
        test(sortAlg, generateIntegerArr(1, 100));
        test(sortAlg, generateIntegerArr(50, 100));
    }

}
