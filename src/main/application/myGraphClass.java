package application;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.Parser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static guru.nidi.graphviz.model.Factory.*;

enum SearchType {
    BFS,
    DFS
}
public class myGraphClass {
    MutableGraph graph = null;

    public boolean checkAllNodes(String[] nodeLabels) {
        Set<String> labelSet = getLabels();
        if (nodeLabels.length != labelSet.size()) {
            return false;
        }
        for(String label : nodeLabels) {
            if (!labelSet.contains(label)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkGivenNodes(String[] nodeLabels) {
        Set<String> labelSet = getLabels();
        for(String label : nodeLabels) {
            if (!labelSet.contains(label)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkEdge(String node1, String node2, Collection<Link> edges) {
        String source;
        String target;
        for (Link edge: edges) {
            source = edge.asLinkSource().name().toString().toLowerCase();
            target = edge.asLinkTarget().name().toString().substring(2).toLowerCase();
            if (node1.equals(source) && node2.equals(target) || node2.equals(source) && node1.equals(target)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkEdges(String[][] edgeNodes) {
        String node1, node2, source, target;
        Collection<Link> edges = graph.edges();
        for (int i = 0; i < edgeNodes.length; i++) {
            node1 = edgeNodes[i][0].toLowerCase();
            node2 = edgeNodes[i][1].toLowerCase();
            if (checkEdge(node1, node2, edges)) return false;
        }
        return true;
    }

    public void parseGraph(String filepath) throws IOException {
        File dotFilePath = new File(filepath);
        graph = new Parser().read(dotFilePath);
    }

    public Set<String> getLabels(){
        Set<String> labelSet = new HashSet<>();
        for (MutableNode entries : graph.nodes()) {
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
        output = output.substring(0, output.length()-1) + "\n\n";
        String tempOutput = "The edges are :-\n";
        int edgeCount = 0;
        for (Link edge : graph.edges()) {
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
        graph.add(mutNode(label).asLinkSource());
    }

    public void addNodes(String[] label){
        for(String label_ : label){
            addNode(label_);
        }
    }

    private MutableGraph addEdge(String srcLabel, String dstLabel, MutableGraph graph){
        Collection<MutableNode> mn = graph.nodes();
        for(MutableNode n: mn){
            if (n.name().toString().equals(srcLabel)){
                n.addLink(dstLabel);
                return graph;
            }
        }
        return graph;
    }

    public void removeNode(String label){
        // Get list of nodes
        MutableGraph tempGraph = getNodeRemoved(label);

        tempGraph = getEdgesRemoved(label, tempGraph);
        graph = tempGraph;
    }

    private MutableGraph getEdgesRemoved(String label, MutableGraph tempGraph) {
        Collection<Link> edges_ = graph.edges();
        for(Link edge: edges_){
            if (!edge.name().toString().contains(label)) {
                String[] nodesOfEdge = edge.name().toString().split("--");
                tempGraph = addEdge(nodesOfEdge[0], nodesOfEdge[1], tempGraph);
            }
        }
        return tempGraph;
    }

    private MutableGraph getNodeRemoved(String label) {
        MutableGraph tempGraph = mutGraph().setDirected(graph.isDirected()).setStrict(graph.isStrict()).setCluster(graph.isCluster()).setName(graph.name().toString());
        Collection<MutableNode> nodes_ = graph.nodes();
        for(MutableNode mNode : nodes_){
            if (!mNode.name().toString().equals(label)){
                tempGraph.add(mutNode(mNode.name().toString()).asLinkSource());
                tempGraph = tempGraph;
            }
        }
        return tempGraph;
    }

    public void removeNodes(String[] label){
        for(String label_: label){
            removeNode(label_);
        }
    }

    public  void addEdge(String srcLabel, String dstLabel){
        Collection<MutableNode> mutableNodes = graph.nodes();

        checkTheNodesOfEdges(srcLabel, dstLabel, mutableNodes);

        mutableNodes = graph.nodes();

        for(MutableNode tempNode: mutableNodes){
            if (tempNode.name().toString().equals(srcLabel)){
                tempNode.addLink(dstLabel);
                break;
            }
        }
    }

    private void checkTheNodesOfEdges(String sourceLabel, String destinationLabel, Collection<MutableNode> mn) {
        boolean srcFlag = true;
        boolean dstFlag = true;

        for(MutableNode n: mn){
            if(n.name().toString().equals(sourceLabel)){
                srcFlag = false;
            }
            if(n.name().toString().equals(destinationLabel)){
                dstFlag = false;
            }
        }

        if(srcFlag){
            addNode(sourceLabel);
        }
        if(dstFlag){
            addNode(destinationLabel);
        }
    }

    public void removeEdge(String sourceLabel, String destinationLabel) {
        MutableGraph tempGraph = mutGraph().setDirected(graph.isDirected()).setStrict(graph.isStrict()).setCluster(graph.isCluster()).setName(graph.name().toString());
        Collection<MutableNode> nodes_ = graph.nodes();
        for(MutableNode mNode : nodes_){
            tempGraph.add(mutNode(mNode.name().toString()).asLinkSource());
        }
        Collection<Link> edges_ = graph.edges();
        for(Link edge: edges_){
            if (!(edge.name().toString().contains(sourceLabel) && edge.name().toString().contains(destinationLabel))) {
                String[] nodesOfEdge = edge.name().toString().split("--");
                tempGraph = addEdge(nodesOfEdge[0], nodesOfEdge[1], tempGraph);
            }
        }
        graph = tempGraph;
    }

    public void outputDOTGraph(String path){
        if (!path.contains(".dot")){
            String[] filepath_ = path.split("\\.");
            path = filepath_[0] + ".dot";
            System.out.println("The file extension was corrected to '.dot' and now the filepath is " + path);
        }
        Graphviz viz = Graphviz.fromGraph(graph);
        try {
            viz.render(Format.DOT).toFile(new File(path));
            System.out.println("The .dot file has been successfully created/updated.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void outputGraphics(String path, String format){
        BufferedImage bufferedImage = Graphviz.fromGraph(graph).height(800).width(800).render(Format.PNG).toImage();
        format = format.toLowerCase();
        if (format.contains("png")){
            try {
                ImageIO.write(bufferedImage, "png", new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(format.contains("jpg") || format.contains("jpeg")){
            try {
                ImageIO.write(bufferedImage, "jpg", new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Currently we only support PNG and JPG formats.");
        }
    }

    private Path getPath(String source, String destination, Map<String, String> parent) {
        Path path = new Path();
        String curr = destination;
        while (curr != null) {
            path.addNode(curr);
            curr = parent.get(curr);
        }
        Collections.reverse(path.getNodes());
        if (!path.getNodes().get(0).equals(source)) {
            return null;
        }
        return path;
    }

    private Map<String, List<String>> getAdjList(addToMap_interface addToMapp, Map<String, List<String>> adjList) {
        if (graph.isDirected() == false) {
            for(Link edge: graph.edges()) {
                String source = edge.name().toString().split("--")[0];
                String destination = edge.name().toString().split("--")[1];
                adjList = addToMapp.addEdge(source, destination, adjList);
                adjList = addToMapp.addEdge(destination, source, adjList);
            }
        } else {
            for(Link edge: graph.edges()) {
                String source = edge.name().toString().split("--")[0];
                String destination = edge.name().toString().split("--")[1];
                adjList = addToMapp.addEdge(source, destination, adjList);
            }
        }
        return adjList;
    }

    interface addToMap_interface {
        Map<String, List<String>> addEdge(String source, String destination, Map<String, List<String>> adjList);
    }

    public Path GraphSearch(Node source, Node destination, SearchType searchType) {
        String src_string = source.name().toString();
        String dst_string = destination.name().toString();

        addToMap_interface addToMapp = (src1, dst1, adjList) -> {
            if (!adjList.containsKey(src1)) {
                adjList.put(src1, new ArrayList<>());
            }
            adjList.get(src1).add(dst1);
            return adjList;
        };

        Map<String, List<String>> adjList = new HashMap<>();

        adjList = getAdjList(addToMapp, adjList);
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        visited.add(src_string);
        parent.put(src_string, null);
        switch(searchType) {
          case BFS:
            Queue<String> queue = new LinkedList<>();

            queue.add(src_string);

            while (!queue.isEmpty()) {
                String curr = queue.poll();
                if (curr.equals(dst_string)) {
                  return getPath(src_string, dst_string, parent);
                }

                for (String neighbor : adjList.getOrDefault(curr, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        parent.put(neighbor, curr);
                        queue.add(neighbor);
                    }
                }
            }
            break;
          case DFS:
            Stack<String> stack = new Stack<>();

            stack.push(src_string);

            while (!stack.isEmpty()) {
                String curr = stack.pop();
                if (curr.equals(dst_string)) {
                    return getPath(src_string, dst_string, parent);
                }

                for (String neighbor : adjList.getOrDefault(curr, new ArrayList<>())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        parent.put(neighbor, curr);
                        stack.push(neighbor);
                    }
                }
              }
              break;
        }
        return null;
      }
}
