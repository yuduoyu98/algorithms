package chapter4.graphs.impl;

import chapter4.graphs.api.SymbolGraph;
import edu.princeton.cs.algs4.StdOut;

/**
 * 间隔度数问题: 在由movie.txt生成的二分图中找出一系列电影/演员来回溯到某个指定点
 */
public class DegreeOfSeparation {

    private String start;
    private SymbolGraph sg;
    private BreadthFirstPaths bfs;

    public DegreeOfSeparation(SymbolGraph sg, String celebrityOrMovie) {
        this.sg = sg;
        this.start = celebrityOrMovie;
        this.bfs = new BreadthFirstPaths(sg.G(), sg.index(start));
    }

    public void printPathTo(String celebrityOrMovie) {
        int index = sg.index(celebrityOrMovie);
        if (bfs.hasPathTo(index)) {
            for (int v : bfs.pathTo(index)) {
                StdOut.println(" " + sg.name(v));
            }
        } else {
            StdOut.println(start + " 与 " + celebrityOrMovie + " 不相关");
        }
    }
}
