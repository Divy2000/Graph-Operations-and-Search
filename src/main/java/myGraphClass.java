import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class myGraphClass {
    MutableGraph g = null;
    public void parseGraph(String filepath) throws IOException {
        File dot = new File(filepath);
        g = new Parser().read(dot);
    }

    public String toString() {
        String output = "";
        Set<String> labelSet = new HashSet<>();
        for (MutableNode entries : g.nodes()) {
            labelSet.add(entries.name().toString());
        }
        output += "There are " + labelSet.size() + " nodes in the graph.\n";
        output += "The label of the nodes are :-\n";
        for (String label: labelSet) {
            output += label + "\n";
        }
        output = output.substring(0, output.length()-2) + "\n\n";
        String tempOutput = "The edges are :-\n";
        int edgeCount = 0;
        for (Link edge : g.edges()) {
           tempOutput += edge.name().toString().replace("--", " -> ") + "\n";
           edgeCount ++;
        }
        output += "There are " + edgeCount + " edges in the graph.\n" + tempOutput;
        return output;
    }

    void outputGraph(String filepath){
        try {
            FileWriter outputFileWriter = new FileWriter(filepath);
            outputFileWriter.write(toString());
            outputFileWriter.close();
            System.out.println("Details about graph are successfully written to the file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
