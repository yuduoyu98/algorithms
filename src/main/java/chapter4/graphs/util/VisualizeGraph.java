package chapter4.graphs.util;

import chapter4.graphs.api.ds.DirectedGraph;
import chapter4.graphs.api.ds.Graph;
import chapter4.graphs.impl.ds.AdjListDGraph;
import chapter4.graphs.impl.ds.AdjListUGraph;
import edu.princeton.cs.algs4.In;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * use graphviz to visualize graph
 */
public class VisualizeGraph {

    private static final String DEFAULT_OUTPUT_DIR = "src/test/java/chapter4/graphs/graph";
    private static final String PIC_TYPE = "png";
    private static final String DIRECTED_EDGE_MARK = "->";
    private static final String UNDIRECTED_EDGE_MARK = "--";
    private Graph G;
    private String outDir;
    private String fileName;
    private boolean isDirected = false;

    public VisualizeGraph(Graph graph, String fileName) {
        this(graph, DEFAULT_OUTPUT_DIR, fileName);
    }

    public VisualizeGraph(Graph graph, String output, String fileName) {
        this.G = graph;
        if (graph instanceof DirectedGraph) isDirected = true;
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
        String header = isDirected ? "digraph" : "strict graph" + " g {\n";
        String edgeMark = isDirected ? DIRECTED_EDGE_MARK : UNDIRECTED_EDGE_MARK;
        sb.append(header);
        // adjacent list
        for (int v = 0; v < G.V(); v++)
            for (Integer w : G.adj(v))
                sb.append("\t").append(v).append(" ").append(edgeMark).append(" ").append(w).append(";\n");
        sb.append("}");
        dotWriter(sb.toString());
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
        String command = "dot " + dotFilePath + " -T" + PIC_TYPE + " -o " + picFilePath;
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
        String filename = "tinyDG";
        File file = new File("C:\\Users\\Fish\\Desktop\\algorithms\\src\\test\\java\\chapter4\\graphs\\data\\" + filename + ".txt");
        In in = new In(file);
//        Graph G = new AdjListUGraph(in);
        Graph G = new AdjListDGraph(in);
        VisualizeGraph test = new VisualizeGraph(G, filename);
        test.picGen();
    }
}
