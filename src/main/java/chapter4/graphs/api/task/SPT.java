package chapter4.graphs.api.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

/**
 * single source shortest path reachability (shortest path tree)
 */
public abstract class SPT {

    protected double[] distTo;
    protected WeightedDiEdge[] edgeTo;
    protected static final double UNREACHABLE = Double.POSITIVE_INFINITY;

    protected EdgeWeightedDiGraph g;
    protected int start;

    public SPT(EdgeWeightedDiGraph G, int s) {
        this.g = G;
        this.start = s;
        this.distTo = new double[g.V()];
        this.edgeTo = new WeightedDiEdge[g.V()];
    }

    // shortest distance between s and v
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    // s->v has shortest path?
    public boolean hasSP(int v) {
        validateVertex(v);
        return distTo[v] != UNREACHABLE;
    }

    // shortest path to v (null if not exist)
    public Iterable<WeightedDiEdge> SPto(int v) {
        validateVertex(v);
        if (!hasSP(v)) return null;
        Stack<WeightedDiEdge> edgesTo = new SimpleStack<>();
        for (; v != start; v = edgeTo[v].from()) {
            edgesTo.push(edgeTo[v]);
        }
        return edgesTo;
    }

    // relax vertex v
    protected void VRelax(int v) {
        validateVertex(v);
        for (WeightedDiEdge e : g.adj(v)) {
            // relax each edge v->w
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                actionsAfterVRelax(w);
            }
        }
    }

    // actions taken after a successful relaxation on vertex w
    protected void actionsAfterVRelax(int w) {
        // nothing to do
    }

    protected void ERelex(WeightedDiEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            actionsAfterERelax(e);
        }
    }

    // actions taken after a successful relaxation on edge e (v->w)
    protected void actionsAfterERelax(WeightedDiEdge e) {
        // nothing to do
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}
