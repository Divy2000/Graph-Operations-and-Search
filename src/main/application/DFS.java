package application;

import guru.nidi.graphviz.model.MutableGraph;

import java.util.Stack;

public class DFS extends GraphSearch{

    private Stack<String> stack = new Stack<>();
    DFS(MutableGraph graph) {
        super(graph);
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
}
