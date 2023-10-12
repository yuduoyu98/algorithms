package chapter4.graphs.api;

/**
 * 单点路径问题
 * 1.是否存在路径
 * 2.指出路径
 */
public abstract class Paths {

    public Graph graph;

    public int start;

    public Paths(Graph G, int s) {
        graph = G;
        start = s;
    }

    public abstract boolean hasPathTo(int v);

    public abstract Iterable<Integer> pathTo(int v);
}
