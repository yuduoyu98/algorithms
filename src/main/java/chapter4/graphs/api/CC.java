package chapter4.graphs.api;

/**
 * Connected Components API
 */
public abstract class CC {

    protected Graph graph;

    public CC(Graph G){
        this.graph = G;
    }

    public abstract boolean connected(int v, int w);

    /**
     * number of connected component
     */
    public abstract int count();

    /**
     * connected component id (0 to count-1)
     */
    public abstract int id(int v);
}
