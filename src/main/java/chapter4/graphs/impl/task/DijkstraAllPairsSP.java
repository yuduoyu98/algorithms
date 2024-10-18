package chapter4.graphs.impl.task;

import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;


/**
 * shortest paths solution for all possible pairs of source and terminal
 * - time cost: O(EVlogV)
 */
public class DijkstraAllPairsSP {
    private DijkstraSPT[] source;

    public DijkstraAllPairsSP(EdgeWeightedDiGraph G) {
        source = new DijkstraSPT[G.V()];
        for (int v = 0; v < G.V(); v++)
            source[v] = new DijkstraSPT(G, v);
    }

    public Iterable<WeightedDiEdge> path(int s, int t) {return source[s].SPto(t);}

    public double dist(int s, int t) {return source[s].distTo(t);}

}