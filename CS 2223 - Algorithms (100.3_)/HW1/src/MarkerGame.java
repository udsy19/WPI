import java.util.Scanner;

public class MarkerGame {
    private MarkerColor[] colors;
    private boolean userFirst;
    private GameTracker tracker;

    public MarkerGame(int greenCount, int yellowCount, int orangeCount, boolean userFirst) {
        this.colors = new MarkerColor[]{
                new MarkerColor("green", greenCount),
                new MarkerColor("yellow", yellowCount),
                new MarkerColor("orange", orangeCount)
        };
        this.userFirst = userFirst;
        this.tracker = new GameTracker();
    }

    public boolean play() {
        Scanner sc = new Scanner(System.in);
        boolean userTurn = userFirst;
        boolean computerTurn = !userFirst;

        printMarkerCounts();

        while (!isGameOver()) {
            if (userTurn) {
                int color = UserInput.getUserColor(sc);
                int count = UserInput.getUserCount(sc, color);
                if (colors[color].getCount() >= count) {
                    GameLogic.updateCounts(colors, color, count);
                    userTurn = false;
                    computerTurn = true;
                } else {
                    System.out.println("Invalid move - not enough markers.");
                }
            } else if (computerTurn) {
                int[] move = ComputerPlayer.getComputerMove(colors);
                int color = move[0];
                int count = move[1];
                System.out.println("Computer's turn - Removing " + count + " " + colors[color].getName() + " markers.");
                GameLogic.updateCounts(colors, color, count);
                userTurn = true;
                computerTurn = false;
            }

            printMarkerCounts();
        }

        GameLogic.declareWinner(userTurn);
        tracker.incrementGameCount();
        return userTurn;
    }

    private boolean isGameOver() {
        return GameLogic.isGameOver(colors);
    }

    private void printMarkerCounts() {
        int greenMarkers = colors[0].getCount();
        int yellowMarkers = colors[1].getCount();
        int orangeMarkers = colors[2].getCount();

        System.out.println("\nCurrent Markers");
        System.out.println(" \033[0m" + "\033[0;32m " + "Green Markers: " +  greenMarkers + "  \033[0m"
                + "\033[0;31m " +"Orange Markers: "+ + orangeMarkers + "  \033[0m"
                + "\033[0;33m "+ "Yellow Markers: "+ yellowMarkers + "  \033[0m\n");
        System.out.println("\033[0;32m " + "|".repeat(greenMarkers) + " \033[0m");
        System.out.println("\033[0;31m " + "|".repeat(orangeMarkers) + " \033[0m");
        System.out.println("\033[0;33m " + "|".repeat(yellowMarkers) + " \033[0m");
    }
}
