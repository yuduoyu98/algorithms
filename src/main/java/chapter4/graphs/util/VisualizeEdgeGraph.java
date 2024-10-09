package chapter4.graphs.util;

import chapter4.graphs.impl.ds.EdgeWeightedGraph;
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
    private EdgeWeightedGraph G;
    private String outDir;
    private String fileName;

    private static final String FALSE = "false";
    private static final String TRUE = "true";

    public VisualizeEdgeGraph(EdgeWeightedGraph graph, String fileName) {
        this(graph, DEFAULT_OUTPUT_DIR, fileName);
    }

    public VisualizeEdgeGraph(EdgeWeightedGraph graph, String output, String fileName) {
        this.G = graph;
        this.outDir = output;
        this.fileName = fileName;
    }

    /**
     * dot file generation
     * <a href="https://graphviz.org/documentation/">Graphviz website<a/>
     */
    private void dotGen() {
        // init dot file
        StringBuilder sb = new StringBuilder();
        String header = "strict graph" + " g {\n";
        sb.append(header);
        // adjacent list
        for (int v = 0; v < G.V(); v++)
            for (WeightedEdge edge : G.adj(v))
                addEdge(sb, edge);
        addSetting(sb, "overlap", FALSE);
        addSetting(sb, "splines", TRUE);
        addSetting(sb, "sep", ".1");
        sb.append("}");
        dotWriter(sb.toString());
    }

    private void addEdge(StringBuilder sb, WeightedEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        sb.append("\t").append(v).append(UNDIRECTED_EDGE_MARK).append(w)
                .append("[label=\"").append(edge.weight()).append("\"]").append(";\n");
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
        String filename = "tinyEWG";
        File file = new File("C:\\Users\\Fish\\Desktop\\algorithms\\src\\test\\java\\chapter4\\graphs\\data\\" + filename + ".txt");
        In in = new In(file);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        VisualizeEdgeGraph test = new VisualizeEdgeGraph(G, filename);
        test.picGen();
    }
}
