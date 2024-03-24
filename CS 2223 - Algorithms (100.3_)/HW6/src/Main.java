import java.util.List;
public class Main {

    public static void main(String[] args) {
        // Task 3: First Solutions
         System.out.println("Task 3: First Solutions");
         for (int n = 4; n <= 100; n++) {
             // Find the first solution for the n-Queens problem
             int[] firstSolution = NQueensSolver.findFirstSolution(n);
             if (firstSolution != null) {
                 // Print out the first solution in yellow
                 System.out.println("\n\n\033[33mFirst solution for n = " + n + ":\033[0m");
                 // Print out the board for the first solution
                 NQueensSolver.printBoard(firstSolution);
                 // Print out the indices for the first solution
                 NQueensSolver.printIndices(firstSolution);
             } else {
                 System.out.println("No solution found for n = " + n);
             }
         }

        // Task 4: All Solutions
        System.out.println("\nTask 4: All Solutions");
        for (int n = 4; n <= 20; n++) {
            // Find all solutions for the n-Queens problem
            List<int[]> allSolutions = NQueensSolver.findAllSolutions(n);
            // Print out the number of solutions found
            System.out.println("There are " + allSolutions.size() + " solutions to the " + n + "-Queens Problem.");
        }
    }

}

// This program solves the n-Queens problem for values of n from 4 to 100 and prints out the first solution for each value of n.
// It also finds all solutions for values of n from 4 to 20 and prints out the number of solutions found for each value of n.
