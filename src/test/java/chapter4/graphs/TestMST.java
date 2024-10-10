package chapter4.graphs;

import chapter4.graphs.api.task.MST;
import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedEdge;
import chapter4.graphs.impl.task.LazyPrimMST;
import chapter4.graphs.impl.task.PrimMST;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * MST API test
 */
@RunWith(Parameterized.class)
public class TestMST {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/43mst/",
            "src/test/java/chapter4/graphs/data/",
            "tinyEWG.txt",
            "mediumEWG.txt",
            "1000EWG.txt");

    private Class<? extends MST> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                LazyPrimMST.class,
                PrimMST.class,
        };
    }

    public TestMST(Class<? extends MST> implClazz) {
        this.implClazz = implClazz;
    }

    /**
     * tinyEWG.txt
     * 0-7 0.16
     * 1-7 0.19
     * 0-2 0.26
     * 2-3 0.17
     * 5-7 0.28
     * 4-5 0.35
     * 6-2 0.40
     * 1.81
     *
     * mediumEWG.txt
     *  0 225 0.02383
     *  49 225 0.03314
     *  44 49 0.02107
     *  44 204 0.01774
     *  49 97 0.03121
     * 202 204 0.04207
     * 176 202 0.04299
     * 176 191 0.02089
     *  68 176 0.04396
     *  58 68 0.04795
     * ... [239 more edges ]
     * 10.46351
     */
    @Test
    public void test() {
        In in = testData.getIn(DataSize.TINY, true);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        MST impl = null;
        try {
            impl = implClazz.getDeclaredConstructor(EdgeWeightedGraph.class).newInstance(G);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Test Class: " + implClazz.getSimpleName());

        for (WeightedEdge e : impl.edges())
            System.out.println(e);
        System.out.printf("MST weight: %.5f%n", impl.weight());

        StdOut.println();
    }

}
