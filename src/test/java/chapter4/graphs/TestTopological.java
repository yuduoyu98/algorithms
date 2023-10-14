package chapter4.graphs;

import chapter4.graphs.api.DirectedGraph;
import chapter4.graphs.api.SymbolGraph;
import chapter4.graphs.api.TopoLogical;
import chapter4.graphs.impl.AdjListDGraph;
import chapter4.graphs.impl.DFSTopoLogical;
import chapter4.graphs.impl.SymbolGraphForJobs;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * 拓扑排序测试
 */
public class TestTopological {

    private SymbolGraph<AdjListDGraph> sg;

    @Before
    public void generateGraph() {
        sg = new SymbolGraphForJobs("src/test/java/chapter4/graphs/data/jobs.txt");
    }

    @Test
    public void test() {
        testByImpl(DFSTopoLogical.class);
    }

    private <T extends TopoLogical> void testByImpl(Class<T> impl) {
        TopoLogical topo;
        try {
            topo = impl.getConstructor(DirectedGraph.class).newInstance(sg.G());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (!topo.isDAG()) {
            StdOut.print("not ");
        }
        StdOut.println("DAG");
        if (topo.isDAG()) {
            StdOut.println("拓扑排序：");
            for (int v : topo.order()) {
                StdOut.println(sg.name(v));
            }
        }
    }
}
