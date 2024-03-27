package chapter4.graphs.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.DirectedGraph;


/**
 * DFS的顶点排序（有向图）
 */
public class DepthFirstOrder {

    private boolean[] marked;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;
    private DirectedGraph graph;

    public DepthFirstOrder(DirectedGraph G) {
        graph = G;
        marked = new boolean[G.V()];
        pre = new SimpleQueue<>();
        post = new SimpleQueue<>();
        reversePost = new SimpleStack<>();
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        pre.enqueue(v);
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }

    /**
     * 前序: dfs调用的顺序
     */
    public Iterable<Integer> pre() {
        return pre;
    }

    /**
     * 后序：dfs顶点遍历完成的顺序
     */
    public Iterable<Integer> post() {
        return post;
    }

    /**
     * 逆后序
     */
    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
