package chapter4.graphs.impl;

import chapter4.graphs.api.Graph;

/**
 * 双色问题
 * 能够用两种颜色将图的所有顶点着色，使得任意一条边的两个端点的颜色都不相同吗?这个问题也等价于:这是一幅二分图吗?
 */
public class TwoColorProblem {

    private Graph graph;

    //0表示未访问 1表示颜色1 -1表示颜色2
    private byte[] marked;

    private boolean result = true;

    public TwoColorProblem(Graph G) {
        this.graph = G;
        this.marked = new byte[G.V()];
        for (int v = 0; v < G.V(); v++) {
            dfsDetect(v, (byte) 1);
            if (result) {
                break;
            }
        }
    }

    /**
     * DFS遍历全图 看是否有不满足双色问题的点出现：和相邻点marked值相同（都被标记过且标记相同）
     */
    private void dfsDetect(int v, byte flag) {
        marked[v] = flag;
        flag = (byte) -flag;
        for (int w : graph.adj(v)) {
            if (marked[w] == marked[v]) {
                //不满足双色问题
                result = false;
                break;
            }
            if (marked[w] != -1) {
                //如果没有被DFS遍历过
                dfsDetect(w, flag);
            }
        }
    }

    public boolean result() {
        return this.result;
    }
}
