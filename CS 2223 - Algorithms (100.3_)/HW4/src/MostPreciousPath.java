public class MostPreciousPath {
    public static void main(String[] args) {
        // Define the grid of gems
        int[][] gems = {
            {84, 99, 68, 75, 98, 44, 33, 96},
            {93, 53, 24, 46, 86, 1, 41, 10},
            {7, 30, 51, 65, 27, 94, 97, 83},
            {12, 67, 88, 22, 64, 47, 70, 56},
            {15, 92, 71, 13, 48, 77, 11, 91},
            {63, 16, 4, 31, 25, 17, 59, 32},
            {74, 40, 37, 78, 23, 14, 5, 79},
            {21, 95, 20, 82, 66, 52, 89, 35}
        };

        // Get the dimensions of the grid
        int numRows = gems.length;
        int numCols = gems[0].length;

        // Initialize dynamic programming table and best path table
        int[][] dpTable = new int[numRows][numCols];
        int[][] bestPath = new int[numRows][numCols];

        // Initialize the base case for dynamic programming
        for (int i = 0; i < numCols; i++) {
            dpTable[0][i] = gems[0][i];
            bestPath[0][i] = -1;
        }

        // Fill the dynamic programming table and record the best path
        for (int row = 1; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int maxGems = dpTable[row - 1][col];
                bestPath[row][col] = col;

                // Check if the left cell has a higher gem count
                if (col > 0 && dpTable[row - 1][col - 1] > maxGems) {
                    maxGems = dpTable[row - 1][col - 1];
                    bestPath[row][col] = col - 1;
                }

                // Check if the right cell has a higher gem count
                if (col < numCols - 1 && dpTable[row - 1][col + 1] > maxGems) {
                    maxGems = dpTable[row - 1][col + 1];
                    bestPath[row][col] = col + 1;
                }

                dpTable[row][col] = gems[row][col] + maxGems;
            }
        }

        // Find the starting column with the highest gem count
        int maxGemsCollected = dpTable[numRows - 1][0];
        int startingColumn = 0;
        for (int i = 1; i < numCols; i++) {
            if (dpTable[numRows - 1][i] > maxGemsCollected) {
                maxGemsCollected = dpTable[numRows - 1][i];
                startingColumn = i;
            }
        }

        // Reconstruct the path of Bilbo
        int[] bilboPath = new int[numRows];
        bilboPath[numRows - 1] = startingColumn;
        for (int i = numRows - 1; i > 0; i--) {
            bilboPath[i - 1] = bestPath[i][bilboPath[i]];
        }

        System.out.println("Bilbo's starting square: Vault " + (bilboPath[0] + 1) + ", Row 1");

        // Print Bilbo's path with gem values
        System.out.print("Bilbo's path with gem values: ");
        for (int i = 0; i < numRows; i++) {
            int vault = bilboPath[i] + 1;
            int row = i + 1;
            int gemValue = gems[i][bilboPath[i]];
            System.out.print("(" + "Vault: " + vault + ", " + "Row: " + row + ", Index Value: " + gemValue + ")" + "\n");
            if (i < numRows - 1) {
                System.out.print(" -> ");
            } else {
                System.out.print("\n");
            }
        }
        System.out.println();


        // Print the total number of gems collected on the way
        System.out.println("Total number of gems collected on the way: " + maxGemsCollected);

        // Print the vault number where the Arkenstone is hidden
        int vaultNumber = bilboPath[numRows - 1] + 1;
        System.out.println("The number of the vault wherein the King has secreted the Arkenstone: " + vaultNumber);
    }
}

