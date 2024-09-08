package chapter3.searching.impl;

import chapter3.searching.api.AutoCheck;

/**
 * (Left-leaning) Red-Black BST implementation of OST
 * - is BST (BST methods are applicable)
 * - 1-1 correspondent to 2-3 tree
 */
public class RedBlackBST<K extends Comparable<K>, V> extends BST<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node extends BST<K, V>.Node {
        private Node right, left;
        private boolean color; // color of parent link

        private Node(K key, V val) {
            super(key, val);
            this.color = RED;
        }
    }

    private boolean isRed(Node n) {
        if (n == null) return false; // null Nodes' color are defined as BLACK
        else return n.color;
    }

    /**
     *    parent of p             parent of p
     *        /                       /
     *        p         --->      p.right
     *        \                     /
     *      p.right                p
     * Rotate the right-leaning red link to lean left
     * @param p see above
     * @return new root node of the subTree
     */
    private Node rotateLeft(Node p) {
        assert (p != null) && isRed(p.right); // isRed requires the node to be not null
        // rotate
        Node t = p.right;
        p.right = t.left;
        t.left = p;
        // color
        t.color = p.color;
        p.color = RED;
        // size (BST)
        t.N = size(p);
        p.N = size(p.left) + size(p.right) + 1;
        return t;
    }

    /**
     *         p
     *        /                       p.left
     *     p.left      --->          /       \
     *      /                   p.left.left   p
     *  p.left.left
     * Rotate right to adjust two consecutive red links
     * @param p see above
     * @return new root node of the subTree
     */
    private Node rotateRight(Node p) {
        assert (p != null) && isRed(p.left);
        // rotate
        Node pLeft = p.left;
        p.left = pLeft.right;
        pLeft.right = p;
        // color
        pLeft.color = p.color; // succeed its parent's color
        p.color = RED;
        // size (BST)
        pLeft.N = size(p);
        p.N = size(p.left) + size(p.right) + 1;
        return pLeft;
    }

    /**
     * Flip the color of p and its children to the opposite color
     * - Flip the color of p to avoid two red links connecting to 1 node
     * - delete?
     * @param p parent node
     */
    private void flipColors(Node p) {
        assert !isRed(p);
        assert isRed(p.left);
        assert isRed(p.right);
        p.color = RED;
        p.left.color = BLACK;
        p.right.color = BLACK;
    }

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        root.color = BLACK; // ensure the root is BLACK
    }

    /**
     * --------------- 需要重新思考 ------------------
     * insert a new node into the subtree rooted at n
     * No need to care about the subtree root color? ok
     * return the new root of the subtree after adjustment
     * @param n the original root of the subtree
     * @return the new root of the subtree
     */
    private Node put(Node n, K key, V val) {
        if (n == null) return new Node(key, val);
        int cmp = key.compareTo(n.key);
        if (cmp > 0) n.right = put(n.right, key, val);
        else if (cmp < 0) n.left = put(n.left, key, val);
        else n.val = val;
        // ----------------------- adjustment -------------------------
        // Node n always should point to the root of the subtree !!
        // Sequence Matters:
        //  1.correct right-leaning red links
        if (isRed(n.right) && !isRed(n.left)) n = rotateLeft(n);
        //  2.rotateRight (Finnish rotation before color flipping)
        if (isRed(n.left) && isRed(n.left.left)) n = rotateRight(n);
        //  3.Flip color
        if (isRed(n.left) && isRed(n.right)) flipColors(n);

        n.N = size(n.left) + size(n.right) + 1;
        return n;
    }

    /**
     * need to override because the root here is not the same one in its super class
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Simple Visualization of tree
     */
    public void print() {
        if (isEmpty())
            System.out.println("The Red-Black BST is empty");
        else printTree(root, 0);
    }

    /**
     * use post-order traversal to print, like:
     *        7(B)
     * 5(R)
     *               3(R)
     *        2(B)
     *               1(R)
     */
    private void printTree(Node n, int level) {
        if (n == null) return;

        // print right subtree
        printTree(n.right, level + 1);
        // print current node
        for (int i = 0; i < level; i++)
            System.out.print("      ");
        System.out.println(n.key + (isRed(n) ? "(R)" : "(B)"));
        // print left subtree
        printTree(n.left, level + 1);
    }

    public static void main(String[] args) {
        // print test
        RedBlackBST<String, Integer> rbt = new RedBlackBST<>();
        rbt.put("S", 1);
        rbt.put("E", 1);
        rbt.put("A", 1);
        rbt.put("R", 1);
        rbt.put("C", 1);
        rbt.put("H", 1);
        rbt.print();
    }

}
