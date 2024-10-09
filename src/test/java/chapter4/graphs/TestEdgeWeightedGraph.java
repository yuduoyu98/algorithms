package chapter4.graphs;

import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedEdge;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

/**
 * {@link EdgeWeightedGraph} test
 */
public class TestEdgeWeightedGraph {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/43mst/",
            "src/test/java/chapter4/graphs/data/",
            "tinyEWG.txt",
            "mediumEWG.txt",
            "1000EWG.txt");

    @Test
    public void test() {
        In in = testData.getIn(DataSize.TINY, true);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(in);
        System.out.println(graph);
        System.out.println("edges: ");
        for (WeightedEdge edge : graph.edges())
            System.out.println(edge);
    }
}
