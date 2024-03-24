import java.util.Random;

public class EasyInversionCount {
    public static void main(String[] args) {
        int[] arr = {3, 2, 1};
        int n = arr.length;
        int inversions = 0;

        long startTime = System.nanoTime(); // start timer

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    inversions++;
                }
            }
        }

        long endTime = System.nanoTime(); // end timer
        long elapsedTime = endTime - startTime; // compute elapsed time

        System.out.println("Number of inversions: " + inversions);
        System.out.println("Elapsed time: " + elapsedTime + " ns");
    }


}
