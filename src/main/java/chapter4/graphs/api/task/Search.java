package chapter4.graphs.api.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter4.graphs.api.ds.Graph;

/**
 * there are a large number of graph-processing algorithms
 * choose to decouple the implementations from the graph representation
 */
public abstract class Search {

    public Graph graph;

    // start vertex
    public int start;

    public Search(Graph G, int s) {
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
    public boolean connectivity() {
        return count() == graph.V();
    }

    public Iterable<Integer> connectedVertexList() {
        Queue<Integer> queue = new SimpleQueue<>();
        for (int v = 0; v < graph.V(); v++) {
            if (marked(v)) queue.enqueue(v);
        }
        return queue;
    }
}
