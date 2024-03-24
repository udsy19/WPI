import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;



/**

 The code provides several methods for solving the N-Queens problem, generating and printing solutions. Here is a brief description of each method:

 - `findFirstSolution(int n)`: Finds the first solution to the N-Queens problem for a board of size `n`, and returns an array representing the board. If no solution is found, returns `null`.

 - `findAllSolutions(int n)`: Finds all solutions to the N-Queens problem for a board of size `n`, and returns a list of arrays representing the boards. If no solutions are found, returns an empty list.

 - `solve(int[] board, int row)`: Recursively solves the N-Queens problem starting from the given `board` and `row`. Returns `true` if a solution is found, and `false` otherwise.

 - `solveAll(int[] board, int row, List<int[]> solutions)`: Recursively solves the N-Queens problem starting from the given `board` and `row`, and adds each solution found to the given `solutions` list.

 - `NextLegalPosition(int[] board, int n)`: Generates the next legal position of the N-Queens problem, given the current `board` and `n`. Returns an array representing the new position. If no legal position can be found, returns an empty board.

 - `Successor(int[] board, int n)`: Generates the successor position of the N-Queens problem, given the current `board` and `n`. Returns an array representing

 */

public class NQueensSolver {

    // Solves the N-Queens problem and returns the first solution found.
    public static int[] findFirstSolution(int n) {
        int[] board = new int[n];  // Initialize an array to represent the board with n rows and columns
        Deque<Integer> stack = new ArrayDeque<>(); // Initialize a stack to keep track of columns where queens are placed
        int row = 0, col = 0; // Initialize row and column to 0

        // Loop through each row
        while (row < n) {
            // Loop through each column in the current row
            while (col < n) {
                board[row] = col + 1; // Place a queen in the current row and column
                // Check if the current position is legal (i.e., no other queens can attack it)
                if (CheckLegalPosition.isLegalPosition(board, row)) {
                    stack.push(col); // Push the current column onto the stack
                    row++; // Move on to the next row
                    col = 0; // Start from the first column in the new row
                    break; // Break out of the inner loop
                } else {
                    col++; // Move on to the next column in the current row
                }
            }

            // If we've tried all columns in the current row and haven't found a legal position,
            // backtrack to the previous row and try a different column in that row
            if (col == n) {
                if (stack.isEmpty()) {
                    return null; // If the stack is empty, we've tried all possible combinations and haven't found a solution, so return null
                } else {
                    col = stack.pop() + 1; // Pop the last column from the stack and try the next column in that row
                    row--; // Move back to the previous row
                }
            }
        }
        return board; // Return the final board configuration (i.e., the first solution found)
    }



    // Solves the N-Queens problem and returns all solutions found.
    public static List<int[]> findAllSolutions(int n) {
        // Create an empty list of solutions.
        List<int[]> solutions = new ArrayList<>();
        // Create an empty board.
        int[] board = new int[n];
        // Solve the problem recursively starting from the first row.
        // Add each solution found to the list.
        solveAll(board, 0, solutions);
        // Return the list of solutions.
        return solutions;
    }

    // Recursively solves the N-Queens problem starting from the given row.
    private static boolean solve(int[] board, int row) {
        // If all rows have been filled with queens, return true (a solution has been found).
        if (row == board.length) {
            return true;
        }

        // Try to place a queen in each column of the current row, and recursively solve the problem
        // starting from the next row if the position is legal.
        for (int col = 0; col < board.length; col++) {
            board[row] = col + 1;
            if (CheckLegalPosition.isLegalPosition(board, row) && solve(board, row + 1)) {
                return true;
            }
        }

        // If no queen can be placed in the current row, backtrack to the previous row.
        return false;
    }

    // Recursively solves the N-Queens problem starting from the given row, and adds each solution
    // found to the given list.
    private static void solveAll(int[] board, int row, List<int[]> solutions) {
        // If all rows have been filled with queens, add the current solution to the list.
        if (row == board.length) {
            solutions.add(board.clone());
            return;
        }

        // Try to place a queen in each column of the current row, and recursively solve the problem
        // starting from the next row if the position is legal.
        for (int col = 0; col < board.length; col++) {
            board[row] = col + 1;
            if (CheckLegalPosition.isLegalPosition(board, row)) {
                solveAll(board, row + 1, solutions);
            }
        }
    }

    // Returns the next legal position of the N-Queens problem, given the current position.
    public static int[] NextLegalPosition(int[] board, int n) {
        // Create a new position by cloning the current one.
        int[] nextPosition = board.clone();
        // Keep generating successor positions until a legal one is found.
        do {
            nextPosition = Successor(nextPosition, n);
            if (nextPosition == null) {
                break;
            }
        } while (!CheckLegalPosition.isLegalPosition(nextPosition, n - 1));

        // If no legal position is found, return an empty board.
        return nextPosition == null ? new int[n] : nextPosition;
    }

    // Generates the successor position of the N-Queens problem, given the current position.
    private static int[] Successor(int[] board, int n) {
        // Create a new position by cloning the current one.
        int[] nextPosition = board.clone();
        // Starting from the bottom row, find the first non-edge column that has a queen.
        for (int row = n - 1; row >= 0; row--) {
            if (nextPosition[row] < n) {
                // If a non-edge column is found, move the queen to the next column and return the new position.
                nextPosition[row]++;
                break;
            } else {
                // If an edge column is found, move the queen to the leftmost column of the current row
                // and continue the search in the previous row.
                nextPosition[row] = 1;
                if (row == 0) {
                    // If the search reaches the top row, no successor position can be generated.
                    return null;
                }
            }
        }
        // Return the new position.
        return nextPosition;
    }

    // Prints the N-Queens board.
    public static void printBoard(int[] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (j == board[i] - 1) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Prints the indices of the N-Queens board.
    public static void printIndices(int[] board) {
        System.out.print("(");
        for (int i = 0; i < board.length; i++) {
            System.out.print(board[i]);
            if (i < board.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println(")");
    }

    // Finds the first solution to the N-Queens problem for a board of size `n` starting from the given row range.
    public static int[] findFirstSolutionInRange(int n, int startRow, int endRow) {
        for (int col = startRow; col < endRow; col++) {
            int[] board = new int[n];
            board[0] = col + 1;
            if (solve(board, 1)) {
                return board;
            }
        }
        return null;
    }

}