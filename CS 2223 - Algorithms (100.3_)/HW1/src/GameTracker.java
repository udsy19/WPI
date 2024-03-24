public class GameTracker {
    private int gameCount;

    public GameTracker() {
        gameCount = 0;
    }

    public void incrementGameCount() {
        gameCount++;
    }

    public int getGameCount() {
        return gameCount;
    }
}
