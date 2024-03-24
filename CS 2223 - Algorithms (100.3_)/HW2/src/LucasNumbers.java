public class LucasNumbers {
    // This is a public static method that takes an integer n as input and returns an integer
    public static int lucasNumbers(int n) {

        // Create an array l with size n+2
        int l[] = new int[n + 2];

        // Declare a variable i
        int i;

        // Initialize the first two values of the array with 2 and 1
        l[0] = 2;
        l[1] = 1;

        // Calculate the values of the array for i = 2 up to n
        for (i = 2; i <= n; i++) {
            l[i] = l[i - 1] + l[i - 2];
        }

        // Return the value of l[n]
        return l[n];
    }

}