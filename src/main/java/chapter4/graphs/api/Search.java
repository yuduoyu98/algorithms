package chapter4.graphs.api;

/**
 * there are a large number of graph-processing algorithms
 * choose to decouple the implementations from the graph representation
 */
public abstract class Search {

    protected Graph graph;

    // start vertex
    protected int start;

    public Search(Graph G, int s){
        this.graph = G;
        this.start = s;
    }

    /**
     * does vertex v and s(start) connected
     */
    public abstract boolean marked(int v);

    /**
     * count of vertices that connected to s(start)
     */
    public abstract int count();

    /**
     * if count equals to vertices count => the graph is connected
     */
    public boolean connectivity(){
        return count() == graph.V();
    }
}
