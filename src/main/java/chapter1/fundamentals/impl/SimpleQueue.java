package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.PrintableLinearDS;
import chapter1.fundamentals.api.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 基于链表实现 Queue API
 */
public class SimpleQueue<Item> extends PrintableLinearDS<Item> implements Queue<Item> {

    private Node<Item> head;

    private Node<Item> tail;

    private int count;

    private static class Node<Item> {

        private Item item;

        private Node<Item> next;

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }

    public SimpleQueue() {
        count = 0;
    }

    @Override
    public void enqueue(Item item) {
        Node<Item> node = new Node<>(item, null);
        if (isEmpty()) {
            //尾插
            head = tail = node;
        } else {
            tail.next = node;
            tail = tail.next;
        }
        count++;
    }

    @Override
    public Item dequeue() {
        //头删: 尾删没法拿到前一个元素
        Node<Item> oldHead = head;
        head = head.next;
        count--;
        return oldHead.item;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator<>(head);
    }

    private static class LinkedListIterator<Item> implements Iterator<Item> {

        Node<Item> current;

        public LinkedListIterator(Node<Item> head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
