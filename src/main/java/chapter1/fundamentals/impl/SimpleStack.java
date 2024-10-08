package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.AbstractLinkedListImpl;
import chapter1.fundamentals.api.Stack;

/**
 * Stack API implementation based on linked list
 */
public class SimpleStack<Item> extends AbstractLinkedListImpl<Item> implements Stack<Item> {

    public SimpleStack() {
        super();
    }

    @Override
    public void push(Item item) {
        headInsert(item);
    }

    @Override
    public Item pop() {
        return headDelete();
    }

}
