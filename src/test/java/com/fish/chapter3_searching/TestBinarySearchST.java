package com.fish.chapter3_searching;

import com.fish.chapter3_searching.impl.BinarySearchST;

import java.util.function.Consumer;

public class TestBinarySearchST {
    public static void main(String[] args) {
        BinarySearchST<String, Integer> BST1 = new BinarySearchST<>(20);
        com.fish.chapter3_searching.official_impl.BinarySearchST<String, Integer> BST2 = new com.fish.chapter3_searching.official_impl.BinarySearchST<>();

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
