package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.task.GeneralSPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

import java.util.Arrays;

/**
 * Bellman-Ford algorithms to solve general single source SPT problem
 *
 * application
 * - single source shortest path problem
 * - detect negative weighted cycle`
 *
 * idea
 * - Algorithm:
 *   - perform V-1 iterations of edge relaxation, where each iteration relaxes all edges without regard to order
 *   - if there is a change in distTo[] in iteration V, then there is no negative cycle.
 * - Proposition:
 *   - After n iterations, distTo[v] is the shortest path distance from s to v using at most n edges for all reachable vertices v.
 * - Proof (Induction)
 *   - Base Case: For k=0, only s is reachable, and distTo[s] = 0.
 *   - Inductive Step:
 *     - Assume true for k < i.
 *     - Assume shortest distance to vertex v in k steps => sp(k)[v]
 *     - In iteration i, distTo[v] = min{distTo[u] + w(u,v)|u ∈ adj(v)}
 *     - By the hypothesis, distTo[u] is the shortest path to u in i-1 edges.
 *     - distTo[v] = min{distTo[u] + w(u,v)|u ∈ adj(v)} = min{sp(i-1)[u] + w(u,v)|u ∈ adj(v)} = sp(i)[v] by definition
 * - whether we need to back up the result of the last iteration?
 *   - dist[v]=min(dist[v], backup[u]+w(u,v))
 *   - if we exactly want the result of iteration k to be the shortest path distance from s to v using at most n edges, then we should use dist backup
 *     - e.g.  s --> v --> w
 *       - in iteration 1, starting from s, assuming that s->v is relaxed earlier than v->w
 *         if we do not use backup, cascade update will occur:
 *         - distTo[w] will be updated to w(s,v)+w(v,w) but vertex w needs 2 edges to reach from s which > 1 (should be infinity)
 *       - if we use backup, distTo[w] will not be altered because backup[v] is infinity
 *   - but if we only care about the shortest path ultimately, it does not matter any more because it only accelerates the process
 *
 * performance
 * - worst case: O(EV)
 */
public class BellmanFordSPT extends GeneralSPT {

    private boolean relaxHappen; // whether the edge relaxation actually take effect in an iteration
    private boolean hasNegativeCycle;
    private Stack<WeightedDiEdge> negativeCycle;

    public BellmanFordSPT(EdgeWeightedDiGraph G, int s) {
        super(G, s);
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[start] = 0.0;
        hasNegativeCycle = false;
        negativeCycle = new SimpleStack<>();

        // V-1 iteration (at most)
        int i = 0;
        for (; i < g.V(); i++) {
            relaxHappen = false;
            // relax each edge not considering the order
            for (int v = 0; v < g.V(); v++)
                for (WeightedDiEdge e : g.adj(v))
                    ERelex(e);
            if (!relaxHappen) break; // convergence
        }
        // if already iterated V-1 times
        if (i == g.V()) {
            // iterate again to find whether there exists a negative cycle
            relaxHappen = false;
            negCycleDetect:
            for (int v = 0; v < g.V(); v++) {
                for (WeightedDiEdge e : g.adj(v)) {
                    ERelex(e);
                    // if there is a relaxation take effect -> negative cycle exists
                    if (relaxHappen) {
                        findNegativeCycle(e.to());
                        break negCycleDetect;
                    }
                }
            }
        }
    }

    /**
     * find a negative cycle and store it in the queue
     * @param v the fist vertex that distTo[v] changed after an edge relaxation
     *          when the first vertex is relaxed in iteration V, the negative loop edges will be traversed following edgeTo[]
     *          the distTo[] change of v must originate from a negative-weight loop
     */
    private void findNegativeCycle(int v) {
        hasNegativeCycle = true;
        SimpleStack<Integer> stack = new SimpleStack<>();
        int u = v;
        for (; !stack.contains(u); u = edgeTo[u].from())
            stack.push(u);
        int k = u;
        do {
            negativeCycle.push(edgeTo[k]);
            k = edgeTo[k].from();
        } while (k != u);
    }

    @Override
    protected void actionsAfterERelax(WeightedDiEdge e) {
        relaxHappen = true;
    }

    @Override
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    @Override
    public Iterable<WeightedDiEdge> negativeCycle() {
        return negativeCycle;
    }

}
