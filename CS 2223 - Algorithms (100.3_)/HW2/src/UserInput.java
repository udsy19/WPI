import java.util.Scanner;

// This class contains four methods that prompt the user for different inputs related to generating a Lucas sequence.
public class UserInput {
    // Prompts the user to input the number of Lucas Sequence to be generated and returns the input as an integer.
    public static int getUserInput() {
        System.out.println("Enter the Number of Lucas Sequence to be Generated");
        Scanner input = new Scanner(System.in);
        int user_input = input.nextInt();
        return user_input;
    }

    // Prompts the user to input the length of the sequence and returns the input as an integer.
// The default sequence length is 40 if no input is provided.
    public static int getSequenceInput_sequenceLength(){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the Length of the Sequence (default 30): ");
        String inputStr = input.nextLine().trim();
        int sequenceInput3 = (inputStr.isEmpty()) ? 30 : Integer.parseInt(inputStr);

        return sequenceInput3;
    }

    // Prompts the user to input the first number of the sequence and returns the input as an integer.
// The default first number is 2 if no input is provided.
    public static int getSequenceInput_1(){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the First Number of the Sequence (default 2): ");
        String inputStr = input.nextLine().trim();
        int sequenceInput1 = (inputStr.isEmpty()) ? 2 : Integer.parseInt(inputStr);

        return sequenceInput1;
    }

    // Prompts the user to input the second number of the sequence and returns the input as an integer.
// The default second number is 3 if no input is provided.
    public static int getSequenceInput_2(){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the Second Number of the Sequence (default 3): ");
        String inputStr = input.nextLine().trim();
        int sequenceInput2 = (inputStr.isEmpty()) ? 3 : Integer.parseInt(inputStr);

        return sequenceInput2;
    }

}