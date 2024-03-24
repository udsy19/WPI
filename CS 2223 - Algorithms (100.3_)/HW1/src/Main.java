import java.util.Scanner;

public class Main {
    private static int userScore = 0;
    private static int computerScore = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("About the Game: " + "\n" + "    Double Trouble is actually a variation of the game of Nim, which is a fundamental combinatorial game." + "\n"
                + "It is not clear who originally solved it, but it has been studied extensively by mathematicians and computer scientists." + "\n"
                + "The game has a rich history and has been played for centuries, but it gained widespread attention in the 20th century due to its connections to mathematical theory." + "\n"
                + "Nim has also appeared in popular culture, including in the movie \"A Beautiful Mind,\" where John Nash uses it as an example to illustrate game theory." + "\n"+ "\n");

        System.out.println("Welcome to Double Trouble!");

        boolean playAgain;
        do {
            int greenCount = promptCount(scanner, "green");
            int yellowCount = promptCount(scanner, "yellow");
            int orangeCount = promptCount(scanner, "orange");

            boolean userFirst = promptUserFirst(scanner);

            MarkerGame game = new MarkerGame(greenCount, yellowCount, orangeCount, userFirst);
            boolean userWon = game.play();

            updateScore(userWon);
            printScore();

            playAgain = promptPlayAgain(scanner);
        } while (playAgain);

        scanner.close();
    }

    private static int promptCount(Scanner scanner, String color) {
        System.out.print("Enter the number of " + color + " markers (default is ");
        int defaultValue;
        switch (color) {
            case "green":
                defaultValue = 3;
                break;
            case "yellow":
                defaultValue = 7;
                break;
            case "orange":
                defaultValue = 5;
                break;
            default:
                defaultValue = 0;
                break;
        }
        System.out.print(defaultValue + "): ");
        String input = scanner.nextLine().trim();
        if (input.equals("")) {
            return defaultValue;
        } else {
            try {
                int count = Integer.parseInt(input);
                if (count >= 0) {
                    return count;
                } else {
                    System.out.println("Invalid input - number of markers must be non-negative.");
                    return -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input - please enter a non-negative integer.");
                return -1;
            }
        }
    }

    private static boolean promptUserFirst(Scanner scanner) {
        System.out.print("Do you want to go first? (Y/N): ");
        String input = scanner.next().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    private static boolean promptPlayAgain(Scanner scanner) {
        System.out.print("Do you want to play another game? (Y/N): ");
        String input = scanner.next().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    private static void updateScore(boolean userWon) {
        if (userWon) {
            userScore++;
        } else {
            computerScore++;
        }
    }

    private static void printScore() {
        System.out.println("Scores: You - " + userScore + ", Computer - " + computerScore);
    }
}
