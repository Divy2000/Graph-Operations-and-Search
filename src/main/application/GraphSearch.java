package application;

import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;

import java.util.*;

abstract class GraphSearch {
    private MutableGraph graph;

    GraphSearch (MutableGraph graph) {
        this.graph = graph;
    }

    private Map<String, List<String>> getAdjList(GraphSearch.addToMap_interface addToMapp, Map<String, List<String>> adjList) {
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

    interface addToMap_interface {
        Map<String, List<String>> addEdge(String source, String destination, Map<String, List<String>> adjList);
    }

    public abstract void addToDataStructure(String string);

    public abstract String removeFromDataStructure();

    public abstract boolean ifEmpty();

    public Path GraphSearch(Node source, Node destination, Set<String> labels) {
        String src_string = source.name().toString();
        String dst_string = destination.name().toString();

        if (!labels.contains(src_string)) {
            System.out.println("Source label is incorrect");
            return null;
        } else if (!labels.contains(dst_string)) {
            System.out.println("Target label is incorrect");
            return null;
        }

        GraphSearch.addToMap_interface addToMapp = (src1, dst1, adjList) -> {
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

        addToDataStructure(src_string);

        while (!ifEmpty()) {
            String curr = removeFromDataStructure();
            if (curr.equals(dst_string)) {
                return getPath(src_string, dst_string, parent);
            }

            for (String neighbor : adjList.getOrDefault(curr, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, curr);
                    addToDataStructure(neighbor);
                }
            }
        }
        return null;
    }
}
