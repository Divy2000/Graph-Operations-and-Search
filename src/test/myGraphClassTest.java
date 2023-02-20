package test;

import main.myGraphClass;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class myGraphClassTest {
    String filepath = "src/main/testGraph.dot";
    @Test
    void feature1() {
        myGraphClass g = new myGraphClass();
        try {
            g.parseGraph(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean pass = false;
        String[] nodeLabelsToCheck = {"red", "pink", "green", "blue", "white", "yellow", "black", "cyan"};
        String[][] edges = {
                {"green", "black"},
                {"white", "pink"},
                {"cyan", "green"},
                {"yellow", "red"},
                {"white", "yellow"},
                {"pink", "blue"},
                {"yellow", "green"},
                {"pink", "red"},
                {"blue", "black"},
                {"red", "black"},
                {"cyan", "blue"}
        };
        if (g.checkAllNodes(nodeLabelsToCheck) && g.checkEdges(edges)) {
            pass = true;
        }
        assertTrue(pass);
    }

//    @Test
//    void feature2() {
//        myGraphClass g = new myGraphClass();
//        try {
//            g.parseGraph(filepath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        g.addNode("wow");
//        output = g.toString();
//        assertTrue(output.contains("wow"));
//
//        String[] labels = {"node1", "node2"};
//        g.addNodes(labels);
//        output = g.toString();
//        assertTrue(output.contains("node1") && output.contains("node2") );
//
//
//        g.removeNode("wow");
//        output = g.toString();
//        assertFalse(output.contains("wow"));
//
//        g.removeNodes(labels);
//        output = g.toString();
//        assertFalse(output.contains("node1") || output.contains("node2") );
//    }
//
//    @Test
//    void feature3() {
//        myGraphClass g = new myGraphClass();
//        try {
//            g.parseGraph(filepath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        g.addEdge("red", "blue");
//        output = g.toString();
//        assertTrue(output.contains("red--blue"));
//
//        g.removeEdge("red", "blue");
//        output = g.toString();
//        assertFalse(output.contains("red--blue"));
//    }
//
//    @Test
//    void feature4 () {
//        myGraphClass g = new myGraphClass();
//        try {
//            g.parseGraph(filepath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String outputPath = "src/main/java/testGraph_o.dot";
//        g.outputDOTGraph(outputPath);
//        myGraphClass g1 = new myGraphClass();
//        try {
//            g1.parseGraph(outputPath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        assertEquals(g.toString(), g1.toString());
//    }
}