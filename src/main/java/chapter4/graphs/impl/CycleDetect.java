package chapter4.graphs.impl;

import chapter4.graphs.api.Graph;

/**
 * 基于DFS实现 成环检测
 */
public class CycleDetect {

    private Graph graph;
    private boolean[] marked;
    private boolean hasCycle = false;

    public CycleDetect(Graph G) {
        this.graph = G;
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            //遍历所有连通子图 逐个检测是否成环 (初始节点无前驱节点 lastV初始化为-1)
            if (!marked[v] && dfsCycleDetect(v, -1)) {
                this.hasCycle = true;
                break;
            }
        }
    }

    /**
     * DFS成环检测： 在DFS过程中 遇到已经被标记遍历过的节点（非上一个节点） => 成环
     */
    private boolean dfsCycleDetect(int v, int lastV) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            //1.v的相邻节点已被DFS遍历 且 不是v的上一个节点lastV
            if (marked[w] && lastV != w) return true;
            //2.w未被遍历 且 w之后同一个连通子图其他顶点满足成环条件
            if (!marked[w] && dfsCycleDetect(w, v)) return true;
            //否则继续遍历 直到遍历所有连通子图节点
        }
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
