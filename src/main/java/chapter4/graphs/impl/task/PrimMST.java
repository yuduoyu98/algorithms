package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter2.sorting.pq.IndexedMinPQ;
import chapter2.sorting.pq.IndexedMinPQImpl;
import chapter4.graphs.api.task.MST;
import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedEdge;

import java.util.Arrays;

/**
 * Prim's MST Algorithms (eager version)
 * idea:
 * - eager: delete ineligible edges from the pq immediately
 * - for each non-tree vertex w adjacent to mst, maintain only one edge on the pq
 *
 * performance:
 * - time: O(ElogV)
 *   - bottleneck: pq insert() & deleteMin()
 * - space: O(V)
 */
public class PrimMST extends MST {
    private IndexedMinPQ<Double> minPQ; // key: non-tree vertex; value: weight of the crossing edge
    private double[] distTo; // dist[v]: dist from mst to v (tree vertices -> 0; non-tree vertices not connected to mst -> infinite)
    private WeightedEdge[] edgeTo; // edgeTo[v]: the mst edge or edge candidate(min weight) to v (edgeTo[start] is null)
    private boolean[] marked; // tree node or non-tree vertex

    public PrimMST(EdgeWeightedGraph G) {
        super(G);
        this.minPQ = new IndexedMinPQImpl<>(g.V());
        this.marked = new boolean[g.V()];
        this.distTo = new double[g.V()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        this.edgeTo = new WeightedEdge[g.V()];

        for (int v = 0; v < g.V(); v++)
            if (!marked[v])
                prim(v);
    }

    /**
     * run Prim's algorithm in g, starting from vertex s
     */
    private void prim(int s) {
        scan(s);
        do {
            int v = minPQ.delMin();
            scan(v);
        } while (!minPQ.isEmpty());
    }

    /**
     * scan vertex v
     * - marked v as tree node
     * - traverse v's adjacent edges
     *   - find a crossing edge (v->w, w is non-tree vertex)
     *     - new non-tree vertex: insert(w, weight)
     *     - old non-tree vertex: update if edge has less weight
     */
    private void scan(int v) {
        marked[v] = true;
        distTo[v] = 0.0;
        for (WeightedEdge edge : g.adj(v)) {
            int w = edge.other(v);
            if (marked[w]) continue; // w is tree vertex
            // w is non-tree vertex
            if (edge.weight() < distTo[w]) {
                edgeTo[w] = edge;
                distTo[w] = edge.weight();
                if (minPQ.contains(w)) minPQ.change(w, edge.weight());
                else minPQ.insert(w, edge.weight());
            }
        }
    }

    @Override
    public double weight() {
        double weight = 0.0;
        for (WeightedEdge e : edgeTo)
            if (e != null) weight += e.weight(); // edgeTo[start] is null
        return weight;
    }

    @Override
    public Iterable<WeightedEdge> edges() {
        Queue<WeightedEdge> mst = new SimpleQueue<>();
        for (WeightedEdge e : edgeTo)
            if (e != null) mst.enqueue(e);  // edgeTo[start] is null
        return mst;
    }
}
