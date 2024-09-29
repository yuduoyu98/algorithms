package chapter4.graphs;

import chapter4.graphs.api.SymbolGraph;
import chapter4.graphs.impl.*;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * degree of separation {@link DegreeOfSeparation} test
 */
public class TestDegreeOfSeparation {

    @Test
    public void movieTest() {
        SymbolGraph<AdjListUGraph> sg = new SymbolGraphForMovies("https://algs4.cs.princeton.edu/41graph/movies.txt");
        String start = "Bacon, Kevin";
        DegreeOfSeparation<AdjListUGraph> dos = new DegreeOfSeparation<>(sg, start);
        String celeb1 = "Kidman, Nicole";
        StdOut.println(start + " and " + celeb1 + " relation：");
        dos.printPathTo(celeb1);
        String celeb2 = "Grant, Cary";
        StdOut.println(start + " and " + celeb2 + " relation：");
        dos.printPathTo(celeb2);
    }

    @Test
    public void routeTest() {
        SymbolGraph<AdjListUGraph> sg = new SymbolGraphForRoutes("src\\test\\java\\chapter4\\graphs\\data\\routes.txt", true);
        String start = "JFK";
        DegreeOfSeparation<AdjListUGraph> dos = new DegreeOfSeparation<>(sg, start);
        String end1 = "LAS";
        StdOut.println(start + " --> " + end1 + " shortest path：");
        dos.printPathTo(end1);
        String end2 = "DFW";
        StdOut.println(start + " --> " + end2 + " shortest path：");
        dos.printPathTo(end2);
    }
}
