public class CheckLegalPosition {

    // Checks if placing a queen at the given row and column in the board is legal.
    public static boolean isLegalPosition(int[] board, int row) {
        // Check all previously placed queens for conflicts.
        for (int i = 0; i < row; i++) {
            if (board[i] == board[row] || Math.abs(board[i] - board[row]) == Math.abs(i - row)) {
                // If the new queen conflicts with a previously placed queen, return false.
                return false;
            }
        }
        // If no conflicts are found, return true.
        return true;
    }
}
