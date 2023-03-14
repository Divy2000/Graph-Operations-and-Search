package test;

import main.myGraphClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
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

        g.addEdge("red", "blue");
        String[][] edges = {
                {"red", "blue"},
        };
        Assertions.assertTrue(g.checkEdges(edges));

        g.removeEdge("red", "blue");
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
        String outputPath = "src/main/java/testGraph_o.dot";
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