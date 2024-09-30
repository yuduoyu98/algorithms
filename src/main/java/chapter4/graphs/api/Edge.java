package chapter4.graphs.api;

public class Edge {

    public int start;
    public int end;

    public Edge(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start + "->" + end;
    }
}
