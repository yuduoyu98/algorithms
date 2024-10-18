package chapter4.graphs;

import chapter4.graphs.api.task.SPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;
import chapter4.graphs.impl.task.DAGLPT;
import chapter4.graphs.impl.task.DAGSPT;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * shortest path problem test
 */
@RunWith(Parameterized.class)
public class TestDAGSPT {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/44sp",
            "src/test/java/chapter4/graphs/data/",
            "tinyEWDAG.txt",
            null,
            null);

    private Class<? extends SPT> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                DAGSPT.class,
                DAGLPT.class,
        };
    }

    public TestDAGSPT(Class<? extends SPT> implClazz) {
        this.implClazz = implClazz;
    }


    /**
     * tinyEWGAG.txt 5
     * shortest path
     * 5 to 0 (0.73)  5->4  0.35   4->0  0.38
     * 5 to 1 (0.32)  5->1  0.32
     * 5 to 2 (0.62)  5->7  0.28   7->2  0.34
     * 5 to 3 (0.61)  5->1  0.32   1->3  0.29
     * 5 to 4 (0.35)  5->4  0.35
     * 5 to 5 (0.00)
     * 5 to 6 (1.13)  5->1  0.32   1->3  0.29   3->6  0.52
     * 5 to 7 (0.28)  5->7  0.28
     *
     * longest path
     * 5 to 0 (2.44)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93   4->0  0.38
     * 5 to 1 (0.32)  5->1  0.32
     * 5 to 2 (2.77)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93   4->7  0.37   7->2  0.34
     * 5 to 3 (0.61)  5->1  0.32   1->3  0.29
     * 5 to 4 (2.06)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93
     * 5 to 5 (0.00)
     * 5 to 6 (1.13)  5->1  0.32   1->3  0.29   3->6  0.52
     * 5 to 7 (2.43)  5->1  0.32   1->3  0.29   3->6  0.52   6->4  0.93   4->7  0.37
     */
    @Test
    public void test() {
        In in = testData.getIn(DataSize.TINY, true);
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);
        int start = 5;
        SPT impl = null;
        try {
            impl = implClazz.getDeclaredConstructor(EdgeWeightedDiGraph.class, int.class).newInstance(G, start);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Test Class: " + implClazz.getSimpleName());

        for (int terminal = 0; terminal < G.V(); terminal++) {
            StdOut.print(start + " to " + terminal);
            StdOut.printf(" (%4.2f): ", impl.distTo(terminal));
            if (impl.hasSP(terminal))
                for (WeightedDiEdge e : impl.SPto(terminal))
                    StdOut.print(e + " ");
            StdOut.println();
        }

        StdOut.println();
    }
}
