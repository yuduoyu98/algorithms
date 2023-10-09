package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Paths;
import chapter4.graphs.impl.*;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * Paths API 的实现校验
 */
public class TestPaths {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyCG.txt",
            "mediumG.txt",
            "largeG.txt");

    @Test
    public void test() {
        /**
         * tinyCG.txt 结果
         * 0 to 0: 0
         * 0 to 1: 0-2-1
         * 0 to 2: 0-2
         * 0 to 3: 0-2-3
         * 0 to 4: 0-2-3-4
         * 0 to 5: 0-2-3-5
         */
        basicImplTest(DepthFirstPaths.class);
    }

    private <P extends Paths> void basicImplTest(Class<P> pathsClass) {
        StdOut.println("Test Class: " + pathsClass.getSimpleName());
        testEntry(0, pathsClass, true);
        StdOut.println();
    }

    private <P extends Paths> void testEntry(int start, Class<P> pathsClass, boolean useLocal) {
        In in = testData.getIn(DataSize.TINY, useLocal);
        Graph G = new AdjListUGraph(in);
        Paths pathsImpl;
        try {
            pathsImpl = pathsClass.getDeclaredConstructor(Graph.class, int.class).newInstance(G, start);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (int v = 0; v < G.V(); v++) {
            StdOut.print(start + " to " + v + ": ");
            if (pathsImpl.hasPathTo(v)) {
                for (int p : pathsImpl.pathTo(v)) {
                    if (p == start) {
                        StdOut.print(p);
                    } else {
                        StdOut.print("-" + p);
                    }
                }
            }
            StdOut.println();
        }
    }
}
