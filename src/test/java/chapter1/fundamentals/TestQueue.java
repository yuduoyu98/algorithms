package chapter1.fundamentals;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.Iterator;

/**
 * Queue实现测试
 */
public class TestQueue {

    @Test
    public void basicTest() {
        Queue<Integer> queue = new SimpleQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        queue.enqueue(3);
        StdOut.println(queue.dequeue());
    }

    @Test
    public void iteratorTest() {
        Queue<Integer> queue = new SimpleQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}
