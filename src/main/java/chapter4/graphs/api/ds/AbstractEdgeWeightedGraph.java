package chapter4.graphs.api.ds;

import chapter4.graphs.impl.ds.Edge;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

/**
 *
 */
public abstract class AbstractEdgeWeightedGraph<E extends Edge> {

    protected final int V;
    protected int E;
    protected Bag<E>[] adj; // can use Set if parallel edges are allowed

    @SuppressWarnings("unchecked")
    public AbstractEdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        this.adj = (Bag<E>[]) new Bag[V];
        for (int v = 0; v < this.V; v++)
            this.adj[v] = new Bag<>();
    }

    @SuppressWarnings("unused")
    public AbstractEdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniformInt(V);
            int w = StdRandom.uniformInt(V);
            double weight = 0.01 * StdRandom.uniformInt(0, 100);
            E e = newEdge(v, w, weight);
            addEdge(e);
        }
    }

    @SuppressWarnings("unchecked")
    public AbstractEdgeWeightedGraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");

        try {
            V = in.readInt();
            adj = (Bag<E>[]) new Bag[V];
            for (int v = 0; v < V; v++) adj[v] = new Bag<>();

            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.readDouble();
                E e = newEdge(v, w, weight);
                addEdge(e);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e);
        }
    }

    protected void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    protected abstract E newEdge(int v, int w, double weight);

    public abstract void addEdge(E edge);

    public int E() {
        return this.E;
    }

    public int V() {
        return this.V;
    }

    public Iterable<E> adj(int v) {
        return adj[v];
    }

    public abstract Iterable<E> edges();

    /**
     * string representation of the graph’s adjacency lists
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(V() + " vertices, " + E() + " edges\n");
        for (int v = 0; v < V(); v++) {
            s.append(v).append(": ");
            for (E e : this.adj(v))
                s.append(e).append(" ");
            s.append("\n");
        }
        return s.toString();
    }
}
