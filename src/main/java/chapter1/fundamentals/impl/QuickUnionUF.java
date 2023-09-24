package chapter1.fundamentals.impl;


/**
 * Union Found 并查集 实现（针对union性能的优化尝试）
 * set数组和之前含义不同：下标 <=> 触点, 下标对应的值 <=> 触点相连的触点（可以是自己） 下标对应的值=下标 => 根触点
 */
public class QuickUnionUF extends AbstractUnionFound {

    public QuickUnionUF(int N) {
        super(N);
    }

    /**
     * 两次find
     */
    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) return;
        set[pId] = qId;
        count--;
    }

    /**
     * find 性能取决于连通图对应树的树高 => 牺牲了find性能
     */
    @Override
    public int find(int p) {
        while (p != set[p]) {
            //不是根节点，不断向上找
            p = set[p];
        }
        return p;
    }

}
