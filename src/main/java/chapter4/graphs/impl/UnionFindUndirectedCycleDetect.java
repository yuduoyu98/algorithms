package chapter4.graphs.impl;

import chapter1.fundamentals.api.UnionFound;
import chapter1.fundamentals.impl.WeightQuickUnionUF;
import chapter4.graphs.api.Graph;

/**
 * 基于union-find实现 无向图成环检测
 * 基本思路：遍历所有边 依次添加到并查集中 如果某条边的两个顶点已经属于同一个连通分量 则表示成环
 */
public class UnionFindUndirectedCycleDetect {

    private boolean hasCycle = false;

    public UnionFindUndirectedCycleDetect(Graph G) {
        UnionFound uf = new WeightQuickUnionUF(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                //先对无向图邻接表产生的边进行去重 不考虑自环
                if (v < w) {
                    //v和w已经属于同一个连通分量 => 成环
                    if (uf.find(v) == uf.find(w)) {
                        hasCycle = true;
                        break;
                    } else {
                        uf.union(v, w);
                    }
                }
            }
        }
    }


    public boolean hasCycle() {
        return hasCycle;
    }

}
