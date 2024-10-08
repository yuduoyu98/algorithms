package chapter4.graphs;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.SCC;
import chapter4.graphs.impl.ds.AdjListDGraph;
import chapter4.graphs.impl.task.TarjanSCC;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * SCC API test
 */
@RunWith(Parameterized.class)
public class TestSCC {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/42digraph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyDG1.txt",
            "mediumDG.txt",
            "largeDG.txt");

    private Class<? extends SCC> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                TarjanSCC.class,
        };
    }

    public TestSCC(Class<? extends SCC> implClazz) {
        this.implClazz = implClazz;
    }

    /**
     * tinyDG.txt
     * 5 strongly connected components
     * 1
     * 0 2 3 4 5
     * 9 10 11 12
     * 6 8
     * 7
     *
     * tinyDG1.txt
     * 5 strongly connected components
     * 1
     * 0 2 3 4 5
     * 9 10 11 12
     * 6
     * 7 8
     *
     * mediumDG.txt
     * 10 strongly connected components
     * 21
     * 2 5 6 8 9 11 12 13 15 16 18 19 22 23 25 26 28 29 30 31 32 33 34 35 37 38 39 40 42 43 44 46 47 48 49
     * 14
     * 3 4 17 20 24 27 36
     * 41
     * 7
     * 45
     * 1
     * 0
     * 10
     */
    @Test
    @SuppressWarnings("unchecked")
    public void sccTest() {
//        In in = testData.getIn(DataSize.TINY, true);
        In in = testData.getIn(DataSize.MEDIUM, true);
        DirectedGraph dg = new AdjListDGraph(in);
        SCC scc = null;
        try {
            scc = implClazz.getConstructor(DirectedGraph.class).newInstance(dg);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Test Class: " + implClazz.getSimpleName());
        StdOut.println(scc.count() + " strongly connected components");
        Queue<Integer>[] components = (Queue<Integer>[]) new SimpleQueue[scc.count()];
        for (int i = 0; i < scc.count(); i++)
            components[i] = new SimpleQueue<>();
        for (int v = 0; v < dg.V(); v++)
            components[scc.id(v)].enqueue(v);

        for (int i = 0; i < scc.count(); i++)
            System.out.println(components[i]);

        System.out.println();
    }


}
