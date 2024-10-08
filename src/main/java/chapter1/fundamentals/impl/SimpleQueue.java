package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.AbstractLinkedListImpl;
import chapter1.fundamentals.api.Queue;

/**
 * Queue API implementation based on linked list
 */
public class SimpleQueue<Item> extends AbstractLinkedListImpl<Item> implements Queue<Item> {

    @Override
    public void enqueue(Item item) {
        tailInsert(item);
    }

    @Override
    public Item dequeue() {
        return headDelete();
    }

}
