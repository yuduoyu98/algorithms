package chapter4.graphs.api.task;

import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedEdge;

/**
 * minimum spanning tree API
 */
public abstract class MST {

    protected EdgeWeightedGraph g;

    protected double weight;

    public MST(EdgeWeightedGraph G){
        this.g = G;
        this.weight = 0;
    }

    // all of the MST edges
    public abstract Iterable<WeightedEdge> edges();

    // weight of the MST
    public double weight(){
        return weight;
    }

}
