package chapter3.searching.impl;

import chapter3.searching.api.AutoCheck;
import chapter3.searching.api.OST;

import java.util.NoSuchElementException;

/**
 * an ordered symbol table implemented using an AVL tree (recursive version)
 * Note about AVL:
 * 1.self-balancing binary search tree:
 *   for each node, the heights of the two child subtrees differ by at most one
 * 2.how to maintain balance:
 *   a) insert: 4 cases
 *   1)     n        2)     n        3)     n        4)     n
 *        /   \           /   \           /   \           /   \
 *       l     h         h     r         l     h         h     r
 *     /  \                  /  \       / \                  /  \
 *    h+1 h                 h   h+1    h  h+1               h+1  h
 *        rR(n)            rL(n)       rL(l)+rR(n)       rR(r)+rL(n)
 *   b) delete:
 *      I. find the ancestor/successor(if any) and replace(delete) it
 *     II. similar 4 cases using rotation to maintain balance
 */

public class AVLTreeST<K extends Comparable<K>, V> implements OST<K, V>, AutoCheck {

    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int height;
        private int N;

        private Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.height = 1;
            this.N = 1;
        }
    }

    /***************************************************************************
     *  AVL tree helper functions.
     ***************************************************************************/

    /**
     * balance factor: left subtree height - right subtree height
     */
    private int balanceFactor(Node n) {
        assert n != null;
        return height(n.left) - height(n.right);
    }

    private Node rotateRight(Node n) {
        assert n != null && n.left != null && (n.left.left != null || n.left.right != null);
        assert height(n.left) > height(n.right);

        Node t = n.left;
        n.left = t.right;
        t.right = n;
        // size maintenance
        t.N = size(n);
        n.N = size(n.left) + size(n.right) + 1;
        // height maintenance
        n.height = Math.max(height(n.left), height(n.right)) + 1;
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private Node rotateLeft(Node n) {
        assert n != null && n.right != null && (n.right.right != null || n.right.left != null);
        assert height(n.left) < height(n.right);

        Node t = n.right;
        n.right = t.left;
        t.left = n;
        // size maintenance
        t.N = size(n);
        n.N = size(n.left) + size(n.right) + 1;
        // height maintenance
        n.height = Math.max(height(n.left), height(n.right)) + 1;
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    /**
     * maintain the balance of subtree rooted at n if needed
     * pre-condition:
     * - both n.left and n.right are balanced
     * - n is not considered as null
     */
    private Node balanceIfNeeded(Node n) {
        assert n != null;

        int bf = balanceFactor(n);
        if (bf > 1) { // n.left won't be null (n.left.height >= 2)
            int lbf = balanceFactor(n.left);
            if (lbf < 0) { // case 2
                n.left = rotateLeft(n.left);
            }
            n = rotateRight(n);
        }
        else if (bf < -1) {
            int rbf = balanceFactor(n.right);
            if (rbf > 0) { // case 4
                n.right = rotateRight(n.right);
            }
            n = rotateLeft(n);
        }
        return n;
    }

    /**
     * print the tree with height
     */
    public void print() {
        if (isEmpty()) System.out.println("The tree is empty");
        else print(root, 0);
    }

    private void print(Node n, int level) {
        if (n.right != null) print(n.right, level + 1);
        StringBuilder gap = new StringBuilder();
        for (int i = 0; i < level; i++) {
            gap.append("      ");
        }
        System.out.println(gap.toString() + n.key + "(" + n.height + ")");
        if (n.left != null) print(n.left, level + 1);
    }

    /***************************************************************************
     *  AVL tree basic methods
     ***************************************************************************/

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node n, K key, V val) {
        assert key != null && val != null;
        if (n == null) return new Node(key, val);
        int cmp = key.compareTo(n.key);
        if (cmp > 0) {
            n.right = put(n.right, key, val);
        }
        else if (cmp < 0) {
            n.left = put(n.left, key, val);
        }
        else {
            n.val = val;
            return n;
        }
        // update height
        n.height = Math.max(height(n.left), height(n.right)) + 1;
        // update size
        n.N = size(n.left) + size(n.right) + 1;
        return balanceIfNeeded(n);
    }

    @Override
    public V get(K key) {
        AutoCheck.keyNotNull(key, "get");
        Node node = get(root, key);
        if (node == null) return null;
        else return node.val;
    }

    private Node get(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp > 0) return get(n.right, key);
        else if (cmp < 0) return get(n.left, key);
        else return n;
    }

    @Override
    public void delete(K key) {
        AutoCheck.keyNotNull(key, "delete");
        if (isEmpty()) return;
        root = delete(root, key);
    }

    public Node delete(Node n, K key) {
        if (n == null) return n;
        int cmp = key.compareTo(n.key);
        if (cmp > 0) n.right = delete(n.right, key);
        else if (cmp < 0) n.left = delete(n.left, key);
        else {
            if (n.right == null) return n.left;

            Node successor = min(n.right);
            n.key = successor.key;
            n.val = successor.val;
            n.right = deleteMin(n.right);
            // size maintenance
            n.N = size(n.left) + size(n.right) + 1;
            n.height = Math.max(height(n.right), height(n.left)) + 1;
        }
        return balanceIfNeeded(n);
    }

    @Override
    public K min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node n) {
        if (n.left == null) return n;
        return min(n.left);
    }

    @Override
    public K max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node n) {
        if (n.right == null) return n;
        return max(n.right);
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("called deleteMin() with empty symbol table");
        root = deleteMin(root);
    }

    private Node deleteMin(Node n) {
        if (n.left == null) return n.right;
        n.left = deleteMin(n.left);
        n.N = size(n.left) + size(n.right) + 1;
        n.height = Math.max(height(n.right), height(n.left)) + 1;
        return balanceIfNeeded(n);
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("called deleteMax() with empty symbol table");
        root = deleteMax(root);
    }

    private Node deleteMax(Node n) {
        assert n != null;

        if (n.right == null) return n.left;
        n.right = deleteMax(n.right);
        n.N = size(n.left) + size(n.right) + 1;
        n.height = Math.max(height(n.right), height(n.left)) + 1;
        return balanceIfNeeded(n);
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) return 0;
        else return n.N;
    }

    public int height() {
        return height(root);
    }

    private int height(Node n) {
        if (n == null) return 0;
        else return n.height;
    }

    /***************************************************************************
     *  AVL tree functions tbd (similar with binary search tree)
     ***************************************************************************/

    @Override
    public K floor(K key) {
        return null;
    }

    @Override
    public K ceiling(K key) {
        return null;
    }

    @Override
    public K select(int r) {
        return null;
    }

    @Override
    public int rank(K key) {
        return 0;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        return null;
    }

    @Override
    public Iterable<K> keys() {
        return null;
    }

}
