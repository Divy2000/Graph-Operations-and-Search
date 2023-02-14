import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class myGraphClassTest {
    String output;
    String filepath = "src/main/java/testGraph.dot";
    @Test
    void feature1() {
        myGraphClass g = new myGraphClass();
        try {
            g.parseGraph(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        output = g.toString();
        String expectedOutput = "There are 8 nodes in the graph.\n" +
                "The label of the nodes are :-\n" +
                "red\n" +
                "pink\n" +
                "green\n" +
                "blue\n" +
                "white\n" +
                "yellow\n" +
                "black\n" +
                "cyan\n" +
                "\n" +
                "There are 12 edges in the graph.\n" +
                "The edges are :-\n" +
                "green--black\n" +
                "white--pink\n" +
                "white--cyan\n" +
                "cyan--green\n" +
                "yellow--red\n" +
                "white--yellow\n" +
                "pink--blue\n" +
                "yellow--green\n" +
                "pink--red\n" +
                "blue--black\n" +
                "red--black\n" +
                "cyan--blue\n";
        assertEquals(output, expectedOutput);
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
        output = g.toString();
        assertTrue(output.contains("wow"));

        String[] labels = {"node1", "node2"};
        g.addNodes(labels);
        output = g.toString();
        assertTrue(output.contains("node1") && output.contains("node2") );


        g.removeNode("wow");
        output = g.toString();
        assertFalse(output.contains("wow"));

        g.removeNodes(labels);
        output = g.toString();
        assertFalse(output.contains("node1") || output.contains("node2") );
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
        output = g.toString();
        assertTrue(output.contains("red--blue"));

        g.removeEdge("red", "blue");
        output = g.toString();
        assertFalse(output.contains("red--blue"));
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
        myGraphClass g1 = new myGraphClass();
        try {
            g1.parseGraph(outputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(g.toString(), g1.toString());
    }
}