package chapter4.graphs.impl;

import chapter4.graphs.api.DirectedGraph;
import chapter4.graphs.api.TopoLogical;

/**
 * DFS实现 拓扑排序 API
 * 有向无环图 DAG 才会有拓扑排序
 * 原理：对于任意一条边v->w，调用dfs(v)可能会出现一下三种情况：
 *      1.dfs(w)已经调用且返回 (marked[w]=true && onStack[w]=false): 有向图中可能出现 跳过即可
 *      2.dfs(w)未被调用 (marked[w]=false): v->w => v的递归返回晚于w => 入栈顺序上v晚于w =>  v会先于w出栈 => v在w前
 *      3.dfs(w)已经调用但未返回 (marked[w]=true && onStack[w]=true): DFS上游调用栈存在 w->v 的路径 成环了
 */
public class DFSTopoLogical extends TopoLogical {

    public DFSTopoLogical(DirectedGraph G) {
        super(G);
        DirectedCycleDetect cycleFinder = new DirectedCycleDetect(G);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder order = new DepthFirstOrder(G);
            cycle = order.reversePost();
        }
    }

    @Override
    public boolean isDAG() {
        return cycle != null;
    }

    @Override
    public Iterable<Integer> order() {
        return cycle;
    }

}
