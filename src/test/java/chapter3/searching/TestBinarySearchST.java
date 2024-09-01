package chapter3.searching;

import chapter3.searching.official_impl.BinarySearchST;

public class TestBinarySearchST {
    public static void main(String[] args) {
        chapter3.searching.impl.BinarySearchST<String, Integer> BST1 = new chapter3.searching.impl.BinarySearchST<>(20);
        BinarySearchST<String, Integer> BST2 = new BinarySearchST<>();

        String[] splits = "S E A R C H E X A M P L E".split(" ");
        System.out.println(splits.length);
        for (int i = 0; i < splits.length; i++) {
            System.out.println(splits[i] + " " + i);
            BST1.put(splits[i], i);
            BST2.put(splits[i], i);
        }

        System.out.println("--------------------------------");
        BST1.keys().forEach(key -> System.out.println(key + " " + BST1.get(key)));
        System.out.println("--------------------------------");
        BST2.keys().forEach(key -> System.out.println(key + " " + BST2.get(key)));
    }
}
