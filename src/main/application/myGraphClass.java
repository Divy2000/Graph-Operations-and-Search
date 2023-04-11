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
    MutableGraph g = null;

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

    private static boolean checkEdge(String node1, String node2, Collection<Link> edges, boolean flag) {
        String source;
        String target;
        for (Link edge: edges) {
            source = edge.asLinkSource().name().toString().toLowerCase();
            target = edge.asLinkTarget().name().toString().substring(2).toLowerCase();
            if (node1.equals(source) && node2.equals(target) || node2.equals(source) && node1.equals(target)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return true;
        }
        return false;
    }

    public boolean checkEdges(String[][] edgeNodes) {
        String node1, node2, source, target;
        Collection<Link> edges = g.edges();
        for (int i = 0; i < edgeNodes.length; i++) {
            node1 = edgeNodes[i][0].toLowerCase();
            node2 = edgeNodes[i][1].toLowerCase();
            boolean flag = false;
            if (checkEdge(node1, node2, edges, flag)) return false;
        }
        return true;
    }

    public void parseGraph(String filepath) throws IOException {
        File dot = new File(filepath);
        g = new Parser().read(dot);
    }

    public Set<String> getLabels(){
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
        output = output.substring(0, output.length()-1) + "\n\n";
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
        MutableGraph g1 = getNodeRemoved(label);

        g1 = getEdgesRemoved(label, g1);
        g = g1;
    }

    private MutableGraph getEdgesRemoved(String label, MutableGraph g1) {
        Collection<Link> edges_ = g.edges();
        for(Link edge: edges_){
            if (!edge.name().toString().contains(label)) {
                String[] nodesOfEdge = edge.name().toString().split("--");
                g1 = addEdge(nodesOfEdge[0], nodesOfEdge[1], g1);
            }
        }
        return g1;
    }

    private MutableGraph getNodeRemoved(String label) {
        MutableGraph g1 = mutGraph().setDirected(g.isDirected()).setStrict(g.isStrict()).setCluster(g.isCluster()).setName(g.name().toString());
        Collection<MutableNode> nodes_ = g.nodes();
        for(MutableNode mNode : nodes_){
            if (!mNode.name().toString().equals(label)){
                g1.add(mutNode(mNode.name().toString()).asLinkSource());
                g1 = g1;
            }
        }
        return g1;
    }

    public void removeNodes(String[] label){
        for(String label_: label){
            removeNode(label_);
        }
    }

    public  void addEdge(String srcLabel, String dstLabel){
        Collection<MutableNode> mn = g.nodes();

        checkTheNodesOfEdges(srcLabel, dstLabel, mn);

        mn = g.nodes();

        for(MutableNode n: mn){
            if (n.name().toString().equals(srcLabel)){
                n.addLink(dstLabel);
                break;
            }
        }
    }

    private void checkTheNodesOfEdges(String srcLabel, String dstLabel, Collection<MutableNode> mn) {
        boolean srcFlag = true;
        boolean dstFlag = true;

        for(MutableNode n: mn){
            if(n.name().toString().equals(srcLabel)){
                srcFlag = false;
            }
            if(n.name().toString().equals(dstLabel)){
                dstFlag = false;
            }
        }

        if(srcFlag){
            addNode(srcLabel);
        }
        if(dstFlag){
            addNode(dstLabel);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        MutableGraph g1 = mutGraph().setDirected(g.isDirected()).setStrict(g.isStrict()).setCluster(g.isCluster()).setName(g.name().toString());
        Collection<MutableNode> nodes_ = g.nodes();
        for(MutableNode mNode : nodes_){
            g1.add(mutNode(mNode.name().toString()).asLinkSource());
            g1 = g1;
        }
        Collection<Link> edges_ = g.edges();
        for(Link edge: edges_){
            if (!(edge.name().toString().contains(srcLabel) && edge.name().toString().contains(dstLabel))) {
                String[] nodesOfEdge = edge.name().toString().split("--");
                g1 = addEdge(nodesOfEdge[0], nodesOfEdge[1], g1);
            }
        }
        g = g1;
    }

    public void outputDOTGraph(String path){
        if (!path.contains(".dot")){
            String[] filepath_ = path.split("\\.");
            path = filepath_[0] + ".dot";
            System.out.println("The file extension was corrected to '.dot' and now the filepath is " + path);
        }
        Graphviz viz = Graphviz.fromGraph(g);
        try {
            viz.render(Format.DOT).toFile(new File(path));
            System.out.println("The .dot file has been successfully created/updated.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void outputGraphics(String path, String format){
        BufferedImage bufferedImage = Graphviz.fromGraph(g).height(800).width(800).render(Format.PNG).toImage();
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

    private Path getPath(String src, String dst, Map<String, String> parent) {
        Path path = new Path();
        String curr = dst;
        while (curr != null) {
            path.addNode(curr);
            curr = parent.get(curr);
        }
        Collections.reverse(path.getNodes());
        if (!path.getNodes().get(0).equals(src)) {
            return null;
        }
        return path;
    }

    private Map<String, List<String>> getAdjList(addToMap_i addToMapp, Map<String, List<String>> adjList) {
        if (g.isDirected() == false) {
            for(Link edge: g.edges()) {
                String source = edge.name().toString().split("--")[0];
                String dest = edge.name().toString().split("--")[1];
                adjList = addToMapp.addEdge(source, dest, adjList);
                adjList = addToMapp.addEdge(dest, source, adjList);
            }
        } else {
            for(Link edge: g.edges()) {
                String source = edge.name().toString().split("--")[0];
                String dest = edge.name().toString().split("--")[1];
                adjList = addToMapp.addEdge(source, dest, adjList);
            }
        }
        return adjList;
    }

    interface addToMap_i {
        Map<String, List<String>> addEdge(String src, String dst, Map<String, List<String>> adjList);
    }

    public Path GraphSearch(Node src, Node dst, SearchType searchType) {
        String src_s = src.name().toString();
        String dst_s = dst.name().toString();

        addToMap_i addToMapp = (src1, dst1, adjList) -> {
            if (!adjList.containsKey(src1)) {
                adjList.put(src1, new ArrayList<>());
            }
            adjList.get(src1).add(dst1);
            return adjList;
        };

        Map<String, List<String>> adjList = new HashMap<>();

        Path p = new Path();
        adjList = getAdjList(addToMapp, adjList);
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        visited.add(src_s);
        parent.put(src_s, null);
        switch(searchType) {
          case BFS:
            Queue<String> queue = new LinkedList<>();

            queue.add(src_s);

            while (!queue.isEmpty()) {
                String curr = queue.poll();
                if (curr.equals(dst_s)) {
                  return getPath(src_s, dst_s, parent);
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

            stack.push(src_s);

            while (!stack.isEmpty()) {
                String curr = stack.pop();
                if (curr.equals(dst_s)) {
                    return getPath(src_s, dst_s, parent);
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
