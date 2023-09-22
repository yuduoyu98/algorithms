package chapter4.graphs.api;

import edu.princeton.cs.algs4.In;

public abstract class AbstractGraph implements Graph {

    /**
     * 根据订单数v初始化一个图
     * @param v 定点数
     */
    public AbstractGraph(int v) {

    }

    /**
     * 根据输入流读入一幅图：先读定点数V，再读边数E，最后读入E对0~V-1的整数对表示每条边
     * @param in 输入流
     */
    public AbstractGraph(In in){

    }

    /**
     * 邻接表形式表示图
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(V() + " vertices, " + E() + " edges\n");
        for (int v = 0; v < V(); v++) {
            s.append(v).append(": ");
            for (int w : this.adj(v)) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
