package chapter4.graphs;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.api.ds.UndirectedGraph;
import chapter4.graphs.api.task.Search;
import chapter4.graphs.impl.ds.AdjListDGraph;
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Search API test
 */
@RunWith(Parameterized.class)
public class TestSearch {

    private TestData testDataUG = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyG.txt",
            "mediumG.txt",
            "largeG.txt");

    private TestData testDataDG = new TestData(
            "https://algs4.cs.princeton.edu/41graph/",
            "src/test/java/chapter4/graphs/data/",
            "tinyDG.txt",
            "mediumDG.txt",
            "largeDG.txt");

    private Map<Class<? extends Search>, Integer> typeMap;

    {
        typeMap = new HashMap<>();
        for (Class<? extends Search> implementation : implementations) {
            typeMap.put(implementation, implementationType(implementation));
        }
    }

    private static final int GENERAL = 0;
    private static final int UG_ONLY = 1;
    private static final int DG_ONLY = 2;

    private Class<? extends Search> implClazz;

    @SuppressWarnings("unchecked")
    private static final Class<? extends Search>[] implementations = new Class[]{
            QuickUnionSearch.class,
            DepthFirstSearch.class,
    };

    @Parameterized.Parameters
    public static Object[] implementations() {
        return implementations;
    }

    public TestSearch(Class<? extends Search> implClazz) {
        this.implClazz = implClazz;
    }

    @Test
    public void UGTest() {
        if (typeMap.get(implClazz) == DG_ONLY) return;

        StdOut.println("Test Class: " + implClazz.getSimpleName());
        boolean isDG = false;
        boolean useLocal = true;
        //0 1 2 3 4 5 6 非连通图
        Search s1 = init(0, implClazz, isDG, useLocal);
        printConnectedVertices(s1);
        assertTrue(s1.marked(1));
        assertFalse(s1.marked(9));
        assertFalse(s1.connectivity());

        //9 10 11 12 非连通图
        Search s2 = init(9, implClazz, isDG, useLocal);
        printConnectedVertices(s2);
        assertTrue(s2.marked(10));
        assertFalse(s2.marked(4));
        assertFalse(s2.connectivity());
    }

    @Test
    public void DGTest() {
        if (typeMap.get(implClazz) == UG_ONLY) return;

        StdOut.println("Test Class: " + implClazz.getSimpleName());
        boolean isDG = true;
        boolean useLocal = true;
        //0 1 2 3 4 5 非连通图
        Search s1 = init(0, implClazz, isDG, useLocal);
        printConnectedVertices(s1);
        assertTrue(s1.marked(2));
        assertFalse(s1.marked(11));
        assertFalse(s1.connectivity());

        // 0 1 2 3 4 5 9 10 11 12 非连通图
        Search s2 = init(9, implClazz, isDG, useLocal);
        printConnectedVertices(s2);
        assertTrue(s2.marked(11));
        assertTrue(s2.marked(1));
        assertFalse(s2.marked(6));
        assertFalse(s2.connectivity());
    }

    private void printConnectedVertices(Search search) {
        StringBuilder sb = new StringBuilder("connected vertices (start from " + search.start + "):");
        for (int v : search.connectedVertexList())
            sb.append(" ").append(v);
        System.out.println(sb);
    }

    private Graph initG(boolean isDG, boolean userLocal) {
        TestData testData = isDG ? testDataDG : testDataUG;
        In in = testData.getIn(DataSize.TINY, userLocal);
        return isDG ? new AdjListDGraph(in) : new AdjListUGraph(in);
    }

    private <S extends Search> Search init(int start, Class<S> searchClass, boolean isDG, boolean useLocal) {
        Graph G = initG(isDG, useLocal);
        Search searchImpl;
        try {
            searchImpl = searchClass.getDeclaredConstructor(Graph.class, int.class).newInstance(G, start);
        } catch (NoSuchMethodException e) {
            try {
                UndirectedGraph UG = (UndirectedGraph) G;
                searchImpl = searchClass.getDeclaredConstructor(UndirectedGraph.class, int.class).newInstance(UG, start);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                e.printStackTrace();
                throw new RuntimeException("Cannot create instance of " + searchClass.getName(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot create instance of " + searchClass.getName(), e);
        }
        return searchImpl;
    }

    private int implementationType(Class<?> searchClass) {
        try {
            searchClass.getDeclaredConstructor(UndirectedGraph.class, int.class);
            return UG_ONLY;
        } catch (NoSuchMethodException e) {
            // ignore
        }

        try {
            searchClass.getDeclaredConstructor(Graph.class, int.class);
            return GENERAL;
        } catch (NoSuchMethodException e) {
            // 忽略异常
        }

        try {
            // 检查有向图构造器
            searchClass.getDeclaredConstructor(DirectedGraph.class, int.class);
            return DG_ONLY;
        } catch (NoSuchMethodException e) {
            // 忽略异常
        }
        throw new RuntimeException("Constructor missing: " + searchClass);
    }

}
