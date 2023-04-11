package application;

import guru.nidi.graphviz.model.MutableGraph;

import java.util.LinkedList;
import java.util.Queue;

public class BFS extends GraphSearch{
    private Queue<String> queue = new LinkedList<>();
    BFS(MutableGraph graph) {
        super(graph);
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
    public boolean ifEmpty() {
        return queue.isEmpty();
    }
}
