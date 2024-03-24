import java.util.Random;

public class ComputerPlayer {
    public static int[] getComputerMove(MarkerColor[] colors) {
        int[] move = new int[2];
        int greenCount = colors[0].getCount();
        int yellowCount = colors[1].getCount();
        int orangeCount = colors[2].getCount();
        int xor = greenCount ^ yellowCount ^ orangeCount;

        if (greenCount > xor) {
            int count = greenCount - xor;
            move[0] = 0; // Green markers
            move[1] = count;
        } else if (yellowCount > xor) {
            int count = yellowCount - xor;
            move[0] = 1; // Yellow markers
            move[1] = count;
        } else if (orangeCount > xor) {
            int count = orangeCount - xor;
            move[0] = 2; // Orange markers
            move[1] = count;
        } else {
            int selectedColor, selectedCount;
            do {
                Random random = new Random();
                selectedColor = random.nextInt(colors.length);
                selectedCount = random.nextInt(colors[selectedColor].getCount() + 1);
            } while (selectedCount == 0 || colors[selectedColor].getCount() == 0);
            move[0] = selectedColor;
            move[1] = selectedCount;

        }
        return move;
    }

}
