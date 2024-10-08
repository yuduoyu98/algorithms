package chapter1.fundamentals.impl;


import chapter1.fundamentals.api.AbstractUnionFound;

import java.util.Arrays;

/**
 * Union Found 并查集 实现（quick-union思路的基础上进行优化）
 * set数组和之前含义不同：下标 <=> 触点, 下标对应的值 <=> 触点相连的触点（可以是自己） 下标对应的值=下标 => 根触点
 * union时永远希望小数挂在大树上（树的节点数大小）
 */
public class WeightQuickUnionUF extends AbstractUnionFound {

    //记录树的节点数 连通分量定点数（只对树的头节点有意义）
    private int[] wt;

    public WeightQuickUnionUF(int N) {
        super(N);
        wt = new int[N];
        Arrays.fill(wt, 1);
    }

    //获取某一顶点相连通的顶点数 树的节点数
    //为方便Search API的实现类QuickUnionSearch#count的实现拓展的api
    public int connectedVertices(int p) {
        return wt[find(p)];
    }

    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) return;
        if (wt[pId] < wt[qId]) {
            set[pId] = qId;
            wt[qId] += wt[pId];
        } else {
            set[qId] = pId;
            wt[pId] += wt[qId];
        }
        count--;
    }

    @Override
    public int find(int p) {
        while (p != set[p]) {
            //不是根节点，不断向上找
            p = set[p];
        }
        return p;
    }

}
