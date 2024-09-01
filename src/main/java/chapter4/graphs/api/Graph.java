package chapter4.graphs.api;

/**
 * 无向图API
 */
public interface Graph {

    /**
     * @return 图的边数
     */
    int E();

    /**
     * @return 图的顶点数
     */
    int V();

    /**
     * 无向图表示相邻顶点
     * 有向图表示从顶点v指出的边指向的所有顶点
     *
     * @param v 顶点
     * @return 顶点相邻的所有顶点 (如果发生自环则相邻顶点中包含自身)
     */
    Iterable<Integer> adj(int v);

    /**
     * 添加一条边
     * @param v 边的一个顶点
     * @param w 边的另一个顶点
     */
    void addEdge(int v, int w);
}
