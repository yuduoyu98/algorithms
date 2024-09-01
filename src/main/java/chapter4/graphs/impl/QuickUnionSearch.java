package chapter4.graphs.impl;

import chapter1.fundamentals.impl.WeightQuickUnionUF;
import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Search;

/**
 * 使用加权quick-union实现Search API
 */
public class QuickUnionSearch extends Search {

    private WeightQuickUnionUF uf;

    /**
     * 初始化
     *
     * @param G 图
     * @param s 起点
     */
    public QuickUnionSearch(Graph G, int s) {
        super(G, s);
        WeightQuickUnionUF uf = new WeightQuickUnionUF(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int adjV : G.adj(v)) {
                //邻接表会重复表示边，这里只取其中一半
                if (adjV >= v) {
                    uf.union(v, adjV);
                }
            }
        }
        this.uf = uf;
    }

    @Override
    public boolean marked(int v) {
        return uf.connected(v, start);
    }

    @Override
    public int count() {
        return uf.connectedVertices(start);
    }
}
