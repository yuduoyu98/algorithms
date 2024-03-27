package chapter4.graphs.api;

/**
 * 连通分量 API
 */
public abstract class CC {

    protected Graph graph;

    public CC(Graph G){
        this.graph = G;
    }

    /**
     * v与w是否连通
     */
    public abstract boolean connected(int v, int w);

    /**
     * 连通分量数
     */
    public abstract int count();

    /**
     * 连通分量ID
     */
    public abstract int id(int v);
}
