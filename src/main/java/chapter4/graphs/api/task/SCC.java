package chapter4.graphs.api.task;

import chapter4.graphs.api.ds.DirectedGraph;

/**
 * Strongly connected components API
 */
public abstract class SCC {

    protected DirectedGraph dg;

    public SCC(DirectedGraph DG){
        this.dg = DG;
    }

    public abstract boolean stronglyConnected(int v, int w);

    // number of SCC
    public abstract int count();

    // SCC id for v
    public abstract int id(int v);
}
