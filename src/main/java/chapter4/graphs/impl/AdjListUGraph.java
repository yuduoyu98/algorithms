package chapter4.graphs.impl;

import chapter4.graphs.api.UndirectedGraph;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * 无向图的邻接表实现
 * 对于稀疏图性能表现好
 */
public abstract class AdjListUGraph implements UndirectedGraph {

    private final int V;
    private int E;
    private Bag<Integer>[] adj; //Bag实现邻接表 不允许平行边可以用Set

    /**
     * 根据订单数v初始化一个图
     * @param V 定点数
     */
    @SuppressWarnings("unchecked")
    public AdjListUGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < this.V; v++) {
            this.adj[v] = new Bag<>();
        }
    }

    /**
     * 根据输入流读入一幅图：先读定点数V，再读边数E，最后读入E对0~V-1的整数对表示每条边
     * @param in 输入流
     */
    public AdjListUGraph(In in) {
        //根据第一个参数初始化图
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    @Override
    public int E() {
        return this.E;
    }

    @Override
    public int V() {
        return this.V;
    }

    @Override
    public void addEdge(int v, int w) {
        this.adj[v].add(w);
        this.adj[w].add(v);
        this.E++;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return this.adj[v];
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
