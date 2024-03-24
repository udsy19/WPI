public class Dijkstra {

    // A helper function to return infinity
    private static int INF() {
        double INF_double = Double.POSITIVE_INFINITY;
        int INF_int = (int) INF_double;
        return INF_int;
    }

    // The main function to perform Dijkstra's algorithm
    public static void dijkstra_operations(int dijkstraMatrix[][], int source, int destination) {
        int numNodes = dijkstraMatrix.length;

        // Initialize the distance, visited, and previous arrays
        int distance[] = new int[numNodes];
        boolean visited[] = new boolean[numNodes];
        int[] previous = new int[numNodes];

        for (int i = 0; i < numNodes; i++) {
            distance[i] = INF();
            visited[i] = false;
            previous[i] = -1;
        }

        // Set the distance of the source node to 0
        distance[source] = 0;

        // Loop until all nodes are visited
        while (!isVisited(visited)) {
            // Find the unvisited node with the smallest distance
            int currentNode = findMinDistance(distance, visited);
            visited[currentNode] = true;

            // Update the distance of adjacent nodes if it is smaller
            for (int i = 0; i < numNodes; i++) {
                if (!visited[i] && dijkstraMatrix[currentNode][i] != 0) {
                    int newDistance = distance[currentNode] + dijkstraMatrix[currentNode][i];
                    if (newDistance < distance[i]) {
                        distance[i] = newDistance;
                        previous[i] = currentNode;
                    }
                }
            }
        }

        // Print the result
        System.out.print("\n\033[32mShortest path from node " + source + " to node " + destination + " is: \033[0m");
        printPath(previous, source, destination);
        System.out.println("\033[32m\nThe shortest distance from node " + source + " to node " + destination + " is: " + distance[destination] + "\n\033[0m\n");
    }
    
    // Check if all the nodes have been visited
    private static boolean isVisited(boolean visited[]) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    // Find the unvisited node with the smallest distance
    private static int findMinDistance(int distance[], boolean visited[]) {
        int minDistance = INF();
        int minDistanceNode = -1;
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] < minDistance) {
                minDistance = distance[i];
                minDistanceNode = i;
            }
        }
        return minDistanceNode;
    }

    // Print the path recursively
    private static void printPath(int[] previous, int source, int destination) {
        if (source == destination) {
            System.out.print(source + " ");
        } else if (previous[destination] == -1) {
            System.out.print("No path exists");
        } else {
            printPath(previous, source, previous[destination]);
            System.out.print(destination + " ");
        }
    }
}
