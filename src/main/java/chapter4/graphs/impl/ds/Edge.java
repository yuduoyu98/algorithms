package chapter4.graphs.impl.ds;

public class Edge {

    public final int start;
    public final int end;

    public Edge(int start, int end) {
        if (start < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (end < 0) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start + "->" + end;
    }

    public static String toString(int precision, boolean directed, Edge edge, double weight) {
        String edgeMark = directed ? "->" : "-";
        return String.format("%d" + edgeMark + "%d(%." + precision + "f)", edge.start, edge.end, weight);
    }
}
