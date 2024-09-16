package chapter3.searching;

import chapter3.searching.impl.RedBlackBST;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;

/**
 * Left-Leaning Red-Black BST Test
 */
@RunWith(Parameterized.class)
public class RedBlackBSTTest {

    private Class<RedBlackBST<Integer, Integer>> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                RedBlackBST.class,
        };
    }

    public RedBlackBSTTest(Class<RedBlackBST<Integer, Integer>> implClazz) {
        this.implClazz = implClazz;
    }

    @Test
    public void deleteTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RedBlackBST<Integer, Integer> rbt1 = init();
        RedBlackBST<Integer, Integer> rbt2 = init();
        System.out.println("--------------- initial ----------------");
        rbt1.print();
        System.out.println("--------------- deleteMax ----------------");
        System.out.println("############# answer #############");
        rbt1.deleteMax();
        rbt1.print();
        System.out.println("############# result #############");
        rbt2.deleteMax1();
        rbt2.print();
        System.out.println("--------------- deleteMin ----------------");
        System.out.println("############# answer #############");
        rbt1.deleteMin();
        rbt1.print();
        System.out.println("############# result #############");
        rbt2.deleteMin1();
        rbt2.print();
    }

    private RedBlackBST<Integer, Integer> init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RedBlackBST<Integer, Integer> rbt = implClazz.getConstructor().newInstance();
        rbt.put(10, 1);
        rbt.put(5, 1);
        rbt.put(15, 1);
        rbt.put(3, 1);
        rbt.put(7, 1);
        rbt.put(13, 1);
        rbt.put(17, 1);
        rbt.put(11, 1);
        return rbt;
    }


}
