package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.task.SPT;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;

import java.util.Arrays;

/**
 * longest path tree problem for directed acyclic graph
 * - relax()
 * - initialize dist[]
 *
 * extends SPT for test convenience
 */
public class DAGLPT extends SPT {

    private boolean[] marked;
    Stack<Integer> reversePost;

    public DAGLPT(EdgeWeightedDiGraph G, int s) {
        super(G, s);
        this.marked = new boolean[g.V()];
        Arrays.fill(distTo, Double.NEGATIVE_INFINITY); // initial update when there is a path
        distTo[start] = 0.0;
        reversePost = new SimpleStack<>();

        dfs(start);
        for (int v : reversePost)
            VRelax(v);
    }

    /**
     * vertex relaxation
     */
    @Override
    protected void VRelax(int v) {
        for (WeightedDiEdge e : g.adj(v)) {
            // relax each edge v->w
            int w = e.to();
            if (distTo[w] < distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }

    private void dfs(int v) {
        marked[v] = true;
        for (WeightedDiEdge e : g.adj(v)) {
            int w = e.to();
            if (!marked[w]) dfs(w);
        }
        reversePost.push(v);
    }

}
