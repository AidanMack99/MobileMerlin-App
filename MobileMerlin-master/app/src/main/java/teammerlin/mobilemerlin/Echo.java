package teammerlin.mobilemerlin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Echo extends Minigame {
    private enum State {
        UserInput(),
        ChooseLength(),
        CheckWinner(),
        Playback();
    }

    private State state;
    private int length;
    private LinkedList<Integer> memory;
    private ArrayList<Integer> checkWin;
    private int playbackPlace;
    private int addNote;
    private Random random = new Random();
    boolean firstPlay = false;
    private int index;
    private int guessCount;
    private int guessesCorrect;

    ArrayList<String> list = new ArrayList<String>();

    public Echo()
    {
        super();
        state = State.ChooseLength;
        length = 1;
        memory = new LinkedList<Integer>();
        checkWin = new ArrayList<Integer>();
        playbackPlace = 0;
        addNote = 1;
        guessCount = 0;
        guessesCorrect = 0;
    }

    @Override
    public void update(Panel panel)
    {
        int button = gridButtons(panel);

        if(state==State.ChooseLength){
            if(button!= -1) { //if button pressed
                length = button;
                panel.playSound("tttCross");
                panel.clearLights();
                panel.setLight(10,2);
                state=State.UserInput;
                for(addNote=1; addNote<=length; addNote++) //Create random tune
                {
                    memory.add(getRandomButton());
                }
            }
        }

        if(state==State.UserInput){
            //If Comp Turn is pressed
            if (panel.getButton(14)) {
                panel.setLight(10, 0);//Turn off light 10
                state = State.Playback;
                panel.setTimer(24);
                firstPlay = true;
            }


            if(firstPlay) {
                if(button != -1) {
                    checkWin.add(button);

                    System.out.println("UISDGJHSDFBKJDSFJQ" + memory.get(guessCount));
                    System.out.println("UISDGJHSDFBKJDSFJQ" + checkWin.get(guessCount));
                    System.out.println("UISDGJHSDFBKJDSFJQ" + (memory.get(guessCount) == checkWin.get(guessCount)));
                    if(memory.get(guessCount) == checkWin.get(guessCount))//Guess correct
                    {
                        System.out.println("UISDGJHSDFBKJDSFJQ IM IN YUOU COCK");
                        panel.setLight(button, 1);
                        panel.playSound(buttonToSound(button));
                        guessesCorrect++;
                    }
                    else//Guess incorrect
                    {
                        panel.playSound("buzz");
                    }
                    guessCount++;

                    if(guessCount == length)//Once length of guesses have been entered
                    {
                        panel.setTimer(24);
                        state = State.CheckWinner;
                    }
                }
            }

        }

        if(state==State.CheckWinner && panel.timerReady())
        {
            panel.clearLights();
            if(guessesCorrect == length)
            {
                panel.playSound("win");
            }
            else
            {
                panel.playSound("lose");
                panel.setLight((length-guessesCorrect), 2);
            }

            guessCount = 0;
            guessesCorrect = 0;
            checkWin.clear();
            state = State.UserInput;
        }

        if(state==State.Playback && panel.timerReady()){
            if(playbackPlace != memory.size())
            {
                //Play sound
                panel.playSound(buttonToSound(memory.get(playbackPlace)));

                //Set lights
                panel.clearLights();
                panel.setLight(memory.get(playbackPlace), 1);

                //Move to next sound
                playbackPlace++;
            }
            else
            {
                playbackPlace = 0;
                panel.clearLights();
                panel.setLight(10, 2);
                state = State.UserInput;
            }
        }

    }

    private int gridButtons(Panel panel)
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

    private int getRandomButton(){ //choose a random button from the list
        int index = random.nextInt(9);
        return index+1;
    }

    private String buttonToSound(int b)
    {
        switch(b)
        {
            case 1:
                return "mm0";
            case 2:
                return "mm1";
            case 3:
                return "mm2";
            case 4:
                return "mm3";
            case 5:
                return "mm4";
            case 6:
                return "mm5";
            case 7:
                return "mm6";
            case 8:
                return "mm7";
            case 9:
                return "mm8";
        }
        return "mm9";
    }

}
