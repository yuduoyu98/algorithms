package chapter4.graphs.api;

/**
 * 无向图API
 */
public interface Graph {

    /**
     * @return edge count of a graph
     */
    int E();

    /**
     * @return vertex count of a graph
     */
    int V();

    /**
     * for directed graph, represent all the vertices pointed to by the edges emanating from vertex v
     * if self-loop included, v itself should be included in the return iterable
     */
    Iterable<Integer> adj(int v);

    /**
     * add an edge
     */
    void addEdge(int v, int w);
}
