package chapter4.graphs.impl;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleBag;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.Graph;

/**
 * 基于DFS实现 成环检测 支持有向图/无向图
 */
public class CycleDetect {

    private Graph graph;
    private boolean[] marked;
    //保存递归的路径信息
    private Stack<Integer> path;
    //任意一条环路
    private Bag<Integer> cycle;

    public CycleDetect(Graph G) {
        this.graph = G;
        this.marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            path = new SimpleStack<>();
            //遍历所有连通子图 逐个检测是否成环 (初始节点无前驱节点 lastV初始化为-1)
            if (!marked[v]) {
                dfsCycleDetect(v, -1);
                if (hasCycle()) {
                    break;
                }
            }
        }
    }

    /**
     * DFS成环检测： 在DFS过程中 遇到已经被标记遍历过的节点（非上一个节点） => 成环
     */
    private void dfsCycleDetect(int v, int lastV) {
        marked[v] = true;
        path.push(v);
        for (int w : graph.adj(v)) {
            //1.v的相邻节点已被DFS遍历 且 不是v的上一个节点lastV
            if (marked[w] && lastV != w) {
                //发现环路：将成环点w加入到环路中后 依次将路径栈内的定点弹出直到返回w
                cycle = new SimpleBag<>(); //基于链表实现
                cycle.add(w);
                int p;
                do {
                    p = path.pop();
                    cycle.add(p);
                } while (w != p);
                return;
            }
            //2.w未被遍历 且 w之后同一个连通子图其他顶点是否满足成环条件
            else if (!marked[w]) {
                dfsCycleDetect(w, v);
                //如果递归发现成环了 直接返回
                if (hasCycle()) {
                    return;
                }
            }
            //否则继续遍历 直到遍历所有连通子图节点
        }
        //如果没有发现成环 + 该DFS分支已经走到头了 => 弹栈
        path.pop();
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

}
