import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        myGraphClass g = new myGraphClass();
            boolean running = true;
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Enter the path to the dot file");
            String graphFilePath = inputScanner.nextLine();
            try {
                g.parseGraph(graphFilePath);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(running) {
                System.out.println("\nChoose one of the following options");
                System.out.println("0 -> Print graph details");
                System.out.println("1 -> Output graph details to a text file");
                System.out.println("2 -> Add a node to the graph");
                System.out.println("3 -> Add multiple nodes to the graph");
                System.out.println("4 -> Add an edge to the graph");
                System.out.println("5 -> Remove a node from the graph");
                System.out.println("6 -> Remove multiple nodes from the graph");
                System.out.println("7 -> Remove an edge from the graph");
                System.out.println("8 -> Output graph to .dot file");
                System.out.println("9 -> Output graph to an image file");
                System.out.println("Enter your choice");

                int choice = inputScanner.nextInt();
                switch (choice){
                    case 0:
                        String graphDetails = g.toString();
                        System.out.println(graphDetails);
                        break;
                    case 1:
                        System.out.println("Enter the filepath of the output file: ");
                        String outputTxtPath = inputScanner.nextLine();
                        g.outputGraph(outputTxtPath);
                        break;
                    case 2:
                        System.out.println("Enter the label of the node you want to add: ");
                        String nodeLabel = inputScanner.nextLine();
                        g.addNode(nodeLabel);
                        break;
                    case 3:
                        System.out.println("Enter the labels of node you want to add separated by comma ('a,b,c'): ");
                        String nodeLabels = inputScanner.nextLine();
                        String[] nodeLabelList = nodeLabels.split(",");
                        g.removeNodes(nodeLabelList);
                        break;
                    case 4:
                        System.out.println("Enter the first/source node of the link");
                        inputScanner.nextLine();
                        String node1 = inputScanner.nextLine();
                        System.out.println("Enter the second/target node of the link");
                        String node2 = inputScanner.nextLine();
                        g.addEdge(node1, node2);
                        break;
                }
            }
//            System.out.print("Enter the filepath of the output file: ");
//            String outputTxtPath = inputScanner.nextLine();
//            System.out.println();
//            g.parseGraph(graphFilePath);
//            String graphString = g.toString();
//            System.out.println(graphString);
//            g.outputGraph(outputTxtPath);
//            g.addNode("test");
//            g.removeNode("green");
//            String[] nodesToAdd = {"node1", "node2"};
//            g.addNodes(nodesToAdd);
//            String[] nodesToRemove = {"test", "node1"};
//            g.removeNodes(nodesToRemove);
//            g.addEdge("test", "a");
//            g.removeEdge("yellow", "red");
//            System.out.print("Enter the filepath to the output code in '.dot' format: ");
//            String outputDotPath = inputScanner.nextLine();
//            System.out.println();
//            g.outputDOTGraph(outputDotPath);
//            System.out.print("Enter path to output the image of the final graph : ");
//            String outputImagePath = inputScanner.nextLine();
//            String[] outputImageDetails = outputImagePath.split("\\.");
//            g.outputGraphics(outputImagePath, outputImageDetails[1]);
    }
}