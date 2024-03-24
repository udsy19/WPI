public class GameLogic {
    public static void updateCounts(MarkerColor[] colors, int color, int count) {
        colors[color].setCount(colors[color].getCount() - count);
    }

    public static boolean isGameOver(MarkerColor[] colors) {
        for (MarkerColor color : colors) {
            if (color.getCount() > 0) {
                return false;
            }
        }
        return true;
    }

    public static void declareWinner(boolean userTurn) {
        if (userTurn) {
            System.out.println("Congratulations, you win!");
        } else {
            System.out.println("Better Luck Next time, Computer Won!");
        }
    }
}
