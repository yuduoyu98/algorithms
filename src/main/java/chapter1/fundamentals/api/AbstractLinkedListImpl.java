package chapter1.fundamentals.api;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * data structure implemented based on linked list
 * - iterable (implement Iterable API)
 * - introduce dummy node to simplify the code logic
 */
public abstract class AbstractLinkedListImpl<Item> implements Iterable<Item> {

    protected Node dummy;   // dummy node
    protected Node tail;
    protected int n;        // size

    protected class Node {
        protected Item item;
        protected Node next;

        protected Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }

        protected Node() {
            this(null, null);
        }
    }

    protected AbstractLinkedListImpl() {
        dummy = new Node();
        tail = dummy.next;
        n = 0;
    }

    public boolean isEmpty() {
        return dummy.next == null;
    }

    public int size() {
        return n;
    }

    public boolean contains(Item item) {
        if (item == null) throw new IllegalArgumentException("input item is null");

        Node current = dummy.next;
        while (current != null) {
            if (item.equals(current.item)) return true;
            current = current.next;
        }
        return false;
    }

    protected void headInsert(Item item) {
        Node insertNode = new Node(item, null);
        if (isEmpty()) dummy.next = tail = insertNode;
        else {
            Node oldHead = dummy.next;
            dummy.next = insertNode;
            insertNode.next = oldHead;
        }
        n++;
    }

    protected void tailInsert(Item item) {
        Node node = new Node(item, null);
        if (isEmpty()) dummy.next = tail = node;
        else {
            tail.next = node;
            tail = tail.next;
        }
        n++;
    }

    protected Item headDelete() {
        if (isEmpty()) {
            StdOut.println("Empty set");
            return null;
        }

        Node deleteNode = dummy.next;
        dummy.next = dummy.next.next;
        n--;
        return deleteNode.item;
    }

    public Iterator<Item> iterator() {
        return new LinkedLiskIterator(dummy);
    }

    protected class LinkedLiskIterator implements Iterator<Item> {
        private Node current;

        public LinkedLiskIterator(Node dummy) {
            current = dummy;
        }

        public boolean hasNext() {
            return current.next != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            current = current.next;
            return current.item;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Item> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
