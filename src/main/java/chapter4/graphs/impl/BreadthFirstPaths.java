package chapter4.graphs.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Paths;

/**
 * 广度优先遍历 BFS实现Paths API （最短路径问题）
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

    private void bfs(int s) {
        //深度优先递归本质上是栈，广度优先需要队列辅助（类似树的层序遍历）
        Queue<Integer> queue = new SimpleQueue<>();
        //添加起点
        queue.enqueue(s);
        marked[s] = true;
        //直到队列为空
        while (!queue.isEmpty()) {
            //取出一个队列元素
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                //顶点没有遍历过（最快到达），否则忽略 -> 得到的路径是最短路径
                if (!marked[w]) {
                    //入队
                    queue.enqueue(w);
                    //路径树
                    edgeTo[w] = v;
                    //标记已被遍历
                    marked[w] = true;
                }
            }
        }
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
