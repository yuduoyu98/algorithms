package chapter3.searching;

import chapter3.searching.api.OST;
import chapter3.searching.impl.BinarySearchST;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;


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
    public void testPutAndGet() throws InstantiationException, IllegalAccessException {
        OST<String, Integer> testOst = implClazz.newInstance();
        String[] splits = "S E A R C H E X A M P L E".split(" ");
        //TreeMap默认升序
        Map<String, Integer> ost = new TreeMap<>();
        for (int i = 0; i < splits.length; i++) {
            testOst.put(splits[i], i);
            ost.put(splits[i], i);
        }

        assertEquals(ost.keySet().size(), testOst.size());

        //检测有序
        String lastKey = null;
        for (String key : testOst.keys()) {
            assert lastKey == null || key.compareTo(lastKey) > 0;
            assertEquals(ost.get(key), testOst.get(key));
            lastKey = key;
        }
    }

}
