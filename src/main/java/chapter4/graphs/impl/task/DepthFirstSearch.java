package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.api.task.Search;

import java.util.Arrays;

/**
 * depth first search
 */
public class DepthFirstSearch extends Search {

    // mark the vertices visited
    private boolean[] marked;
    // number of vertices connected to s
    private int count;

    public DepthFirstSearch(Graph G, int s) {
        super(G, s);
        marked = new boolean[G.V()];
        Arrays.fill(marked, false);
        dfs1(start);
    }

    // recursive version
    @SuppressWarnings("unused")
    private void dfs(int p) {
        // mark the current vertex as visited
        marked[p] = true;
        count++;
        for (int v : graph.adj(p))
            if (!marked(v))
                dfs(v);
    }

    // non-recursive version
    @SuppressWarnings("unused")
    private void dfs1(int p) {
        Stack<Integer> stack = new SimpleStack<>();
        stack.push(p);
        do {
            int v = stack.pop();
            marked[v] = true;
            count++;
            for (Integer w : graph.adj(v))
                if (!marked[w]) stack.push(w);
        } while (!stack.isEmpty());
    }

    @Override
    public boolean marked(int v) {
        return marked[v];
    }

    @Override
    public int count() {
        return count;
    }
}
