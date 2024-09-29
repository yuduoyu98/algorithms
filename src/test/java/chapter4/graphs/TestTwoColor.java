package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.impl.AdjListUGraph;
import chapter4.graphs.impl.TwoColorProblem;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link TwoColorProblem} test
 */
public class TestTwoColor {

    // bipartite graph
    private TestData data1 = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "twoColorG1.txt",
            null,
            null);

    // non-bipartite graph
    private TestData data2 = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "twoColorG2.txt",
            null,
            null);

    // bipartite
    private Graph G1;
    // non-bipartite
    private Graph G2;

    @Before
    public void generateGraph() {
        G1 = new AdjListUGraph(data1.getIn(DataSize.TINY, true));
        G2 = new AdjListUGraph(data2.getIn(DataSize.TINY, true));
    }

    @Test
    public void twoColorTest() {
        boolean result1 = new TwoColorProblem(G1).isBipartite();
        assert result1 : "error detection G1";
        StdOut.println("G1 is bipartite");
        boolean result2 = new TwoColorProblem(G2).isBipartite();
        assert !result2 : "error detection G2";
        StdOut.println("G2 is non-bipartite");
    }
}
