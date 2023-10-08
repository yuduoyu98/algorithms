package chapter4.graphs.impl;

import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Search;

/**
 * 使用深度优先遍历实现Search API
 */
public class DepthFirstSearch extends Search {

    /**
     * 初始化
     *
     * @param G 图
     * @param s 起点
     */
    public DepthFirstSearch(Graph G, int s) {
        super(G, s);
    }

    @Override
    public boolean marked(int v) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }
}
