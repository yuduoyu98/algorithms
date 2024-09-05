package chapter3.searching.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.SimpleStack;
import chapter3.searching.api.AutoCheck;

/**
 * 在BST的基础上用非递归的方式重写get/put/keys
 * 有空可以将递归版本的其他方法重写为非递归版本
 */
public class NonRecursiveBST<K extends Comparable<K>, V> extends BST<K, V> {

    @Override
    public Iterable<K> keys(K lo, K hi) {
        return null;
    }

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        if (val == null) {
            delete(key);
            return;
        }
        Node newNode = new Node(key, val);
        // store path -> maintain N
        Stack<Node> stack = new SimpleStack<>();
        Node n = root;
        while (n != null) {
            stack.push(n);
            int cmp = key.compareTo(n.key);
            if (cmp > 0) n = n.right;
            else if (cmp < 0) n = n.left;
            else {
                n.val = val;
                return;
            }
        }
        if (stack.isEmpty()) root = newNode; // the bst is empty
        else {
            Node pn = stack.pop(); // parent of the new put node
            if (key.compareTo(pn.key) > 0) pn.right = newNode;
            else pn.left = newNode;
            // maintain N
            pn.N = size(pn.left) + size(pn.right) + 1;
            while (!stack.isEmpty()) {
                BST<K, V>.Node t = stack.pop();
                t.N = size(t.right) + size(t.left) + 1;
            }
        }
    }

    @Override
    public V get(K key) {
        AutoCheck.keyNotNull(key, "get");
        Node n = root;
        while (n != null) {
            int cmp = key.compareTo(n.key);
            if (cmp > 0) n = n.right;
            else if (cmp < 0) n = n.left;
            else return n.val;
        }
        return null;
    }

    /**
     * non-recursive version of pre-order traversal
     * 还需练习
     */
    @Override
    public Iterable<K> keys() {
        Queue<K> queue = new SimpleQueue<>();
        if (isEmpty()) return queue;
        SimpleStack<Node> stack = new SimpleStack<>(); //store the path
        Node n = root;
        stack.push(root);
        while (n != null || !stack.isEmpty()) {
            // 非null节点 -> 1.加入path栈 2.向左子树探索
            if (n != null) {
                stack.push(n);
                n = n.left;
            }
            else {
                // 左边到头：1.回退 2.入队 3.向右探索
                n = stack.pop();
                queue.enqueue(n.key);
                n = n.right;
            }
        }
        return queue;
    }

}
