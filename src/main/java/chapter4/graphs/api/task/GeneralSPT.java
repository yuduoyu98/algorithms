package chapter4.graphs.api.task;

import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

/**
 *
 */
public abstract class GeneralSPT extends SPT {
    public GeneralSPT(EdgeWeightedDiGraph G, int s) {
        super(G, s);
    }

    // Is there a negative cycle reachable from the source vertex s?
    public abstract boolean hasNegativeCycle();

    // Returns a negative cycle reachable from the source vertex s, or null if there is no such cycle.
    public abstract Iterable<WeightedDiEdge> negativeCycle();
}
