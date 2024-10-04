package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.Topological;

/**
 * Topological API implementation based on Kahn Algorithms
 */
public class KahnTopological extends Topological {

    public KahnTopological(DirectedGraph G) {
        super(G);
        // in-degree counter
        int[] inDegree = new int[G.V()];
        // 0 in-degree queue
        Queue<Integer> zeroInDegreeV = new SimpleQueue<>();
        // topological order queue
        Queue<Integer> order = new SimpleQueue<>();

        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                inDegree[w]++;

        for (int v = 0; v < inDegree.length; v++)
            if (inDegree[v] == 0)
                zeroInDegreeV.enqueue(v);

        while (zeroInDegreeV.size() > 0) {
            int v = zeroInDegreeV.dequeue();
            order.enqueue(v);
            for (int w : G.adj(v))
                if (--inDegree[w] == 0)  // update in-degree
                    zeroInDegreeV.enqueue(w); // check whether in-degree decrease to 0
        }

        // DAG check: if there are vertices' in-degree still can not decrease to 0, there must be a cycle
        if (order.size() == G.V())
            topoOrder = order;
    }

}
