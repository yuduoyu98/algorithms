package chapter3.searching;

import chapter3.searching.impl.BST;
import chapter3.searching.impl.NonRecursiveBST;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BSTTest {

    private Class<? extends BST<Integer, Integer>> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                BST.class,
                NonRecursiveBST.class,
        };
    }

    public BSTTest(Class<? extends BST<Integer, Integer>> implClazz) {
        this.implClazz = implClazz;
    }

    @Test
    public void simpleTest() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // input:  1 2 0 7 5 5 10
        BST<Integer, Integer> bst = mockData();
        // output: 0 1 2 5 7 10
        printIterable("order: ", bst.preOrderTraversal());
        sizeTest(bst);
        getTest(bst);
        minMaxTest(bst);
        floorCeilingTeST(bst);
        selectTest(bst);
        keysTest(bst);
        rankTest(bst);
        deleteMinMaxTest(bst);
        deleteTest(bst);
    }

    private static void deleteTest(BST<Integer, Integer> bst) {
        bst.delete(1);
        assertEquals(5, bst.size());
        assertEquals(5, bst.size());
        assertNull(bst.get(1));
        printIterable("After delete key 1: ", bst.preOrderTraversal());
        System.out.println("✔✔✔ delete() checked");
    }

    private static void deleteMinMaxTest(BST<Integer, Integer> bst) {
        bst.deleteMin();
        assertEquals(5, bst.size());
        assertNull(bst.get(0));
        bst.put(0, 4);
        System.out.println("✔✔✔ deleteMin() checked");
        bst.deleteMax();
        assertEquals(5, bst.size());
        assertNull(bst.get(10));
        bst.put(10, 4);
        System.out.println("✔✔✔ deleteMax() checked");
    }

    private static void rankTest(BST<Integer, Integer> bst) {
        assertEquals(0, bst.rank(-1));
        assertEquals(2, bst.rank(2));
        assertEquals(3, bst.rank(3));
        assertEquals(6, bst.rank(11));
        System.out.println("✔✔✔ rank() checked");
    }

    private static void keysTest(BST<Integer, Integer> bst) {
        printIterable("keys: ", bst.keys());
        printIterable("keys within [1,8]: ", bst.preOrderTraversalWithBound(1, 8));
    }

    private static void selectTest(BST<Integer, Integer> bst) {
        assertEquals(Integer.valueOf(1), bst.select(1));
        assertThrows(IllegalArgumentException.class, () -> bst.select(6));
    }

    private static void floorCeilingTeST(BST<Integer, Integer> bst) {
        assertEquals(Integer.valueOf(2), bst.floor(3));
        assertNull(bst.floor(-1));
        assertEquals(Integer.valueOf(7), bst.floor(7));
        assertEquals(Integer.valueOf(10), bst.ceiling(8));
        assertNull(bst.ceiling(11));
        assertEquals(Integer.valueOf(7), bst.ceiling(7));
        System.out.println("✔✔✔ floor() & ceiling checked");
    }

    private static void minMaxTest(BST<Integer, Integer> bst) {
        assertEquals(Integer.valueOf(0), bst.min());
        assertEquals(Integer.valueOf(10), bst.max());
        System.out.println("✔✔✔ min() & max() checked");
    }

    private static void getTest(BST<Integer, Integer> bst) {
        assertNull(bst.get(8));
        assertEquals(Integer.valueOf(6), bst.get(5));
        System.out.println("✔✔✔ get() checked");
    }

    private static void sizeTest(BST<Integer, Integer> bst) {
        assertEquals(6, bst.size());
        System.out.println("✔✔✔ size() checked");
    }

    private BST<Integer, Integer> mockData() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        BST<Integer, Integer> bst = implClazz.getConstructor().newInstance();
        bst.put(1, 1);
        bst.put(2, 3);
        bst.put(0, 4);
        bst.put(7, 4);
        bst.put(5, 4);
        bst.put(5, 6);
        bst.put(10, 4);
        return bst;
    }

    private static void printIterable(String prefix, Iterable<Integer> iterable) {
        StringBuilder sb = new StringBuilder(prefix);
        for (int key : iterable) {
            sb.append(key).append(" ");
        }
        System.out.println(sb);
    }
}
