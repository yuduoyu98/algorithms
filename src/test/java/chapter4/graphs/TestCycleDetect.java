package chapter4.graphs;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.CycleDetect;
import chapter4.graphs.impl.ds.AdjListDGraph;
import chapter4.graphs.impl.ds.AdjListUGraph;
import chapter4.graphs.impl.task.*;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

/**
 * cycle detection test
 * {@link UndirectedCycleDetect} for undirected cycle detection
 * {@link DirectedCycleDetect} for directed cycle detection
 */
public class TestCycleDetect {

    // undirected graph with cycle
    private TestData cycleUGData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "cycleG1.txt",
            null,
            null);

    // undirected graph with no cycle
    private TestData noCycleUGData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "cycleG2.txt",
            null,
            null);

    // directed graph with cycle
    private TestData cycleDGData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "tinyDG.txt",
            null,
            null);

    // directed graph with cycle
    private TestData noCycleDGData2 = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "tinyDG2.txt",
            null,
            null);

    private AdjListUGraph cycleUG;
    private AdjListUGraph noCycleUG;
    private DirectedGraph cycleDG;
    private DirectedGraph cycleDG2;

    @Before
    public void generateGraph() {
        cycleUG = new AdjListUGraph(cycleUGData.getIn(DataSize.TINY, true));
        noCycleUG = new AdjListUGraph(noCycleUGData.getIn(DataSize.TINY, true));
        cycleDG = new AdjListDGraph(cycleDGData.getIn(DataSize.TINY, true));
        cycleDG2 = new AdjListDGraph(noCycleDGData2.getIn(DataSize.TINY, true));
    }

    @Test
    public void UGCycleTest() {
        StdOut.println("------------- undirected graph cycle detection(dfs) -------------");
        CycleDetect undirectedCycleDetect = new UndirectedCycleDetect(cycleUG);
        boolean cycleResult = undirectedCycleDetect.hasCycle();
        undirectedCycleDetect.printCycle();
        assert cycleResult : "error detection";
        boolean noCycleResult = new UndirectedCycleDetect(noCycleUG).hasCycle();
        assert !noCycleResult : "error detection";
        System.out.println("test passes");
    }

    @Test
    public void UnionFindUGCycleTest() {
        StdOut.println("------------- undirected graph cycle detection(union-find) -------------");
        boolean cycleResult = new UnionFindUndirectedCycleDetect(cycleUG).hasCycle();
        assert cycleResult : "error detection";
        boolean noCycleResult = new UnionFindUndirectedCycleDetect(noCycleUG).hasCycle();
        assert !noCycleResult : "error detection";
        System.out.println("test passes");
    }

    @Test
    public void DGCycleTest() {
        StdOut.println("------------- directed graph cycle detection(dfs) -------------");
        CycleDetect cycleDetect = new DirectedCycleDetect(cycleDG);
        boolean cycleResult = cycleDetect.hasCycle();
        assert cycleResult : "error detection";
        cycleDetect.printCycle();
        CycleDetect cycleDetect2 = new DirectedCycleDetect(cycleDG2);
        boolean noCycleResult = cycleDetect2.hasCycle();
        assert !noCycleResult : "error detection";
        StdOut.println("test passes");
    }
}
