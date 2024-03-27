package chapter4.graphs.impl;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter4.graphs.api.DirectedGraph;
import chapter4.graphs.api.TopoLogical;

/**
 * 基于Kahn算法实现的拓扑排序API
 */
public class KahnTopological extends TopoLogical {

    public KahnTopological(DirectedGraph G) {
        super(G);
        //入度统计
        int[] inDegree = new int[G.V()];
        //零入度队列
        Queue<Integer> zeroInDegreeV = new SimpleQueue<>();
        //拓扑排序临时List
        Queue<Integer> order = new SimpleQueue<>();
        //原图每个点的入度等价于反向图对应点的出度(邻接表顶点的个数)
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                inDegree[w]++;
            }
        }
        //初始化：将所有入度为0的顶点加入到队列中
        for (int v = 0; v < inDegree.length; v++) {
            if (inDegree[v] == 0) {
                zeroInDegreeV.enqueue(v);
            }
        }
        while (zeroInDegreeV.size() > 0) {
            //还存在入度为0的顶点
            int v = zeroInDegreeV.dequeue();
            //拓扑排序临时队列 入队
            order.enqueue(v);
            //将该点从图中去除: 抹除该点对其他顶点提供的入度
            for (int w : G.adj(v)) {
                if (--inDegree[w] == 0) {
                    //检测到新增入度为0的顶点 加入到零入度队列中
                    zeroInDegreeV.enqueue(w);
                }
            }
        }
        //所有加入过零入度队列的点的个数小于顶点数 意味着有环 拓扑排序没有意义
        if (order.size() == G.V()) {
            topoOrder = order;
        }
    }

}
