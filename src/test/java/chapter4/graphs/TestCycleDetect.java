package chapter4.graphs;

import chapter4.graphs.api.DirectedGraph;
import chapter4.graphs.api.UndirectedGraph;
import chapter4.graphs.impl.*;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

/**
 * 成环检测代码校验
 */
public class TestCycleDetect {

    private TestData cycleUGData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "cycleG1.txt",
            null,
            null);

    private TestData noCycleUGData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "cycleG2.txt",
            null,
            null);

    private TestData cycleDGData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "tinyDG.txt",
            null,
            null);

    private TestData cycleDGData2 = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "tinyDG2.txt",
            null,
            null);

    private UndirectedGraph cycleUG;
    private UndirectedGraph noCycleUG;
    private DirectedGraph cycleDG;
    private DirectedGraph cycleDG2;

    @Before
    public void generateGraph() {
        cycleUG = new AdjListUGraph(cycleUGData.getIn(DataSize.TINY, true));
        noCycleUG = new AdjListUGraph(noCycleUGData.getIn(DataSize.TINY, true));
        cycleDG = new AdjListDGraph(cycleDGData.getIn(DataSize.TINY, true));
        cycleDG2 = new AdjListDGraph(cycleDGData2.getIn(DataSize.TINY, true));
    }

    @Test
    public void UGCycleTest() {
        StdOut.println("------------- 无向图成环检测 -------------");
        boolean cycleResult = new UndirectedCycleDetect(cycleUG).hasCycle();
        assert cycleResult : "有环图误测为无环图";
        StdOut.println("有环图检测通过");
        boolean noCycleResult = new UndirectedCycleDetect(noCycleUG).hasCycle();
        assert !noCycleResult : "无环图误测为有环图";
        StdOut.println("无环图检测通过");
    }

    @Test
    public void UnionFindUGCycleTest() {
        StdOut.println("------------- 无向图成环检测（并查集实现） -------------");
        boolean cycleResult = new UnionFindUndirectedCycleDetect(cycleUG).hasCycle();
        assert cycleResult : "有环图误测为无环图";
        StdOut.println("有环图检测通过");
        boolean noCycleResult = new UnionFindUndirectedCycleDetect(noCycleUG).hasCycle();
        assert !noCycleResult : "无环图误测为有环图";
        StdOut.println("无环图检测通过");
    }

    @Test
    public void DGCycleTest() {
        StdOut.println("------------- 有向图成环检测 -------------");
        DirectedCycleDetect cycleDetect = new DirectedCycleDetect(cycleDG);
        boolean cycleResult = cycleDetect.hasCycle();
        assert cycleResult : "有环图误测为无环图";
        StdOut.println("有环图检测通过");
        StdOut.print("环路：");
        for (int v : cycleDetect.cycle()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        DirectedCycleDetect cycleDetect2 = new DirectedCycleDetect(cycleDG2);
        boolean cycleResult2 = cycleDetect2.hasCycle();
        assert !cycleResult2 : "无环图误测为有环图";
        StdOut.println("无环图检测通过");
        StdOut.println();
    }

    @Test
    public void DG1CycleTest() {
        StdOut.println("------------- 有向图成环检测 -------------");
        DirectedCycleDetect1 cycleDetect = new DirectedCycleDetect1(cycleDG);
        boolean cycleResult = cycleDetect.hasCycle();
        assert cycleResult : "有环图误测为无环图";
        StdOut.println("有环图检测通过");
        StdOut.print("环路：");
        for (int v : cycleDetect.cycle()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        DirectedCycleDetect1 cycleDetect2 = new DirectedCycleDetect1(cycleDG2);
        boolean cycleResult2 = cycleDetect2.hasCycle();
        assert !cycleResult2 : "无环图误测为有环图";
        StdOut.println("无环图检测通过");
        StdOut.println();
    }
}
