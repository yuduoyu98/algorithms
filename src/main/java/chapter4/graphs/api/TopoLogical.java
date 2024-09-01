package chapter4.graphs.api;

/**
 * 拓扑排序 API
 * 有向无环图 DAG 才会有拓扑排序
 */
public abstract class TopoLogical {

    protected DirectedGraph graph;
    protected Iterable<Integer> topoOrder;

    public TopoLogical(DirectedGraph G){
        graph = G;
    }

    public boolean isDAG(){
        return topoOrder != null;
    }

    public Iterable<Integer> order(){
        return topoOrder;
    }
}
