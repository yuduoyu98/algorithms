package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.impl.AdjListUGraph;
import chapter4.graphs.impl.TwoColorProblem;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

/**
 * 双色问题实现测试
 */
public class TestTwoColor {

    //满足双色问题
    private TestData data1 = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "twoColorG1.txt",
            null,
            null);

    //不满足双色问题
    private TestData data2 = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "twoColorG2.txt",
            null,
            null);

    //满足双色问题
    private Graph G1;
    //不满足双色问题
    private Graph G2;

    @Before
    public void generateGraph() {
        G1 = new AdjListUGraph(data1.getIn(DataSize.TINY, true));
        G2 = new AdjListUGraph(data2.getIn(DataSize.TINY, true));
    }

    @Test
    public void twoColorTest() {
        boolean result1 = new TwoColorProblem(G1).result();
        assert result1 : "双色问题检测错误：满足检测为不满足";
        StdOut.println("图1满足双色问题");
        boolean result2 = new TwoColorProblem(G2).result();
        assert !result2 : "双色问题检测错误：不满足检测为满足";
        StdOut.println("图2不满足双色问题");
    }
}
