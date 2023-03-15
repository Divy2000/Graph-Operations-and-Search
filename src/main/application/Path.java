package application;

import guru.nidi.graphviz.model.Node;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<String> nodes;

    public Path() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(String node) {
        nodes.add(node);
    }

    public List<String> getNodes() {
        return nodes;
    }

    public boolean contains(String node) {
        return nodes.contains(node);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.size(); i++) {
            sb.append(nodes.get(i));
            if (i < nodes.size() - 1) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }
}
