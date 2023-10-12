package chapter4.graphs.impl;

import chapter4.graphs.api.CC;
import chapter4.graphs.api.Graph;

import java.util.Arrays;

/**
 * DFS实现连通分量 CC API
 */
public class DepthFirstCC extends CC {

    //是否被DFS访问过，如果为-1则表示没有访问过，否则表示属于对应值id的连通分量
    private int[] marked;
    //连通分量数
    private int count;

    public DepthFirstCC(Graph G) {
        super(G);
        marked = new int[G.V()];
        Arrays.fill(marked, -1);
        count = 0;
        for (int v = 0; v < G.V(); v++) {
            if (marked[v] == -1) {
                dfs(v);
                count++;
            }
        }
    }

    private void dfs(int v) {
        marked[v] = count;
        for (int w : graph.adj(v)) {
            //注意顶点不允许重复经过
            if (marked[w] == -1) {
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
