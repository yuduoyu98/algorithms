package chapter2.sorting;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.net.Socket;

public class SortTest {

    public static final Integer[] arr = new Integer[]{5, 2, 3, 4, 1};

    public static Integer[] generateIntegerArr(int size, int bound){
        if(bound <= size){
            throw new IllegalArgumentException("随机数生成上界应大于随机数组元素个数");
        }
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = StdRandom.uniformInt(bound);
        }
        return arr;
    }

    public static void test(SortTemplate<String> sortImpl, Socket socket) {
        In in = new In(socket);
        String[] arr = in.readAllStrings();
        sortImpl.sort(arr);
        assert sortImpl.isSorted(arr);
        sortImpl.print(arr);
    }

    public static void test(SortTemplate<Integer> sortImpl, Integer[] arr) {
        sortImpl.print(arr, "排序前");
        sortImpl.sort(arr);
        sortImpl.print(arr, "排序后");
        assert sortImpl.isSorted(arr) : "排序结果错误";
    }

    @Test
    public void selectionSortTest() {
        SelectionSort<Integer> sortAlg = new SelectionSort<>();
        test(sortAlg, generateIntegerArr(50, 100));
    }

}
