package chapter4.graphs.impl.task;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.task.SCC;

import java.util.Arrays;

/**
 * Kosaraju Algorithm
 *
 * idea
 * - algorithms
 *   1.calculate the reverse postorder of the reverse G
 *   2.choose the unmarked vertex in the order just computed to run a dfs on G
 *   3.all the vertices reached during a single dfs belong to the same SCC.
 * - proof:
 *   assume we run a DFS on G starting from a vertex s. for each vertex v reached during this dfs:
 *   -> there exists a path s->...->v in G
 *      -> there exists a path v->...->s in reverse G
 *   -> s is earlier in reverse postorder of G(R) than v
 *      -> in dfs of the reverse G, dfs(s) is done later than dfs(v)
 *   -> 2 cases may happen in reverse G's dfs:
 *      case 1: dfs(v)        case 2: dfs(s)
 *              ...                   ...
 *              done                     dfs(v)
 *              ...                      ...
 *              dfs(s)                   done
 *              ...                   ...
 *              done                  done
 *   -> case 1 is impossible:
 *      - there exists a path v->...->s in reverse G (contradiction)
 *   -> case 2 indicates there exists a path s->...->v in reverse G
 *      -> there exists a path v->...->s in G
 *      -> s and v are strongly connected
 *
 * performance analysis
 * - extremely easy to code
 * - time cost: 2 dfs traversal
 */
public class KosarajuSCC extends SCC {

    private int[] scc;
    private int count;
    public static final int UNASSIGNED = -1;

    public KosarajuSCC(DirectedGraph DG) {
        super(DG);
        scc = new int[dg.V()];
        Arrays.fill(scc, UNASSIGNED);
        // calculate the reverse postorder of the reverse G
        Iterable<Integer> reversePost = new DepthFirstOrder(dg.reverse()).reversePost();
        // run dfs on G based on the order
        for (int v : reversePost) {
            if (scc[v] == UNASSIGNED) {
                // each dfs mark all the vertices of a SCC
                dfs(v);
                count++;
            }
        }
    }

    private void dfs(int v) {
        scc[v] = count;
        for (int w : dg.adj(v))
            if (scc[w] == UNASSIGNED) dfs(w);
    }

    @Override
    public boolean stronglyConnected(int v, int w) {
        return scc[v] == scc[w];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int id(int v) {
        return scc[v];
    }
}
