package chapter4.graphs;

import chapter4.graphs.api.SymbolGraph;
import chapter4.graphs.impl.DegreeOfSeparation;
import chapter4.graphs.impl.SymbolGraphForMovies;
import chapter4.graphs.impl.SymbolGraphForRoutes;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * 间隔度数测试
 */
public class TestDegreeOfSeparation {

    @Test
    public void movieTest() {
        SymbolGraph sg = new SymbolGraphForMovies("https://algs4.cs.princeton.edu/41graph/movies.txt");
        String start = "Bacon, Kevin";
        DegreeOfSeparation dos = new DegreeOfSeparation(sg, start);
        String celeb1 = "Kidman, Nicole";
        StdOut.println(start + " 与 " + celeb1 + " 的关系：");
        dos.printPathTo(celeb1);
        String celeb2 = "Grant, Cary";
        StdOut.println(start + " 与 " + celeb2 + " 的关系：");
        dos.printPathTo(celeb2);
    }

    @Test
    public void routeTest() {
        SymbolGraph sg = new SymbolGraphForRoutes("src\\test\\java\\chapter4\\graphs\\data\\routes.txt", true);
        String start = "JFK";
        DegreeOfSeparation dos = new DegreeOfSeparation(sg, start);
        String end1 = "LAS";
        StdOut.println(start + " --> " + end1 + " 最短路径：");
        dos.printPathTo(end1);
        String end2 = "DFW";
        StdOut.println(start + " --> " + end2 + " 最短路径：");
        dos.printPathTo(end2);
    }
}
