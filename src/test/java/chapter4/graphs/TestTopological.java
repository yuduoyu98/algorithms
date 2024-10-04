package chapter4.graphs;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.ds.SymbolGraph;
import chapter4.graphs.api.task.Topological;
import chapter4.graphs.impl.ds.AdjListDGraph;
import chapter4.graphs.impl.task.DFSTopological;
import chapter4.graphs.impl.task.KahnTopological;
import chapter4.graphs.impl.ds.SymbolGraphForJobs;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;

/**
 * Topological sort test
 */
@RunWith(Parameterized.class)
public class TestTopological {

    private SymbolGraph<AdjListDGraph> sg;

    private Class<? extends Topological> implClazz;

    public TestTopological(Class<? extends Topological> implClazz) {
        this.implClazz = implClazz;
    }

    @Before
    public void generateGraph() {
        sg = new SymbolGraphForJobs("src/test/java/chapter4/graphs/data/jobs.txt");
    }

    @Parameterized.Parameters
    public static Object[] implementations() {
        return new Object[]{
                DFSTopological.class,
                KahnTopological.class
        };
    }

    @Test
    public void topoTest(){
        System.out.println("---------------------------------");
        StdOut.println("Test Class： " + implClazz);
        Topological topo;
        try {
            topo = implClazz.getConstructor(DirectedGraph.class).newInstance(sg.G());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        if (!topo.isDAG()) {
            StdOut.print("not ");
        }
        StdOut.println("DAG");
        if (topo.isDAG()) {
            StdOut.println("Topological sort: ");
            for (int v : topo.order()) {
                StdOut.println(sg.name(v));
            }
        }
    }

}
