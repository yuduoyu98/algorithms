package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter4.graphs.impl.ds.Edge;
import chapter4.graphs.api.ds.UndirectedGraph;

import java.util.Arrays;

/**
 * bridge: an edge that, if removed, would separate a connected graph into two disjoint subgraphs
 * Find Bridges:
 * - how many bridges a graph have?
 * - find out those bridges

 * Algorithms: Tarjan's Algorithm
 * basic ideas:
 * - in a dfs traversal, current edge v->w is a bridge?
 *      - if and only if none of the vertices w and w's descendants has a back-edge to v or any of its ancestor
 *          - back-edge: edge that exclude w->v back to v or its ancestor
 * how to implement?
 * - how to represent order (v's ancestor) or dfs path(distinguish from the other way back)?
 *      - order array: order[v] (the order of the vertex v visited in a dfs traversal)
 * - how to represent the other way back?
 *      - lowest vertex back array: low[v] (the lowest order of vertices that could be reached through another back path)
 *      - low[v] is the minimum of: suppose back-edge(p->v) if exists
 *          - order[v]
 *          - order[p]  for all p that has a back-edge to v
 *          - low[w]    for all w that has a tree-edge from v (dfs traversal tree)

 * Links:
 * 1. https://cp-algorithms.com/graph/bridge-searching.html
 */
public class FindBridges {
    UndirectedGraph ug;
    private int cnt;        // counter of the order
    private int[] order;    // order[v]  the entry order of the vertex v visited in a dfs traversal
    private int[] low;      // low[v]  the lowest order(earliest entry time) of vertex v can reach via a back-edge from itself or its descendants in a dfs traversal

    private static final int UNVISITED = -1;

    private Queue<Edge> bridges;

    public FindBridges(UndirectedGraph UG) {
        this.ug = UG;
        low = new int[ug.V()];
        order = new int[ug.V()];
        bridges = new SimpleQueue<>();
        Arrays.fill(order, UNVISITED);
        Arrays.fill(low, UNVISITED);

        for (int v = 0; v < ug.V(); v++)
            if (order[v] == UNVISITED)
                dfs(ug, v, v);
    }

    /**
     * @param lastV v's parent (in DFS Tree)
     * @param v current visiting vertex
     */
    private void dfs(UndirectedGraph G, int lastV, int v) {
        order[v] = cnt++;
        low[v] = order[v];
        for (int w : G.adj(v)) {
            // if w is unvisited
            if (order[w] == UNVISITED) {
                // dfs(w) and find out whether there is another way back
                dfs(G, v, w);
                // update the lowest vertex v->w could reach
                low[v] = Math.min(low[v], low[w]);
                // check if v-w is a bridge
                if (order[v] < low[w]) // no back-edge to v or v's ancestor
                // if (low[w] == order[w]) // w has no back-edge to any w's ancestor (v is the direct ancestor to w)
                    bridges.enqueue(new Edge(v, w));
            }

            // if w is visited and ignore reverse of edge leading to v
            else if (w != lastV)
                // update back path if w is v's ancestor (maybe not)
                low[v] = Math.min(low[v], order[w]);
        }
    }

    public int bridgeCounts() {return bridges.size();}

    public boolean hasBridge() {return bridges.size() != 0;}

    public Iterable<Edge> bridges() {
        return bridges;
    }

}
