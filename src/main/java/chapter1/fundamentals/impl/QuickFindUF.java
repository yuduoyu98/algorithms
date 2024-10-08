package chapter1.fundamentals.impl;

import chapter1.fundamentals.api.AbstractUnionFound;

/**
 * Union Found 并查集 实现
 * set数组表示触点和连通分量的关系：连通分量用其中某一个触点的id表示，下标 <=> 触点, 下标对应的值 <=> 触点所属连通分量
 */
public class QuickFindUF extends AbstractUnionFound {

    /**
     * 初始化N个触点
     */
    public QuickFindUF(int N) {
        super(N);
    }

    /**
     * O(N)的实现
     */
    @Override
    public void union(int p, int q) {
        if (connected(p, q)) return;
        //临时保存q处的连通分量id，否则循环到q后set[q]被修改，之后的元素就无法正常替换了
        int qId = find(q);
        //p其实不用保存临时变量
        int pId = find(p);
        for (int i = 0; i < set.length; i++) {
            if (set[i] == qId) {
                set[i] = pId;
            }
        }
        count--;
    }

    @Override
    public int find(int p) {
        return set[p];
    }

}
