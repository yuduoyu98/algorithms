package chapter4.graphs.api;

import chapter1.fundamentals.api.Bag;
import chapter4.graphs.impl.AdjListUGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 符号图
 */
public abstract class SymbolGraph {

    private Graph G;

    //顶点名 -> 索引
    public ST<String, Integer> st;

    //索引 -> 顶点名
    public String[] keys;

    /**
     * 读入文件构造图
     *
     * @param filePath 文件名
     * @param delim    顶点名分隔符
     */
    public SymbolGraph(String filePath, String delim, boolean useLocal) {
        In in = getIn(filePath, useLocal);
        st = new ST<>();
        //第一遍：构造顶点名称和id的映射 + 保存边信息
        Bag<Pair<Integer, Integer>> edges = parse(in, delim);
        //索引 -> 顶点名称
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }
        //第二遍：构造图
        G = new AdjListUGraph(st.size());
        for (Pair<Integer, Integer> edge : edges) {
            G.addEdge(edge.getLeft(), edge.getRight());
        }
    }

    /**
     * 解析输入 生成顶点索引符号表ST 返回索引化的边信息
     * @param in 输入流
     * @param delim 分隔符
     * @return 顶点转换为id的边信息
     */
    public abstract Bag<Pair<Integer, Integer>> parse(In in, String delim);

    private In getIn(String filePath, boolean useLocal) {
        In in;
        if (useLocal) {
            in = new In(new File(filePath));
        } else {
            try {
                in = new In(new URL(filePath));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("从URL读取文件失败");
            }
        }
        return in;
    }

    /**
     * key是图的顶点吗
     */
    public boolean contains(String key) {
        return st.contains(key);
    }

    /**
     * key的索引
     */
    public int index(String key) {
        return st.get(key);
    }

    /**
     * 索引v的顶点名
     */
    public String name(int v) {
        return keys[v];
    }

    /**
     * Graph对象
     */
    public Graph G() {
        return G;
    }
}
