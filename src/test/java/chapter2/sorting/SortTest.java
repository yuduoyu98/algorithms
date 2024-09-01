package chapter2.sorting;

import chapter2.sorting.impl.HeapSort;
import chapter2.sorting.impl.InsertionSort;
import chapter2.sorting.impl.SelectionSort;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

public class SortTest {

    private static Integer[] generateIntegerArr(int size, int bound) {
        if (bound < size) {
            throw new IllegalArgumentException("随机数生成上界应不小于随机数组元素个数");
        }
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = StdRandom.uniformInt(bound);
        }
        return arr;
    }

    private static void test(SortTemplate<Integer> sortImpl, Integer[] arr) {
        sortImpl.print(arr, "排序前");
        sortImpl.sort(arr);
        sortImpl.print(arr, "排序");
        assert sortImpl.isSorted(arr) : "排序结果错误";
    }

    private static void basicTests(SortTemplate<Integer> sortAlg){
        //corner case
        test(sortAlg, generateIntegerArr(1, 100));
        //test case
        test(sortAlg, generateIntegerArr(6, 10));
        //regular case
        test(sortAlg, generateIntegerArr(50, 100));
    }

    @Test
    public void selectionSortTest() {
        System.out.println("------------------------ 选择排序测试 ------------------------");
        SelectionSort<Integer> sortAlg = new SelectionSort<>();
        basicTests(sortAlg);
    }

    @Test
    public void insertionSortTest() {
        System.out.println("------------------------ 插入排序测试 ------------------------");
        InsertionSort<Integer> sortAlg = new InsertionSort<>();
        basicTests(sortAlg);
    }

    @Test
    public void heapSortTest() {
        System.out.println("------------------------ 堆排序测试 ------------------------");
        HeapSort<Integer> sortAlg = new HeapSort<>();
        basicTests(sortAlg);
    }

}
