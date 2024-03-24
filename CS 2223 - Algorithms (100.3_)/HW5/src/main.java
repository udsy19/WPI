import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String fileName = "Moby-Dick-Chapter-1-groomed.txt";
        hash hashTable = new hash();

        try {
            hashTable.build(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Error: The specified file '" + fileName + "' does not exist.");
            System.exit(1);
        }

        // Calling the methods related to Question 3
        System.out.println("\n\033[33m" + "Question 3:" + "\033[0m\n");
        hashTable.nonEmptyAddressesAndLoadFactor();
        hashTable.longestEmptyArea();
        hashTable.longestCluster();
        hashTable.hashValueWithMostWords();
        hashTable.wordFarthestFromHashValue();

        // Declaring the Dijkstra Matrix
        int DijkstraMatrix[][] = {
                { 0, 54, 11, 13, 0, 0, 0, 0, 0, 0 },
                { 54, 0, 37, 0, 3, 0, 102, 0, 0, 0 },
                { 11, 37, 0, 10, 36, 19, 0, 0, 0, 0 },
                { 13, 0, 10, 0, 0, 18, 0, 0, 7, 0 },
                { 0, 3, 36, 0, 0, 15, 124, 123, 0, 0 },
                { 0, 0, 19, 18, 15, 0, 0, 138, 8, 0 },
                { 0, 102, 0, 0, 124, 0, 0, 9, 0, 72 },
                { 0, 0, 0, 0, 123, 138, 9, 0, 146, 67 },
                { 0, 0, 0, 7, 0, 8, 0, 146, 0, 213 },
                { 0, 0, 0, 0, 0, 0, 72, 67, 213, 0 }
        };

        // Get input for start and destination nodes
        System.out.println("\n\033[33mQuestion: 4\033[0m");
        Scanner scanner = new Scanner(System.in);
        
        int startNode, destNode;
        while(true) {
            System.out.print("\033[0;35;3mEnter the start node: \033[0m");
            if(scanner.hasNextInt()) {
                startNode = scanner.nextInt();
                if(startNode >= 0 && startNode <= 9) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("\033[31mError: Invalid input. Please enter a number between 0 and 9.\033[0m");
        }
        
        while(true) {
            System.out.print("\u001B[35;3mEnter the destination node: \u001B[0m");
            if(scanner.hasNextInt()) {
                destNode = scanner.nextInt();
                if(destNode >= 0 && destNode <= 9) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.println("\033[31mError: Invalid input. Please enter a number between 0 and 9.\033[0m");
        }
        

        // Calling the methods in the Dijkstra class
        Dijkstra.dijkstra_operations(DijkstraMatrix, startNode, destNode);
    }

}
