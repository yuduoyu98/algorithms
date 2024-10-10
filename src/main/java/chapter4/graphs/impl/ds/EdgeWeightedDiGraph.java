package chapter4.graphs.impl.ds;

import chapter4.graphs.api.ds.AbstractEdgeWeightedGraph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * edge weighted directed graph
 */
public class EdgeWeightedDiGraph extends AbstractEdgeWeightedGraph<WeightedDiEdge> {

    @SuppressWarnings("unused")
    public EdgeWeightedDiGraph(int V) {
        super(V);
    }

    @SuppressWarnings("unused")
    public EdgeWeightedDiGraph(int V, int E) {
        super(V, E);
    }

    public EdgeWeightedDiGraph(In in) {
        super(in);
    }

    @Override
    protected WeightedDiEdge newEdge(int v, int w, double weight) {
        return new WeightedDiEdge(v, w, weight);
    }

    @Override
    public void addEdge(WeightedDiEdge e) {
        validateVertex(e.from());
        validateVertex(e.to());
        adj[e.from()].add(e);
        E++;
    }

    @Override
    public Iterable<WeightedDiEdge> edges() {
        Bag<WeightedDiEdge> b = new Bag<>();
        for (int v = 0; v < V; v++)
            for (WeightedDiEdge e : adj[v])
                b.add(e);
        return b;
    }

}
