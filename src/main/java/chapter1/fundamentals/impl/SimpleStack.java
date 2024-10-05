package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.PrintableLinearDS;
import chapter1.fundamentals.api.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 基于链表实现的 Stack API （Node为静态内部类）
 */
public class SimpleStack<Item> extends PrintableLinearDS<Item> implements Stack<Item> {

    private Node<Item> head;
    private int count;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;

        public Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }

    public SimpleStack() {
        head = new Node<>(null, null);
        count = 0;
    }

    @Override
    public void push(Item item) {
        head.next = new Node<>(item, head.next);
        count++;
    }

    @Override
    public Item pop() {
        if (!isEmpty()) {
            Node<Item> node = head.next;
            head.next = head.next.next;
            count--;
            return node.item;
        } else {
            StdOut.println("Stack为空，无法pop");
            return null;
        }
    }

    @Override
    public boolean isEmpty() {
        return head.next == null;
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

        private Node<Item> current;

        public LinkedListIterator(Node<Item> head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.next.item;
            current = current.next;
            return item;
        }
    }

}
