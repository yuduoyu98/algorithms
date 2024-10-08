package chapter1.fundamentals.impl;


import chapter1.fundamentals.api.AbstractUnionFound;

import java.util.Arrays;

/**
 * Union Found 并查集 实现（quick-union思路的基础上进行优化）
 * set数组和之前含义不同：下标 <=> 触点, 下标对应的值 <=> 触点相连的触点（可以是自己） 下标对应的值=下标 => 根触点
 * quick-union算法的复杂度取决于树高，优化点便在于降低树高 -> 两个数归并永远希望是小树接到大树上 -> 需要知道每颗树的树高
 */
public class HeightQuickUnionUF extends AbstractUnionFound {

    //记录树高（只对树的头节点有意义）
    private int[] ht;

    public HeightQuickUnionUF(int N) {
        super(N);
        ht = new int[N];
        Arrays.fill(ht, 1);
    }

    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) return;
        if (ht[pId] < ht[qId]) {
            set[pId] = qId;
        } else {
            set[qId] = pId;
            if (ht[pId] == ht[qId]) {
                //两颗子树相等时 归并两棵树会增加树高
                ht[pId] = ht[pId] + 1;
            }
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
