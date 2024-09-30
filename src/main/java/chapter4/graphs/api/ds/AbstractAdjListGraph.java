package chapter4.graphs.api.ds;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Abstract implementation of graph based on adjacent list
 */
public abstract class AbstractAdjListGraph implements Graph {

    protected final int V;
    protected int E;
    protected Bag<Integer>[] adj; // can use Set if parallel edges are allowed

    /**
     * initial a graph with V vertices and 0 edge
     */
    @SuppressWarnings("unchecked")
    public AbstractAdjListGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < this.V; v++) {
            this.adj[v] = new Bag<>();
        }
    }

    /**
     * build a graph with 2E+2 input
     * 1. vertex count
     * 2. edge count
     * 3. every edge
     * @param in input stream
     */
    public AbstractAdjListGraph(In in) {
        //根据第一个参数初始化图
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    @Override
    public int E() {
        return this.E;
    }

    @Override
    public int V() {
        return this.V;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }


    /**
     * string representation of the graph’s adjacency lists
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(V() + " vertices, " + E() + " edges\n");
        for (int v = 0; v < V(); v++) {
            s.append(v).append(": ");
            for (int w : this.adj(v))
                s.append(w).append(" ");
            s.append("\n");
        }
        return s.toString();
    }

}
