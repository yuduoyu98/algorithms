package chapter4.graphs.api.ds;

/**
 * directed graph
 */
public interface DirectedGraph extends Graph {

    /**
     * generate a replica of the graph with all edge reversed
     * convenient for using the adj method to find all edges pointing to a certain vertex
     */
    DirectedGraph reverse();
}
