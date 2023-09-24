package chapter1.fundamentals.api;

/**
 * Union Found 并查集 API 以解决动态连通性问题
 */
public interface UnionFound {

    /**
     * 在p和q之间添加一条连接 (连接/归并两个连通分量)
     */
    void union(int p, int q);

    /**
     * p所在的连通分量标识符 (0 ~ N-1)
     */
    int find(int p);

    /**
     * p和q是否属于同一个连通分量
     */
    boolean connected(int p, int q);

    /**
     * 并查集中连通分量的数量
     */
    int count();
}
