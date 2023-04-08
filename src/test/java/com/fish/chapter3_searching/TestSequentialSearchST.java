package com.fish.chapter3_searching;

import com.fish.chapter3_searching.impl.SequentialSearchST;
import org.junit.Test;

/**
 * @author Yuyi-YuShaoyu
 * @Description
 * @create 2023-04-03 16:43
 * @Modified By
 */
public class TestSequentialSearchST {

    @Test
    public void test(){
        SequentialSearchST<String, Integer> ST = new SequentialSearchST<>();
        ST.put("hello", 1);
        ST.keys().forEach(System.out::println);
        System.out.println("----------------------------------");
        ST.put("hello", 2);
        ST.keys().forEach(System.out::println);
        System.out.println("----------------------------------");
        ST.put("world", 2);
        ST.keys().forEach(System.out::println);
        System.out.println("----------------------------------");
        ST.put("java", 1);
        ST.keys().forEach(System.out::println);
        System.out.println("----------------------------------");
        ST.delete("world");
        ST.keys().forEach(System.out::println);
        System.out.println("----------------------------------");
        System.out.println(ST.contains("hello"));
        System.out.println(ST.contains("world"));
        System.out.println("----------------------------------");
        System.out.println(ST.size());
    }
}
