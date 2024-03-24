public class MySequence {
    // This is a public static method named "generateSequence" that takes three parameters:
// n0 - an integer representing the first number in the sequence
// n1 - an integer representing the second number in the sequence
// sequenceLength - an integer representing the length of the sequence to be generated
    public static void generateSequence(int n0, int n1, int sequenceLength){

        // Check if the sequence length is a positive integer; if not, print an error message and return
        if (sequenceLength <= 0) {
            System.out.println("Sequence length should be a positive integer.");
            return;
        }

        // Check if the initial sequence numbers are non-negative; if not, print an error message and return
        if (n0 < 0 || n1 < 0) {
            System.out.println("Initial sequence numbers should be non-negative.");
            return;
        }

        // Create an array of long values named "time" with a length of "sequenceLength"
        long [] time = new long[sequenceLength];

        // Create an array of int values named "sequence" with a length of "sequenceLength"
        int[] sequence = new int[sequenceLength];

        // Set the first two values of "sequence" to "n0" and "n1", respectively
        sequence[0] = n0;
        sequence[1] = n1;

        // Print the first two values of the sequence
        System.out.println("The first " + sequenceLength + " numbers of the sequence are:");
        System.out.println("N(0) = " + n0);
        System.out.println("N(1) = " + n1);

        // Loop through the remaining values of the sequence and calculate their values
        for (int i = 2; i < sequenceLength; i++) {

            // Get the current system time in nanoseconds
            long startTime = System.nanoTime();

            // Calculate the value of the current element in the sequence using the formula: N(i) = N(i-1) + 2*N(i-2)
            sequence[i] = sequence[i-1] + 2*sequence[i-2];

            // Get the current system time in nanoseconds
            long endTime = System.nanoTime();

            // Calculate the time taken to calculate the current element in the sequence
            time[i] = endTime - startTime;

            // Print the value of the current element in the sequence, the time taken to calculate it, and a newline character
            System.out.println("N(" + (i+1) + ") = " + sequence[i] + " Time Taken = " + time[i] + " ns") ;

            // Calculate the ratio of the current element to the previous element in the sequence
            double ratio = (double) sequence[i] / sequence [i-1];

            // Calculate the ratio of the time taken to calculate the current element to the time taken to calculate the previous element in the sequence
            double timeRatio = (double) time[i] / time[i - 1];

            // Print the ratio of the current element to the previous element in the sequence and a newline character
            System.out.println("Ratio of successive numbers: "+ ratio + "\n");
        }
    }

}