import java.util.Scanner;

public class TicTacToe {

    private int[][] board;
    private int playerTurn;
    private String name1, name2;
    private Scanner s;
    private boolean aiCanPlace;

    TicTacToe()
    {
        board = new int[3][3];
        playerTurn = 1;
        name1 = "Player1";
        name2 = "Player2";
        s = new Scanner(System.in);
        aiCanPlace = false;
    }

    public void Play()
    {
        while(!boardIsFull())
        {
            if(winner(2))
            {
                System.out.println(name2 + " wins!");
            }
            else if(winner(1))
            {
                System.out.println(name1 + " wins!");
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
                int row = s.nextInt();
                System.out.print("Column: ");
                int column = s.nextInt();
                place(row, column);
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
            System.out.println(name2 + " wins!");
        }
        else if(winner(1))
        {
            System.out.println(name1 + " wins!");
        }
        else
        {
            promptRestart();
        }
    }

    public void PlayAgainstAI()
    {
        name2 = "Computer";
        while(!boardIsFull())
        {
            if(winner(2))
            {
                System.out.println(name2 + " wins!");
            }
            else if(winner(1))
            {
                System.out.println(name1 + " wins!");
            }
            else
            {
                displayPlayer();
            }
            System.out.println("===============");
            displayBoard();
            if(winner(1) || winner(2))
            {
                promptRestartAI();
            }
            else if(playerTurn == 1)
            {
                System.out.print("Row: ");
                int row = s.nextInt();
                System.out.print("Column: ");
                int column = s.nextInt();
                placeAI(row, column);
                switchPlayers();
                System.out.println("===============");
                aiCanPlace = true;
            }
            else {
                for (int r = 0; r < board.length; r++)
                {
                    for (int c = 0; c < board[r].length; c++)
                    {
                        if (board[r][c] == 0 && aiCanPlace)
                        {
                            if(((topMiddle(2) && topLeft(2))
                                    || (topMiddle(1) && topLeft(1))
                                    || (middleLeft(2) && bottomLeft(2))
                                    || (middleLeft(1) && bottomLeft(1))
                                    || (center(2) && bottomRight(2))
                                    || (center(1) && bottomRight(1)))
                                    && topLeft(0))
                            {
                                placeAI(1, 1);
                            }
                            else if(((topLeft(2) && bottomLeft(2))
                                    || (topLeft(1) && bottomLeft(1))
                                    || (center(2) && middleRight(2))
                                    || (center(1) && middleRight(1)))
                                    && middleLeft(0))
                            {
                                placeAI(2, 1);
                            }
                            else if(((topLeft(2) && middleLeft(2))
                                    || (topLeft(1) && middleLeft(1))
                                    || (bottomMiddle(2) && bottomRight(2))
                                    || (bottomMiddle(1) && bottomRight(1))
                                    || (topRight(2) && center(2))
                                    || (topRight(1) && center(1)))
                                    && bottomLeft(0))
                            {
                                placeAI(3, 1);
                            }
                            else if(((topLeft(2) && topRight(2))
                                    || (topLeft(1) && topRight(1))
                                    || (center(2) && bottomMiddle(2))
                                    || (center(1) && bottomMiddle(1)))
                                    && topMiddle(0))
                            {
                                placeAI(1, 2);
                            }
                            else if(((topLeft(2) && topMiddle(2))
                                    || (topLeft(1) && topMiddle(1))
                                    || (center(2) && bottomLeft(2))
                                    || (center(1) && bottomLeft(1))
                                    || (middleRight(2) && bottomRight(2))
                                    || (middleRight(1) && bottomRight(1)))
                                    && topRight(0))
                            {
                                placeAI(1, 3);
                            }
                            else if(((topLeft(2) && bottomRight(2))
                                    || (topLeft(1) && bottomRight(1))
                                    || (topRight(2) && bottomLeft(2))
                                    || (topRight(1) && bottomLeft(1))
                                    || (topMiddle(2) && bottomMiddle(2))
                                    || (topMiddle(1) && bottomMiddle(1))
                                    || (middleLeft(2) && middleRight(2))
                                    || (middleLeft(1) && middleRight(1)))
                                    && center(0))
                            {
                                placeAI(2, 2);
                            }
                            else if(((topRight(2) && bottomRight(2))
                                    || (topRight(1) && bottomRight(1))
                                    || (middleLeft(2) && center(2))
                                    || (middleLeft(1) && center(1)))
                                    && middleRight(0))
                            {
                                placeAI(2,3);
                            }
                            else if(((bottomLeft(2) && bottomRight(2))
                                    || (bottomLeft(1) && bottomRight(1))
                                    || (topMiddle(2) && center(2))
                                    || (topMiddle(1) && center(1)))
                                    && bottomMiddle(0))
                            {
                                placeAI(3,2);
                            }
                            else if(((topRight(2) && middleRight(2))
                                    || (topRight(1) && middleRight(1))
                                    || (bottomLeft(2) && bottomMiddle(2))
                                    || (bottomLeft(1) && bottomMiddle(1))
                                    || (topLeft(2) && center(2))
                                    || (topLeft(1) && center(1)))
                                    && bottomRight(0))
                            {
                                placeAI(3,3);
                            }
                            else
                            {
                                placeAI(r + 1, c + 1);
                            }
                            switchPlayers();
                            aiCanPlace = false;
                        }
                    }
                }
                System.out.println("===============");
            }
        }
        if(boardIsFull() && (!winner(1) || !winner(2)))
        {
            System.out.println("Tie.");
            System.out.println("=====");
            promptRestartAI();
        }
        else if(winner(2))
        {
            System.out.println(name2 + " wins!");
        }
        else if(winner(1))
        {
            System.out.println(name1 + " wins!");
        }
        else
        {
            promptRestartAI();
        }
    }

    public void promptRestart()
    {
        System.out.print("Type '1' to restart: ");
        int restart = s.nextInt();
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

    public void promptRestartAI()
    {
        System.out.print("Type '1' to restart: ");
        int restart = s.nextInt();
        System.out.println("===============");
        if(restart == 1)
        {
            TicTacToe t = new TicTacToe();
            t.PlayAgainstAI();
        }
        else
        {
            System.exit(1);
        }
    }

    public void displayPlayer()
    {
        if(playerTurn == 2)
        {
            System.out.print(name2);
        }
        else
        {
            System.out.print(name1);
        }
        System.out.print("'s Turn");
        System.out.println();
    }

    public void displayBoard()
    {
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[r].length; c++)
            {
                if(board[r][c] == 1)
                {
                    System.out.print(" X ");
                }
                else if(board[r][c] == 2)
                {
                    System.out.print(" O ");
                }
                else
                {
                    System.out.print("   ");
                }

                if(c != board.length-1)
                {
                    System.out.print("|");
                }
                else
                {
                    System.out.println();
                    if(r != board.length-1)
                    {
                        for(int i = 0; i < board.length*4-1; i++)
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
        if(this.board[r-1][c-1] == 0)
        {
            this.board[r-1][c-1] = playerTurn;
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

    public void placeAI(int r, int c)
    {
        if(this.board[r-1][c-1] == 0)
        {
            this.board[r-1][c-1] = playerTurn;
        }
        else if(boardIsFull())
        {
            System.out.println("==============");
            System.out.println("Board is full.");
            System.out.println("==============");
            PlayAgainstAI();
        }
        else
        {
            System.out.println("================================");
            System.out.println("Space already filled. Try again.");
            System.out.println("================================");
            PlayAgainstAI();
        }
    }

    public void switchPlayers()
    {
        if(playerTurn == 1)
        {
            playerTurn = 2;
        }
        else
        {
            playerTurn = 1;
        }
    }

    public boolean boardIsFull()
    {
        boolean boardIsFull = true;
        for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board[r].length; c++)
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
        for(int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                if(winRow1(n) || winRow2(n) || winRow3(n)
                        || winCol1(n) || winCol2(n) || winCol3(n)
                        || winDiag1(n) || winDiag2(n))
                {
                    winner = true;
                }
            }
        }
        return winner;
    }

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
}
