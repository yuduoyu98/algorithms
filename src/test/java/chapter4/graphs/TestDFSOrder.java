package chapter4.graphs;

import chapter4.graphs.api.DirectedGraph;
import chapter4.graphs.impl.AdjListDGraph;
import chapter4.graphs.impl.DepthFirstOrder;
import common.DataSize;
import common.TestData;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * 有向图 DFS的顶点顺序
 */
public class TestDFSOrder {

    private TestData data = new TestData(
            null,
            "src/test/java/chapter4/graphs/data/",
            "tinyDAG.txt",
            null,
            null);

    @Test
    public void order() {
        DirectedGraph G = new AdjListDGraph(data.getIn(DataSize.TINY, true));
        DepthFirstOrder dfsOrder = new DepthFirstOrder(G);
        StdOut.print("前序： ");
        dfsOrder.pre().forEach(v -> StdOut.print(v + " "));
        StdOut.println();
        StdOut.print("后序： ");
        dfsOrder.post().forEach(v -> StdOut.print(v + " "));
        StdOut.println();
        StdOut.print("逆后序： ");
        dfsOrder.reversePost().forEach(v -> StdOut.print(v + " "));
    }
}
