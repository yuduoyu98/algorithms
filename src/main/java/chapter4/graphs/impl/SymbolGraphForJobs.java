package chapter4.graphs.impl;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.impl.SimpleBag;
import chapter4.graphs.api.SymbolGraph;
import edu.princeton.cs.algs4.In;
import org.apache.commons.lang3.tuple.Pair;

public class SymbolGraphForJobs extends SymbolGraph<AdjListDGraph> {

    public SymbolGraphForJobs(String filePath) {
        super(filePath, "/", true);
    }

    @Override
    public Bag<Pair<Integer, Integer>> parse(In in, String delim) {
        Bag<Pair<Integer, Integer>> edges = new SimpleBag<>();
        int id = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] vertices = line.split(delim);
            for (int i = 1; i < vertices.length; i++) {
                String name1 = vertices[i - 1];
                String name2 = vertices[i];
                int v1, v2;
                if (!st.contains(name1)) {
                    v1 = id;
                    st.put(name1, id++);
                }
                else v1 = st.get(name1);

                if (!st.contains(name2)) {
                    v2 = id;
                    st.put(name2, id++);
                }
                else v2 = st.get(name2);

                edges.add(Pair.of(v1, v2));
            }
        }
        return edges;
    }
}
