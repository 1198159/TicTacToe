import java.util.Random;
import java.util.Scanner;

public class TicTacToeAI extends TicTacToe{

    private boolean aiCanPlace;

    TicTacToeAI()
    {
        setBoard(new int[3][3]);
        setPlayerTurn(1);
        setName1("Player1");
        setName2("Computer");
        setS(new Scanner(System.in));
        aiCanPlace = false;
    }

    public boolean isAiCanPlace() {
        return aiCanPlace;
    }

    public void setAiCanPlace(boolean aiCanPlace) {
        this.aiCanPlace = aiCanPlace;
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
        }
        if(getBoard()[randRow-1][randCol-1] == 0 && aiCanPlace)
        {
            place(randRow, randCol);
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

}
