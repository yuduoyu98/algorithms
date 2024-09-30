package chapter4.graphs.impl.task;

import chapter1.fundamentals.impl.WeightQuickUnionUF;
import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.api.task.Search;
import chapter4.graphs.api.ds.UndirectedGraph;

/**
 * weighted quick-union to implement Search API
 * (undirected graph only)
 */
public class QuickUnionSearch extends Search {

    private WeightQuickUnionUF uf;

    public QuickUnionSearch(Graph G, int s) {
        super(G, s);
        if (!(G instanceof UndirectedGraph)) {
            throw new IllegalArgumentException("graphs not undirected are not allowed");
        }
        WeightQuickUnionUF uf = new WeightQuickUnionUF(G.V());
        // traverse a graph
        for (int v = 0; v < G.V(); v++)
            for (int adjV : G.adj(v))
                // undirected graph implemented by adjacent lists will duplicate the edge
                if (adjV >= v)
                    uf.union(v, adjV);
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
