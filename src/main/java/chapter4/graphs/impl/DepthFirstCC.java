package chapter4.graphs.impl;

import chapter4.graphs.api.CC;
import chapter4.graphs.api.Graph;

import java.util.Arrays;

/**
 * DFS Based implementation of CC API
 */
public class DepthFirstCC extends CC {

    // if unvisited -> -1; otherwise the connected component id
    private int[] marked;
    // connected component count
    private int count;

    private static final int UNVISITED = -1;

    public DepthFirstCC(Graph G) {
        super(G);
        marked = new int[G.V()];
        Arrays.fill(marked, UNVISITED);
        count = 0;
        for (int v = 0; v < G.V(); v++) {
            if (marked[v] == UNVISITED) {
                dfs(v);
                count++;
            }
        }
    }

    private void dfs(int v) {
        marked[v] = count;
        for (int w : graph.adj(v)) {
            if (marked[w] == UNVISITED) {
                dfs(w);
            }
        }
    }

    @Override
    public boolean connected(int v, int w) {
        return marked[v] == marked[w];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int id(int v) {
        return marked[v];
    }
}
