package chapter4.graphs.impl;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.impl.SimpleBag;
import chapter4.graphs.api.SymbolGraph;
import edu.princeton.cs.algs4.In;
import org.apache.commons.lang3.tuple.Pair;


/**
 *
 */
public class SymbolGraphForRoutes extends SymbolGraph {

    /**
     * 读入文件构造图
     *
     * @param filePath 文件名
     * @param useLocal 是否使用本地文件
     */
    public SymbolGraphForRoutes(String filePath, boolean useLocal) {
        super(filePath, " ", useLocal);
    }

    @Override
    public Bag<Pair<Integer, Integer>> parse(In in, String delim) {
        Bag<Pair<Integer, Integer>> edges = new SimpleBag<>();
        int id = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String v1Name = line.split(delim)[0];
            String v2Name = line.split(delim)[1];
            int v1, v2;
            if (!st.contains(v1Name)) {
                v1 = id++;
                st.put(v1Name, v1);
            } else {
                v1 = st.get(v1Name);
            }
            if (!st.contains(v2Name)) {
                v2 = id++;
                st.put(v2Name, v2);
            } else {
                v2 = st.get(v2Name);
            }
            edges.add(Pair.of(v1, v2));
        }
        return edges;
    }

}
