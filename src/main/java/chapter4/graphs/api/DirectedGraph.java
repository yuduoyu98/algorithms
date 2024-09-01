package chapter4.graphs.api;

/**
 * 有向图
 */
public interface DirectedGraph extends Graph {

    /**
     * 生成一个图的副本 其中所有边的指向反向
     * 方便找出指向某个点的所有边（adj方法）
     */
    DirectedGraph reverse();
}
