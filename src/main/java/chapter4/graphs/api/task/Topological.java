package chapter4.graphs.api.task;

import chapter4.graphs.api.ds.DirectedGraph;

/**
 * Topological API
 * A digraph has a topological order if and only if it is a DAG
 */
public abstract class Topological {

    protected DirectedGraph dg;
    protected Iterable<Integer> topoOrder;

    public Topological(DirectedGraph DG){
        dg = DG;
    }

    public boolean isDAG(){
        return topoOrder != null;
    }

    public Iterable<Integer> order(){
        return topoOrder;
    }
}
