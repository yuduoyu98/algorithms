package chapter4.graphs.impl.ds;

import chapter1.fundamentals.api.Bag;
import chapter1.fundamentals.impl.SimpleBag;
import chapter4.graphs.api.ds.SymbolGraph;
import chapter4.graphs.impl.ds.AdjListUGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.apache.commons.lang3.tuple.Pair;

public class SymbolGraphForMovies extends SymbolGraph<AdjListUGraph> {

    public SymbolGraphForMovies(String url) {
        super(url, "/", false);
    }

    @Override
    public Bag<Pair<Integer, Integer>> parse(In in, String delim) {
        Bag<Pair<Integer, Integer>> edges = new SimpleBag<>();
        int id = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] vertices = line.split(delim);
            String movie = vertices[0];
            int movieId;
            if (!st.contains(movie)) {
                movieId = id;
                st.put(movie, id++);
            }
            else movieId = st.get(movie);

            for (int i = 1; i < vertices.length; i++) {
                int celebrityId;
                String celebrity = vertices[i];
                if (!st.contains(celebrity)) {
                    celebrityId = id;
                    st.put(celebrity, id++);
                }
                else celebrityId = st.get(celebrity);

                edges.add(Pair.of(movieId, celebrityId));
            }
        }
        StdOut.println("图顶点数：" + st.size());
        return edges;
    }

}
