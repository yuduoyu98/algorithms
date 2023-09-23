package chapter4.graphs.api;

/**
 * 图处理算法较多，考虑图的实现和处理算法分开
 */
public abstract class Search {

    /**
     * 初始化
     * @param G 图
     * @param s 起点
     */
    public Search(Graph G, int s){

    }

    /**
     * 顶点v和s是否连通
     */
    abstract boolean marked(int v);

    /**
     * 与s连通的顶点总数
     */
    abstract int count();
}
