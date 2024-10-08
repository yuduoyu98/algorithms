package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.AbstractLinkedListImpl;
import chapter1.fundamentals.api.Bag;

/**
 * Bag API implementation based on linked list
 */
public class SimpleBag<Item> extends AbstractLinkedListImpl<Item> implements Bag<Item> {

    public SimpleBag() {
        super();
    }

    @Override
    public void add(Item item) {
        headInsert(item);
    }
}
