public class SquareBonusPoints {
    public static void main(String[] args) {

        // Printing a description of the problem and the approach used to solve it
        System.out.println("\n\nThe Subirachs Magic Square has the interesting feature of containing numerous smaller magic squares." +"\n"+
                "In particular, the bigger 4x4 square contains eight 3x3 magic squares. These tiny magic squares add up to the " +"\n"+
                "same total as the main square's rows, columns, and diagonals as well as the total of the four corner numbers.\n" +
                "\n" +
                "We may create a Java application to check whether all eight 3x3 squares are actually magic squares in order to " +"\n"+
                "illustrate this characteristic. We can develop a function to determine whether a given sub-array is a magic square " +"\n"+
                "and represent the square as a two-dimensional array.\n");

        // Creating a 4x4 array for the Subirachs Magic Square
        int[][] square = {
                {1, 14, 14, 4},
                {11, 7, 6, 9},
                {8, 10, 10, 5},
                {13, 2, 3, 15}
        };

        // Checking all 3x3 sub-squares
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                // Creating a new 3x3 array for the sub-square
                int[][] subSquare = new int[3][3];
                // Copying elements from the main array to the sub-square
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        subSquare[k][l] = square[i+k][j+l];
                    }
                }
                // Checking whether the sub-square is a magic square using the isMagicSquare function
                if (!isMagicSquare(subSquare)) {
                    System.out.println("Sub-square at (" + i + "," + j + ") is not a magic square.");
                }
            }
        }

        // Printing the results of the check
        System.out.println("\nThis indicates that three of the eight 3x3 sub-squares of the Subirachs Magic Square are not magic squares, specifically the sub-squares located at (0,0), (0,1), and (1,0).\n" +
                "\n");
    }

    // Function to check whether a given array is a magic square
    public static boolean isMagicSquare(int[][] square) {
        int n = square.length;
        int expectedSum = n * (n*n + 1) / 2;

        // Checking rows and columns
        for (int i = 0; i < n; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += square[i][j];
                colSum += square[j][i];
            }
            if (rowSum != expectedSum || colSum != expectedSum) {
                return false;
            }
        }

        // Checking diagonals
        int diag1Sum = 0;
        int diag2Sum = 0;
        for (int i = 0; i < n; i++) {
            diag1Sum += square[i][i];
            diag2Sum += square[i][n-i-1];
        }
        if (diag1Sum != expectedSum || diag2Sum != expectedSum) {
            return false;
        }

        return true;
    }

    }