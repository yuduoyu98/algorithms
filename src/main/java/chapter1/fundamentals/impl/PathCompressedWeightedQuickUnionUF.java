package chapter1.fundamentals.impl;

import java.util.Arrays;

/**
 * 在加权quick-union算法的基础上 考虑在执行find的时候将路径进行"压缩": 所有触点指向根节点
 */
public class PathCompressedWeightedQuickUnionUF extends AbstractUnionFound {

    //记录树高（只对树的头节点有意义）
    private int[] ht;

    public PathCompressedWeightedQuickUnionUF(int N) {
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
        int startNode = p;
        while (p != set[p]) {
            //不是根节点，不断向上找
            p = set[p];
        }
        //将路径上的所有触点指向根节点
        while (startNode != p) {
            //p此时是根节点
            int fatherNode = set[startNode];
            set[startNode] = p;
            startNode = fatherNode;
        }
        return p;
    }

}
