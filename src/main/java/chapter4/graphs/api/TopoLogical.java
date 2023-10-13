package chapter4.graphs.api;

/**
 * 拓扑排序 API
 * 有向无环图 DAG 才会有拓扑排序
 */
public abstract class TopoLogical {

    protected DirectedGraph graph;
    protected Iterable<Integer> cycle;

    public TopoLogical(DirectedGraph G){
        graph = G;
    }

    public abstract boolean isDAG();

    public abstract Iterable<Integer> order();
}
