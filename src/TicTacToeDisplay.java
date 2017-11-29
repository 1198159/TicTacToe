import processing.core.PApplet;

public class TicTacToeDisplay extends PApplet{
    private int WIN_WIDTH = 1920, WIN_HEIGHT = 1080;

    public static void main(String[] args) {
        PApplet.main("TicTacToeDisplay");
    }

    public void settings()
    {
        size(WIN_WIDTH,WIN_HEIGHT);
    }

    public void setup()
    {
        background(0);
    }
}
