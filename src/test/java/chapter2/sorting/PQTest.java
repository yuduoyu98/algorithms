package chapter2.sorting;

import chapter2.sorting.pq.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * priority queue test
 */
public class PQTest {

    @Test
    public void indexedPQTest() {
        // ascending order: B C G H I K O R T W
        //      ki            0    1    2    3    4    5    6    7    8    9
        String[] testData = {"I", "W", "T", "B", "O", "G", "K", "C", "H", "R"};

        System.out.println("===================== MaxPQ =====================");
        test(testData, new IndexedMaxPQImpl<>(testData.length), true);
        System.out.println("===================== MinPQ =====================");
        test(testData, new IndexedMinPQImpl<>(testData.length), false);
    }

    private void test(String[] testData, IndexedPQ<String> pq, boolean isMaxPQ) {

        String[] result = new String[testData.length];

        // insert
        for (int i = 0; i < testData.length; i++)
            pq.insert(i, testData[i]);

        // deleteMin & insert test
        int j = 0;
        String prev = null;
        for (int ki : pq) {
            orderCheck(prev, testData[ki], isMaxPQ);
            result[j++] = testData[ki];
            prev = testData[ki];
        }
        ArrayTools.print(testData, "origin     ");
        ArrayTools.print(result, "heap sorted");

        System.out.println("--------------------------------------");

        // change test
        pq.change(4, "E");
        testData[4] = "E";
        // ascending order: B C E G H I K R T W
        j = 0;
        prev = null;
        Arrays.fill(result, null);
        for (int ki : pq) {
            orderCheck(prev, testData[ki], isMaxPQ);
            result[j++] = testData[ki];
            prev = testData[ki];
        }
        ArrayTools.print(testData, "change O(4) to E        ");
        ArrayTools.print(result, "heap sorted after change");

        System.out.println("----------------------------------------");

        // delete test
        pq.delete(2);

        j = 0;
        prev = null;
        Arrays.fill(result, null);
        for (int ki : pq) {
            orderCheck(prev, testData[ki], isMaxPQ);
            result[j++] = testData[ki];
            prev = testData[ki];
        }

        ArrayTools.print(testData, "origin                    ");
        ArrayTools.print(result, "iterator after delete T(2)");
    }

    private void orderCheck(String prev, String val, boolean isMaxPQ) {
        if (prev == null) return;
        if (isMaxPQ) assert val.compareTo(prev) <= 0;
        else assert val.compareTo(prev) >= 0;
    }

}
