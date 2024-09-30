package chapter4.graphs;

import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.api.task.Search;
import chapter4.graphs.impl.ds.AdjListUGraph;
import chapter4.graphs.impl.task.DepthFirstSearch;
import chapter4.graphs.impl.task.QuickUnionSearch;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Search API test
 */
@RunWith(Parameterized.class)
public class TestSearch {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyG.txt",
            "mediumG.txt",
            "largeG.txt");

    private Class<? extends Search> implClazz;

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                QuickUnionSearch.class,
                DepthFirstSearch.class,
        };
    }

    public TestSearch(Class<? extends Search> implClazz) {
        this.implClazz = implClazz;
    }

    @Test
    public void connectionTest() {
        StdOut.println("Test Class: " + implClazz.getSimpleName());
        //0 1 2 3 4 5 6 非连通图
        Search s1 = init(0, implClazz, true);
        assertTrue(s1.marked(1));
        assertFalse(s1.marked(9));
        assertFalse(s1.connectivity());

        //9 10 11 12 非连通图
        Search s2 = init(9, implClazz, true);
        assertTrue(s2.marked(10));
        assertFalse(s2.marked(4));
        assertFalse(s2.connectivity());
    }

    private <S extends Search> Search init(int start, Class<S> searchClass, boolean useLocal) {
        In in = testData.getIn(DataSize.TINY, useLocal);
        Graph G = new AdjListUGraph(in);
        Search searchImpl;
        try {
            searchImpl = searchClass.getDeclaredConstructor(Graph.class, int.class).newInstance(G, start);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("build graph failure");
        }
        return searchImpl;
    }
}
