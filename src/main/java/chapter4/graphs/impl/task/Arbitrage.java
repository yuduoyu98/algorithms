package chapter4.graphs.impl.task;

import chapter4.graphs.api.task.GeneralSPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;
import edu.princeton.cs.algs4.In;

import java.io.File;

/******************************************************************************
 *  Arbitrage detection.
 *  - what is arbitrage?
 *    - e.g. 1000 USD -> 741 EUR -> 1,012.206 CAD -> 1,007.14497 USD => 7.14497 profit
 *      rate:       0.741       1.366            0.995
 *  - how to detect it?
 *    - construct a complete-digraph representation of the exchange table
 *    - finding a negative cycle in the digraph.
 *      - for 1 USD, example above => 1USD × rate(USD,EUR) × rate(EUR,CAD) × rate(CAD,USD) > 1 USD
 *      - taking the logarithm on both side => ln(r1) + ln(r2) + ln(r3) > 0
 *      - negated => (-ln(r1)) + (-ln(r2)) + (-ln(r3)) < 0 => negative cycle!
 *
 *  Data file: rates.txt (https://algs4.cs.princeton.edu/44sp/rates.txt)
 *      USD    EUR    GBP    CHF    CAD
 *  USD 1      0.741  0.657  1.061  1.005
 *  EUR 1.349  1      0.888  1.433  1.366
 *  GBP 1.521  1.126  1      1.614  1.538
 *  CHF 0.942  0.698  0.619  1      0.953
 *  CAD 0.995  0.732  0.650  1.049  1
 *
 *  Result example:
 *  1000.00000 USD =  741.00000 EUR
 *   741.00000 EUR = 1012.20600 CAD
 *  1012.20600 CAD = 1007.14497 USD
 ******************************************************************************/

public class Arbitrage {

    /**
     *  Reads the currency exchange table from standard input and
     *  prints an arbitrage opportunity to standard output (if one exists).
     */
    public static void main(String[] args) {

        File file = new File("src/test/java/chapter4/graphs/data/rates.txt");
        In in = new In(file);

        // currency map
        int V = in.readInt();
        String[] name = new String[V];

        // create complete digraph of currency rates
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = in.readString();
            for (int w = 0; w < V; w++) {
                double rate = in.readDouble();
                WeightedDiEdge e = new WeightedDiEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }


        // find negative cycle
//        GeneralSPT spt = new BellmanFordSPT(G, 0); // start from any vertex (complete graph all vertices are strongly connected)
        GeneralSPT spt = new SPFA(G, 0);

        // result
        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (WeightedDiEdge e : spt.negativeCycle()) {
                System.out.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                System.out.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        }
        else System.out.println("No arbitrage opportunity");
    }

}
