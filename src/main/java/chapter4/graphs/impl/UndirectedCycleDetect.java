package chapter4.graphs.impl;

import chapter4.graphs.api.UndirectedGraph;

/**
 * Undirected graph cycle detection based on DFS
 * assume no self-loops or parallel edges
 * unlike directed graph, A-B-A is not considered as cycle
 */
public class UndirectedCycleDetect {

    private UndirectedGraph ug;
    private boolean[] marked;
    private boolean hasCycle = false;

    public UndirectedCycleDetect(UndirectedGraph G) {
        this.ug = G;
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v] && dfsCycleDetect(v, -1)) {
                this.hasCycle = true;
                break;
            }
        }
    }

    /**
     * @param lastV v's predecessor vertex
     * A-B-A should be excluded
     */
    private boolean dfsCycleDetect(int v, int lastV) {
        marked[v] = true;
        for (int w : ug.adj(v)) {
            // has cycle
            if (marked[w] && lastV != w) return true;
            if (!marked[w] && dfsCycleDetect(w, v)) return true;
            // no cycle, carry on detection
        }
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
