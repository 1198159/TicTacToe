import java.util.Scanner;

public class TicTacToe {

    private int[][] board;
    private int     playerTurn;
    private String  name1, name2;
    private Scanner s;

    TicTacToe()
    {
        board = new int[3][3];
        playerTurn = 1;
        name1 = "Player1";
        name2 = "Player2";
        s = new Scanner(System.in);
    }

    public void Play()
    {
        while(!boardIsFull())
        {
            if(winner(2))
            {
                System.out.println(getName2() + " wins!");
            }
            else if(winner(1))
            {
                System.out.println(getName1() + " wins!");
            }
            else
            {
                displayPlayer();
            }
            System.out.println("===============");
            displayBoard();
            if(winner(1) || winner(2))
            {
                promptRestart();
            }
            else
            {
                System.out.print("Row: ");
                int row = getS().nextInt();
                System.out.print("Column: ");
                int column = getS().nextInt();
                place(row, column);
                switchPlayers();
                System.out.println("===============");
            }
        }
        if(boardIsFull() && (!winner(1) || !winner(2)))
        {
            System.out.println("Board is full.");
            System.out.println("=============");
            promptRestart();
        }
        else if(winner(2))
        {
            System.out.println(getName2() + " wins!");
        }
        else if(winner(1))
        {
            System.out.println(getName1() + " wins!");
        }
        else
        {
            promptRestart();
        }
    }

    public void promptRestart()
    {
        System.out.print("Type '1' to restart: ");
        int restart = getS().nextInt();
        System.out.println("===============");
        if(restart == 1)
        {
            TicTacToe t = new TicTacToe();
            t.Play();
        }
        else
        {
            System.exit(1);
        }
    }

    public void displayPlayer()
    {
        if(getPlayerTurn() == 2)
        {
            System.out.print(getName2());
        }
        else
        {
            System.out.print(getName1());
        }
        System.out.print("'s Turn");
        System.out.println();
    }

    public void displayBoard()
    {
        for(int r = 0; r < getBoard().length; r++)
        {
            for(int c = 0; c < getBoard()[r].length; c++)
            {
                if(getBoard()[r][c] == 1)
                {
                    System.out.print(" X ");
                }
                else if(getBoard()[r][c] == 2)
                {
                    System.out.print(" O ");
                }
                else
                {
                    System.out.print("   ");
                }

                if(c != getBoard().length-1)
                {
                    System.out.print("|");
                }
                else
                {
                    System.out.println();
                    if(r != getBoard().length-1)
                    {
                        for(int i = 0; i < getBoard().length*4-1; i++)
                        {
                            System.out.print("-");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    public void place(int r, int c)
    {
        if(getBoard()[r-1][c-1] == 0)
        {
            getBoard()[r-1][c-1] = getPlayerTurn();
        }
        else if(boardIsFull())
        {
            System.out.println("==============");
            System.out.println("Board is full.");
            System.out.println("==============");
            Play();
        }
        else
        {
            System.out.println("================================");
            System.out.println("Space already filled. Try again.");
            System.out.println("================================");
            Play();
        }
    }

    public void switchPlayers()
    {
        if(playerTurn == 1)
        {
            setPlayerTurn(2);
        }
        else
        {
            setPlayerTurn(1);
        }
    }

    public boolean boardIsFull()
    {
        boolean boardIsFull = true;
        for(int r = 0; r < getBoard().length; r++)
        {
            for(int c = 0; c < getBoard()[r].length; c++)
            {
                if(available(r, c))
                {
                    boardIsFull = false;
                }
            }
        }
        return boardIsFull;
    }

    public boolean winner(int n)
    {
        boolean winner = false;
        for(int r = 0; r < getBoard().length; r++)
        {
            for (int c = 0; c < getBoard()[r].length; c++)
            {
                if(winningConditions(n))
                {
                    winner = true;
                }
            }
        }
        return winner;
    }

    public boolean winningConditions(int n) { return winRow1(n) || winRow2(n) || winRow3(n) || winCol1(n) || winCol2(n) || winCol3(n) || winDiag1(n) || winDiag2(n);}

    public boolean available(int r, int c)
    {
        return board[r][c] == 0;
    }

    public boolean winRow1(int n)
    {
        return (topLeft(n) && topMiddle(n) && topRight(n));
    }

    public boolean winRow2(int n)
    {
        return (middleLeft(n) && center(n) && middleRight(n));
    }

    public boolean winRow3(int n)
    {
        return (bottomLeft(n) && bottomMiddle(n) && bottomRight(n));
    }

    public boolean winCol1(int n)
    {
        return (topLeft(n) && middleLeft(n) && bottomLeft(n));
    }

    public boolean winCol2(int n)
    {
        return (topMiddle(n) && center(n) && bottomMiddle(n));
    }

    public boolean winCol3(int n)
    {
        return (topRight(n) && middleRight(n) && bottomRight(n));
    }

    public boolean winDiag1(int n)
    {
        return (topLeft(n) && center(n) && bottomRight(n));
    }

    public boolean winDiag2(int n)
    {
        return (topRight(n) && center(n) && bottomLeft(n));
    }

    public boolean topLeft(int n)
    {
        return board[0][0] == n;
    }

    public boolean topMiddle(int n)
    {
        return board[0][1] == n;
    }

    public boolean topRight(int n)
    {
        return board[0][2] == n;
    }

    public boolean middleLeft(int n)
    {
        return board[1][0] == n;
    }

    public boolean center(int n)
    {
        return board[1][1] == n;
    }

    public boolean middleRight(int n)
    {
        return board[1][2] == n;
    }

    public boolean bottomLeft(int n)
    {
        return board[2][0] == n;
    }

    public boolean bottomMiddle(int n)
    {
        return board[2][1] == n;
    }

    public boolean bottomRight(int n)
    {
        return board[2][2] == n;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public Scanner getS() {
        return s;
    }

    public void setS(Scanner s) {
        this.s = s;
    }

}
