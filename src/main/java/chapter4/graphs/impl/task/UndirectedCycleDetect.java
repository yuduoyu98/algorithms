package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.UndirectedGraph;
import chapter4.graphs.api.task.CycleDetect;

import java.util.Arrays;

/**
 * Undirected graph cycle detection based on DFS
 * assume no self-loops or parallel edges
 * unlike directed graph, A-B-A is not considered as cycle
 */
public class UndirectedCycleDetect implements CycleDetect {

    private UndirectedGraph ug;
    private boolean[] marked;
    private int[] edgeTo;
    private boolean hasCycle = false;
    private Stack<Integer> cycle;

    public UndirectedCycleDetect(UndirectedGraph UG) {
        this.ug = UG;
        this.marked = new boolean[ug.V()];
        Arrays.fill(marked, false);
        this.edgeTo = new int[ug.V()];
        Arrays.fill(edgeTo, -1);
        for (int v = 0; v < ug.V(); v++) {
            if (!marked[v]) {
                // if the graph has a cycle, all the vertices should be visited in a single DFS
                dfsCycleDetect(v, -1);
                if (hasCycle) break;
            }
        }
    }

    /**
     * @param lastV v's predecessor vertex
     * A-B-A should be excluded in undirected graph
     */
    private void dfsCycleDetect(int v, int lastV) {
        marked[v] = true;
        for (int w : ug.adj(v)) {
            if (hasCycle) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfsCycleDetect(w, v);
            }
            else if (w != lastV) { // not reversed edge
                hasCycle = true;
                cycle = new SimpleStack<>();
                cycle.push(w);
                for (int u = v; u != w; u = edgeTo[u])
                    cycle.push(u);
                return;
            }
        }
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
