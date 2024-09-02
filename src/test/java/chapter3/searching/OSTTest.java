package chapter3.searching;

import chapter3.searching.api.OST;
import chapter3.searching.impl.BinarySearchST;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class OSTTest {

    private Class<? extends OST<String, Integer>> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                BinarySearchST.class
        };
    }

    public OSTTest(Class<? extends OST<String, Integer>> implClazz) {
        this.implClazz = implClazz;
    }

    private OST<String, Integer> mockTestData() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        OST<String, Integer> ost = implClazz.getConstructor().newInstance();
        String[] splits = "S E A R C H E X A M P L E".split(" ");
        //A C E H L M P R S X
        for (int i = 0; i < splits.length; i++) {
            ost.put(splits[i], i);
        }
        return ost;
    }

    @Test
    public void testRank() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        OST<String, Integer> ost = mockTestData();

        StringBuilder sb = new StringBuilder();
        for (String key : ost.keys()) {
            sb.append(key).append(" ");
        }
        System.out.println("ordered keys: " + sb);

        assertEquals(0, ost.rank("A"));
        assertEquals(1, ost.rank("B"));
        assertEquals(1, ost.rank("C"));
        assertEquals(6, ost.rank("P"));
        assertEquals(7, ost.rank("Q"));
        assertEquals(10, ost.rank("Y"));
    }

    @Test
    public void testDelete() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        OST<String, Integer> ost = mockTestData();
        int originSize = ost.size();

        assertThrows(IllegalArgumentException.class, () -> ost.delete(null));

        ost.delete("B");
        assertEquals(ost.size(), originSize);
        assertEquals(1, ost.rank("B"));
        assertEquals(6, ost.rank("P"));

        ost.delete("C");
        assertEquals(ost.size(), originSize - 1);
        assert (ost.get("C") == null);
        assertEquals(1, ost.rank("B"));
        assertEquals(5, ost.rank("P"));
    }


    @Test
    public void testFloorAndCeiling() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        OST<String, Integer> ost = mockTestData();

        assertEquals("A", ost.floor("B"));
        assertEquals("C", ost.ceiling("B"));

        assertEquals("C", ost.floor("C"));
        assertEquals("C", ost.ceiling("C"));

        assertEquals("X", ost.floor("Y"));
        assertNull(ost.ceiling("Y"));
    }

}
