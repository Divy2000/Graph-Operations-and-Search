package application;

import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.Node;

import java.util.*;

class GraphSearch_Strategy {
    private MutableGraph graph;
    GraphSearch_interface search;
    Algorithm search_algo;

    GraphSearch_Strategy(MutableGraph graph, Algorithm search_algo) {
        this.graph = graph;
        this.search_algo = search_algo;
        if (search_algo == Algorithm.BFS) {
            this.search = new BFS_Strategy();
        } else if (search_algo == Algorithm.DFS) {
            this.search = new DFS_Strategy();
        } else if (search_algo == Algorithm.RandomWalk) {
            this.search = new RandomWalk_Strategy();
        }
    }

    protected Map<String, List<String>> getAdjList(GraphSearch_Strategy.addToMap_interface addToMapp, Map<String, List<String>> adjList) {
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

    protected Path getPath(String source, String destination, Map<String, String> parent) {
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

        GraphSearch_Strategy.addToMap_interface addToMapp = (src1, dst1, adjList) -> {
            if (!adjList.containsKey(src1)) {
                adjList.put(src1, new ArrayList<>());
            }
            adjList.get(src1).add(dst1);
            return adjList;
        };

        while(true) {
            Map<String, List<String>> adjList = new HashMap<>();
            adjList = getAdjList(addToMapp, adjList);
            Map<String, String> parent = new HashMap<>();
            Set<String> visited = new HashSet<>();
            visited.add(src_string);
            parent.put(src_string, null);

            this.search.addToDataStructure(src_string);

            while (!this.search.isEmpty()) {
                String curr = this.search.removeFromDataStructure();
                if (curr.equals(dst_string)) {
                    return getPath(src_string, dst_string, parent);
                }
                List<String> neighbors = adjList.getOrDefault(curr, new ArrayList<>());
                IntermediateObjects objects = this.search.subSearchfunction(visited, parent, neighbors, curr);
                visited = objects.visited;
                parent = objects.parent;
                if (this.search_algo == Algorithm.RandomWalk) {
                    System.out.println(getPath(src_string, curr, parent));
                }
            }
            if (this.search_algo != Algorithm.RandomWalk) {
                return null;
            }
        }
    }
}
interface GraphSearch_interface
{
    void addToDataStructure(String string);

    String removeFromDataStructure();

    boolean isEmpty();

    IntermediateObjects subSearchfunction(Set<String> visited, Map<String, String> parent, List<String> neighbors, String curr);
}

class BFS_Strategy implements GraphSearch_interface
{
    private Queue<String> queue = new LinkedList<>();
    public void addToDataStructure(String string) {
        queue.add(string);
    }

    public String removeFromDataStructure() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public IntermediateObjects subSearchfunction(Set<String> visited, Map<String, String> parent,List<String> neighbors, String curr) {
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

class DFS_Strategy implements GraphSearch_interface {

    private Stack<String> stack = new Stack<>();

    public void addToDataStructure(String string) {
        stack.add(string);
    }

    public String removeFromDataStructure() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

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

class RandomWalk_Strategy implements GraphSearch_interface {
    private Stack<String> stack = new Stack<>();
    private Random random = new Random();

    public void addToDataStructure(String string) {
        stack.add(string);
    }

    public String removeFromDataStructure() {
        int randomIndex = random.nextInt(stack.size());
        return stack.remove(randomIndex);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
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
