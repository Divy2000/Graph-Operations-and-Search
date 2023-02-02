import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.Parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.*;

public class myGraphClass {
    MutableGraph g = null;
    public void parseGraph(String filepath) throws IOException {
        File dot = new File(filepath);
        g = new Parser().read(dot);
        try {
            Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(new File("example/ex1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getLabels(){
        Set<String> labelSet = new HashSet<>();
        for (MutableNode entries : g.nodes()) {
            labelSet.add(entries.name().toString());
        }
        return labelSet;
    }

    public String toString() {
        String output = "";
        Set<String> labelSet = getLabels();
        output += "There are " + labelSet.size() + " nodes in the graph.\n";
        output += "The label of the nodes are :-\n";
        for (String label: labelSet) {
            output += label + "\n";
        }
        output = output.substring(0, output.length()-2) + "\n\n";
        String tempOutput = "The edges are :-\n";
        int edgeCount = 0;
        for (Link edge : g.edges()) {
           tempOutput += edge.name() + "\n";
           edgeCount ++;
        }
        output += "There are " + edgeCount + " edges in the graph.\n" + tempOutput;
        return output;
    }

    public void outputGraph(String filepath){
        try {
            FileWriter outputFileWriter = new FileWriter(filepath);
            outputFileWriter.write(toString());
            outputFileWriter.close();
            System.out.println("Details about graph are successfully written to the file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addNode(String label){
        Set<String> labelSet = getLabels();
        if (labelSet.contains(label)) {
            System.out.println("The label is already present");
            return;
        }
        g.add(mutNode(label).asLinkSource());
    }

    private MutableGraph addNode(String label, MutableGraph g) {
        g.add(mutNode(label).asLinkSource());
        return g;
    }

    public void addNodes(String[] label){
        for(String label_ : label){
            addNode(label_);
        }
    }

    private MutableGraph addEdge(String srcLabel, String dstLabel, MutableGraph g){
        Collection<MutableNode> mn = g.nodes();
        for(MutableNode n: mn){
            if (n.name().toString().equals(srcLabel)){
                n.addLink(dstLabel);
                break;
            }
        }
        return g;
    }

    public void removeNode(String label){
        // Get list of nodes
        MutableGraph g1 = mutGraph().setDirected(g.isDirected()).setStrict(g.isStrict()).setCluster(g.isCluster()).setName(g.name().toString());
        Collection<MutableNode> nodes_ = g.nodes();
        for(MutableNode mNode : nodes_){
            if (!mNode.name().toString().equals(label)){
                g1 = addNode(mNode.name().toString(), g1);
            }
        }

        Collection<Link> edges_ = g.edges();
        for(Link edge: edges_){
            if (!edge.name().toString().contains(label)) {
                String[] nodesOfEdge = edge.name().toString().split("--");
                g1 = addEdge(nodesOfEdge[0], nodesOfEdge[1], g1);
            }
        }
        g = g1;
    }

    public void removeNodes(String[] label){
        for(String label_: label){
            removeNode(label_);
        }
    }
}
