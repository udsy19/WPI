import java.util.ArrayList;

// The MagicSquareCombinations class calculates the number of combinations of numbers
// that can be added to create a magic square with the given square array.
public class MagicSquareCombinations {
    // The square array contains the numbers in the magic square.
    public final static int[] square = {
            1, 14, 14, 4,
            11, 7, 6, 9,
            8, 10, 10, 5,
            13, 2, 3, 15
    };

    // The countCombinations method calculates the number of combinations of numbers
// that can be added to create a magic square.
    public static int countCombinations() {
        int count = 0;
        for (int i = 0; i < square.length; i++) {
            for (int j = i + 1; j < square.length; j++) {
                for (int k = j + 1; k < square.length; k++) {
                    if (square[i] + square[j] + square[k] == 33) {
                        count++;
                    } else {
                        for (int l = k + 1; l < square.length; l++) {
                            if (square[i] + square[j] + square[k] + square[l] == 33) {
                                count++;
                            } else {
                                for (int m = l + 1; m < square.length; m++) {
                                    if (square[i] + square[j] + square[k] + square[l] + square[m] == 33) {
                                        count++;
                                    } else {
                                        for (int n = m + 1; n < square.length; n++) {
                                            if (square[i] + square[j] + square[k] + square[l] + square[m] + square[n] == 33) {
                                                count++;
                                            } else {
                                                for (int o = n + 1; o < square.length; o++) {
                                                    if (square[i] + square[j] + square[k] + square[l] + square[m] + square[n] + square[o] == 33) {
                                                        count++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    // The countSums method calculates the number of times each sum of numbers can be
// added to create a magic square.
    public ArrayList<Integer> allSumsConstrained(int[] list, int offset, int sum){
        ArrayList<Integer>allsums = new ArrayList<>(list.length - offset);
        for(int i = offset; i < list.length; i++){
            int total = sum + list[i];
            allsums.add(total);
            allsums.addAll(allSumsConstrained(list, i + 1, total));
        }
        return allsums;
    }


    public ArrayList<Integer> countAll(ArrayList<Integer> list){
        ArrayList<Integer> finalList = new ArrayList<>();
        int counter = 0;
        for(int j = 0; j<= 132; j++){
            for (int i : list){
                if(i == j){
                    counter++;
                }
            }
            finalList.add(counter);
            counter = 0;
        }
        finalList.set(0, 1);
        return finalList;
    }


}