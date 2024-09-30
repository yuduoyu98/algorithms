package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.Graph;

import java.util.Arrays;

/**
 * 书上方案
 * 基于DFS实现 有向图成环检测
 */
public class DirectedCycleDetect {

    private Graph graph;
    private boolean[] marked;
    //保存遍历经过的有向边
    //edge[w] = v : v -> w
    private int[] edgeTo;
    //环路
    private Stack<Integer> cycle;
    //DFS递归调用栈上的所有顶点
    private boolean[] onStack;

    public DirectedCycleDetect(Graph G) {
        this.graph = G;
        this.marked = new boolean[G.V()];
        this.onStack = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        Arrays.fill(this.edgeTo, -1);
        for (int v = 0; v < G.V(); v++) {
            //对于有向图来说 循环中的一次DFS 不等于 遍历一个连通分量
            //但如果成环 必定在一次DFS中遍历得到 不可能只遍历到环的一半
            if (!marked[v]) {
                dfs(v);
                if (hasCycle()) {
                    break;
                }
            }
        }
    }

    /**
     * DFS成环检测
     */
    private void dfs(int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : graph.adj(v)) {
            if (hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w);
            } else if (onStack[w]) {
                //成环
                cycle = new SimpleStack<>();
                //从w的上一个节点开始回溯 回溯到w
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                //环路 从哪个顶点开始就要从哪个顶点结束
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

}
