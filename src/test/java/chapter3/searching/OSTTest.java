package chapter3.searching;

import chapter3.searching.api.OST;
import chapter3.searching.impl.BinarySearchST;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


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

    @Test
    public void testRank() throws InstantiationException, IllegalAccessException {
        OST<String, Integer> ost = implClazz.newInstance();
        String[] splits = "S E A R C H E X A M P L E".split(" ");
        //A C E H L M P R S X
        for (int i = 0; i < splits.length; i++) {
            ost.put(splits[i], i);
        }

        StringBuilder sb = new StringBuilder();
        for (String key : ost.keys()) {
            sb.append(key).append(" ");
        }
        System.out.println("ordered keys: " + sb);

        assertEquals(ost.rank("A"), 0);
        assertEquals(ost.rank("B"), 1);
        assertEquals(ost.rank("C"), 1);
        assertEquals(ost.rank("P"), 6);
        assertEquals(ost.rank("Q"), 7);
        assertEquals(ost.rank("Y"), 10);
    }

    @Test
    public void testDelete() throws InstantiationException, IllegalAccessException {
        OST<String, Integer> ost = implClazz.newInstance();
        String[] splits = "S E A R C H E X A M P L E".split(" ");
        //A C E H L M P R S X
        for (int i = 0; i < splits.length; i++) {
            ost.put(splits[i], i);
        }
        assertThrows(IllegalArgumentException.class, () -> ost.delete(null));
        int originSize = ost.size();
        ost.delete("B");
        assertEquals(ost.size(), originSize);
        assertEquals(ost.rank("B"), 1);
        assertEquals(ost.rank("P"), 6);
        ost.delete("C");
        assertEquals(ost.size(), originSize - 1);
        assert (ost.get("C") == null);
        assertEquals(ost.rank("B"), 1);
        assertEquals(ost.rank("P"), 5);
    }


}
