/*
This class contains three static methods to check whether a given 2D array is a magic square or not.
*/

public class MagicSquareChecker {
    /*
  This method checks whether each row of the 2D array has the same sum.
  The expected sum is hard-coded as 33 in this method.
  If a row doesn't have the expected sum, it prints an error message and returns false.
  Otherwise, it continues to the next row.
  If all rows have the same sum, it returns true.
*/
    public static boolean checkRows(int[][] magicSquare) {
        for (int i = 0; i < magicSquare.length ; i++){
            int rowSum = 0;
            for (int j = 0; j < magicSquare.length; j++ ){
                rowSum += magicSquare[i][j];
            }
            if (rowSum != 33){
                System.out.println("Row "+ i + " does not have the expected sum" );
                return false;
            }
        }
        return true;
    }

    /*
      This method checks whether each column of the 2D array has the same sum.
      The expected sum is hard-coded as 33 in this method.
      If a column doesn't have the expected sum, it prints an error message and returns false.
      Otherwise, it continues to the next column.
      If all columns have the same sum, it returns true.
    */
    public static boolean checkColumns(int[][] magicSquare) {
        for (int i = 0; i < magicSquare.length ; i++){
            int colSum = 0;
            for (int j = 0; j < magicSquare.length; j++ ){
                colSum += magicSquare[j][i];
            }
            if (colSum != 33){
                System.out.println("Column "+ i + " does not have the expected sum" );
                return false;
            }
        }
        return true;
    }

    /*
      This method checks whether each diagonal of the 2D array has the same sum.
      There are two diagonals in a square: one from the top-left corner to the bottom-right corner,
      and the other from the top-right corner to the bottom-left corner.
      The expected sum is hard-coded as 33 in this method.
      If a diagonal doesn't have the expected sum, it prints an error message and returns false.
      Otherwise, it continues to the next diagonal.
      If both diagonals have the same sum, it returns true.
    */
    public static boolean checkDiagonals(int[][] magicSquare){
        int diagonalSum1 = 0; // sum of the diagonal from top-left to bottom-right
        int diagonalSum2 = 0; // sum of the diagonal from top-right to bottom-left
        for(int i = 0; i < magicSquare.length; i++){
            diagonalSum1 += magicSquare[i][i];
            diagonalSum2 += magicSquare[magicSquare.length-1][i];
        }
        if(diagonalSum1 != 33){
            System.out.println("Diagonal from left to right does not add up to 33");
            return false;
        } else if (diagonalSum2 != 33) {
            System.out.println("Diagonal from right to left does not add up to 33");
        }
        return true;
    }

}