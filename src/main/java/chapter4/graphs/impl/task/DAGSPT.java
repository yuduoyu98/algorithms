package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.task.SPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

import java.util.Arrays;

/**
 * shortest path tree problem for directed acyclic graph
 *
 * performance optimization (compare to dijkstra algorithm)
 * - use topological order to relax the vertex
 *   - vertex relaxation: for all edges v->w, update dist[w] if dist[w] > dist[v] + e.weight
 * - time cost: linear O(E+V) [Dijkstra O(ElogV)]
 * - handles negative weight edges
 *
 * theory basis
 * - vertex relaxation based on topological sort sequence means
 *   - for each v, the predecessors of v have been relaxed
 *     => there won't exist an edge point to v which could affect dist[v]
 *        => the shortest path from start point to v is determined
 */
public class DAGSPT extends SPT {

    private double[] distTo;
    private WeightedDiEdge[] edgeTo;
    private boolean[] marked;
    Stack<Integer> reversePost;

    public DAGSPT(EdgeWeightedDiGraph G, int s) {
        super(G, s);
        this.distTo = new double[g.V()];
        this.marked = new boolean[g.V()];
        this.edgeTo = new WeightedDiEdge[g.V()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[start] = 0.0;
        reversePost = new SimpleStack<>();

        dfs(start);
        for (int v : reversePost)
            relax(v);
    }

    /**
     * vertex relaxation
     */
    private void relax(int v) {
        for (WeightedDiEdge e : g.adj(v)) {
            // relax each edge v->w
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }

    private void dfs(int v) {
        marked[v] = true;
        for (WeightedDiEdge e : g.adj(v)) {
            int w = e.to();
            if (!marked[w]) dfs(w);
        }
        reversePost.push(v);
    }

    @Override
    public double distTo(int v) {
        return distTo[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        return distTo[v] != Double.POSITIVE_INFINITY;
    }

    @Override
    public Iterable<WeightedDiEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<WeightedDiEdge> edgesTo = new SimpleStack<>();
        for (; v != start; v = edgeTo[v].from()) {
            edgesTo.push(edgeTo[v]);
        }
        return edgesTo;
    }

}
