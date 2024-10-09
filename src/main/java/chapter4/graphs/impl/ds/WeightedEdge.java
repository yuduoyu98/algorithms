package chapter4.graphs.impl.ds;

/**
 * weighted edge (undirected)
 */
public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {

    private final double weight;

    public WeightedEdge(int start, int end, double weight) {
        super(start, end);
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    // either vertex of the edge
    public int either() {
        return end;
    }

    public int other(int v) {
        if (v == start) return end;
        else if (v == end) return start;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    @Override
    public int compareTo(WeightedEdge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return toString(2);
    }

    public String toString(int precision) {
        return String.format("%d-%d(%." + precision + "f)", start, end, weight);
    }

    public static void main(String[] args) {
        WeightedEdge e = new WeightedEdge(12, 34, 5.67);
        System.out.println(e);
    }
}
