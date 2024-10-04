package chapter4.graphs.impl.task;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.Topological;

/**
 * Topological API implementation based on DFS
 *
 * Proposition: reverse post order in a DAG is the topological sort
 * Proof: to a directed edge v->w in a DAG
 * - the reverse post order should be v ahead of w => dfs(v) should be finished after dfs(w)
 * - when dfs(v) is called, there are 3 cases:
 *     1.w hasn't been visited => dfs(w) will be called after dfs(v) and returned before dfs(v) (satisfy)
 *     2.dfs(w) has already been called and returned (satisfy)
 *     3.dfs(w) has already been called but has not yet returned => there is a way from w to v => cycle exists => not a DAG (impossible)
 *
 * Time Complexity: O(V+E)
 */
public class DFSTopological extends Topological {

    public DFSTopological(DirectedGraph G) {
        super(G);
        DirectedCycleDetect cycleFinder = new DirectedCycleDetect(G);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder order = new DepthFirstOrder(G);
            this.topoOrder = order.reversePost();
        }
    }

}
