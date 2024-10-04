package chapter4.graphs.impl.ds;

import chapter4.graphs.api.ds.AbstractAdjListGraph;
import chapter4.graphs.api.ds.DirectedGraph;
import edu.princeton.cs.algs4.In;

/**
 * directed graph based on adjacent list
 */
public class AdjListDGraph extends AbstractAdjListGraph implements DirectedGraph {

    public AdjListDGraph(int V) {
        super(V);
    }

    public AdjListDGraph(In in) {
        super(in);
    }

    @Override
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    @Override
    public DirectedGraph reverse() {
        DirectedGraph diGraph = new AdjListDGraph(V);
        for (int v = 0; v < adj.length; v++)
            for (int w : adj[v])
                diGraph.addEdge(w, v);
        return diGraph;
    }

}
