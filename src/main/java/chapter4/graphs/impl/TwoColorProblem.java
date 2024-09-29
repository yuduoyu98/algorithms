package chapter4.graphs.impl;

import chapter4.graphs.api.Graph;

/**
 * Is the graph bipartite?
 * Can the vertices of a given graph be assigned one of two colors in such a way that no edge connects vertices of the same color?
 * based on DFS
 */
public class TwoColorProblem {

    private Graph graph;

    private static final byte UNVISITED = 0;
    //0:unvisited 1&-1:two colors
    private byte[] marked;

    private boolean twoColorable = true;

    public TwoColorProblem(Graph G) {
        this.graph = G;
        this.marked = new byte[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (marked[v] == UNVISITED)
                twoColorable = dfsDetect(v, (byte) 1);
            if (!twoColorable) break;
        }
    }

    /**
     * ensure that down to v, two-color problem is satisfied
     * if not when detecting adj of v return false immediately
     */
    private boolean dfsDetect(int v, byte flag) {
        marked[v] = flag;
        for (int w : graph.adj(v)) {
            if (marked[w] == UNVISITED) {
                boolean twoColorable = dfsDetect(w, (byte) -flag); // inversion of color
                if (!twoColorable) return false;
            }
            if (marked[w] == marked[v]) // non-bipartite
                return false;
        }
        return true;
    }

    public boolean isBipartite() {
        return this.twoColorable;
    }
}
