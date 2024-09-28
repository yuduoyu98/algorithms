package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Paths;
import chapter4.graphs.impl.*;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Paths API test
 */
@RunWith(Parameterized.class)
public class TestPaths {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyCG.txt",
            "mediumG.txt",
            "largeG.txt");

    private Class<? extends Paths> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                DepthFirstPaths.class,
                BreadthFirstPaths.class,
        };
    }

    public TestPaths(Class<? extends Paths> implClazz) {
        this.implClazz = implClazz;
    }

    /**
     * TinyG
     * 6 vertices, 8 edges
     * 0: 2 1 5
     * 1: 0 2
     * 2: 0 1 3 4
     * 3: 5 4 2
     * 4: 3 2
     * 5: 3 0
     * (BFS result should be the shortest path)
     */
    @Test
    public void pathTest() {
        StdOut.println("Test Class: " + implClazz.getSimpleName());
        pathTest(0, implClazz, true);
        StdOut.println();
    }

    private <P extends Paths> void pathTest(int start, Class<P> pathsClass, boolean useLocal) {
        In in = testData.getIn(DataSize.TINY, useLocal);
        Graph G = new AdjListUGraph(in);
        Paths pathsImpl;
        try {
            pathsImpl = pathsClass.getDeclaredConstructor(Graph.class, int.class).newInstance(G, start);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("build failure");
        }
        printAllPaths(G, start, pathsImpl);
    }

    private void printAllPaths(Graph G, int start, Paths pathsImpl) {
        for (int v = 0; v < G.V(); v++) {
            StdOut.print(start + " to " + v + ": ");
            if (pathsImpl.hasPathTo(v)) {
                for (int p : pathsImpl.pathTo(v)) {
                    if (p == start) {
                        StdOut.print(p);
                    }
                    else {
                        StdOut.print("-" + p);
                    }
                }
            }
            StdOut.println();
        }
    }
}
