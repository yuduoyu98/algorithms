package chapter3.searching.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter3.searching.api.AutoCheck;
import chapter3.searching.api.OST;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * OST implementation based on Binary Search Tree (recursive)
 */
public class BST<K extends Comparable<K>, V> implements OST<K, V>, AutoCheck {

    private Node root;
    private boolean INTERNAL_CHECK = false;

    // private修饰的内部类成员变量只能在外部类的内部访问
    private class Node {
        private K key;
        private V val;
        private Node left, right;
        // 当前节点有多少个子节点(包括自身)
        private int N;

        private Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.N = 1;
        }
    }

    @Override
    public boolean check() {
        //todo
        return INTERNAL_CHECK;
    }

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        if (val == null) {
            delete(key);
            return;
        }
        if (isEmpty()) root = new Node(key, val);
        else put(root, key, val);
    }

    private void put(Node n, K key, V val) {
        int cmp = key.compareTo(n.key);
        if (cmp > 0) {
            if (n.right != null) put(n.right, key, val);
            else n.right = new Node(key, val);
        }
        else if (cmp < 0) {
            if (n.left != null) put(n.left, key, val);
            else n.left = new Node(key, val);
        }
        else n.val = val;
        //注意递归中代码的顺序 递归put完成后 从下往上重新计算N值
        n.N = size(n.left) + size(n.right) + 1;
    }

    @Override
    public V get(K key) {
        AutoCheck.keyNotNull(key, "get");
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp == 0) return n.val;
        else if (cmp > 0) return get(n.right, key);
        else return get(n.left, key);
    }

    /**
     * 1.key非空校验
     * 2.维护N
     * 3.保持数据结构正确性
     */
    @Override
    public void delete(K key) {
        AutoCheck.keyNotNull(key, "delete");
        root = delete(root, key);
    }

    /**
     * ***************** ！！！ *********************
     * 无指针指向一个对象 也等同于删除（会被垃圾回收）
     * 递归方法delete表示 删除顶节点为n的（子）树 键为key的节点 返回删除后的顶节点
     */
    private Node delete(Node n, K key) {
        if (n == null) return null;

        int cmp = key.compareTo(n.key);
        if (cmp > 0) delete(n.right, key);
        else if (cmp < 0) delete(n.left, key);
        else {
            if (n.right == null) return n.left;
            else if (n.left == null) return n.right;
            // 右子树最小值作为successor
            Node t = n;
            n = min(n.right);
            // 右子树删除最小值调整后的顶节点
            n.right = deleteMin(t.right);
            n.left = t.left;
        }
        n.N = size(n.left) + size(n.right) + 1;
        return n;
    }

    /**
     * 不再复用delete方法 方便delete方法的实现
     */
    @Override
    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("call deleteMin() with empty symbol table");
        root = deleteMin(root);
    }

    /**
     * 删除的可能是n的这棵（子）树的根节点 所以需要通过返回值得到删除后的顶节点
     * 无指针指向的时候 对象会被垃圾回收 无需显式删除
     */
    private Node deleteMin(Node n) {
        // 如果不再有比n小的节点 则找到了最小值 递归终止
        // 直接返回n.right分支作为新的顶节点 n.right==null的边界条件并不影响
        if (n.left == null) return n.right;
        // 将左子树最小值删除后的根节点重新和n绑定
        n.left = deleteMin(n.left);
        // n.left.right部分的节点计数不受影响 路径上的节点需要重新计算
        n.N = size(n.left) + size(n.right) + 1;
        // 返回顶节点
        return n;
    }

    @Override
    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("call deleteMax() with empty symbol table");
        root = deleteMax(root);
    }

    private Node deleteMax(Node n) {
        if (n.right == null) return n.left;
        n.right = deleteMax(n.right);
        n.N = size(n.left) + size(n.right) + 1;
        return n;
    }

    @Override
    public boolean contains(K key) {
        AutoCheck.keyNotNull(key, "contains");
        return get(key) != null;
    }

    @Override
    public K min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    /**
     * @return 返回Node 方便delete获取最小节点对象的引用
     */
    private Node min(Node n) {
        if (n.left != null) return min(n.left);
        else return n;
    }

    @Override
    public K max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        else return max(root).key;
    }

    private Node max(Node n) {
        if (n.right != null) return max(n.right);
        else return n;
    }

    @Override
    public K floor(K key) {
        AutoCheck.keyNotNull(key, "floor");
        return floor(root, key);
    }

    /**
     * 小于等于key的最小值
     * 思路：从上往下递归的时候 是一个区间缩小的过程
     * 1）如果能找到key值相同的node 那么floor即为该点
     * 2）如果不存在key值相同的node 逐级返回途中合并子树floor值与当前父级n的结果作为新的该层级的floor值
     *   2.1）左子树向上回溯
     *      a）左子树所有节点 < n => floor值若存在 < n
     *      b）从n向下search时走左子树 => key < n => n不可能成为key的floor值
     *      => 只需直接返回下级floor值
     *   2.2）右子树向上回溯
     *      a）右子树所有节点 > n => floor值若存在 > n
     *      b）从n向下search时走右子树 => key > n
     *      => n < floor(若存在) < key
     *      => 若存在 返回floor; 若不存在 返回n
     */
    private K floor(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp > 0) return floor(n.right, key) == null ? n.key : floor(n.right, key);
        else if (cmp < 0) return floor(n.left, key);
        else return n.key;
    }

    @Override
    public K ceiling(K key) {
        AutoCheck.keyNotNull(key, "ceiling");
        return ceiling(root, key);
    }

    private K ceiling(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        // 逻辑同floor方法
        if (cmp > 0) return ceiling(n.right, key);
        else if (cmp < 0) return ceiling(n.right, key) == null ? n.key : ceiling(n.right, key);
        else return n.key;
    }

    @Override
    public K select(int r) {
        if (r < 0 || r >= size()) throw new IllegalArgumentException("argument to select() is invalid: " + r);
        return select(root, r);
    }

    /**
     * 小于n小的节点数（左子树节点数） 与 r（rank值：从0开始） 的关系
     */
    private K select(Node n, int r) {
        if (n == null) return null;
        if (size(n.left) > r) return select(n.left, r);
        else if (size(n.left) < r) return select(n.right, r);
        else return n.key;
    }

    @Override
    public int rank(K key) {
        AutoCheck.keyNotNull(key, "rank");
        return rank(root, key);
    }

    private int rank(Node n, K key) {
        if (n == null) return 0;
        int cmp = key.compareTo(n.key);
        if (cmp < 0) return rank(n.left, key);
        else if (cmp > 0) return size(n.left) + 1 + rank(n.right, key);
        else return size(n.left);
    }

    @Override
    public Iterable<K> keys() {
        return preOrderTraversal();
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        AutoCheck.keyNotNull(lo, "keys");
        AutoCheck.keyNotNull(hi, "keys");
        if (lo.compareTo(hi) < 0)
            throw new IllegalArgumentException("argument to select() is invalid: " + lo + ">" + hi);
        return preOrderTraversalWithBound(lo, hi);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) return 0;
        else return n.N;
    }

    /**
     * 树高
     */
    public int height() {
        return height(root);
    }

    private int height(Node n) {
        if (n == null) return 0;
        else return Math.max(height(n.left), height(n.right)) + 1;
    }

    /**
     * 前序遍历
     */
    public Iterable<K> preOrderTraversal() {
        Queue<K> queue = new SimpleQueue<>();
        if (root == null) return queue;
        else {
            preOrderTraversal(root, queue);
            return queue;
        }
    }

    private void preOrderTraversal(Node n, Queue<K> queue) {
        if (n.left != null) preOrderTraversal(n.left, queue);
        queue.enqueue(n.key);
        if (n.right != null) preOrderTraversal(n.right, queue);
    }

    public Iterable<K> preOrderTraversalWithBound(K lo, K hi) {

        Queue<K> queue = new SimpleQueue<>();
        if (root == null) return queue;
        else {
            preOrderTraversalWithBound(root, lo, hi, queue);
            return queue;
        }
    }

    /**
     * 前序遍历：左 -> 中 -> 右
     * 1）入队需判断是否落在区间内
     * 2）部分情况可以免去继续递归遍历
     */
    private void preOrderTraversalWithBound(Node n, K lo, K hi, Queue<K> queue) {
        // n.key > lo
        if (n.left != null && n.key.compareTo(lo) > 0) preOrderTraversalWithBound(n.left, lo, hi, queue);
        // lo <= n。key <= hi
        if (lo.compareTo(n.key) <= 0 && hi.compareTo(n.key) >= 0) queue.enqueue(n.key);
        // n.key < hi
        if (n.right != null && n.key.compareTo(hi) < 0) preOrderTraversalWithBound(n.right, lo, hi, queue);
    }

    // Simple Test
    public static void main(String[] args) {
        BST<Integer, Integer> bst = new BST<>();
        bst.put(1, 1);
        bst.put(2, 3);
        bst.put(0, 4);
        bst.put(7, 4);
        bst.put(5, 4);
        bst.put(5, 6);
        bst.put(10, 4);
        // input:  1 2 0 7 5 5 10
        // output: 0 1 2 5 7 10
        StringBuilder sb = new StringBuilder("order: ");
        for (Integer key : bst.preOrderTraversal()) {
            sb.append(key).append(" ");
        }
        System.out.println(sb);
        // size
        assertEquals(6, bst.size());
        System.out.println("✔✔✔ size() checked");
        // get
        assertNull(bst.get(8));
        assertEquals(Integer.valueOf(6), bst.get(5));
        System.out.println("✔✔✔ get() checked");
        // min & max
        assertEquals(Integer.valueOf(0), bst.min());
        assertEquals(Integer.valueOf(10), bst.max());
        System.out.println("✔✔✔ min() & max() checked");
        // floor & ceiling
        assertEquals(Integer.valueOf(2), bst.floor(3));
        assertNull(bst.floor(-1));
        assertEquals(Integer.valueOf(7), bst.floor(7));
        assertEquals(Integer.valueOf(10), bst.ceiling(8));
        assertNull(bst.ceiling(11));
        assertEquals(Integer.valueOf(7), bst.ceiling(7));
        System.out.println("✔✔✔ floor() & ceiling checked");
        // select
        assertEquals(Integer.valueOf(1), bst.select(1));
        assertThrows(IllegalArgumentException.class, () -> bst.select(6));
        // keys & keys with bound
        Iterable<Integer> keys = bst.keys();
        StringBuilder sb1 = new StringBuilder("keys: ");
        for (Integer i : keys)
            sb1.append(i).append(" ");
        System.out.println(sb1);
        StringBuilder sb2 = new StringBuilder("keys within [1,8]: ");
        Iterable<Integer> keysWithBound = bst.preOrderTraversalWithBound(1, 8);
        for (Integer i : keysWithBound)
            sb2.append(i).append(" ");
        System.out.println(sb2);
        // rank
        assertEquals(0, bst.rank(-1));
        assertEquals(2, bst.rank(2));
        assertEquals(3, bst.rank(3));
        assertEquals(6, bst.rank(11));
        System.out.println("✔✔✔ rank() checked");
        // deleteMin & deleteMax
        bst.deleteMin();
        assertEquals(5, bst.size());
        assertNull(bst.get(0));
        bst.put(0, 4);
        System.out.println("✔✔✔ deleteMin() checked");
        bst.deleteMax();
        assertEquals(5, bst.size());
        assertNull(bst.get(10));
        bst.put(10, 4);
        System.out.println("✔✔✔ deleteMax() checked");
        // delete
        bst.delete(1);
        assertEquals(5, bst.size());
        assertNull(bst.get(1));
        StringBuilder sb3 = new StringBuilder("After delete key 1: ");
        for (Integer i : bst.preOrderTraversal()) {
            sb3.append(i).append(" ");
        }
        System.out.println(sb3);
        System.out.println("✔✔✔ delete() checked");
    }
}
