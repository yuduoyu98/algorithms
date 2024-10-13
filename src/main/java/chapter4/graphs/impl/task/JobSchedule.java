package chapter4.graphs.impl.task;

import chapter1.fundamentals.api.Stack;
import chapter1.fundamentals.impl.SimpleStack;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

/**
 * parallel precedence-constrained scheduling
 * - given a set of jobs with duration to be completed and precedence constraints
 * - given as many processors as needed to preform jobs in parallel
 * - calculate the minimum time to complete all the jobs
 *   - critical path: lower bound on the length of the schedule
 */

public class JobSchedule {

    public EdgeWeightedDiGraph dag;

    private boolean[] marked;

    private Stack<Integer> reversePost;

    private double[] distTo; // jobID -> min start time

    private double[] duration; // job ID -> duration

    /**
     * example:
     * 10 (jobs)
     * 41.0  3  1 7 9 (duration|precedence constraints count|successor jobs)
     * 51.0  1  2
     * 50.0  0
     * 36.0  0
     * 38.0  0
     * 45.0  0
     * 21.0  2  3 8
     * 32.0  2  3 8
     * 32.0  1  2
     * 29.0  2  4 6
     */
    public JobSchedule(In in) {
        // init
        int jobCount = in.readInt();
        marked = new boolean[jobCount];
        reversePost = new SimpleStack<>();
        distTo = new double[jobCount];
        Arrays.fill(distTo, Double.NEGATIVE_INFINITY);
        duration = new double[jobCount];
        dag = new EdgeWeightedDiGraph(jobCount);
        for (int jobID = 0; jobID < jobCount; jobID++) {
            duration[jobID] = in.readDouble();
            int successors = in.readInt();
            for (int p = 0; p < successors; p++) {
                int successor = in.readInt();
                WeightedDiEdge weightedDiEdge = new WeightedDiEdge(jobID, successor, duration[jobID]);
                dag.addEdge(weightedDiEdge);
            }
        }

        // topological order
        for (int v = 0; v < dag.V(); v++)
            if (!marked[v])
                dfs(v);

        // traverse a DAG in topological order to calculate the longest path to reach each vertex
        for (int v : reversePost) {
            // all those vertices' distTo haven't been initialized are 0-degree starting vertex
            // if v has in degree, then distTo[v] should have been initialized during relaxation
            if (distTo[v] == Double.NEGATIVE_INFINITY) distTo[v] = 0.0;
            relax(v);
        }
    }

    // vertex relaxation
    private void relax(int v) {
        for (WeightedDiEdge e : dag.adj(v)) {
            int w = e.to();
            if (distTo[w] < distTo[v] + e.weight())
                distTo[w] = distTo[v] + e.weight();
        }
    }

    // dfs to get the reversePost order
    private void dfs(int v) {
        marked[v] = true;
        for (WeightedDiEdge edge : dag.adj(v)) {
            int w = edge.to();
            if (!marked[w]) dfs(w);
        }
        reversePost.push(v);
    }

    public int jobCount() {
        return dag.V();
    }

    // job minimum start time
    public double startTime(int jobID) {
        return distTo[jobID];
    }

    // job minimum finnish time
    public double finnishTime(int jobID) {
        return distTo[jobID] + duration[jobID];
    }

    // critical path's length
    public double minTotalTime() {
        int lastJobID = -1;
        double lastJobFinnishTime = 0.0;
        for (int jobID = 0; jobID < jobCount(); jobID++) {
            if (finnishTime(jobID) > lastJobFinnishTime) {
                lastJobID = jobID;
                lastJobFinnishTime = finnishTime(jobID);
            }
        }
        return finnishTime(lastJobID);
    }

}
