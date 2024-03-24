import java.util.Scanner;

public class Palindrome {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String palindromeInput;
        boolean exit = false;

        while (!exit) {
            // Getting User Input
            System.out.println("Enter a Sentence to check Palindrome (Max 256 Characters)");
            palindromeInput = scanner.nextLine();

            // Limit the input string to 256 characters
            if (palindromeInput.length() > 256) {
                palindromeInput = palindromeInput.substring(0, 256);
                System.out.println("Input string was truncated to 256 characters.");
            }

            // Remove special characters and convert to lowercase
            String palindromeCheck = palindromeInput.replaceAll("[^a-zA-Z0-9]+", "").toLowerCase();

            // Check if the input string is a palindrome
            if (isPalindrome(palindromeCheck)) {
                System.out.println("\u001B[32m" + palindromeInput + "\u001B[0m" + " is a palindrome");
            } else {
                System.out.println("\u001B[31m" + palindromeInput + "\u001B[0m" + " is not a palindrome");
            }

            // Ask the user if they want to check another input or exit
            System.out.println("Do you want to check another input? (Y/N)");
            String answer = scanner.nextLine();
            switch (answer.toUpperCase()) {
                case "Y":
                    break;
                case "N":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input. Exiting program.");
                    exit = true;
            }
        }
    }

    // Recursive function to check if a string is a palindrome
    public static boolean isPalindrome(String str) {
        if (str.length() <= 1) {
            return true;
        } else if (str.charAt(0) != str.charAt(str.length()-1)) {
            return false;
        } else {
            return isPalindrome(str.substring(1, str.length()-1));
        }
    }
}
