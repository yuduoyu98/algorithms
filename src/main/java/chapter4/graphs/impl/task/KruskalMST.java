package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.UnionFound;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.WeightQuickUnionUF;
import chapter2.sorting.pq.MinPriorityQueue;
import chapter2.sorting.pq.MinPriorityQueueImpl;
import chapter4.graphs.api.task.MST;
import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedEdge;

/**
 * Kruskal’s Algorithm
 * idea:
 * - find the smallest weighted edge that do not form a cycle
 * - how to detect a cycle? union find
 *
 * performance:
 * - time: O(ElogE)
 *   - edge heap: O(ElogE)
 *   - union find: O(V) union + O(E) find at most -> does not contribute to the ElogE order of growth
 * - space: O(E)
 *
 * comparison:
 * - prim's algorithms finds a new edge to attach to a single growing tree at each step.
 * - kruskal's algorithms starts with a degenerate forest of V single-vertex trees and finds an edge that combines two trees at a time
 */
public class KruskalMST extends MST {
    private double weight;
    private Queue<WeightedEdge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        super(G);
        this.weight = 0.0;
        this.mst = new SimpleQueue<>();
        UnionFound uf = new WeightQuickUnionUF(G.V()); // cycle detection
        MinPriorityQueue<WeightedEdge> minPQ = new MinPriorityQueueImpl<>(g.E());

        // add all edges into PQ
        for (WeightedEdge edge : g.edges())
            minPQ.insert(edge);

        while (mst.size() < g.V() - 1) {
            WeightedEdge minEdge = minPQ.deleteMin();
            int v = minEdge.either();
            int w = minEdge.other(v);
            if (uf.connected(v, w)) continue; // v and w have already been connected, adding that edge will form  a cycle
            mst.enqueue(minEdge);
            weight += minEdge.weight();
            uf.union(v, w);
        }
    }

    @Override
    public Iterable<WeightedEdge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return weight;
    }
}
