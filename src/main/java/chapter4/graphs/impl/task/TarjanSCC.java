package chapter4.graphs.impl.task;

import chapter1.fundamentals.impl.SimpleStack;
import chapter2.sorting.ArrayTools;
import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.SCC;
import chapter4.graphs.impl.ds.AdjListDGraph;
import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.Arrays;

/**
 * Tarjan Algorithm
 *
 * idea
 * - prerequisite definition
 *   - four kinds of edges (e.g. v->w) in a dfs traversal tree (<a href="https://www.geeksforgeeks.org/tree-back-edge-and-cross-edges-in-dfs-of-graph/"><a/>)
 *     - tree edge (part of the dfs traversal tree)
 *       - dfs(w) has not invoked yet
 *     - forward edge (not part of the dfs traversal tree)
 *        - v is w's ancestor (dfs invoke time v earlier than w)
 *        - dfs(w) is invoked and returned
 *     - back edge (not part of the dfs traversal tree)
 *        - v is w's descendant (dfs invoke time v later than w)
 *        - dfs(w) is invoked but not returned yet
 *     - cross edge (not part of the dfs traversal tree)
 *        - v and w do not have any ancestor and a descendant relationship
 *        - dfs(w) is invoked and returned
 *   - proposition
 *     - all vertices of a scc shall be visited in a single dfs traversal no matter from which vertex it starts
 *     - back edge => cycle => scc
 *
 * - key point of implementation
 *   - stack maintenance
 *   - low[] maintenance
 *
 * performance
 * - time cost: 1 dfs traversal -> O(V+E) for adjacent list
 */
public class TarjanSCC extends SCC {

    private int[] scc; // vertex -> scc id
    private int count; // scc counter

    // dfs traversal order
    private int[] order; // vertex -> order
    /**
     * the lowest vertex (in order) can reach in a dfs traversal
     * - 'in a dfs traversal'
     *   - in graphs like: SCC1 (vertex 5's low will be 2 and the rest vertices of the scc will be 1)
     *   - the lowest vertex 5 can reach is 1 but in a dfs start from 1 it will be 2
     */
    public int[] low; // vertex -> ancestor vertex's order
    private int n; // order counter

    private SimpleStack<Integer> onStack; // vertices that are yet to be classified (into scc).

    private static final int UNVISITED = -1;

    public TarjanSCC(DirectedGraph DG) {
        super(DG);
        scc = new int[dg.V()];
        order = new int[dg.V()];
        low = new int[dg.V()];
        Arrays.fill(scc, -1);
        Arrays.fill(order, UNVISITED);
        Arrays.fill(low, UNVISITED);
        count = 0;
        n = 0;
        onStack = new SimpleStack<>();

        for (int v = 0; v < dg.V(); v++)
            if (order[v] == UNVISITED)
                dfs(v);
    }


    private void dfs(int v) {
        order[v] = n++;
        low[v] = order[v];
        onStack.push(v);
        for (int w : dg.adj(v)) {
            if (order[w] == UNVISITED) {
                dfs(w);
                low[v] = Math.min(low[v], low[w]);
            }
            else if (onStack.contains(w)) {
                low[v] = Math.min(low[v], low[w]);
            }
        }
        // no ancestor is strongly connected with v
        // -> root of the scc (earliest vertex in a scc)
        // -> all the vertices of the scc are in the stack
        // descendant vertices of other scc should have already been popped out of the stack
        if (order[v] == low[v]) {
            int u;
            while ((u = onStack.pop()) != v)
                scc[u] = count;
            scc[v] = count++;
        }
    }

    @Override
    public boolean stronglyConnected(int v, int w) {
        return scc[v] == scc[w];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int id(int v) {
        return scc[v];
    }

    public static void main(String[] args) {
        File file = new File("src/test/java/chapter4/graphs/data/tinyDG1.txt");
//        File file = new File("src/test/java/chapter4/graphs/data/MediumDG.txt");
//        File file = new File("src/test/java/chapter4/graphs/data/SCC1.txt");
        DirectedGraph dg = new AdjListDGraph(new In(file));
        TarjanSCC scc = new TarjanSCC(dg);
        ArrayTools.printIntArr(scc.order, "order[]", true);
        ArrayTools.printIntArr(scc.low, "low[]", true);
        ArrayTools.printIntArr(scc.scc, "scc[]", true);
    }
}
