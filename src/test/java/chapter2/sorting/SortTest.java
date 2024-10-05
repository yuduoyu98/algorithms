package chapter2.sorting;

import chapter2.sorting.impl.*;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;


@RunWith(Parameterized.class)
public class SortTest {

    private Class<? extends SortTemplate<Integer>> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                SelectionSort.class,
                InsertionSort.class,
                BubbleSort.class,
                ShellSort.class,
                HeapSort.class,
        };
    }

    public SortTest(Class<? extends SortTemplate<Integer>> implClazz) {
        this.implClazz = implClazz;
    }

    @Test
    public void test() {
        System.out.println("------------------------ test: " + implClazz.getSimpleName() + " ------------------------");
        SortTemplate<Integer> impl = null;
        try {
            impl = implClazz.getConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("sort impl build failure");
            e.printStackTrace();
            System.exit(0);
        }

        // corner case
        test(impl, generateIntegerArr(1, 100));
        // test case
        test(impl, generateIntegerArr(6, 10));
        // regular case
        test(impl, generateIntegerArr(50, 100));

        System.out.println("test passes");
    }

    private static Integer[] generateIntegerArr(int size, int bound) {
        if (bound < size) throw new IllegalArgumentException("upper bound less than random number size");

        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = StdRandom.uniformInt(bound);
        }
        return arr;
    }

    private static void test(SortTemplate<Integer> sortImpl, Integer[] arr) {
        sortImpl.print(arr, "initial");
        Integer[] clone = arr.clone();
        Arrays.sort(clone);
        sortImpl.sort(arr);
        sortImpl.print(arr, "sorted");

        assert sortImpl.isSorted(arr) : " incorrect implementation: array not sorted";
        assert Arrays.equals(clone, arr) : "incorrect implementation: modified array elements";
    }

}
