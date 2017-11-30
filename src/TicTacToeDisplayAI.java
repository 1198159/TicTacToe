import processing.core.PApplet;

public class TicTacToeDisplayAI extends PApplet {
    // WINDOW DIMENSIONS
    private int WIN_WIDTH = 1920, WIN_HEIGHT = 1080;

    // TEXT SIZE
    private float textSize = WIN_HEIGHT / 3;

    // BACKGROUND COLOR
    private float bgR = random(50,205), bgG = random(50,205), bgB = random(50,205);

    // STROKE PROPERTIES
    private float strokeWeight = 5;
    private float strokeR = bgR*0.8f, strokeG = bgG*0.8f, strokeB = bgB*0.8f;

    // TEXT COLOR
    private float tR = strokeR, tG = strokeG, tB = strokeB;

    // DECLARE TICTACTOE
    private TicTacToeAI t = new TicTacToeAI();

    // DECLARE DIVIDER POSITIONS
    private float leftDivX = WIN_WIDTH / 3, rightDivX = WIN_WIDTH * 2 / 3, vertDivY1 = 0, vertDivY2 = WIN_HEIGHT;
    private float topDivY = WIN_HEIGHT / 3, botDivY = WIN_HEIGHT * 2 / 3, horzDivX1 = 0, horzDivX2 = WIN_WIDTH;

    // DECLARE MARKER POSITIONS
    private float
                leftX = leftDivX-textSize*1.25f,
                midX = rightDivX-textSize*1.25f,
                rightX = WIN_WIDTH-textSize*1.25f,
                topY = topDivY-textSize/6,
                midY = botDivY-textSize/6,
                botY = WIN_HEIGHT-textSize/6;

    // DECLARE PLAYER
    private int player;

    // MARKED
    private boolean
                topLeftMarked = false, topMiddleMarked = false, topRightMarked = false,
                midLeftMarked = false, centerMarked    = false, midRightMarked = false,
                botLeftMarked = false, botMiddleMarked = false, botRightMarked = false;

    // DELAY BEFORE DISPLAYING RESULT
    private int timer = 0, timeUp = 50;
    private boolean resultDeclared = false, resultDisplayed = false;

    // MAIN
    public static void main(String[] args) {
        PApplet.main("TicTacToeDisplayAI");
    }

    // SETTINGS - WINDOW DIMENSIONS
    public void settings() {
        size(WIN_WIDTH, WIN_HEIGHT);
    }

    // SETUP - BACKGROUND COLOR & DIVIDERS
    public void setup() { background(bgR, bgG, bgB); drawDividers(); fill(tR,tG,tB);}

    // DRAW
    public void draw() {
        cursor(HAND);
        if(!resultDeclared) {
            player = t.getPlayerTurn();
            if (player == 2 && t.isAiCanPlace()) { aiMove(); }
            textSize(textSize);
        }
        if(t.winner(1))
        {
            resultDeclared = true;
            if(timer > timeUp) { displayWin(1); } else { timer++; }
        }
        else if(t.winner(2))
        {
            resultDeclared = true;
            if(timer > timeUp) { displayWin(2); } else { timer++; }
        }
        else if(t.boardIsFull()) {
            resultDeclared = true;
            if(timer > timeUp) { displayTie(); } else { timer++; }
        }
    }

    /**
     * Display victory screen for player n
     * @param n
     */
    public void displayWin(int n)
    {
        t.displayBoard();
        fill(bgR,bgG,bgB);
        rect(0, 0, WIN_WIDTH, WIN_HEIGHT);
        fill(tR,tG,tB);
        String text;
        if(n == 2) { text = t.getName2() + " Wins"; print(t.getName2() + " Wins");}
        else       { text = t.getName1() + " Wins"; print(t.getName1() + " Wins");}
        textSize(textSize / text.length() * 10);
        text(text, WIN_WIDTH / 4 - textSize * text.length() * 0.1f, WIN_HEIGHT / 4);
        resultDisplayed = true;
        stop();
    }

    /**
     * Display tie screen
     */
    public void displayTie()
    {
        t.displayBoard();
        fill(bgR,bgG,bgB);
        rect(0, 0, WIN_WIDTH, WIN_HEIGHT);
        fill(tR,tG,tB);
        text("Tie", WIN_WIDTH / 3, WIN_HEIGHT / 2);
        print("Tie");
        resultDisplayed = true;
        stop();
    }

