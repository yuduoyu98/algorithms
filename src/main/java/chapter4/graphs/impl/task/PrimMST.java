package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter2.sorting.pq.HeapBasedMinPriorityQueue;
import chapter2.sorting.pq.MinPriorityQueue;
import chapter4.graphs.api.task.MST;
import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedEdge;

/**
 * Prim's MST Algorithms
 *
 * idea
 * - start with any vertex as a single-vertex tree
 * - always taking the next minimum-weight crossing edge for the cut
 * - add V-1 edges to the tree
 *
 * implementation
 * - How do we (efficiently) find the crossing edge of minimal weight?
 */
public class PrimMST extends MST {

    private boolean[] marked; // vertices on the tree
    private MinPriorityQueue<WeightedEdge> minPQ; // possible crossing edges of the current tree
    private Queue<WeightedEdge> mst; // edges on the tree


    public PrimMST(EdgeWeightedGraph G) {
        super(G);
        marked = new boolean[g.V()];
        minPQ = new HeapBasedMinPriorityQueue<>(g.E());
        mst = new SimpleQueue<>();

        for (int v = 0; v < g.V(); v++)
            if (!marked[v])
                prim(v);
    }

    private void prim(int v) {
        if (mst.size() >= g.V() - 1) return; // mst has V-1 edge => finish
        if (!marked[v]) {
            marked[v] = true;
            for (WeightedEdge edge : g.adj(v))
                if (!marked[edge.other(v)]) // add crossing edge to the queue
                    minPQ.insert(edge);

            int x, y;
            WeightedEdge mstEdge;
            // find the minimum crossing edge
            do {
                mstEdge = minPQ.deleteMin();
                x = mstEdge.either();
                y = mstEdge.other(x);
                if (!marked[x] || !marked[y]) break; // edge x->y may not be the crossing edge now
            } while (!minPQ.isEmpty());

            mst.enqueue(mstEdge);
            weight += mstEdge.weight();
            if (marked[x]) prim(y);
            else prim(x);
        }
    }

    @Override
    public Iterable<WeightedEdge> edges() {
        return mst;
    }

}
