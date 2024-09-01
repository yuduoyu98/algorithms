package chapter4.graphs.impl;

import chapter4.graphs.api.UndirectedGraph;
import edu.princeton.cs.algs4.In;

/**
 * 无向图的邻接表实现
 * 对于稀疏图性能表现好
 */
public class AdjListUGraph extends AbstractAdjListGraph implements UndirectedGraph {

    public AdjListUGraph(int V){
        super(V);
    }

    public AdjListUGraph(In in){
        super(in);
    }

    @Override
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

}
