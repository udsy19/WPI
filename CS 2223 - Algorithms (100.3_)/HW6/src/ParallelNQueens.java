import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelNQueens {

    private static final int THREAD_COUNT = 16; // Define the number of threads to use

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Iterate through different board sizes
        for (int n = 4; n <= 37; n++) {
            System.out.println("Solving for n = " + n);
            final int finalN = n; // Store the final value of n in a variable to use in lambda expression

            long startTime = System.currentTimeMillis();

            // Create a thread pool with a fixed number of threads
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
            List<Future<int[]>> futures = new ArrayList<>();

            // Divide the board into equal parts for each thread to solve
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int finalI = i; // Store the final value of i in a variable to use in lambda expression
                Callable<int[]> task = () -> {
                    int rowsPerThread = finalN / THREAD_COUNT; // Calculate the number of rows per thread
                    int extraRows = finalN % THREAD_COUNT; // Calculate the number of rows that can't be evenly divided
                    final int startRow = finalI * rowsPerThread + Math.min(finalI, extraRows); // Calculate the starting row for this thread
                    final int endRow = startRow + rowsPerThread + (finalI < extraRows ? 1 : 0); // Calculate the ending row for this thread
                    return NQueensSolver.findFirstSolutionInRange(finalN, startRow, endRow); // Call a method to solve this thread's section of the board
                };
                futures.add(executor.submit(task)); // Submit the task to the executor and store the future result in a list
            }

            int[] firstSolution = null;
            // Wait for the first thread to find a solution, then stop all other threads and retrieve the solution
            for (Future<int[]> future : futures) {
                int[] result = future.get(); // Block and wait for the thread to finish and return its result
                if (result != null) {
                    firstSolution = result;
                    break;
                }
            }

            executor.shutdown(); // Shut down the executor to free up system resources

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Print the first solution found, if any
            if (firstSolution != null) {
                System.out.println("First solution found:");
                NQueensSolver.printBoard(firstSolution); // Print the board with queens placed in the correct positions
                System.out.println("Indices:");
                NQueensSolver.printIndices(firstSolution); // Print the row and column indices of the queens
            } else {
                System.out.println("No solution found.");
            }

            System.out.println("Time taken: " + duration + " ms\n");
        }
    }
}
