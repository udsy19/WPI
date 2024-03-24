import java.util.ArrayList;
import java.util.Arrays;
/**

 This is the main class that generates Lucas Numbers, My Sequence, and Subirachs Magic Square.

 It also checks the sum of all the 4-Element Combinations, counts the number of combinations,

 counts the number of sums, and finds the most number of sums.
 */
public class Main {
    public static void main(String[] args) {
        // Generate Lucas Numbers
        System.out.println("\n\u001B[33mLucas Number Generator\u001B[0m");
        int user_input = UserInput.getUserInput();
        LucasNumberGenerator.generateLucasNumbers(user_input);
        System.out.println("The algorithm has a time complexity of O(n), where n is the input parameter representing the" + "\n" +
                "number of Lucas numbers to generate. This is because the algorithm iterates over n numbers, and for " +"\n" +
                "each number, it performs a constant amount of arithmetic operations that do not depend on the input size." +"\n" +
                "Therefore, the time taken to compute the Lucas numbers grows linearly with the input size.\n" + "\n"

        );
        System.out.println("-----------------------------------------\n");

        // Generate My Sequence
        System.out.println("\u001B[33mMy Sequence Generator\u001B[0m");
        int mysequence_input3 = UserInput.getSequenceInput_sequenceLength();
        int mysequence_input1 = UserInput.getSequenceInput_1();
        int mysequence_input2 = UserInput.getSequenceInput_2();
        System.out.println("\n-----------------------------------------\n");
        MySequence.generateSequence(mysequence_input1, mysequence_input2, mysequence_input3);
        System.out.println("The order of growth of this sequence is exponential, since each term is a linear " + "\n" +
                "combination of the previous two terms. Therefore, the sequence grows very quickly.");
        System.out.println("\n-----------------------------------------\n");

        // Generate Subirachs Magic Square
        System.out.println("\u001B[33mSubirachs Magic Square\u001B[0m\n");
        int magicSquare[][] = {
                {1, 14, 14, 4},
                {11, 7, 6, 9},
                {8, 10, 10, 5},
                {13, 2, 3, 15}
        };

        // Print the magic square
        for (int i = 0; i < magicSquare.length; i++) {
            for (int j = 0; j < magicSquare[i].length; j++) {
                System.out.printf("\u001B[34m%-4d\u001B[0m", magicSquare[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");

        // Check Sum of all the 4-Element Combinations
        boolean rowValue = MagicSquareChecker.checkRows(magicSquare);
        if (rowValue) {
            System.out.println("\u001B[32mThe Sums in the Rows add to 33\n\u001B[0m");
        } else {
            System.out.println("\u001B[31mThe Sums in the Rows does not add to 33\n\u001B[0m");
        }

        boolean colValue = MagicSquareChecker.checkColumns(magicSquare);
        if (colValue) {
            System.out.println("\u001B[32mThe Sums in the Columns add to 33\n\u001B[0m");
        } else {
            System.out.println("\u001B[31mThe Sums in the Columns does not add to 33\n\u001B[0m");
        }

        boolean diagonalVal_1 = MagicSquareChecker.checkDiagonals(magicSquare);
        if (diagonalVal_1) {
            System.out.println("\u001B[32mThe Sums in the Diagonals add to 33\n\u001B[0m");
        } else {
            System.out.println("\u001B[31mThe Sums in the Diagonal does not add up to 33\u001B[0m");
        }

        // Count Number of Combinations
        int count = MagicSquareCombinations.countCombinations();
        System.out.println("\u001B[32mNumber of combinations that add up to the magic sum: " + count + "\n\u001B[0m");

        // Count Sums
        int[] arr = { 1, 14, 14, 4, 11, 7, 6, 9, 8, 10, 10, 5, 13, 2, 3, 15 };
        MagicSquareCombinations msc = new MagicSquareCombinations();

        ArrayList<Integer> allSums = msc.allSumsConstrained(arr, 0, 0);
        ArrayList<Integer> countSums = msc.countAll(allSums);

        for (int j = 0; j <= 132; j++) {
            System.out.println("The sum " + j + " occurs " + countSums.get(j) + " times.");
        }

        //Highest Sum
        int maxCount = 0;
        int maxSum = 0;

        for (int j = 0; j <= 132; j++) {
            int count1 = countSums.get(j);
            if (count1 > maxCount) {
                maxCount = count1;
                maxSum = j;
            }
        }

        System.out.println("\nThe sum that can be created with the greatest amount of combinations is " + maxSum + ", with " + maxCount + " combinations.\n");
        System.out.println("It's interesting to note that this sum is in the middle of the sum range (0 to 132). " +"\n"+
                "In addition to being the sum of all the numbers in the magic square divided by 3, this indicates that " +"\n"+
                "it is equally distant from the minimum and maximum sums (since 33 is the sum of each row, column, " +"\n"+
                "and diagonal in the square).");
    }
}




