package chapter4.graphs;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.ds.SymbolGraph;
import chapter4.graphs.api.task.TopoLogical;
import chapter4.graphs.impl.ds.AdjListDGraph;
import chapter4.graphs.impl.task.DFSTopoLogical;
import chapter4.graphs.impl.task.KahnTopological;
import chapter4.graphs.impl.ds.SymbolGraphForJobs;
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
        testByImpl(KahnTopological.class);
    }

    private <T extends TopoLogical> void testByImpl(Class<T> impl) {
        StdOut.println("测试实现类： " + impl);
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
