package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.Bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  基于链表实现（Node为静态内部类）：SimpleBag
 *
 *  @param <Item> 泛型类型
 */
public class SimpleBag<Item> implements Bag<Item> {
    private Node<Item> first;    // 链表头节点
    private int n;               // Bag元素个数

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public SimpleBag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    //头插
    public void add(Item item) {
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public Iterator<Item> iterator()  {
        return new LinkedLiskIterator(first);
    }

    private class LinkedLiskIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedLiskIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
