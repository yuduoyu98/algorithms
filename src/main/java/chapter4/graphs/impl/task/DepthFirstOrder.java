package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.DirectedGraph;


/**
 * DFS traversal order
 * - pre-order: the order of vertices dfs visit (enqueue a queue before dfs)
 * - post-order: the order of vertices dfs finishes (enqueue a queue after dfs)
 * - reverse post-order: reverse order of post-order (enqueue a stack after dfs)
 */
public class DepthFirstOrder {

    private boolean[] marked;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;
    private DirectedGraph dg;

    public DepthFirstOrder(DirectedGraph DG) {
        this.dg = DG;
        marked = new boolean[DG.V()];
        pre = new SimpleQueue<>();
        post = new SimpleQueue<>();
        reversePost = new SimpleStack<>();
        for (int v = 0; v < dg.V(); v++)
            if (!marked[v])
                dfs(v);
    }

    private void dfs(int v) {
        pre.enqueue(v);
        marked[v] = true;
        for (int w : dg.adj(v)) {
            if (!marked[w]) {
                dfs(w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }

    /**
     * pre order of dfs traversal
     */
    public Iterable<Integer> pre() {
        return pre;
    }

    /**
     * post order of dfs traversal
     */
    public Iterable<Integer> post() {
        return post;
    }

    /**
     * reverse post order of dfs traversal
     */
    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
