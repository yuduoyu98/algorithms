package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.CycleDetect;

import java.util.Arrays;

/**
 * direct cycle detection based on DFS
 *
 * comparison with undirected graph:
 * - no 'reversed edge'
 * - undirected graphs are naturally strongly connected(each v and w are mutual connected)
 *    - for undirected graphs, no matter which vertex you choose to start, you will visit the whole connected component in a single dfs
 *    - but for directed graphs not strongly connected, this may not hold true
 */
public class DirectedCycleDetect implements CycleDetect {

    private byte[] marked;
    private static final byte UNVISITED = 0;
    private static final byte IMPOSSIBLE = -1; // vertices that are not possible to form a cycle
    private static final byte VISITING = 1; // vertices that are visiting and possible to form a cycle
    private int[] edgeTo;
    private DirectedGraph dg;
    private boolean hasCycle;
    private Stack<Integer> cycle;

    public DirectedCycleDetect(DirectedGraph DG) {
        this.dg = DG;
        this.hasCycle = false;
        this.marked = new byte[dg.V()];
        this.edgeTo = new int[dg.V()];
        Arrays.fill(marked, UNVISITED);
        Arrays.fill(edgeTo, -1);
        for (int v = 0; v < dg.V(); v++) {
            // if there is a cycle, all the vertices shall be visited in a single dfs
            dfsCycleDetect(v);
            if (hasCycle) break;
        }
    }

    private void dfsCycleDetect(int v) {
        marked[v] = VISITING;
        for (int w : dg.adj(v)) {
            if (hasCycle) return;
            if (marked[w] == UNVISITED) {
                edgeTo[w] = v;
                dfsCycleDetect(w);
            }
            else if (marked[w] == VISITING) {
                hasCycle = true;
                cycle = new SimpleStack<>();
                cycle.push(w);
                for (int u = v; u != w; u = edgeTo[u])
                    cycle.push(u);
                return;
            }
        }
        /**
         * in case:
         * A->B  A->C->B
         * should not be detected as a cycle
         */
        marked[v] = IMPOSSIBLE; // if end up here, this branch of the dfs traversal tree won't form a cycle
    }

    @Override
    public boolean hasCycle() {
        return hasCycle;
    }

    @Override
    public Iterable<Integer> cycle() {
        return cycle;
    }
}
