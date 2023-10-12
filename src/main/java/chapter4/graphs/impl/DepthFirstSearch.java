package chapter4.graphs.impl;

import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Search;

import java.util.Arrays;

/**
 * 使用深度优先遍历实现Search API
 */
public class DepthFirstSearch extends Search {

    //遍历过的顶点
    private boolean[] marked;
    //与起点连通的顶点数
    private int count;

    /**
     * 初始化
     *
     * @param G 图
     * @param s 起点
     */
    public DepthFirstSearch(Graph G, int s) {
        super(G, s);
        marked = new boolean[G.V()];
        Arrays.fill(marked, false);
        dfs(start);
    }

    //递归方法
    private void dfs(int p) {
        //将其标记为已访问
        marked[p] = true;
        count++;
        for (int v : graph.adj(p)) {
            if (!marked(v)) {
                dfs(v);
            }
        }
    }

    @Override
    public boolean marked(int v) {
        return marked[v];
    }

    @Override
    public int count() {
        return count++;
    }
}
