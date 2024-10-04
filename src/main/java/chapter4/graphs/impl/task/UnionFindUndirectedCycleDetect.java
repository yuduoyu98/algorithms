package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.UnionFound;
import chapter1.fundamentals.impl.WeightQuickUnionUF;
import chapter4.graphs.impl.ds.AdjListUGraph;

/**
 * Undirected graph cycle detection based on union-find
 * idea:
 * - put each edge into the uf
 * - if both vertices of the edge are already in a same connected component, then there is a cycle
 * notes:
 * - uf not suitable for directed graph
 * - not convenient to provide the cycle path
 */
public class UnionFindUndirectedCycleDetect {

    private boolean hasCycle = false;

    public UnionFindUndirectedCycleDetect(AdjListUGraph G) {
        UnionFound uf = new WeightQuickUnionUF(G.V());
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                // deduplicate the edges of the adjacent list
                if (v < w) {
                    if (uf.find(v) == uf.find(w)) {
                        hasCycle = true;
                        break;
                    }
                    else uf.union(v, w);
                }
    }


    public boolean hasCycle() {
        return hasCycle;
    }

}
