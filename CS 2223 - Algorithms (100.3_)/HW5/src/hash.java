import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
public class hash {
    // Define a constant for the size of the hash table
    private static final int TABLE_SIZE = 997;
    // Create an array to store the words
    private String[] table = new String[TABLE_SIZE];

    // Method to build the hash table from a file
    public void build(String fileName) throws FileNotFoundException {
        System.out.println("\n\033[33mQuestion 1 & 2: \033[0m\n");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
            Scanner scanner = new Scanner(reader);

        // Variables to store the total number of words and their total length
        int totalWords = 0;
        int totalLength = 0;

        // Read words from the file until there are no more
        while (scanner.hasNext()) {
            String word = scanner.next();

            // Check if the word contains only valid characters
            if (word.matches("[a-zA-Z'-]+")) {
                // Compute the hash value for the word and find its index in the table
                int index = computeHash(word) % TABLE_SIZE;

                // Handle collisions using linear probing
                while (table[index] != null && !table[index].equals(word)) {
                    index = (index + 1) % TABLE_SIZE;
                }

                // If the word is not already in the table, add it and update the counters
                if (table[index] == null) {
                    table[index] = word;
                    totalWords++;
                    totalLength = totalLength + word.length();
                }
            }
        }

        // Close the scanner object
        scanner.close();

        // Print the contents of the hash table and the corresponding hash values
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] != null) {
                int j = i;
                int hashValue = computeHash(table[i]);

                // Find the correct index for the word using linear probing
                while (table[j] != null && j != hashValue % TABLE_SIZE) {
                    j = (j + 1) % TABLE_SIZE;
                }

                System.out.printf("%03d, %s, %d%n", i, table[i], hashValue);
            }
        }

        // Print statistics about the words in the table
        System.out.printf("\nTotal number of words read: %d%n", totalWords);
        System.out.printf("Average length of valid words hashed: %.2f%n", (double) totalLength / TABLE_SIZE);
        System.out.println("\n--------------------------------------------------------------------------------------");
        }catch (NullPointerException e) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

    }

    // -------############----Question: 3----############--------------------------------------------------------------------------------------------

    // a. Calculate the number of non-empty addresses and the load factor
    public void nonEmptyAddressesAndLoadFactor() {
        int nonEmptyAddresses = 0;

        for (String word : table) {
            if (word != null) {
                nonEmptyAddresses++;
            }
        }

        double loadFactor = (double) nonEmptyAddresses / TABLE_SIZE;
        System.out.printf("\033[3;32mNumber of non-empty addresses: %d%n\033[0m", nonEmptyAddresses);
        System.out.printf("\033[3;32mLoad factor (α): %.2f%n\033[0m", loadFactor);
        
    }

    // b. Find the longest empty area and its starting index
    public void longestEmptyArea() {
        int longestEmptyArea = 0;
        int startIndex = -1;
        int currentEmptyArea = 0;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] == null) {
                currentEmptyArea++;
                if (currentEmptyArea > longestEmptyArea) {
                    longestEmptyArea = currentEmptyArea;
                    startIndex = i - currentEmptyArea + 1;
                }
            } else {
                currentEmptyArea = 0;
            }
        }

        System.out.printf("\033[3;32mLongest empty area: %d, starting at index: %d%n\033[0m", longestEmptyArea, startIndex);
    }

    // c. Find the longest cluster and its starting index
    public void longestCluster() {
        int longestCluster = 0;
        int startIndex = -1;
        int currentCluster = 0;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] != null) {
                currentCluster++;
                if (currentCluster > longestCluster) {
                    longestCluster = currentCluster;
                    startIndex = i - currentCluster + 1;
                }
            } else {
                currentCluster = 0;
            }
        }

        System.out.printf("\033[32m\033[3mLongest cluster: %d, starting at index: %d%n\033[0m", longestCluster, startIndex);
    }

    // d. Find the hash value with the greatest number of distinct words
    public void hashValueWithMostWords() {
        int[] hashCounts = new int[1009];
        int maxCount = 0;
        int maxValue = 0;

        for (String word : table) {
            if (word != null) {
                int hashValue = computeHash(word);
                hashCounts[hashValue]++;
                if (hashCounts[hashValue] > maxCount) {
                    maxCount = hashCounts[hashValue];
                    maxValue = hashValue;
                }
            }
        }

        System.out.printf("\033[3;32mHash value with the most distinct words: %d, number of words: %d%n\033[0m", maxValue, maxCount);
    }

    // e. Find the word farthest from its actual hash value
    public void wordFarthestFromHashValue() {
        int maxDistance = 0;
        String farthestWord = null;

        for (int i = 0; i < TABLE_SIZE; i++) {
            String word = table[i];
            if (word != null) {
                int actualHashValue = computeHash(word) % TABLE_SIZE;
                int distance = (TABLE_SIZE + i - actualHashValue) % TABLE_SIZE;
                if (distance > maxDistance) {
                    maxDistance = distance;
                    farthestWord = word;
                }
            }
        }

        System.out.printf("\033[32m\033[3mWord farthest from its actual hash value: %s, distance: %d%n\033[0m", farthestWord, maxDistance);
        System.out.println("\n--------------------------------------------------------------------------------------");
    }

    
    // ------------------------------------------------------------------------------------------------------------------------------------

    // Method to compute the hash value for a given word
    private int computeHash(String word) {
        int hashValue = 0;

        // Iterate through each character in the word
        for (char c : word.toCharArray()) {
            // If the character is a letter or digit, update the hash value
            if (Character.isLetterOrDigit(c)) {
                hashValue = (hashValue * 127 + c) % 1009;
            }
        }

        // Return the computed hash value
        return hashValue;
    }
}
