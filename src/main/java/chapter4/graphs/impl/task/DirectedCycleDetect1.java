package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleBag;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.api.ds.Graph;

/**
 * 个人方法
 * 基于DFS实现 成环检测 支持有向图/无向图
 */
public class DirectedCycleDetect1 {

    private Graph graph;
    private boolean[] marked;
    //保存递归的路径信息
    private Stack<Integer> path;
    //保存处于当前dfs递归遍历过的顶点
    private boolean[] onStack;
    //任意一条环路
    private Bag<Integer> cycle;

    public DirectedCycleDetect1(Graph G) {
        this.graph = G;
        this.marked = new boolean[G.V()];
        this.onStack = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            path = new SimpleStack<>();
            //遍历所有连通子图 逐个检测是否成环 (初始节点无前驱节点 lastV初始化为-1)
            //上述想法错误！对于有向图 这样dfs遍历顶点 可能无法完整遍历整个连通分量
            if (!marked[v]) {
                dfsCycleDetect(v);
                if (hasCycle()) {
                    break;
                }
            }
        }
    }

    /**
     * 错误的实现！
     * 说明：对于有向图 在最外层根据连通分量遍历的时候 起点可能并非连通子图的起点（入度可能为0）
     *      比如1->0这个连通分量 如果先遍历0 这个连通分量是无法完整遍历的 后序从1开始dfs的时候会碰到已经marked的0
     *      所以 用marked来判断是否有环是不合理的 需要额外增加一个布尔数组表示顶点是否处于当前递归中
     *      因为 实际上如果有环 必定是一次dfs中就可以检测出的 无论dfs起点在哪里
     */
    private void dfsCycleDetect(int v) {
        marked[v] = true;
        onStack[v] = true;
        path.push(v);
        for (int w : graph.adj(v)) {
            //1.v的相邻节点已被DFS遍历 且 处于当前递归栈中的顶点
            if (marked[w] && onStack[w]) {
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
                dfsCycleDetect(w);
                //如果递归发现成环了 直接返回
                if (hasCycle()) {
                    return;
                }
            }
            //否则继续遍历 直到遍历所有连通子图节点
        }
        //如果没有发现成环 + 该DFS分支已经走到头了 => 弹栈
        path.pop();
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

}
