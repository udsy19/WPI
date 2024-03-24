import java.util.Random;
public class FastInversionCount {
    public static void main(String[] args) {
        int[] arr = {3, 2, 1};
        long startTime = System.nanoTime(); // start timer
        int inversions = mergeSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime(); // end timer
        long elapsedTime = endTime - startTime; // compute elapsed time

        System.out.println("Number of inversions: " + inversions);
        System.out.println("Elapsed time: " + elapsedTime + " ns");
    }

    public static int mergeSort(int[] arr, int left, int right) {
        int inversions = 0;
        if (left < right) {
            int mid = (left + right) / 2;
            inversions += mergeSort(arr, left, mid);
            inversions += mergeSort(arr, mid + 1, right);
            inversions += merge(arr, left, mid, right);
        }
        return inversions;
    }

    public static int merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        int inversions = 0;

        int caseNum = 1;
        while (caseNum != 0) {
            switch (caseNum) {
                case 1:
                    if (i <= mid && j <= right) {
                        if (arr[i] <= arr[j]) {
                            temp[k++] = arr[i++];
                        } else {
                            temp[k++] = arr[j++];
                            inversions += mid - i + 1;
                        }
                    } else {
                        caseNum = 2;
                    }
                    break;
                case 2:
                    while (i <= mid) {
                        temp[k++] = arr[i++];
                    }
                    caseNum = 3;
                    break;
                case 3:
                    while (j <= right) {
                        temp[k++] = arr[j++];
                    }
                    caseNum = 4;
                    break;
                case 4:
                    for (i = left; i <= right; i++) {
                        arr[i] = temp[i - left];
                    }
                    caseNum = 0;
                    break;
            }
        }

        return inversions;
    }



}
