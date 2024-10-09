package chapter4.graphs.impl.ds;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

/**
 * edge weighted graph
 */
public class EdgeWeightedGraph {

    protected final int V;
    protected int E;
    protected Bag<WeightedEdge>[] adj; // can use Set if parallel edges are allowed

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        this.adj = (Bag<WeightedEdge>[]) new Bag[V];
        for (int v = 0; v < this.V; v++)
            this.adj[v] = new Bag<>();
    }

    public EdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniformInt(V);
            int w = StdRandom.uniformInt(V);
            double weight = 0.01 * StdRandom.uniformInt(0, 100);
            WeightedEdge e = new WeightedEdge(v, w, weight);
            addEdge(e);
        }
    }

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");

        try {
            V = in.readInt();
            adj = (Bag<WeightedEdge>[]) new Bag[V];
            for (int v = 0; v < V; v++) adj[v] = new Bag<>();

            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.readDouble();
                WeightedEdge e = new WeightedEdge(v, w, weight);
                addEdge(e);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e);
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public void addEdge(WeightedEdge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public int E() {
        return this.E;
    }

    public int V() {
        return this.V;
    }

    public Iterable<WeightedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<WeightedEdge> edges() {
        Bag<WeightedEdge> b = new Bag<>();
        for (int v = 0; v < V; v++)
            for (WeightedEdge e : adj[v])
                if (e.other(v) > v) b.add(e);
        return b;
    }

    /**
     * string representation of the graph’s adjacency lists
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(V() + " vertices, " + E() + " edges\n");
        for (int v = 0; v < V(); v++) {
            s.append(v).append(": ");
            for (WeightedEdge w : this.adj(v))
                s.append(w).append(" ");
            s.append("\n");
        }
        return s.toString();
    }


}
