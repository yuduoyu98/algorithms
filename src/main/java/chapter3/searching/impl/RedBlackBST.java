package chapter3.searching.impl;

import chapter3.searching.api.AutoCheck;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * (Left-leaning) Red-Black BST implementation of 2-3 tree
 * - is BST (BST methods are applicable)
 * - 1-1 correspondent to 2-3 tree
 */
public class RedBlackBST<K extends Comparable<K>, V> extends BST<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    @SuppressWarnings("FieldCanBeLocal")
    private boolean INTERNAL_CHECK = true;
    private Node root; // use own root instead of inheriting BST's root

    private class Node extends BST<K, V>.Node {
        private Node right, left;
        private boolean color; // color of parent link

        private Node(K key, V val) {
            super(key, val);
            this.color = RED;
        }
    }

    /***************************************************************************
     *  Red-black tree helper functions.
     ***************************************************************************/

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
        assert (p != null) && isRed(p.right); // require the right child to be RED so that perfect black balance won't break
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
        assert (p != null) && isRed(p.left); // require the left child to be RED so that perfect black balance won't break
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
     * @param p parent node
     */
    private void flipColors(Node p) {
        // h must have opposite color of its two children
        assert (p != null) && (p.left != null) && (p.right != null);
        assert (!isRed(p) && isRed(p.left) && isRed(p.right)) || (isRed(p) && !isRed(p.left) && !isRed(p.right));
        p.color = !p.color;
        p.left.color = !p.left.color;
        p.right.color = !p.right.color;
    }

    /***************************************************************************
     *  Red-black tree auto check functions.
     ***************************************************************************/

    /**
     * if assert doesn't work, add '-ea' to VMOptions
     */
    @Override
    public boolean check() {
        if (INTERNAL_CHECK) {
            if (!isBST()) StdOut.println("Not in symmetric order");
            if (!is23Tree()) StdOut.println("Not a 2-3 tree");
            if (!isBlackBalanced()) StdOut.println("Not balanced");
            if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
            return isBST() && is23Tree() && isBlackBalanced() && isSizeConsistent();
        }
        else return true;
    }

    private boolean isSizeConsistent() {return isSizeConsistent(root);}

    private boolean isSizeConsistent(Node n) {
        if (n == null) return true;
        if (n.N != size(n.left) + size(n.right) + 1) return false;
        return isSizeConsistent(n.left) && isSizeConsistent(n.right);
    }

    /**
     * check whether black height is perfectly balanced
     */
    private boolean isBlackBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBlackBalanced(root, black);
    }

    // check whether there is any path from the root to a leaf doesn't have the given number of black links
    private boolean isBlackBalanced(Node n, int black) {
        if (n == null) return black == 0;
        if (!isRed(n)) black--;
        return isBlackBalanced(n.left, black) && isBlackBalanced(n.right, black);
    }

    /**
     * whether the tree contains:
     * - either right-leaning red link
     * - or a node with two red link attached
     */
    private boolean is23Tree() {return is23Tree(root);}

    private boolean is23Tree(Node n) {
        if (n == null) return true;
        if (isRed(n.right)) return false;
        if (n != root && isRed(n) && isRed(n.left))
            return false;
        return is23Tree(n.left) && is23Tree(n.right);
    }

    /***************************************************************************
     *  Red-black tree main functions.
     ***************************************************************************/

    @Override
    public void put(K key, V val) {
        AutoCheck.keyNotNull(key, "put");
        if (val == null) {
            delete1(key);
            assert check();
            return;
        }
        root = put(root, key, val);
        root.color = BLACK; // ensure the root is BLACK
        assert check();
    }

    /**
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
        // adjustments
        return balance(n);
    }

    /**
     * (self implemented) recursive solution
     */
    public void delete1(K key) {
        AutoCheck.keyNotNull(key, "delete");
        if (isEmpty()) return;
        // root is a 2-node(both children are BLACK), turn root RED
        if (!isRed(root.left))
            root.color = RED;
        root = delete1(root, key);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    /**
     * delete key from the subtree rooted at n
     * 1. ensure the child node stepping into is not a 2-node during the top-down search
     * 2. ensure
     * @return new root of the subtree
     *
     */
    private Node delete1(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp > 0) {
            // ensure the right child is not a 2-node
            // delete key not found -> head back (repair)
            if (n.right == null) return n;
            // n.right is a 2-node
            if (!isRed(n.right) && !isRed(n.right.left)) {
                if (isRed(n.left))
                    n = rotateRight(n);
                else // both children of n are BLACK
                    flipColors(n);
            }
            n.right = delete1(n.right, key);
        }
        else if (cmp < 0) {
            // ensure the left child is not a 2-node
            // delete key not found -> head back (repair)
            if (n.left == null) return n;
            // n.left is a 2-node
            if (!isRed(n.left) && !isRed(n.left.left)) {
                if (isRed(n.right))
                    n = rotateLeft(n);
                else
                    flipColors(n);
            }
            n.left = delete1(n.left, key);
        }
        else {
            // delete n
            // if n is leaf node
            if (n.left == null && n.right == null)
                return null;
            // otherwise, try to find the successor
            if (n.right != null) {
                // ensure the initial right node is not a 2-node because unlike root it can not change its color
                // if n.right is a 2-node, at least one of n and n.left is RED + n.left is not null
                if (!isRed(n.right) && !isRed(n.right.left)) {
                    if (isRed(n.left)) { // left child is RED
                        // rotateRight the node match the key, delete later
                        n = rotateRight(n);
                        n.right = delete1(n.right, key);
                    }
                    else {
                        flipColors(n);
                        if (isRed(n.left.left)) {
                            // rotateRight the node match the key, delete later
                            n = rotateRight(n);
                            flipColors(n);
                            n.right = delete1(n.right, key);
                        }
                        else {
                            BST<K, V>.Node successor = min(n.right);
                            n.right = deleteMin(n.right);
                            n.key = successor.key;
                            n.val = successor.val;
                        }
                    }
                }
                else {
                    BST<K, V>.Node successor = min(n.right);
                    n.right = deleteMin(n.right);
                    n.key = successor.key;
                    n.val = successor.val;
                }
            }
            else {
                BST<K, V>.Node successor = n.left;
                n.left = null;
                n.key = successor.key;
                n.val = successor.val;
            }
        }
        return balance(n);
    }

    /**
     * (book version)
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table)
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    /**
     * (book version) need to dive in a bit more
     * delete the key-value pair with the given key rooted at h
     */
    private Node delete(Node h, K key) {
        if (key.compareTo(h.key) < 0) {
            // h.left is a 2-node (both h.left and h.left.left are BLACK)
            // h.right is BLACK?
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            // even key and h.key are equal, there are cases that can not remove h here
            // because deleteMin need to ensure initial node h.right is not a 2-node
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            // h.right is a 2-node
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                BST<K, V>.Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    /**
     * (implemented myself)
     * recursive solution
     * revision idea:
     * - most of the code at root is the same with any other node dealt in the recursive method
     * - Difference: have to keep the root color BLACK after color flipping
     *      - only occur when root and its children are all 2-node
     * - Solution:
     *      - if the above scenario occur, turn the color of root to RED ahead
     *        -> when flipping color after, back to BLACK as expected.
     *
     * @see chapter3.searching.impl.RedBlackBST#deleteMin()
     */
    @SuppressWarnings("unused")
    public void deleteMin1() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // root is the minimum -> single 2-node (right child's existence will break the balance)
        if (root.left == null) {
            root = null;
            return;
        }
        // if root is a 2-node
        if (!isRed(root.left)) {
            // ensure the left-child is not a 2-node before moving left
            // left-child of root is 2-node
            if (!isRed(root.left.left)) {
                // right-child of root is a 3-node
                if (isRed(root.right.left)) { // root.right won't be null
                    root = borrowFromRight2Left(root);
                }
                // right-child of root is a 2-node as well
                else {
                    // form a temporary 4-node with two red link attached to root
                    root.color = RED;
                    flipColors(root);
                }
            }
        }
        root.left = deleteMin1(root.left);
        root = balance(root);
        root.color = BLACK;
        assert check();
    }

    /**
     * (implemented myself)
     * delete the minimum node of the subtree
     * @param n the root of the subtree (should not be a 2-node)
     * @return the new root of the subtree
     */
    private Node deleteMin1(Node n) {
        // left-child of n is not a 2-node
        if (n.left == null) return null;
            // n.left is a 2-node
        else if (!isRed(n.left) && !isRed(n.left.left)) {
            // n.right is a 3-node
            if (isRed(n.right.left)) n = borrowFromRight2Left(n);
                // n.right is a 2-node
            else flipColors(n);
        }
        // moving left
        n.left = deleteMin1(n.left);
        // adjustments after deletion
        return balance(n);
    }

    /**
     * (implemented myself)
     *                     rotate right            rotate left
     *            o                      o                            b
     *          /    \                 /    \                       /    \
     *         l      r       =>      l      b         =>          o      r
     *        /     /   \            /     /   \                  / \    /  \
     *      s1     b    s4         s1     s2    r                l  s2  s3  s4
     *            /  \                        /  \              /
     *           s2  s3                      s3   s4           s1
     * Nodes:
     * - o (origin root)
     * - l (left-child of o)
     * - r (right-child of o)
     * - b (node to be borrowed)
     * - s1~s4: subtree 1~4
     * Preconditions:
     * - l: 2-node
     * - r: 3-node -> b is red
     * @param originRoot see above
     * @return new root
     */
    private Node borrowFromRight2Left(Node originRoot) {
        // rotate right
        Node originRightChild = originRoot.right;
        Node newRightChild = originRightChild.left;
        originRightChild.left = newRightChild.right;
        newRightChild.right = originRightChild;
        newRightChild.color = originRightChild.color;
        newRightChild.N = size(originRightChild);
        originRightChild.N = size(originRightChild.left) + size(originRightChild.right) + 1;
        originRoot.right = newRightChild;

        // rotate left
        Node newRoot = originRoot.right;
        originRoot.right = newRoot.left;
        newRoot.left = originRoot;
        newRoot.color = originRoot.color;
        originRoot.left.color = RED;
        newRoot.N = size(originRoot);
        originRoot.N = size(originRoot.left) + size(originRoot.right) + 1;

        return newRoot;
    }

    /**
     * book version
     */
    @Override
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    /**
     * book version
     */
    private Node deleteMin(Node n) {
        if (n.left == null)
            return null;

        if (!isRed(n.left) && !isRed(n.left.left))
            n = moveRedLeft(n);

        n.left = deleteMin(n.left);
        return balance(n);
    }

    /**
     * book version
     *                   flipColor(n)           rotateRight(n.r)          rotateLeft(n)              flipColor(n.r.l)
     *          n(R)                    n(B)                    n(B)                        n.r.l(B)                   n.r.l(R)
     *         /    \                  /    \                  /    \                       /    \                     /    \
     *      n.l(B) n.r(B)    ->     n.l(R) n.r(R)   ->      n.l(R) n.r.l(R)    ->        n(R)    n.l(R)      ->     n(B)    n.l(B)
     *       /      /                /      /                /         \                 /                          /
     *   n.l.l(B) n.r.l(R)       n.l.l(B) n.r.l(R)       n.l.l(B)     n.l(R)          n.l(R)                     n.l(R)
     *                                                                                 /                          /
     *                                                                             n.l.l(B)                   n.l.l(B)
     */
    private Node moveRedLeft(Node h) {
        assert (h != null);
        assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * adjustments after recursion:
     * 1.right-leaning red link correction
     * 2.two consecutive red links correction
     * 3.both children are red correction
     */
    private Node balance(Node n) {
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
     * (implemented myself)
     * delete the max node from the tree
     * recursive solution
     */
    @SuppressWarnings("unused")
    public void deleteMax1() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if root is the max node
        if (root.right == null) {
            root = root.left;
            return;
        }
        // borrow case, change the root color ahead for flipping
        if (!isRed(root.right.left) && !isRed(root.left)) { //root.right is a 2-node and root is a 2-node
            root.color = RED;
        }
        // root.right is not null => able to move right
        root = deleteMax1(root);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    /**
     * (implemented myself)
     * delete the max node of the subtree not breaking the invariants and return the new root of the subtree
     * precondition: n is not a 2-node (either n is red or n.left is red)
     * @param n root of the subtree
     * @return new root of the subtree
     */
    private Node deleteMax1(Node n) {
        // n is the max node
        if (n.right == null) {
            if (n.left != null) { // due to perfect black balance, if n has its left child, it must be red.
                n.left.color = n.color;
            }
            return n.left;
        }
        // ensure right child of n is not a 2-node
        // if n.right is 2-node
        // need to check if right link is red because rotateRight may produce right leaning link
        if (!isRed(n.right.left) && !isRed(n.right)) {
            // n.left must not be null due to perfect black balance
            // n is a 3-node -> rotate right
            if (isRed(n.left)) {
                n = rotateRight(n);
            }
            // n is a 2-node (both n's children is BLACK)
            else {
                // case 1: both n and its children are 2-node -> flip to form a temporary 4-node
                // case 2: n.left is a 3-node -> 'borrow' one from left -> flip first
                flipColors(n);
                if (isRed(n.left.left)) {
                    n = rotateRight(n);
                    flipColors(n);
                }
            }
        }
        n.right = deleteMax1(n.right);
        return balance(n);
    }

    /**
     * (book version)
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        assert check();
    }

    /**
     * (book version)
     * delete the key-value pair with the maximum key rooted at h
     */
    private Node deleteMax(Node h) {
        // rotate right even if right child is not a 2-node (h.right.left is RED)
        // won't cause unnecessary rotate, because it will inevitably rotate either
        //      in the next right node (ensure h.right.right is not a 2-node)
        //   or before return (h is the rightest node with a red left child)
        if (isRed(h.left))
            h = rotateRight(h);

        // due to previous adjustment, when h.right is null, h.left shall be null.
        // based on perfect black balance, as for subtree rooted at h, if h.right is null and h.left is not null, h.left must be RED
        // so if h.left is RED, h.left shall be rotated ahead
        if (h.right == null)
            return null;

        // h.right is a 2-node
        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    /**
     * (book version)
     * Assuming that h is red and both h.right and h.right.left
     * are black, make h.right or one of its children red.
     */
    private Node moveRedRight(Node h) {
        assert (h != null);
        assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) {
            // not similar to moveRedLeft: just rotate h.left to the root not h.left.left
            // principle:
            // 1.to maintain the perfect black balance, before rotate, the node that will be rotated to the top shall be red
            // 2.when borrow one from the other side, rotate the nearest node.
            //   (h.right.left is the nearest node to h on the right side, so does h.left on the left side)
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    /**
     * need to override because the root here is not the same one in its super class
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public V get(K key) {
        AutoCheck.keyNotNull(key, "get");
        return get(root, key);
    }

    // class Node's member left and right are not inherited from BST.Node
    private V get(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp == 0) return n.val;
        else if (cmp > 0) return get(n.right, key);
        else return get(n.left, key);
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

}
