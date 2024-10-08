package chapter2.sorting;

import java.util.Arrays;

/**
 *
 */
public class ArrayTools {

    public static void printIntArr(int[] arr, String prefix, boolean withIndex) {
        Integer[] boxedArr = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        print(boxedArr, prefix, withIndex);
    }


    // print the arr
    public static <T extends Comparable<T>> void print(T[] arr) {
        print(arr, null, false);
    }

    public static <T extends Comparable<T>> void print(T[] arr, String prefix) {
        print(arr, prefix, false);
    }

    // print the arr with index
    public static <T extends Comparable<T>> void print(T[] arr, String prefix, boolean withIndex) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null) sb.append(prefix).append(": ");
        int count = 0;
        for (T t : arr) {
            sb.append(t);
            if (withIndex) sb.append("(").append(count).append(")");
            count++;
            if (count < arr.length) sb.append(",");
        }
        System.out.println(sb);
    }

}
