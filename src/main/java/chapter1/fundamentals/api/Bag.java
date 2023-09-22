package chapter1.fundamentals.api;

public interface Bag<Item> extends Iterable<Item> {
    void add(Item item);

    boolean isEmpty();

    int size();
}