    /**
     * AI places marker
     */
    public void aiMove()
    {
        for (int r = 0; r < t.getBoard().length; r++) {
            for (int c = 0; c < t.getBoard()[r].length; c++) {
                if (t.getBoard()[r][c] == 0 && t.isAiCanPlace() && !resultDeclared) {
                    if      (t.canWin(2)) { t.aiPlace(2);   }  // WINNING MOVE
                    else if (t.canWin(1)) { t.aiPlace(1);   }  // BLOCK MOVE
                    else                     { t.aiPlaceRandom(); } // RANDOM MOVE
                    t.displayBoard();
                    mark(2);
                    t.switchPlayers();
                    t.setAiCanPlace(false);
                    break;
                }
            }
        }
    }

    /**
     * Display marker & mark tile
     * @param n
     */
    public void mark(int n)
    {
        if (t.topLeft(n) && !topLeftMarked) {
            place(n, leftX, topY);
            topLeftMarked = true;
        } else if (t.topMiddle(n) && !topMiddleMarked) {
            place(n, midX, topY);
            topMiddleMarked = true;
        } else if (t.topRight(n) && !topRightMarked) {
            place(n, rightX, topY);
            topRightMarked = true;
        } else if (t.middleLeft(n)&& !midLeftMarked) {
            place(n, leftX, midY);
            midLeftMarked = true;
        } else if (t.center(n) && !centerMarked) {
            place(n, midX, midY);
            centerMarked = true;
        } else if (t.middleRight(n) && !midRightMarked) {
            place(n, rightX, midY);
            midRightMarked = true;
        } else if (t.bottomLeft(n) && !botLeftMarked) {
            place(n, leftX, botY);
            botLeftMarked = true;
        } else if (t.bottomMiddle(n) && !botMiddleMarked) {
            place(n, midX, botY);
            botMiddleMarked = true;
        } else if (t.bottomRight(n) && !botRightMarked) {
            place(n, rightX, botY);
            botRightMarked = true;
        }
    }

    /**
     * Draw boundaries of tiles
     */
    public void drawDividers()
    {
        stroke(strokeR, strokeG, strokeB);
        strokeWeight(strokeWeight);
        line(leftDivX, vertDivY1, leftDivX, vertDivY2);   // VertLeft
        line(rightDivX, vertDivY1, rightDivX, vertDivY2); // VertRight
        line(horzDivX1, topDivY, horzDivX2, topDivY);     // HorzTop
        line(horzDivX1, botDivY, horzDivX2, botDivY);     // HorzBottom
    }

    // MOUSEPRESSED
    public void mousePressed()
    {
        t.displayBoard();
        if((!t.winner(1) || !t.winner(2)) && !t.boardIsFull() && !resultDeclared)
        {
            if(t.getClass() == TicTacToeAI.class)
            {
                if(player == 1)
                {
                    placeClick();
                    pushStyle();
                    fill(tB, tG, tR);
                    mark(1);
                    popStyle();
                }
            }
            else
            { placeClick(); }
        }
    }

    /**
     * AI's turn
     */
    public void aiTurn()
    {
        t.setAiCanPlace(true);
        t.switchPlayers();
    }

    /**
     * On Mouse Over -- place & aiTurn()
     */
    public void placeClick()
    {
        if (mouseOnTopLeft() && t.topLeft(0)) {
            t.place(1, 1);
            aiTurn();
        } else if (mouseOnTopMiddle() && t.topMiddle(0)) {
            t.place(1, 2);
            aiTurn();
        } else if (mouseOnTopRight() && t.topRight(0)) {
            t.place(1, 3);
            aiTurn();
        } else if (mouseOnMiddleLeft() && t.middleLeft(0)) {
            t.place(2, 1);
            aiTurn();
        } else if (mouseOnCenter() && t.center(0)) {
            t.place(2, 2);
            aiTurn();
        } else if (mouseOnMiddleRight() && t.middleRight(0)) {
            t.place(2, 3);
            aiTurn();
        } else if (mouseOnBottomLeft() && t.bottomLeft(0)) {
            t.place(3, 1);
            aiTurn();
        } else if (mouseOnBottomMiddle() && t.bottomMiddle(0)) {
            t.place(3, 2);
            aiTurn();
        } else if (mouseOnBottomRight() && t.bottomRight(0)) {
            t.place(3, 3);
            aiTurn();
        } else {
            System.out.println("OOPS");
        }
    }

    /**
     * Draw marker
     * @param n turn
     * @param x pos
     * @param y pos
     */
    public void place(int n, float x, float y)
    {
        char m = 'X';
        if(n == 2)
        {
            m = 'O';
        }
        text(m, x, y);
    }

    boolean mouseOn(float minX, float maxX, float minY, float maxY)  { return (mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY); }

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
