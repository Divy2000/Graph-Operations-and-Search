package test;

import application.myGraphClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class myGraphClassTest {
    String filepath = "src/main/input2.dot";
    @Test
    void feature1() {
        myGraphClass g = new myGraphClass();
        try {
            g.parseGraph(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean pass = false;
        String[] nodeLabelsToCheck = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[][] edges = {
                {"a", "b"},
                {"b", "c"},
                {"c", "d"},
                {"d", "a"},
                {"a", "e"},
                {"e", "f"},
                {"e", "g"},
                {"f", "h"},
                {"g", "h"}
        };
        if (g.checkAllNodes(nodeLabelsToCheck) && g.checkEdges(edges)) {
            pass = true;
        }
        Assertions.assertTrue(pass);
    }

    @Test
    void feature2() {
        myGraphClass g = new myGraphClass();
        try {
            g.parseGraph(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.addNode("wow");
        String[] nodeToCheck = {"wow"};
        Assertions.assertTrue(g.checkGivenNodes(nodeToCheck));

        String[] labels = {"node1", "node2"};
        g.addNodes(labels);
        Assertions.assertTrue(g.checkGivenNodes(labels) );


        g.removeNode("wow");
        Assertions.assertFalse(g.checkGivenNodes(nodeToCheck));

        g.removeNodes(labels);
        Assertions.assertFalse(g.checkGivenNodes(labels));
    }

    @Test
    void feature3() {
        myGraphClass g = new myGraphClass();
        try {
            g.parseGraph(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.addEdge("d", "e");
        String[][] edges = {
                {"d", "e"},
        };
        Assertions.assertTrue(g.checkEdges(edges));

        g.removeEdge("d", "e");
        Assertions.assertFalse(g.checkEdges(edges));
    }

    @Test
    void feature4 () {
        myGraphClass g = new myGraphClass();
        try {
            g.parseGraph(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String outputPath = "src/main/output2.dot";
        g.outputDOTGraph(outputPath);
        g.outputGraphics("ex1.png", "png");
        File f = new File("ex1.png");
        Assertions.assertTrue(f.exists());
        myGraphClass g1 = new myGraphClass();
        try {
            g1.parseGraph(outputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean passG = false;
        String[] nodeLabelsToCheck = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[][] edges = {
                {"a", "b"},
                {"b", "c"},
                {"c", "d"},
                {"d", "a"},
                {"a", "e"},
                {"e", "f"},
                {"e", "g"},
                {"f", "h"},
                {"g", "h"}
        };
        if (g.checkAllNodes(nodeLabelsToCheck) && g.checkEdges(edges)) {
            passG = true;
        }

        boolean passG1 = false;
        if (g1.checkAllNodes(nodeLabelsToCheck) && g1.checkEdges(edges)) {
            passG1 = true;
        }
        Assertions.assertTrue(passG);
        Assertions.assertTrue(passG1);
    }
}