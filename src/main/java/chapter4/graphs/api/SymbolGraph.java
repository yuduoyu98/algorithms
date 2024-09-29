package chapter4.graphs.api;

import chapter1.fundamentals.api.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * symbol graph
 */
public abstract class SymbolGraph<G extends Graph> {

    private G graph;

    // name -> id
    protected ST<String, Integer> st;

    // id -> name
    protected String[] keys;

    /**
     * build graph from a file
     *
     * @param filePath file path
     * @param delim    delimiter in file to separate vertex names
     * @param useLocal use local file or web file
     */
    public SymbolGraph(String filePath, String delim, boolean useLocal) {
        In in = getIn(filePath, useLocal);
        st = new ST<>();
        // generate a mapping from vertex name to name, load edges represented by id pairs to a bag
        Bag<Pair<Integer, Integer>> edges = parse(in, delim);
        // map vertex id to name
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }
        // build the graph
        graph = graphReflector(st.size());
        for (Pair<Integer, Integer> edge : edges)
            this.graph.addEdge(edge.getLeft(), edge.getRight());
    }

    /**
     * Generate graph objects based on generic reflection
     * @param size vertex count
     */
    @SuppressWarnings("unchecked")
    private G graphReflector(int size) {
        Class<G> graphClass =
                (Class<G>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        G graph;
        try {
            graph = graphClass.getConstructor(int.class).newInstance(size);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }

    /**
     * generate a mapping from vertex name to name
     * load edges represented by id pairs to a bag
     */
    public abstract Bag<Pair<Integer, Integer>> parse(In in, String delim);

    private In getIn(String filePath, boolean useLocal) {
        In in;
        if (useLocal) {
            in = new In(new File(filePath));
        }
        else {
            try {
                in = new In(new URL(filePath));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("从URL读取文件失败");
            }
        }
        return in;
    }

    public boolean contains(String key) {
        return st.contains(key);
    }

    /**
     * vertex name -> id
     */
    public int index(String key) {
        return st.get(key);
    }

    /**
     * vertex id -> name
     */
    public String name(int v) {
        return keys[v];
    }

    public G G() {
        return graph;
    }
}
