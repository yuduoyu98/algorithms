package chapter4.graphs.impl.ds;

import chapter4.graphs.api.ds.AbstractEdgeWeightedGraph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * edge weighted graph
 */
public class EdgeWeightedGraph extends AbstractEdgeWeightedGraph<WeightedEdge> {

    @SuppressWarnings("unused")
    public EdgeWeightedGraph(int V) {
        super(V);
    }

    @SuppressWarnings("unused")
    public EdgeWeightedGraph(int V, int E) {
        super(V, E);
    }

    public EdgeWeightedGraph(In in) {
        super(in);
    }

    @Override
    protected WeightedEdge newEdge(int v, int w, double weight) {
        return new WeightedEdge(v, w, weight);
    }

    @Override
    public void addEdge(WeightedEdge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    @Override
    public Iterable<WeightedEdge> edges() {
        Bag<WeightedEdge> b = new Bag<>();
        for (int v = 0; v < V; v++)
            for (WeightedEdge e : adj[v])
                if (e.other(v) > v) b.add(e);
        return b;
    }

}
