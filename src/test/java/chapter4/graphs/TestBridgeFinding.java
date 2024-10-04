package chapter4.graphs;

import chapter4.graphs.api.ds.Edge;
import chapter4.graphs.api.ds.UndirectedGraph;
import chapter4.graphs.impl.ds.AdjListUGraph;
import chapter4.graphs.impl.task.FindBridges;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

/**
 *  {@link FindBridges} test
 */
public class TestBridgeFinding {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "edgeConnectivityG.txt",
            null,
            null);

    @Test
    public void bridgeTest() {
        FindBridges fb = initBridge();
        int count = fb.bridgeCounts();
        System.out.println("bridge count: " + count);
        if (fb.hasBridge()) {
            System.out.println("bridges: ");
            for (Edge edge : fb.bridges()) {
                System.out.println(edge);
            }
        }
    }

    private FindBridges initBridge() {
        In in = testData.getIn(DataSize.TINY, true);
        UndirectedGraph G = new AdjListUGraph(in);
        FindBridges impl;
        try {
            impl = FindBridges.class.getDeclaredConstructor(UndirectedGraph.class).newInstance(G);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("build failure");
        }
        return impl;
    }
}
