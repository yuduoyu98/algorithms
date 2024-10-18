package chapter4.graphs.impl.task;

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
 * - non-negative weight edges
 *
 * idea
 * - edge relaxation
 *   - for directed edge v->w:
 *     - if s->..->v->w is shorter than s->..->w, then update
 *     - otherwise the edge is ineligible for SPT
 * - each time delete vertex v from PQ => certain that the shortest path to v has been found
 *   - assume that an unreached vertex w, w->v is shorter
 *   => dist[w] + e.weight < dist[v] => dist[w] <= dist[v] => w should be deleted from PQ earlier than v (contradiction)
 *
 * thinking
 * - why dijkstra does not suitable for negative weight edges?
 *   - dijkstra
 *
 * performance
 * - time: O(ElogV)
 */
public class DijkstraSPT extends SPT {
    private IndexedMinPQ<Double> minPQ;  // unreached vertex -> shortest dist

    public DijkstraSPT(EdgeWeightedDiGraph G, int s) {
        super(G, s);
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[start] = 0.0;
        this.minPQ = new IndexedMinPQImpl<>(g.V());

        int v = start;
        do {
            for (WeightedDiEdge e : g.adj(v))
                ERelex(e);
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
    @Override
    protected void actionsAfterERelax(WeightedDiEdge e) {
        int w = e.to();
        if (minPQ.contains(w)) minPQ.change(w, distTo[w]);
        else minPQ.insert(w, distTo[w]);
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
