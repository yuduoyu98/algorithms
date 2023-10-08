package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Search;
import chapter4.graphs.impl.*;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * Search API 的实现校验
 */
public class TestSearch {

    private TestData testData = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyG.txt",
            "mediumG.txt",
            "largeG.txt");

    @Test
    public void test() {
        basicImplTest(QuickUnionSearch.class);
        basicImplTest(DepthFirstSearch.class);
    }

    private <S extends Search> void basicImplTest(Class<S> searchClass) {
        StdOut.println("Test Class: " + searchClass.getSimpleName());
        //0 1 2 3 4 5 6 非连通图
        testEntry(0, searchClass, true);
        //9 10 11 12 非连通图
        testEntry(9, searchClass, true);
        StdOut.println();
    }

    private <S extends Search> void testEntry(int start, Class<S> searchClass, boolean useLocal) {
        In in = testData.getIn(DataSize.TINY, useLocal);
        Graph G = new AdjListUGraph(in);
        Search searchImpl;
        try {
            searchImpl = searchClass.getDeclaredConstructor(Graph.class, int.class).newInstance(G, start);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (int v = 0; v < G.V(); v++) {
            //与起点相连的顶点
            if (searchImpl.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
        //是否为连通图
        if (searchImpl.count() != G.V()) {
            StdOut.print("非");
        }
        StdOut.println("连通图");
    }
}
