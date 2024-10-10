package chapter4.graphs.api.task;

import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

/**
 * single source shortest path reachability (shortest path tree)
 */
public abstract class SPT {

    protected EdgeWeightedDiGraph g;
    protected int start;

    public SPT(EdgeWeightedDiGraph G, int s) {
        this.g = G;
        this.start = s;
    }

    public abstract double distTo(int v);

    public abstract boolean hasPathTo(int v);

    public abstract Iterable<WeightedDiEdge> pathTo(int v);
}
