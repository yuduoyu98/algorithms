package chapter3.searching;


import chapter3.searching.impl.AVLTreeST;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class AVLTreeSTTest {

    private Class<AVLTreeST<Integer, Integer>> implClazz;
    private static final int[] testData = new int[]{10, 5, 15, 3, 7, 6, 8, 4, 1, 13};

    private Integer max(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        int max = Integer.MIN_VALUE;
        for (int i : arr)
            if (i > max) max = i;
        return max;
    }

    private Integer min(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        int min = Integer.MAX_VALUE;
        for (int i : arr)
            if (i < min) min = i;
        return min;
    }

    private Integer random(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        return arr[(int) (Math.random() * arr.length)];
    }

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                AVLTreeST.class,
        };
    }

    public AVLTreeSTTest(Class<AVLTreeST<Integer, Integer>> implClazz) {
        this.implClazz = implClazz;
    }

    private AVLTreeST<Integer, Integer> init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AVLTreeST<Integer, Integer> avl = implClazz.getConstructor().newInstance();
        for (int i : testData) {
            avl.put(i, 1);
        }
        avl.put(6, 123);
        return avl;
    }

    @Test
    public void testPutAndGet() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AVLTreeST<Integer, Integer> avl = init();
        // put test
        avl.print();
        // get test
        assertEquals(Integer.valueOf(123), avl.get(6));
        assertNull(avl.get(-1));
    }

    @Test
    public void testMinMax() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AVLTreeST<Integer, Integer> avl = init();
        Integer min = min(testData);
        Integer max = max(testData);

        assertEquals(min, avl.min());
        assertEquals(max, avl.max());

        System.out.println("-------------- origin --------------");
        avl.print();
        System.out.println("-------------- deleteMin " + min + " --------------");
        avl.deleteMin();
        avl.print();
        System.out.println("-------------- deleteMax " + max + " --------------");
        avl.deleteMax();
        avl.print();
    }

    @Test
    public void testDelete() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        AVLTreeST<Integer, Integer> avl = init();
        System.out.println("-------------- origin --------------");
        avl.print();
        int random = random(testData);
//        random = 6;
        System.out.println("-------------- delete " + random + " --------------");
        avl.delete(random);
        avl.print();
    }
}
