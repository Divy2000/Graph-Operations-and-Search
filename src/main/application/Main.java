package application;

import java.io.IOException;
import java.util.Scanner;
import static guru.nidi.graphviz.model.Factory.node;

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
        String nodeLabel, nodeLabels, node1, node2, filepath;
        String[] nodeLabelList;
        while (running) {
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
                System.out.println("10 -> Perform BFS search or DFS search in the graph");
                System.out.println("Q -> Exit the software");
                System.out.println("Enter your choice");

                String choice = inputScanner.nextLine();
                switch (choice){
                    case "0":
                        System.out.println(g);
                        break;
                    case "1":
                        System.out.println("Enter the filepath of the output file: ");
                        String outputTxtPath = inputScanner.nextLine();
                        g.outputGraph(outputTxtPath);
                        break;
                    case "2":
                        System.out.println("Enter the label of the node you want to add: ");
                        nodeLabel = inputScanner.nextLine();
                        g.addNode(nodeLabel);
                        break;
                    case "3":
                        System.out.println("Enter the labels of node you want to add separated by comma ('a,b,c'): ");
                        nodeLabels = inputScanner.nextLine();
                        nodeLabelList = nodeLabels.split(",");
                        g.addNodes(nodeLabelList);
                        break;
                    case "4":
                        System.out.println("Enter the first/source node of the edge");
                        node1 = inputScanner.nextLine();
                        System.out.println("Enter the second/target node of the edge");
                        node2 = inputScanner.nextLine();
                        g.addEdge(node1, node2);
                        break;
                    case "5":
                        System.out.println("Enter the label of the node you want to remove: ");
                        nodeLabel = inputScanner.nextLine();
                        g.removeNode(nodeLabel);
                        break;
                    case "6":
                        System.out.println("Enter the labels of node you want to remove separated by comma ('a,b,c'): ");
                        nodeLabels = inputScanner.nextLine();
                        nodeLabelList = nodeLabels.split(",");
                        g.removeNodes(nodeLabelList);
                        break;
                    case "7":
                        System.out.println("Enter the first/source node of the edge");
                        node1 = inputScanner.nextLine();
                        System.out.println("Enter the second/target node of the edge");
                        node2 = inputScanner.nextLine();
                        g.removeEdge(node1, node2);
                        break;
                    case "8":
                        System.out.println("Enter the path of the output .dot file");
                        filepath = inputScanner.nextLine();
                        g.outputDOTGraph(filepath);
                        break;
                    case "9":
                        System.out.println("Enter path to output the image of the final graph");
                        filepath = inputScanner.nextLine();
                        String[] outputImageDetails = filepath.split("\\.");
                        g.outputGraphics(filepath, outputImageDetails[1]);
                        break;
                    case "10":
                        System.out.println("Enter the first/source node");
                        node1 = inputScanner.nextLine();
                        System.out.println("Enter the second/target node");
                        node2 = inputScanner.nextLine();
                        System.out.println("Enter the search type 'bfs', 'dfs' or 'rw'(for random walk search)");
                        boolean validSearchType = false;
                        Algorithm algo = null;
                        while (!validSearchType) {
                          String searchType_s = inputScanner.nextLine();
                          if (searchType_s.toLowerCase().equals("bfs")) {
                            algo = Algorithm.BFS;
                            validSearchType = true;
                          } else if (searchType_s.toLowerCase().equals("dfs")) {
                            algo = Algorithm.DFS;
                            validSearchType = true;
                          } else if (searchType_s.toLowerCase().equals("rw")) {
                              algo = Algorithm.RandomWalk;
                              validSearchType = true;
                          }else {
                            System.out.println("Please enter valid search type");
                          }
                        }
                        Path path = g.GraphSearch(node(node1), node(node2), algo);
                        if (path != null) {
                            System.out.println(path);
                        } else {
                            System.out.println("The path cannot be found");
                        }
                        break;
                    case "Q":
                        running = false;
                        break;
                    default:
                        System.out.println("Enter the correct choice");
                }
            }
    }
}