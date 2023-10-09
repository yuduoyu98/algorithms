package chapter1.fundamentals;

import chapter1.fundamentals.impl.SimpleStack;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.Iterator;

/**
 * Stack实现测试
 */
public class TestStack {

    @Test
    public void basicTest() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        StdOut.println(stack.pop());
        StdOut.println(stack.pop());
        stack.push(3);
        StdOut.println(stack.pop());
    }

    @Test
    public void iteratorTest() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Iterator<Integer> iter = stack.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}
