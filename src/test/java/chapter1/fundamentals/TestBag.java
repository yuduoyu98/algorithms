package chapter1.fundamentals;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.impl.SimpleBag;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * Bag API test
 */
public class TestBag {
    @Test
    public void basicTest() {
        Bag<Integer> bag = new SimpleBag<>();
        bag.add(1);
        bag.add(2);
        bag.add(3);
        StdOut.println("bag size: " + bag.size());
        StdOut.println("bag empty? " + bag.isEmpty());
        System.out.print("bag elements: ");
        for (int i : bag) System.out.print(i + " ");
        System.out.println();
        System.out.println("toString: " + bag);
    }

}
