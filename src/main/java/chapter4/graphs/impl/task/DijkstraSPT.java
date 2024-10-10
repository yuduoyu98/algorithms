package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter2.sorting.pq.IndexedMinPQ;
import chapter2.sorting.pq.IndexedMinPQImpl;
import chapter4.graphs.api.task.SPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

import java.util.Arrays;

/**
 * Dijkstra’s algorithm solves
 * - single-source shortest-paths problem
 * - edge-weighted digraphs
 * - non-negative weights
 * idea
 * - edge relaxation
 *   - for directed edge v->w:
 *     - if s->..->v->w is shorter than s->..->w, then update
 *     - otherwise the edge is ineligible for SPT
 * - each time delete vertex v from PQ => certain that the shortest path to v has been found
 *   - assume that an unreached vertex w, w->v is shorter
 *   => dist[w] + e.weight < dist[v] => dist[w] <= dist[v] => w should be deleted from PQ earlier than v (contradiction)
 *
 * performance
 * - time: O(ElogV)
 */
public class DijkstraSPT extends SPT {

    private WeightedDiEdge[] edgeTo;
    private double[] distTo;
    private IndexedMinPQ<Double> minPQ;  // unreached vertex -> shortest dist
    private static final double UNREACHABLE = Double.POSITIVE_INFINITY;

    public DijkstraSPT(EdgeWeightedDiGraph G, int s) {
        super(G, s);
        this.edgeTo = new WeightedDiEdge[g.V()];
        this.distTo = new double[g.V()];
        Arrays.fill(distTo, UNREACHABLE);
        distTo[start] = 0.0;
        this.minPQ = new IndexedMinPQImpl<>(g.V());

        int v = start;
        do {
            for (WeightedDiEdge e : g.adj(v))
                relax(e);
            v = minPQ.delMin();
        } while (!minPQ.isEmpty());
    }

    /**
     * for edge v->w:
     * - w is already reached (w in PQ):
     *   - if v->w is a shorter path:
     *     - update PQ
     *     - update dist[] and edgeTo[]
     * - w is new (w not in PQ)
     *   - insert PQ
     *   - update dist[] and edgetTo[]
     * - otherwise
     *   - do nothing
     */
    private void relax(WeightedDiEdge edge) {
        int v = edge.from(), w = edge.to();
        if (distTo[w] <= distTo[v] + edge.weight()) return;
        distTo[w] = distTo[v] + edge.weight();
        edgeTo[w] = edge;
        if (minPQ.contains(w)) minPQ.change(w, distTo[w]);
        else minPQ.insert(w, distTo[w]);
    }

    @Override
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] != UNREACHABLE;
    }

    @Override
    public Iterable<WeightedDiEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<WeightedDiEdge> path = new SimpleStack<>();
        int u = v;
        while (u != start) {
            WeightedDiEdge edge = edgeTo[u];
            path.push(edge);
            u = edge.from();
        }
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    @SuppressWarnings("unused")
    private boolean check(EdgeWeightedDiGraph G, int s) {

        // check that edge weights are non-negative
        for (WeightedDiEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (WeightedDiEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            WeightedDiEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

}
