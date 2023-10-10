package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.impl.AdjListUGraph;
import chapter4.graphs.impl.CycleDetect;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

/**
 * 成环检测代码校验
 */
public class TestCycleDetect {

    private TestData cycleData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "cycleG1.txt",
            null,
            null);

    private TestData noCycleData = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "cycleG2.txt",
            null,
            null);

    private Graph cycleG;
    private Graph noCycleG;

    @Before
    public void generateGraph() {
        cycleG = new AdjListUGraph(cycleData.getIn(DataSize.TINY, true));
        noCycleG = new AdjListUGraph(noCycleData.getIn(DataSize.TINY, true));
    }

    @Test
    public void cycleTest() {
        boolean cycleResult = new CycleDetect(cycleG).hasCycle();
        assert cycleResult : "有环图误测为无环图";
        StdOut.println("有环图检测通过");
        boolean noCycleResult = new CycleDetect(noCycleG).hasCycle();
        assert !noCycleResult : "无环图误测为有环图";
        StdOut.println("无环图检测通过");
    }
}
