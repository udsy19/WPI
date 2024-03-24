// This is a class for generating the first n Lucas Numbers, where n is a user-input parameter
public class LucasNumberGenerator {
    // This method generates the first n Lucas Numbers and prints them along with the time taken to compute them
    public static void generateLucasNumbers(int user_input) {
        System.out.println("The first " + user_input + " numbers of the Lucas Numbers are: ");

        // Create arrays to store the Lucas Numbers and the time taken to compute each number
        long[] lucas = new long[user_input];
        long[] time = new long[user_input];

        // Compute the Lucas Numbers and the time taken to compute them, and store them in the arrays
        for (int i = 0; i < user_input; i++) {
            long startTime = System.nanoTime();
            lucas[i] = LucasNumbers.lucasNumbers(i);
            long endTime = System.nanoTime();
            time[i] = endTime - startTime;

            // Print the Lucas Number and the time taken to compute it
            System.out.println("L(" + (i+1) + ") = " + lucas[i] + " Time Taken = " + time[i] + " ns");

            // Calculate the ratio of successive Lucas Numbers and the ratio of time taken to compute them
            if (i > 0) {
                double ratio = (double) lucas[i] / lucas[i - 1];
                double timeRatio = (double) time[i] / time[i - 1];

                // Print the ratios
                System.out.println("Ratio of successive Lucas numbers: " + ratio);
                System.out.println("Ratio of time to compute successive Lucas numbers: " + timeRatio  + "\n");
            }
        }
    }

}