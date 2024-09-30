package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.api.task.Paths;

/**
 * BFS based implementation of Paths API （single-source shortest paths）
 */
public class BreadthFirstPaths extends Paths {


    private boolean[] marked;
    private int[] edgeTo;

    public BreadthFirstPaths(Graph G, int s) {
        super(G, s);
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        bfs(s);
    }

    private void bfs(int p) {
        // DFS needs a stack to keep track of the search path
        // BFS needs a queue to maintain a list of vertices to be visited, in the order similar to the level-order traversal of a tree
        Queue<Integer> queue = new SimpleQueue<>();
        queue.enqueue(p);
        // different mark timing
        // BFS mark it before dequeue (unlike DFS mark it after pop)
        // when exploiting adjacent vertices in the next level/depth
        // - BFS need to prevent the search from revisiting vertices in previous level (already queued)
        // - DFS explores as deeply as possible along a single path, mark only the visiting vertex (pop from stack)
        marked[p] = true;
        do {
            int v = queue.dequeue();
            for (int w : graph.adj(v))
                if (!marked[w]) {
                    queue.enqueue(w);
                    edgeTo[w] = v;
                    marked[w] = true;
                }
        } while (!queue.isEmpty());
    }

    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> stack = new SimpleStack<>();
        while (v != start) {
            stack.push(v);
            v = edgeTo[v];
        }
        stack.push(start);
        return stack;
    }
}
