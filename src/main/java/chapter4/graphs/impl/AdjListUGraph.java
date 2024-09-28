package chapter4.graphs.impl;

import chapter4.graphs.api.AbstractAdjListGraph;
import chapter4.graphs.api.UndirectedGraph;
import edu.princeton.cs.algs4.In;

/**
 * undirected graph based on adjacent list
 * - adjacent list provides a good performance for sparse graph
 */
public class AdjListUGraph extends AbstractAdjListGraph implements UndirectedGraph {

    public AdjListUGraph(int V) {
        super(V);
    }

    public AdjListUGraph(In in) {
        super(in);
    }

    @Override
    public void addEdge(int v, int w) {
        adj[v].add(w);
        // do not add self loop twice
        if (v != w) adj[w].add(v);
        E++;
    }

}
