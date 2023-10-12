package chapter4.graphs;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.impl.SimpleBag;
import chapter4.graphs.api.CC;
import chapter4.graphs.api.Graph;
import chapter4.graphs.impl.AdjListUGraph;
import chapter4.graphs.impl.DepthFirstCC;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * CC API 的实现校验
 */
public class TestCC {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyG.txt",
            "mediumG.txt",
            "largeG.txt");

    @Test
    public void test() {
        /**
         * tinyG.txt 结果
         * 连通分量个数：3
         * 连通分量1: 6 5 4 3 2 1 0
         * 连通分量2: 8 7
         * 连通分量3: 12 11 10 9
         */
//        basicImplTest(DepthFirstPaths.class);
        basicImplTest(DepthFirstCC.class);
    }

    private <C extends CC> void basicImplTest(Class<C> implClass) {
        StdOut.println("Test Class: " + implClass.getSimpleName());
        testEntry(0, implClass, true);
        StdOut.println();
    }

    @SuppressWarnings("unchecked")
    private <C extends CC> void testEntry(int start, Class<C> implClass, boolean useLocal) {
        In in = testData.getIn(DataSize.TINY, useLocal);
        Graph G = new AdjListUGraph(in);
        CC impl;
        try {
            impl = implClass.getDeclaredConstructor(Graph.class).newInstance(G);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        StdOut.println("连通分量个数：" + impl.count());
        Bag<Integer>[] components = (Bag<Integer>[]) new Bag[impl.count()];
        for (int i = 0; i < impl.count(); i++) {
            components[i] = new SimpleBag<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[impl.id(v)].add(v);
        }
        for (int i = 0; i < impl.count(); i++) {
            StdOut.print("连通分量" + (i + 1) + ": ");
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
