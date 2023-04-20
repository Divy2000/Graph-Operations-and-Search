package application;

import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;

import java.util.*;

abstract class GraphSearch_Template {
    private MutableGraph graph;
    private Algorithm searchAlgo;

    GraphSearch_Template(MutableGraph graph, Algorithm searchAlgo) {
        this.graph = graph;
        this.searchAlgo = searchAlgo;
    }

    private Map<String, List<String>> getAdjList(GraphSearch_Template.addToMap_interface addToMapp, Map<String, List<String>> adjList) {
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

    public abstract boolean isEmpty();

    public abstract IntermediateObjects subSearchfunction(Set<String> visited, Map<String, String> parent, List<String> neighbors, String curr);

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

        GraphSearch_Template.addToMap_interface addToMapp = (src1, dst1, adjList) -> {
            if (!adjList.containsKey(src1)) {
                adjList.put(src1, new ArrayList<>());
            }
            adjList.get(src1).add(dst1);
            return adjList;
        };

        while (true) {
            Map<String, List<String>> adjList = new HashMap<>();
            adjList = getAdjList(addToMapp, adjList);
            Map<String, String> parent = new HashMap<>();
            Set<String> visited = new HashSet<>();
            visited.add(src_string);
            parent.put(src_string, null);

            addToDataStructure(src_string);

            while (!isEmpty()) {
                String curr = removeFromDataStructure();
                if (curr.equals(dst_string)) {
                    return getPath(src_string, dst_string, parent);
                }

                List<String> neighbors = adjList.getOrDefault(curr, new ArrayList<>());
                IntermediateObjects objects = subSearchfunction(visited, parent, neighbors, curr);
                visited = objects.visited;
                parent = objects.parent;
                if (this.searchAlgo == Algorithm.RandomWalk) {
                    System.out.println(getPath(src_string, curr, parent));
                }
            }
            if (searchAlgo != Algorithm.RandomWalk) {
                return null;
            }
        }
    }
}

class BFS_Template extends GraphSearch_Template {
    private Queue<String> queue = new LinkedList<>();
    BFS_Template(MutableGraph graph, Algorithm searchAlgo) {
        super(graph, searchAlgo);
    }

    @Override
    public void addToDataStructure(String string) {
        queue.add(string);
    }

    @Override
    public String removeFromDataStructure() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public IntermediateObjects subSearchfunction(Set<String> visited, Map<String, String> parent, List<String> neighbors, String curr) {
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parent.put(neighbor, curr);
                addToDataStructure(neighbor);
            }
        }
        IntermediateObjects objects = new IntermediateObjects(visited, parent);
        return objects;
    }
}

class DFS_Template extends GraphSearch_Template {

    private Stack<String> stack = new Stack<>();
    DFS_Template(MutableGraph graph, Algorithm searchAlgo) {
        super(graph, searchAlgo);
    }
    @Override
    public void addToDataStructure(String string) {
        stack.add(string);
    }

    @Override
    public String removeFromDataStructure() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public IntermediateObjects subSearchfunction(Set<String> visited, Map<String, String> parent, List<String> neighbors, String curr) {
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parent.put(neighbor, curr);
                addToDataStructure(neighbor);
            }
        }
        IntermediateObjects objects = new IntermediateObjects(visited, parent);
        return objects;
    }
}

class RandomWalk_Template extends GraphSearch_Template {
    RandomWalk_Template(MutableGraph graph, Algorithm searchAlgo) {
        super(graph, searchAlgo);
    }
    private Stack<String> stack = new Stack<>();
    private Random random = new Random();

    @Override
    public void addToDataStructure(String string) {
        stack.add(string);
    }

    @Override
    public String removeFromDataStructure() {
        int randomIndex = random.nextInt(stack.size());
        return stack.remove(randomIndex);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public IntermediateObjects subSearchfunction(Set<String> visited, Map<String, String> parent, List<String> neighbors, String curr) {
        if (neighbors.size() > 0) {
            String neighbor = neighbors.get(random.nextInt(neighbors.size()));
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parent.put(neighbor, curr);
                addToDataStructure(neighbor);
            }
        }
        IntermediateObjects objects = new IntermediateObjects(visited, parent);
        return objects;
    }
}
