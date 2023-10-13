package chapter4.graphs.impl;

import chapter4.graphs.api.DirectedGraph;
import chapter4.graphs.api.TopoLogical;

/**
 * DFS实现 拓扑排序 API
 * 有向无环图 DAG 才会有拓扑排序
 */
public class DFSTopoLogical extends TopoLogical {

    public DFSTopoLogical(DirectedGraph G){
        super(G);
        DirectedCycleDetect cycleFinder = new DirectedCycleDetect(G);
        if(!cycleFinder.hasCycle()){
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
