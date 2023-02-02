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
            System.out.print("\n");
            g.parseGraph(graphFilePath);
            String graphString = g.toString();
            System.out.println(graphString);
            g.outputGraph(outputTxtPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}