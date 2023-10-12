package chapter4.graphs;

import chapter4.graphs.api.Graph;
import chapter4.graphs.api.SymbolGraph;
import chapter4.graphs.impl.SymbolGraphForMovies;
import chapter4.graphs.impl.SymbolGraphForRoutes;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

/**
 * 符号表基本测试
 */
public class TestSymbolGraph {


    /**
     * 测试结果：
     * JFK
     * MCO
     * ATL
     * ORD
     * LAX
     * PHX
     * LAS
     */
    @Test
    public void routesTest() {
        SymbolGraph sg = new SymbolGraphForRoutes("src\\test\\java\\chapter4\\graphs\\data\\routes.txt", true);
        Graph G = sg.G();
        String v1 = "JFK";
        StdOut.println(v1);
        for (int w : G.adj(sg.index(v1))) {
            StdOut.println(" " + sg.name(w));
        }
        String v2 = "LAX";
        StdOut.println(v2);
        for (int w : G.adj(sg.index(v2))) {
            StdOut.println(" " + sg.name(w));
        }
    }

    @Test
    public void movieTest() {
        SymbolGraph sg = new SymbolGraphForMovies("https://algs4.cs.princeton.edu/41graph/movies.txt");
        Graph G = sg.G();
        String celebrity = "Bacon, Kevin";
        StdOut.println("演员 " + celebrity + " 饰演的电影：");
        for (int w : G.adj(sg.index(celebrity))) {
            StdOut.println(" " + sg.name(w));
        }
        StdOut.println();
        String movie = "Tin Men (1987)";
        StdOut.println("电影 《" + movie + "》 演员表：");
        for (int w : G.adj(sg.index(movie))) {
            StdOut.println(" " + sg.name(w));
        }
    }
}
