package chapter1.fundamentals;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Queue API test
 */
public class TestQueue {

    @Test
    public void basicTest() {
        Queue<Integer> queue = new SimpleQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        queue.enqueue(3);
        assertEquals(Integer.valueOf(3), queue.dequeue());
    }

    @Test
    public void iteratorTest() {
        Queue<Integer> queue = new SimpleQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        Iterator<Integer> iter = queue.iterator();
        System.out.print("iterator: ");
        while (iter.hasNext())
            System.out.print(iter.next() + " ");
        System.out.println();
        System.out.println("toString: " + queue);
    }
}
