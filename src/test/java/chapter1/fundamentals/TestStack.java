package chapter1.fundamentals;

import chapter1.fundamentals.impl.SimpleStack;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Stack API test
 */
public class TestStack {

    @Test
    public void basicTest() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
        stack.push(3);
        assertEquals(Integer.valueOf(3), stack.pop());
    }

    @Test
    public void iteratorTest() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Iterator<Integer> iter = stack.iterator();
        System.out.print("iterator: ");
        while (iter.hasNext())
            System.out.print(iter.next() + " ");
        System.out.println();
        System.out.println("toString: " + stack);
    }
}
