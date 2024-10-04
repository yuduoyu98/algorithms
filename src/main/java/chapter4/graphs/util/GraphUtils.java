package chapter4.graphs.util;

import chapter4.graphs.api.ds.Graph;

public class GraphUtils {

    /**
     * v's degree
     */
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (Integer w : G.adj(v))
            degree++;
        return degree;
    }

    /**
     * max degree of all vertex in a graph
     */
    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++)
            if (degree(G, v) > max)
                max = degree(G, v);
        return max;
    }

    /**
     * average degree of all vertex in a graph (self-loop 'one edge' counts 2 degrees to a vertex)
     */
    public static double avgDegree(Graph G) {
        // 1 edge bring 2 degrees
        return 2.0 * G.E() / G.V();
    }

    /**
     * self-loop count of a graph
     */
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++)
            for (Integer w : G.adj(v))
                if (w == v) count++;
        return count;
    }
}
