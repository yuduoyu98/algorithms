package chapter4.graphs;

import chapter4.graphs.api.task.SPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;
import chapter4.graphs.impl.task.DijkstraSPT;
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
public class TestSPT {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/44sp",
            "src/test/java/chapter4/graphs/data/",
            "tinyEWD.txt",
            "mediumEWD.txt",
            "1000EWG.txt ");

    private Class<? extends SPT> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                DijkstraSPT.class,
        };
    }

    public TestSPT(Class<? extends SPT> implClazz) {
        this.implClazz = implClazz;
    }


    /**
     * tinyEWG.txt 0
     * 0 to 0 (0.00):
     * 0 to 1 (1.05): 0->4 0.38 4->5 0.35 5->1 0.32
     * 0 to 2 (0.26): 0->2 0.26
     * 0 to 3 (0.99): 0->2 0.26 2->7 0.34 7->3 0.39
     * 0 to 4 (0.38): 0->4 0.38
     * 0 to 5 (0.73): 0->4 0.38 4->5 0.35
     * 0 to 6 (1.51): 0->2 0.26 2->7 0.34 7->3 0.39 3->6 0.52
     * 0 to 7 (0.60): 0->2 0.26 2->7 0.34
     *
     * mediumEWD.txt 0
     * 0 to 0 (0.00)
     * 0 to 1 (0.71)  0->44  0.06   44->93  0.07   ...  107->1  0.07
     * 0 to 2 (0.65)  0->44  0.06   44->231  0.10  ...  42->2  0.11
     * 0 to 3 (0.46)  0->97  0.08   97->248  0.09  ...  45->3  0.12
     * 0 to 4 (0.42)  0->44  0.06   44->93  0.07   ...  77->4  0.11
     */
    @Test
    public void test() {
        In in = testData.getIn(DataSize.TINY, true);
//        In in = testData.getIn(DataSize.MEDIUM, true);
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);
        int start = 0;
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
