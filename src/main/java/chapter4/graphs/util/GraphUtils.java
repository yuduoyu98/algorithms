package chapter4.graphs.util;

import chapter4.graphs.api.Graph;

public class GraphUtils {

    /**
     * @param G 图
     * @param v 顶点
     * @return 图中该顶点的度数
     */
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (Integer w : G.adj(v)) {
            degree++;
        }
        return degree;
    }

    /**
     * @param G 图
     * @return 图所有顶点最大度数
     */
    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        }
        return max;
    }

    /**
     * @param G 图
     * @return 图所有顶点平均度数 (自环 一条边 => 两个度)
     */
    public static double avgDegree(Graph G) {
        //一个边会带来两个度 => 所有顶点的度数 = 2 * 边数
        return 2.0 * G.E() / G.V();
    }

    /**
     * @param G 图
     * @return 图的自环个数 (自环不属于简单图的范畴中，一条连接一个顶点和自身的边)
     */
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (Integer w : G.adj(v)) {
                if (w == v) {
                    count++;
                }
            }
        }
        //todo 书上为什么要/2？
        return count;
    }
}
