package chapter4.graphs.impl.ds;

/**
 * directed weighted edge
 */
public class WeightedDiEdge extends Edge implements Comparable<WeightedDiEdge> {

    private double weight;

    public WeightedDiEdge(int from, int to, double weight) {
        super(from, to);
        this.weight = weight;
    }

    public int from() {
        return start;
    }

    public int to() {
        return end;
    }

    public double weight() {
        return weight;
    }

    @Override
    public int compareTo(WeightedDiEdge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return Edge.toString(2, true, this, weight);
    }

    public static void main(String[] args) {
        WeightedDiEdge e = new WeightedDiEdge(12, 34, 5.67);
        System.out.println(e);
    }

}
