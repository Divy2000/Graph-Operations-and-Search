package application;

import java.util.Map;
import java.util.Set;

class IntermediateObjects {
    Set<String> visited;
    Map<String, String> parent;
    IntermediateObjects (Set<String> visited, Map<String, String> parent) {
        this.visited = visited;
        this.parent = parent;
    }
}