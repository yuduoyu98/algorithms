package chapter4.graphs.api.task;

import chapter4.graphs.api.ds.Graph;

/**
 * single-source paths problem
 */
public abstract class Paths {

    protected Graph graph;

    protected int start;

    public Paths(Graph G, int s) {
        graph = G;
        start = s;
    }

    public abstract boolean hasPathTo(int v);

    // path from s to v (null if no such path)
    public abstract Iterable<Integer> pathTo(int v);
}
