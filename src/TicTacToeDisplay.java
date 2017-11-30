import processing.core.PApplet;

public class TicTacToeDisplay extends PApplet {
    private int WIN_WIDTH = 1920, WIN_HEIGHT = 1080;
    private int textSize = WIN_HEIGHT / 3;
    private int strokeWeight = 5;
    private int strokeR = 255, strokeG = 255, strokeB = 255;
    private int bgR = 0, bgG = 0, bgB = 0;
    private TicTacToe t = new TicTacToe();
    private float leftDivX = WIN_WIDTH / 3, rightDivX = WIN_WIDTH * 2 / 3, vertDivY1 = 0, vertDivY2 = WIN_HEIGHT;
    private float topDivY = WIN_HEIGHT / 3, botDivY = WIN_HEIGHT * 2 / 3, horzDivX1 = 0, horzDivX2 = WIN_WIDTH;
    private float leftX = leftDivX-textSize*1.25f,
                midX = rightDivX-textSize*1.25f,
                rightX = WIN_WIDTH-textSize*1.25f,
                topY = topDivY-textSize/6,
                midY = botDivY-textSize/6,
                botY = WIN_HEIGHT-textSize/6;
    private int player;
    private char mark = 'X';

    public static void main(String[] args) {
        PApplet.main("TicTacToeDisplay");
    }

    public void settings() {
        size(WIN_WIDTH, WIN_HEIGHT);
    }

    public void setup() {
        background(bgR, bgG, bgB);
        drawDividers();
    }

    public void draw() {
        if(t.winner(1))
        {
            rect(0,0, WIN_WIDTH, WIN_HEIGHT);
            fill(0);
            String text = t.getName1() + " Wins";
            textSize(textSize/text.length()*10);
            text(text, WIN_WIDTH/4-textSize*text.length()*0.1f, WIN_HEIGHT/4);
            stop();
        }
        else if(t.winner(2))
        {
            rect(0,0, WIN_WIDTH, WIN_HEIGHT);
            fill(0);
            String text = t.getName2() + " Wins";
            textSize(textSize/text.length()*10);
            text(text, WIN_WIDTH/4-textSize*text.length()*0.1f, WIN_HEIGHT/4);
            stop();
        }
        else if(t.boardIsFull())
        {
            rect(0,0, WIN_WIDTH, WIN_HEIGHT);
            fill(0);
            text("Tie", WIN_WIDTH/3, WIN_HEIGHT/2);
            stop();
        }
        player = t.getPlayerTurn();
        if(player == 2)
        {
            mark = 'O';
        }
        else
        {
            mark = 'X';
        }
        textSize(textSize);
    }

    public void drawDividers()
    {
        stroke(strokeR, strokeG, strokeB);
        strokeWeight(strokeWeight);
        line(leftDivX, vertDivY1, leftDivX, vertDivY2); // Vert Left
        line(rightDivX, vertDivY1, rightDivX, vertDivY2); // Vert Right
        line(horzDivX1, topDivY, horzDivX2, topDivY); // Horz Top
        line(horzDivX1, botDivY, horzDivX2, botDivY); // Horz Bottom
    }

    public void drawPlaceholders()
    {
        text(mark, leftX, topY);
        text(mark, midX, topY);
        text(mark, rightX, topY);
        text(mark, leftX, midY);
        text(mark, midX, midY);
        text(mark, rightX, midY);
        text(mark, leftX, botY);
        text(mark, midX, botY);
        text(mark, rightX, botY);
    }

    public void mousePressed() {
        if((!t.winner(1) || !t.winner(2)) && !t.boardIsFull()) {
            if (mouseOnTopLeft() && t.topLeft(0)) {
                t.place(1, 1);
                place(leftX, topY);
            } else if (mouseOnTopMiddle() && t.topMiddle(0)) {
                t.place(1, 2);
                place(midX, topY);
            } else if (mouseOnTopRight() && t.topRight(0)) {
                t.place(1, 3);
                place(rightX, topY);
            } else if (mouseOnMiddleLeft() && t.middleLeft(0)) {
                t.place(2, 1);
                place(leftX, midY);
            } else if (mouseOnCenter() && t.center(0)) {
                t.place(2, 2);
                place(midX, midY);
            } else if (mouseOnMiddleRight() && t.middleRight(0)) {
                t.place(2, 3);
                place(rightX, midY);
            } else if (mouseOnBottomLeft() && t.bottomLeft(0)) {
                t.place(3, 1);
                place(leftX, botY);
            } else if (mouseOnBottomMiddle() && t.bottomMiddle(0)) {
                t.place(3, 2);
                place(midX, botY);
            } else if (mouseOnBottomRight() && t.bottomRight(0)) {
                t.place(3, 3);
                place(rightX, botY);
            }
        }
    }

    public void place(float x, float y)
    {
        text(mark, x, y);
        t.switchPlayers();
    }

    boolean mouseOn(float minX, float maxX, float minY, float maxY)  {
        return (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY);
    }

    boolean mouseOnTopLeft()
    {
        return mouseOn(0, leftDivX, 0, topDivY);
    }

    boolean mouseOnTopMiddle()
    {
        return mouseOn(leftDivX, rightDivX, 0, topDivY);
    }

    boolean mouseOnTopRight()
    {
        return mouseOn(rightDivX, WIN_WIDTH, 0, topDivY);
    }

    boolean mouseOnMiddleLeft()
    {
        return mouseOn(0, leftDivX, topDivY, botDivY);
    }

    boolean mouseOnCenter()
    {
        return mouseOn(leftDivX, rightDivX, topDivY, botDivY) ;
    }

    boolean mouseOnMiddleRight()
    {
        return mouseOn(rightDivX, WIN_WIDTH, topDivY, botDivY);
    }

    boolean mouseOnBottomLeft()
    {
        return mouseOn(0, leftDivX, botDivY, WIN_HEIGHT);
    }

    boolean mouseOnBottomMiddle()
    {
        return mouseOn(leftDivX, rightDivX, botDivY, WIN_HEIGHT);
    }

    boolean mouseOnBottomRight()
    {
        return mouseOn(rightDivX, WIN_WIDTH, botDivY, WIN_HEIGHT);
    }
}
