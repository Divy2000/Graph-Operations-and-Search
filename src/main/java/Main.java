import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        myGraphClass g = new myGraphClass();
        try {
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Enter the filepath of the graph (.dot file) : ");
            String graphFilePath = inputScanner.nextLine();
            System.out.print("Enter the filepath of the output file: ");
            String outputTxtPath = inputScanner.nextLine();
            System.out.println();
            g.parseGraph(graphFilePath);
            String graphString = g.toString();
            System.out.println(graphString);
            g.outputGraph(outputTxtPath);
            g.addNode("test");
            g.removeNode("green");
            String[] nodesToAdd = {"node1", "node2"};
            g.addNodes(nodesToAdd);
            String[] nodesToRemove = {"test", "node1"};
            g.removeNodes(nodesToRemove);
            g.addEdge("peach", "orange");
            g.removeEdge("yellow", "red");
            System.out.print("Enter the filepath to the output code in '.dot' format: ");
            String outputDotPath = inputScanner.nextLine();
            System.out.println();
            g.outputDOTGraph(outputDotPath);
            System.out.print("Enter path to output the image of the final graph : ");
            String outputImagePath = inputScanner.nextLine();
            String[] outputImageDetails = outputImagePath.split("\\.");
            g.outputGraphics(outputImagePath, outputImageDetails[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}