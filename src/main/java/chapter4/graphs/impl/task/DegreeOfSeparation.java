package chapter4.graphs.impl.task;

import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.api.ds.SymbolGraph;
import edu.princeton.cs.algs4.StdOut;

/**
 * Degree of separation
 * in bipartite graph of movie-performer: degree of separation -> the movie count of the shortest path between 2 performers
 */
public class DegreeOfSeparation<G extends Graph> {

    private String start;
    private SymbolGraph<G> sg;
    private BreadthFirstPaths bfs;

    public DegreeOfSeparation(SymbolGraph<G> sg, String vertexName) {
        this.sg = sg;
        this.start = vertexName;
        this.bfs = new BreadthFirstPaths(sg.G(), sg.index(start));
    }

    public void printPathTo(String vertexName) {
        int index = sg.index(vertexName);
        if (bfs.hasPathTo(index))
            for (int v : bfs.pathTo(index))
                StdOut.println(" " + sg.name(v));
        else StdOut.println(start + " and " + vertexName + " are irrelevant");
    }
}
