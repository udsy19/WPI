import java.util.Scanner;
import java.util.InputMismatchException;


public class UserInput {
    public static int getUserColor(Scanner sc) {
        System.out.print("Your turn - Enter the color (0=green, 1=yellow, 2=orange): ");
        try {
            int color = sc.nextInt();
            if (color >= 0 && color < 3) {
                return color;
            } else {
                System.out.println("Invalid input - please enter a number between 0 and 2.");
                return -1;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input - please enter a number.");
            sc.nextLine(); // clear the input buffer
            return -1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid input - please enter a number between 0 and 2.");
            sc.nextLine(); // clear the input buffer
            return -1;
        }
    }


    public static int getUserCount(Scanner sc, int color) {
        System.out.print("Enter the count: ");
        return sc.nextInt();
    }
}
