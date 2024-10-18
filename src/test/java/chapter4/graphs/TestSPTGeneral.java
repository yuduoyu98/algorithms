package chapter4.graphs;

import chapter4.graphs.api.task.GeneralSPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;
import chapter4.graphs.impl.task.BellmanFordSPT;
import chapter4.graphs.impl.task.SPFA;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * general SPT test
 */
@RunWith(Parameterized.class)
public class TestSPTGeneral {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/code/algs4-data/",
            "src/test/java/chapter4/graphs/data/",
            "tinyEWDn.txt",
            "tinyEWDnc.txt",
            null);

    private Class<? extends GeneralSPT> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                BellmanFordSPT.class,
                SPFA.class,
        };
    }

    public TestSPTGeneral(Class<? extends GeneralSPT> implClazz) {
        this.implClazz = implClazz;
    }


    /**
     * tinyEWGn.txt 0
     * 0 to 0 ( 0.00)
     * 0 to 1 ( 0.93)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   4->5  0.35   5->1  0.32
     * 0 to 2 ( 0.26)  0->2  0.26
     * 0 to 3 ( 0.99)  0->2  0.26   2->7  0.34   7->3  0.39
     * 0 to 4 ( 0.26)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25
     * 0 to 5 ( 0.61)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52   6->4 -1.25   4->5  0.35
     * 0 to 6 ( 1.51)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52
     * 0 to 7 ( 0.60)  0->2  0.26   2->7  0.34
     */
    @Test
    public void test() {
        System.out.println("Test Class: " + implClazz.getSimpleName());

        In in1 = testData.getIn(DataSize.TINY, true); // no negative cycle
        In in2 = testData.getIn(DataSize.MEDIUM, true); // have negative cycle
        int start = 0;
        graphTest(implClazz, in1, start, false);
        graphTest(implClazz, in2, start, true);

        System.out.println("\n");
    }

    private void graphTest(Class<? extends GeneralSPT> implClazz, In in, int start, boolean hasNegativeFCycle) {
        if (hasNegativeFCycle) System.out.println("-------------------- negative weight cycle --------------------");
        else System.out.println("-------------------- no negative weight cycle --------------------");

        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);
        GeneralSPT impl = null;
        try {
            impl = implClazz.getDeclaredConstructor(EdgeWeightedDiGraph.class, int.class).newInstance(G, start);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        if (hasNegativeFCycle) assertTrue("negative weight cycle error detection", impl.hasNegativeCycle());
        else assertFalse("negative weight cycle error detection", impl.hasNegativeCycle());
        // print negative cycle if exists
        if (impl.hasNegativeCycle()) {
            System.out.println("Negative Weight Cycle!");
            for (WeightedDiEdge e : impl.negativeCycle())
                System.out.print(e + " ");
        }

        // print shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                if (impl.hasSP(v)) {
                    System.out.printf("%d to %d (%.2f)  ", start, v, impl.distTo(v));
                    for (WeightedDiEdge e : impl.SPto(v)) {
                        System.out.print(e + "   ");
                    }
                    System.out.println();
                }
                else System.out.printf("%d to %d           no path\n", start, v);
            }
        }
    }
}
