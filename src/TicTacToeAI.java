import java.util.Random;
import java.util.Scanner;

public class TicTacToeAI extends TicTacToe{

    private boolean aiCanPlace;

    TicTacToeAI()
    {
        setBoard(new int[3][3]);
        setPlayerTurn(1);
        setName1("Player1");
        setName2("Player2");
        setS(new Scanner(System.in));
        aiCanPlace = false;
    }

    public void Play()
    {
        setName2("Computer");
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
//                System.out.println("aiCanPlace: "+aiCanPlace);
                displayPlayer();
            }
            System.out.println("===============");
            displayBoard();
            if(winner(1) || winner(2))
            {
                promptRestart();
            }
            else if(getPlayerTurn() == 1)
            {
                System.out.print("Row: ");
                int row = getS().nextInt();
                System.out.print("Column: ");
                int column = getS().nextInt();
                place(row, column);
                switchPlayers();
                System.out.println("===============");
                aiCanPlace = true;
            }
            else {
                for (int r = 0; r < getBoard().length; r++)
                {
                    for (int c = 0; c < getBoard()[r].length; c++)
                    {
                        if (getBoard()[r][c] == 0 && aiCanPlace)
                        {
                            if(canWin(2))
                            {
                                aiPlace(2);
                                System.out.println("aiPlace(2)");
                            }
                            else if(canWin(1))
                            {
                                aiPlace(1);
                                System.out.println("aiPlace(1)");
                            }
                            else
                            {
                                aiPlaceRandom();
                                System.out.println("aiPlaceRandom()");
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
            displayPlayer();
            System.out.println("===============");
            displayBoard();
            System.out.println("===========");
            System.out.println("Tie.");
            System.out.println("=====");
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
        else if(getBoard()[r-1][c-1] != 0)
        {

            System.out.println("================================");
            System.out.println("Space already filled. Try again.");
            System.out.println("Row: " + r);
            System.out.println("Col: " + c);
            if(getBoard()[r-1][c-1] == 1)
            {
                System.out.println("Space occupied by: " + getName1());
            }
            else if(getBoard()[r-1][c-1] == 2)
            {
                System.out.println("Space occupied by: " + getName2());
            }
            System.out.println("================================");
            Play();
        }
        else
        {
            System.out.println("ERROR!");
        }
    }

    public void aiPlace(int n)
    {
        if(canWinTopLeft(n))
        {
            place(1, 1);
        }
        else if(canWinTopMiddle(n))
        {
            place(1,2);
        }
        else if(canWinTopRight(n))
        {
            place(1,3);
        }
        else if(canWinMiddleLeft(n))
        {
            place(2,1);
        }
        else if(canWinCenter(n))
        {
            place(2,2);
        }
        else if(canWinMiddleRight(n))
        {
            place(2,3);
        }
        else if(canWinBottomLeft(n))
        {
            place(3,1);
        }
        else if(canWinBottomMiddle(n))
        {
            place(3,2);
        }
        else if(canWinBottomRight(n))
        {
            place(3,3);
        }
        else
        {
            System.out.println("Can't win");
        }
    }

    public void aiPlaceRandom()
    {
        Random random1 = new Random();
        Random random2 = new Random();
        int randRow = random1.nextInt(getBoard().length)+1;
        int randCol = random2.nextInt(getBoard().length)+1;
        while(!(randRow < getBoard().length+1 && randRow > 0 && randCol < getBoard().length+1 && randCol > 0) || getBoard()[randRow-1][randCol-1] != 0)
        {
            randRow = random1.nextInt(getBoard().length)+1;
            randCol = random2.nextInt(getBoard().length)+1;
//            System.out.println("New random numbers: " + "(" + randRow + "," + randCol + ")");
        }
        if(getBoard()[randRow-1][randCol-1] == 0 && aiCanPlace)
        {
            place(randRow, randCol);
//            System.out.println("place("+randRow+","+randCol+")");
        }
    }

    public void promptRestart()
    {
        System.out.print("Type '1' to restart: ");
        int restart = getS().nextInt();
        System.out.println("===============");
        if(restart == 1)
        {
            TicTacToeAI t = new TicTacToeAI();
            t.Play();
        }
        else
        {
            System.exit(1);
        }
    }

    public boolean canWin(int n)
    {
        return  canWinTopLeft(n) || canWinTopMiddle(n) || canWinTopRight(n)
                ||
                canWinMiddleLeft(n) || canWinCenter(n) || canWinMiddleRight(n)
                ||
                canWinBottomLeft(n) || canWinBottomMiddle(n) || canWinBottomRight(n)
                ;
    }

    public boolean canWinTopLeft(int n)
    {
        return (((topMiddle(n) && topLeft(n))
                || (middleLeft(n) && bottomLeft(n))
                || (center(n) && bottomRight(n)))
                && topLeft(0));
    }

    public boolean canWinTopMiddle(int n)
    {
        return (((topLeft(n) && topRight(n))
                || (center(n) && bottomMiddle(n)))
                && topMiddle(0));
    }

    public boolean canWinTopRight(int n)
    {
        return (((topLeft(n) && topMiddle(n))
                || (center(n) && bottomLeft(n))
                || (middleRight(n) && bottomRight(n)))
                && topRight(0));
    }

    public boolean canWinMiddleLeft(int n)
    {
        return (((topLeft(n) && bottomLeft(n))
                || (center(n) && middleRight(n)))
                && middleLeft(0));
    }

    public boolean canWinCenter(int n)
    {
        return (((topLeft(n) && bottomRight(n))
                || (topRight(n) && bottomLeft(n))
                || (topMiddle(n) && bottomMiddle(n))
                || (middleLeft(n) && middleRight(n)))
                && center(0));
    }

    public boolean canWinMiddleRight(int n)
    {
        return (((topRight(n) && bottomRight(n))
                || (middleLeft(n) && center(n)))
                && middleRight(0));
    }

    public boolean canWinBottomLeft(int n) {
        return (((topLeft(n) && middleLeft(n))
                || (bottomMiddle(n) && bottomRight(n))
                || (topRight(n) && center(n)))
                && bottomLeft(0));
    }

    public boolean canWinBottomMiddle(int n)
    {
        return (((bottomLeft(n) && bottomRight(n))
                || (topMiddle(n) && center(n)))
                && bottomMiddle(0));
    }

    public boolean canWinBottomRight(int n)
    {
        return (((topRight(n) && middleRight(n))
                || (bottomLeft(n) && bottomMiddle(n))
                || (topLeft(n) && center(n)))
                && bottomRight(0));
    }

}
