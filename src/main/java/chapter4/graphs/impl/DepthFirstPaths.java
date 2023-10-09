package chapter4.graphs.impl;

import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.Graph;
import chapter4.graphs.api.Paths;

import java.util.Arrays;

/**
 * 基于DFS实现的Paths API
 * (quick-union在路径寻找上就无能力为力了)
 */
public class DepthFirstPaths extends Paths {

    //是否与起点连通
    private boolean[] marked;
    //从起点到顶点的已知路径的最后一个顶点。
    //实际上是一棵树的数组表示 根节点是顶点 “树枝”由dfs遍历生成 每个元素的值 <=> 其父节点/路径上的上一个节点
    private int[] edgeTo;

    public DepthFirstPaths(Graph G, int s) {
        super(G, s);
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        //默认位false
//        Arrays.fill(marked, false);
        //默认为0
        Arrays.fill(edgeTo, -1);
        dfs(start);
    }

    public void dfs(int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w);
            }
        }
    }

    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        SimpleStack<Integer> stack = new SimpleStack<>();
        while (v != start) {
            stack.push(v);
            v = edgeTo[v];
        }
        stack.push(start);
        //Stack实现了Iterable接口：可以通过forEach或者增强for循环进行遍历（和push顺序逆序）
        return stack;
    }
}
