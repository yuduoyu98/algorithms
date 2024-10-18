package chapter4.graphs.util;

import chapter1.fundamentals.api.Queue;
import chapter1.fundamentals.impl.SimpleQueue;
import chapter4.graphs.impl.ds.EdgeWeightedDiGraph;
import chapter4.graphs.impl.ds.EdgeWeightedGraph;
import chapter4.graphs.impl.ds.WeightedDiEdge;
import chapter4.graphs.impl.ds.WeightedEdge;
import edu.princeton.cs.algs4.In;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * use graphviz to visualize edge weighted graph {@link EdgeWeightedGraph}
 */
public class VisualizeEdgeGraph {

    private static final String DEFAULT_OUTPUT_DIR = "src/test/java/chapter4/graphs/graph";
    private static final String PIC_TYPE = "png";
    private static final String DIRECTED_EDGE_MARK = "->";
    private static final String UNDIRECTED_EDGE_MARK = "--";
    private Queue<Edge> edges;
    private String outDir;
    private String fileName;
    private boolean isDirected;
    private String[] names;

    private static final String FALSE = "false";
    private static final String TRUE = "true";

    private static class Edge {
        private int x;
        private int y;
        private double weight;

        public Edge(WeightedEdge edge) {
            this.x = edge.start;
            this.y = edge.end;
            this.weight = edge.weight();
        }

        private Edge(WeightedDiEdge edge) {
            this.x = edge.from();
            this.y = edge.to();
            this.weight = edge.weight();
        }
    }

    @SuppressWarnings("unused")
    public VisualizeEdgeGraph(EdgeWeightedGraph graph, String fileName) {
        this(graph, DEFAULT_OUTPUT_DIR, null, fileName);
    }

    public VisualizeEdgeGraph(EdgeWeightedGraph graph, String output, String[] names, String fileName) {
        this.outDir = output;
        this.fileName = fileName;
        this.edges = new SimpleQueue<>();
        this.isDirected = false;
        this.names = names;
        for (WeightedEdge edge : graph.edges())
            edges.enqueue(new Edge(edge));
    }

    public VisualizeEdgeGraph(EdgeWeightedDiGraph graph, String fileName) {
        this(graph, DEFAULT_OUTPUT_DIR, null, fileName);
    }

    @SuppressWarnings("unused")
    public VisualizeEdgeGraph(EdgeWeightedDiGraph graph, String[] names, String fileName) {
        this(graph, DEFAULT_OUTPUT_DIR, names, fileName);
    }

    public VisualizeEdgeGraph(EdgeWeightedDiGraph graph, String output, String[] names, String fileName) {
        this.outDir = output;
        this.fileName = fileName;
        this.edges = new SimpleQueue<>();
        this.isDirected = true;
        this.names = names;
        for (WeightedDiEdge edge : graph.edges())
            edges.enqueue(new Edge(edge));
    }


    /**
     * dot file generation
     * <a href="https://graphviz.org/documentation/">Graphviz website<a/>
     */
    private void dotGen() {
        // init dot file
        StringBuilder sb = new StringBuilder();
        String header = (isDirected ? "digraph" : "strict graph") + " g {\n";

        sb.append(header);
        // adjacent list
        for (Edge edge : edges)
            if (edge.x != edge.y)  // ignore self-loop
                addEdge(sb, edge);

        addSetting(sb, "overlap", FALSE);
        addSetting(sb, "splines", TRUE);
        addSetting(sb, "sep", ".1");
        sb.append("}");
        dotWriter(sb.toString());
    }

    private void addEdge(StringBuilder sb, Edge edge) {
        String edgeMark = isDirected ? DIRECTED_EDGE_MARK : UNDIRECTED_EDGE_MARK;
        sb.append("\t");
        if (names != null) {
            sb.append(names[edge.x]).append(edgeMark).append(names[edge.y]);
        }
        else sb.append(edge.x).append(edgeMark).append(edge.y);
        sb.append("[label=\"")
                .append(String.format("%.4f", edge.weight)) // keep 3 digit after .
//                .append(edge.weight)
                .append("\"]").append(";\n");

    }

    private void addSetting(StringBuilder sb, String key, String val) {
        sb.append("\t").append(key).append("=").append(val).append(";\n");
    }

    /**
     * generate file path for dot/pic
     */
    private String genFilePath(boolean isDot) {
        if (isDot)
            return outDir + "/dot/" + fileName + ".dot";
        else
            return outDir + "/" + fileName + "." + PIC_TYPE;
    }

    /**
     * writing dot to a file
     */
    private void dotWriter(String dotFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(genFilePath(true)))) {
            writer.write(dotFile);
            System.out.println("dotFile file created");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail creating dotFile file");
        }
    }

    /**
     * graph pic generation
     */
    public void picGen() {
        // dot file generation
        dotGen();
        // dot command
        String dotFilePath = genFilePath(true);
        String picFilePath = genFilePath(false);
        String command = "neato " + dotFilePath + " -T" + PIC_TYPE + " -o " + picFilePath;
//        String command = "dot " + dotFilePath + " -T" + PIC_TYPE + " -o " + picFilePath;
        System.out.println("command: " + command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            String result = exitCode == 0 ? "Succeed" : "Failed";
            System.out.println("pic generation " + result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // test example
    public static void main(String[] args) {
//        String filename = "tinyEWG";
        String filename = "tinyEWDAG";
        File file = new File("C:\\Users\\Fish\\Desktop\\algorithms\\src\\test\\java\\chapter4\\graphs\\data\\" + filename + ".txt");
        In in = new In(file);
//        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);
        VisualizeEdgeGraph test = new VisualizeEdgeGraph(G, filename);
        test.picGen();
    }
}
