
// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// |    The code defines a Java class called `GaussJordanElimination` that contains two methods: `main` and `gaussJordanElimination`. The `main` method defines a 2D array called `matrix` that represents a system of linear equations, calls the `gaussJordanElimination` method to solve the system, and prints the solution to the console.
// |
// |      The `gaussJordanElimination` method takes a 2D array as input and returns an array of doubles. It implements the Gauss-Jordan elimination algorithm to solve the system of linear equations. The method loops through each row of the matrix and performs the following steps:
// |
// |        - Find the row with the largest absolute value in the current column and swap it with the current row if necessary
// |        - Divide the pivot row by its value to make it 1
// |        - Subtract multiples of the pivot row from all other rows to eliminate the variable in the current column
// |
// |        After performing these steps for all rows, the method extracts the solution from the rightmost column of the matrix and returns it.
// |
// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

public class GaussJordanElimination {
    // Define a method called gaussJordanElimination that takes a 2D array as input
    // and returns an array of doubles
    public static double[] gaussJordanElimination(double matrix[][]) {
        // Get the length of the matrix
        int matrix_length = matrix.length;

        // Loop through each row of the matrix
        for (int i = 0; i < matrix_length; i++) {

            // Find the row with the largest absolute value in the i-th column
            int maxRowIndex = i;
            for (int j = i + 1; j < matrix_length; j++) {
                if (Math.abs(matrix[j][i]) > Math.abs(matrix[maxRowIndex][i])) {
                    maxRowIndex = j;
                }
            }

            // Swap rows if necessary
            if (maxRowIndex != i) {
                double temp[] = matrix[i];
                matrix[i] = matrix[maxRowIndex];
                matrix[maxRowIndex] = temp;
            }
            // Divide the pivot row by its value to make it 1
            double maxRowIndexVal = matrix[i][i];
            for (int j = i; j <= matrix_length; j++) {
                matrix[i][j] /= maxRowIndexVal;
            }

            // Subtract multiples of the pivot row from all other rows to eliminate the i-th
            // variable
            for (int j = 0; j < matrix_length; j++) {
                if (j != i) {
                    double factor = matrix[j][i];
                    for (int k = i; k <= matrix_length; k++) {
                        matrix[j][k] -= factor * matrix[i][k];
                    }
                }
            }
        }

        // Create a new array to store the solution
        double solution[] = new double[matrix_length];
        for (int i = 0; i < matrix_length; i++) {
            solution[i] = matrix[i][matrix_length];
        }

        return solution;
    }

 
public static void main(String[] args) {

    // Define a 2D array called matrix, which represents the system of equations to
    // be solved
    double matrix[][] = {
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2047 },
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 12 },
            { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 48 },
            { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 384 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1536 },
            { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5 },
            { 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 50 },
            { 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1952 },
            { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 4083 },
            { 11, -10, 9, -8, 7, -6, 5, -4, 3, -2, 1, 459 }
    };
    // Call the gaussJordanElimination method to solve the system of equations
    double solution[] = gaussJordanElimination(matrix);
    // Print the solution to the console
    for (int i = 0; i < solution.length; i++) {
        System.out.printf("x%d = %.2f\n", i, solution[i]);
    }
}
}
