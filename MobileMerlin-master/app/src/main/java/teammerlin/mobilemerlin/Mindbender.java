package teammerlin.mobilemerlin;

import java.util.Random;

public class Mindbender extends Minigame {

    private enum State {
        ChooseLength(),
        ChooseMystery(),
        UserInput(),
        CompareNumbers(),
        Freeze(),
        SameGame();
    }

    private State state;
    private int length, guessCount, pointer, blinking, steady;
    private int[] mystery, guess;
    private boolean first;

    public Mindbender()
    {
        super();
        state = State.ChooseLength;
        first = true;
    }

    @Override
    public void update(Panel panel)
    {
        //Temp variables
        int button = buttonPressed(panel);

        if(state == State.ChooseLength)
        {
            if(button != -1)
            {
                length = button;
                panel.clearLights();
                panel.setLight(10, 2);
                panel.playSound("tttCross");
                state = State.ChooseMystery;
            }
        }
        else if(state == State.ChooseMystery)
        {
            mystery = new int[length];
            guess = new int[length];
            for(int i = 0; i < length; i++)
            {
                mystery[i] = getRandomNumber();
            }
            guessCount = 0;
            pointer = 0;
            state = State.UserInput;
        }
        else if(state == State.UserInput)
        {
            if(button != -1)
            {
                panel.playSound("mm"+(button-1));
                guess[pointer] = button;
                pointer++;
            }
            if(pointer == length)
            {
                pointer = 0;
                guessCount++;
                state = State.CompareNumbers;
                first = true;
            }
        }
        else if(state == State.CompareNumbers)
        {
            if(first)
            {
                //Comparing guess values to mystery values
                blinking = 0;
                steady = 0;
                for (int i = 0; i < length; i++) {
                    //Blinking
                    if (guess[i] == mystery[i]) {
                        blinking++;
                    } else if (mysteryContains(guess[i])) {
                        steady++;
                    }
                }

                //Set blinking and steady lights
                panel.clearLights();
                for (int i = 0; i < blinking; i++) {
                    panel.setLight(1 + i, 2);
                }
                for (int i = 0; i < steady; i++) {
                    panel.setLight(1 + i + blinking, 1);
                }

                first = false;
                panel.setTimer(24);
            }
            else if(panel.timerReady())
            {
                if (blinking == length)//Won
                {
                    state = State.Freeze;
                    panel.playSound("win");
                } else {
                    state = State.UserInput;
                    panel.playSound("lose");
                }
            }

        }

        if(state == State.SameGame)
        {
            if(first)
            {
                if(guessCount > 9)
                {
                    guessCount = 9;
                }

                panel.clearLights();
                panel.setLight(guessCount, 1);

                first = false;
                panel.setTimer(24);
            }
            else if(panel.timerReady())
            {
                state = State.ChooseMystery;
                panel.clearLights();
                panel.setLight(10, 2);
            }
        }


    }

    @Override
    public void sameGame(Panel panel)
    {
        panel.playSound("select");
        if(state != State.ChooseLength)
        {
            state = State.SameGame;
            first = true;
        }
    }

    private int buttonPressed(Panel panel)
    {
        for(int i = 1; i < 10; i++)
        {
            if(panel.getButton(i))
            {
                return i;
            }
        }
        return -1;
    }

    private int getRandomNumber()
    {
        return (new Random().nextInt(9) + 1);
    }

    private boolean mysteryContains(int i)
    {
        for(int m : mystery)
        {
            if(m == i)
            {
                return true;
            }
        }
        return false;
    }

}
