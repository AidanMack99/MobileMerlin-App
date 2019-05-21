package teammerlin.mobilemerlin;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe extends Minigame {

    private enum State {
        UserInput(),
        CompTurn(),
        CheckWinner(),
        Freeze();
    }

    private State state;
    private String[] grid; //Human = ex, AI = oh
    private int[][] solutions;
    private boolean first;
    private int AISolution;

    public TicTacToe()
    {
        super();
        state = State.UserInput;
        grid = newGrid();
        solutions = newSolutions();
        first = true;
        AISolution = pickNewSolution();
    }

    @Override
    public void update(Panel panel)
    {
        if(state == State.UserInput)
        {
            //Check for grid button presses
            int button = gridButtons(panel);

            //If there was a button pressed
            if(button != -1)
            {
                grid[button] = "ex";
                panel.setLight(10, 0);//Turn off light 10
                panel.setLight(button, 2);
                panel.playSound("tttCross");
                state = State.CheckWinner;
                panel.setTimer(24);
            }

            //If Comp Turn is pressed
            if(panel.getButton(14))
            {
                panel.setLight(10, 0);//Turn off light 10
                state = State.CompTurn;
            }
        }
        else if(state == State.CompTurn)
        {
            int placeToFill = 0;

            if(sumsContain(-2))//If the AI is set up to win, fill the solution
            {
                placeToFill = nextEmptyInSolution(getSolutionWithSum(-2));

            }
            else if(sumsContain(2))//If the human is set up to win, block the solution
            {
                placeToFill = nextEmptyInSolution(getSolutionWithSum(2));

            }
            else//If none of the above, follow AI Solution
            {
                //Check if AISolution is still valid
                if(!isSolutionValid(AISolution))
                {
                    AISolution = pickNewSolution();
                }

                //If AISolution = 0, then the grid is almost full so pick the next avail. grid place
                if(AISolution == 0)
                {
                    placeToFill = nextEmptyInGrid();
                }
                else
                {
                    placeToFill = nextEmptyInSolution(AISolution);
                }
            }

            //Make Computer turn
            grid[placeToFill] = "oh";
            panel.setLight(placeToFill, 1);
            panel.playSound("tttCircle");
            state = State.CheckWinner;
            panel.setTimer(24);
        }
        else if(state == State.CheckWinner && panel.timerReady())
        {
            if(sumsContain(3))//Human Wins
            {
                panel.playSound("win");
                state = State.Freeze;
            }
            else if(sumsContain(-3))//AI Wins
            {
                panel.playSound("lose");
                state = State.Freeze;
            }
            else if(isGridFull())//Tie
            {
                panel.playSound("tie");
                state = State.Freeze;
            }
            else//Game continues
            {
                state = State.UserInput;
            }
        }
    }

    private String[] newGrid()
    {
        String[] grid = new String[10];
        for (int i = 0; i < grid.length; i++) {

            grid[i] = "none";

        }
        return grid;
    }

    private int[][] newSolutions()
    {
        int[][] solutions = new int[9][3];

        solutions[0] = new int[]{0,0,0};
        solutions[1] = new int[]{1,4,7};
        solutions[2] = new int[]{2,5,8};
        solutions[3] = new int[]{3,6,9};
        solutions[4] = new int[]{1,2,3};
        solutions[5] = new int[]{4,5,6};
        solutions[6] = new int[]{7,8,9};
        solutions[7] = new int[]{1,5,9};
        solutions[8] = new int[]{3,5,7};

        return solutions;
    }

    private int gridButtons(Panel panel)
    {
        for(int i = 1; i < 10; i++)
        {
            if(panel.getButton(i) && grid[i] == "none")
            {
                return i;
            }
        }
        return -1;
    }

    private int getSolutionSum(int index)
    {
        int[] solution = solutions[index];
        int value = 0;

        for(int i = 0; i < 3; i++)
        {
            if(grid[solution[i]] == "ex")
            {
                value++;
            }
            else if(grid[solution[i]] == "oh")
            {
                value--;
            }
        }

        return value;
    }

    private boolean sumsContain(int value)
    {
        for (int i = 1; i < 9; i++)
        {
            if(getSolutionSum(i) == value)
            {
                return true;
            }
        }
        return false;
    }

    private int getSolutionWithSum(int value)
    {
        for (int i = 1; i < 9; i++)
        {
            if(getSolutionSum(i) == value)
            {
                return i;
            }
        }
        return 0;
    }

    private int nextEmptyInSolution(int index)
    {
        int[] solution = solutions[index];

        for(int i = 0; i < 3; i++)
        {
            if(grid[solution[i]] == "none")
            {
                return solution[i];
            }
        }

        return 0;
    }

    private int nextEmptyInGrid()
    {
        for(int i = 1; i < 10; i++)
        {
            if(grid[i] == "none")
            {
                return i;
            }
        }

        return 0;
    }

    private boolean isSolutionValid(int index)
    {
        int[] solution = solutions[index];

        for(int i = 0; i < 3; i++)
        {
            if(grid[solution[i]] == "ex")
            {
                return false;
            }
        }

        return true;
    }

    private int pickNewSolution()
    {
        ArrayList<Integer> possibleSolutions = new ArrayList<Integer>();

        //Go through each solution
        for (int i = 1; i < 9; i++)
        {
            if(isSolutionValid(i))
            {
                possibleSolutions.add(i);
            }
        }

        //Check possible solutions isn't empty
        if(possibleSolutions.isEmpty())
        {
            return 0;
        }

        //Pick a random solution
        Random r = new Random();
        return possibleSolutions.get(r.nextInt(possibleSolutions.size()));
    }

    private boolean isGridFull()
    {
        int count = 0;
        for (int i = 1; i < 10; i++)
        {
            if(grid[i] != "none")
            {
                count++;
            }
        }

        if(count == 9)
        {
            return true;
        }
        return false;
    }

}
