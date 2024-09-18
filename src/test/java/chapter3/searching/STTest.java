package chapter3.searching;

import chapter3.searching.api.ST;
import chapter3.searching.impl.BinarySearchST;
import chapter3.searching.impl.LinearProbingHashST;
import chapter3.searching.impl.SeparateChainingHashST;
import chapter3.searching.impl.SequentialSearchST;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class STTest {

    private Class<? extends ST<String, Integer>> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                SequentialSearchST.class,
                BinarySearchST.class,
                LinearProbingHashST.class,
                SeparateChainingHashST.class
        };
    }

    public STTest(Class<? extends ST<String, Integer>> implClazz) {
        this.implClazz = implClazz;
    }

    @Test
    public void testPutAndGet() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ST<String, Integer> st = implClazz.getConstructor().newInstance();
        st.put("key1", 1);
        st.put("key2", 2);

        assertEquals(Integer.valueOf(1), st.get("key1"));
        assertEquals(Integer.valueOf(2), st.get("key2"));
    }

    @Test
    public void testDelete() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ST<String, Integer> st = implClazz.getConstructor().newInstance();
        st.put("key1", 1);
        st.put("key2", 2);

        st.delete("key1");
        assertFalse(st.contains("key1"));
        assertTrue(st.contains("key2"));
    }

    @Test
    public void testContains() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ST<String, Integer> st = implClazz.getConstructor().newInstance();
        st.put("key1", 1);

        assertTrue(st.contains("key1"));
        assertFalse(st.contains("key2"));
    }

    @Test
    public void testIsEmpty() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ST<String, Integer> st = implClazz.getConstructor().newInstance();
        assertTrue(st.isEmpty());

        st.put("key1", 1);
        assertFalse(st.isEmpty());
    }

    @Test
    public void testSize() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ST<String, Integer> st = implClazz.getConstructor().newInstance();
        assertEquals(0, st.size());

        st.put("key1", 1);
        st.put("key2", 2);
        assertEquals(2, st.size());

        st.delete("key1");
        assertEquals(1, st.size());
    }

    @Test
    public void testKeys() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ST<String, Integer> st = implClazz.getConstructor().newInstance();
        st.put("key1", 1);
        st.put("key2", 2);

        Iterable<String> keys = st.keys();
        for (String key : keys) {
            assertTrue(st.contains(key));
        }
    }

}
