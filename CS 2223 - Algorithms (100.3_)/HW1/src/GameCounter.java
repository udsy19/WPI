public class GameCounter {
    private int userCount;
    private int computerCount;

    public GameCounter() {
        userCount = 0;
        computerCount = 0;
    }

    public void incrementUserCount() {
        userCount++;
    }

    public void incrementComputerCount() {
        computerCount++;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getComputerCount() {
        return computerCount;
    }
}
