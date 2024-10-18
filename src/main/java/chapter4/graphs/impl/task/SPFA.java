package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.task.GeneralSPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

import java.util.Arrays;

/**
 * SPFA Algorithms (shortest path faster algorithm)
 * - queue optimized Bellman-Ford algorithms to solve general single source SPT problem
 *   - general
 *     - negative weight edges
 *     - directed cycle
 *
 * optimization approach
 * - the only edges that could lead to a change in distTo[] => those edges that starting from vertices whose distTo[] has changed in the previous iteration
 *
 * negative cycle detect
 * - if a vertex v is relaxed V times then there must exist a negative weight cycle
 */
public class SPFA extends GeneralSPT {

    // vertices to be relaxed
    // distTo[] changed vertices could lead to a distTo[] change by vertex relaxation
    private Queue<Integer> changedV;
    private boolean[] onQ; // whether vertex is on changedV -> reduce relax operations
    private boolean hasNegativeCycle;
    private Stack<WeightedDiEdge> negativeCycle;

    public SPFA(EdgeWeightedDiGraph G, int s) {
        super(G, s);
        changedV = new SimpleQueue<>();
        onQ = new boolean[g.V()];
        hasNegativeCycle = false;
        negativeCycle = new SimpleStack<>();
        // relax count of the vertex
        int[] relaxCount = new int[g.V()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);

        distTo[start] = 0.0;
        changedV.enqueue(start);
        onQ[start] = true;
        while (!changedV.isEmpty()) {
            // only distance changed vertices may lead to distance changes
            int v = changedV.dequeue();
            onQ[v] = false;
            relaxCount[v] = relaxCount[v] + 1;
            if (relaxCount[v] >= g.V()) {
                findNegativeCycle(v);
                break;
            }
            VRelax(v);
        }
    }

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
    protected void actionsAfterVRelax(int w) {
        if (!onQ[w]) {
            changedV.enqueue(w);
            onQ[w] = true;
        }
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
