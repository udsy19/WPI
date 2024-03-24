import java.util.Arrays;

public class Klutzomaniacs {
    public static void main(String[] args) {
        int n = 7;
        generateGrayCodes(n);
    }

    // Function to generate Gray codes for the given number of bits (n)
    static void generateGrayCodes(int n) {
        if (n <= 0) {
            return;
        }

        // Array of names to be used for the riders
        String[] names = {"Jiggles", "Fitz", "Enzo", "Doofus", "Crunchy", "Boxo", "Axel"};

        // Calculate the size of the grayCodes array based on the number of bits (n)
        int size = 1 << n;
        String[] grayCodes = new String[size];

        grayCodes[0] = "0";
        grayCodes[1] = "1";

        int i, j;
        for (i = 2; i < size; i = i << 1) {
            // Reflect the existing codes and add them in reverse order.
            for (j = i - 1; j >= 0; j--)
                grayCodes[2 * i - 1 - j] = grayCodes[j];

            // Add a "0" prefix to the first half of the codes.
            for (j = 0; j < i; j++)
                grayCodes[j] = "0" + grayCodes[j];

            // Add a "1" prefix to the second half of the codes.
            for (j = i; j < 2 * i; j++)
                grayCodes[j] = "1" + grayCodes[j];
        }

        // Print the modified Gray codes.
        System.out.println("Index | Gray Code | Klutzomaniacs Riding | Action ");
        int lastIndex = 0;
        String lastname = null;
        for (i = 0; i < grayCodes.length; i++) {
            if (i == 0) {
                System.out.println(i + " | " + grayCodes[i] + " | Empty Tricycle | Spotlight");
                continue;
            }


            String previousCode = grayCodes[i - 1];
            String currentCode = grayCodes[i];

            // Strings to store joined and left actions
            String joined = "";
            String left = "";


            // Compare the previous and current gray codes to find joined and left actions
            for (j = 0; j < currentCode.length(); j++) {
                if (previousCode.charAt(j) == '0' && currentCode.charAt(j) == '1') {
                    joined += names[j] + " has joined; ";
                } else if (previousCode.charAt(j) == '1' && currentCode.charAt(j) == '0') {
                    left += names[j] + " has left; ";
                }
            }

            // Print the index, gray code, riders, and action
            System.out.print(i + " | " + currentCode + " | ");

            // Create an array to store the names of the riders in the current gray code
            String[] nameArray = new String[names.length];
            int index = 0;
            if (currentCode.charAt(0) == '1') {
                nameArray[index] = names[0];
                index++;
            }
            if (currentCode.charAt(1) == '1') {
                nameArray[index] = names[1];
                index++;
            }
            if (currentCode.charAt(2) == '1') {
                nameArray[index] = names[2];
                index++;
            }
            if (currentCode.charAt(3) == '1') {
                nameArray[index] = names[3];
                index++;
            }
            if (currentCode.charAt(4) == '1') {
                nameArray[index] = names[4];
                index++;
            }

            if (currentCode.charAt(5) == '1') {
                nameArray[index] = names[5];
                index++;
            }
            if (currentCode.charAt(6) == '1') {
                nameArray[index] = names[6];
                index++;
            }

            System.out.print(Arrays.toString(Arrays.copyOfRange(nameArray, 0, index)));
            System.out.println(" | " + joined + left);
            lastname = nameArray[index - 1];

        }
        System.out.println("\n** " + lastname + " has crashed");

    }

}
