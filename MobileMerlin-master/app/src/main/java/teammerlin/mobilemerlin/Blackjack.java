package teammerlin.mobilemerlin;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import teammerlin.game.GameState;

public class Blackjack extends Minigame {

    private enum State {
        DisplayChips(),
        UserInput(),
        CompTurn(),
        CheckWinner(),
        Win(),
        Tie(),
        Lose(),
        Freeze();
    }

    boolean newhand;
    boolean displaychips;
    boolean secondhand;
    boolean hit;
    boolean stand;
    boolean bust;
    boolean merlinbust;
    boolean freeze;

    private State state;
    private int chips;
    private boolean first, stick;
    private ArrayList<Integer> userhand, dealerhand, deck;

    public Blackjack()
    {
        super();

        chips = 5;
        first = true;
        stick = false;
        userhand = new ArrayList<Integer>();
        dealerhand = new ArrayList<Integer>();
        deck = newDeck();

        state = State.DisplayChips;
    }

    @Override
    public void update(Panel panel)
    {
        if(state == State.DisplayChips)
        {
            if(first)
            {
                panel.setLight(chips, 1);
                panel.setTimer(24);
                first = false;
            }
            else
            {
                if(panel.timerReady())
                {
                    panel.clearLights();
                    userhand = dealCard(userhand);
                    panel.setLight(userhand.get(userhand.size()-1), 2);
                    dealerhand = dealCard(dealerhand);
                    panel.setLight(dealerhand.get(dealerhand.size()-1), 1);
                    state = State.UserInput;
                }
            }
        }
        else if(state == State.UserInput)
        {
            if(panel.getButton(13))//If Hit Me is pressed
            {
                panel.playSound("mm9");
                userhand = dealCard(userhand);
                panel.setLight(userhand.get(userhand.size()-1), 2);
                state = State.CheckWinner;
            }
            else if(panel.getButton(14))//If Comp Turn is pressed
            {
                stick = true;
                state = State.CompTurn;
                panel.setTimer(24);
            }
        }
        else if(state == State.CompTurn && panel.timerReady())
        {
            panel.playSound("blackjackComp");
            dealerhand = dealCard(dealerhand);
            panel.setLight(dealerhand.get(dealerhand.size()-1), 1);
            state = State.CheckWinner;
        }
        else if(state == State.CheckWinner)
        {
            if(!stick)//If we came from a user move
            {
                if(getHandTotal(userhand) > 13)//If the user has gone bust
                {
                    state = State.Lose;
                    panel.setTimer(24);
                }
                else if(getHandTotal(userhand) == 13)//If the user gets 13
                {
                    state = State.Win;
                    panel.setTimer(24);
                }
                else//Nothing happens
                {
                    state = State.UserInput;
                }
            }
            else//If we came from a dealer move
            {
                if(getHandTotal(dealerhand) > 13)//If the dealer has gone bust
                {
                    state = State.Win;
                    panel.setTimer(24);
                }
                else if(getHandTotal(dealerhand) > getHandTotal(userhand))//If the dealer hasn't gone bust, but has a higher score than the user
                {
                    state = state.Lose;
                    panel.setTimer(24);
                }
                else if(getHandTotal(dealerhand) == getHandTotal(userhand))//If the dealer hasn't gone bust, but has the same score as the user
                {
                    state = state.Tie;
                    panel.setTimer(24);
                }
                else//Nothing happens
                {
                    state = State.CompTurn;
                    panel.setTimer(24);
                }
            }
        }
        else if(state == State.Win && panel.timerReady())
        {
            panel.playSound("win");
            chips++;
            state = State.Freeze;
        }
        else if(state == State.Tie && panel.timerReady())
        {
            panel.playSound("tie");
            state = State.Freeze;
        }
        else if(state == State.Lose && panel.timerReady())
        {
            panel.playSound("lose");
            chips--;
            state = State.Freeze;
        }
    }

    @Override
    public void sameGame(Panel panel)
    {
        if(state == State.Freeze)
        {
            if(chips == 0)
            {
                panel.playSound("lose");
                panel.clearLights();
                panel.setLight(0, 1);
            }
            else if(chips == 10)
            {
                panel.playSound("win");
                panel.clearLights();
                panel.setLight(10, 1);
            }
            else
            {
                panel.playSound("select");
                panel.clearLights();
                first = true;
                stick = false;
                userhand = new ArrayList<Integer>();
                dealerhand = new ArrayList<Integer>();
                deck = newDeck();
                state = State.DisplayChips;
            }
        }
        else
        {
            panel.playSound("select");
            panel.clearLights();
            chips = 5;
            first = true;
            stick = false;
            userhand = new ArrayList<Integer>();
            dealerhand = new ArrayList<Integer>();
            deck = newDeck();
            state = State.DisplayChips;
        }
    }

    private ArrayList<Integer> newDeck()
    {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i = 1; i < 11; i++)
        {
            arr.add(i);
        }
        return arr;
    }

    private ArrayList<Integer> dealCard(ArrayList<Integer> arr)
    {
        Random r = new Random();
        int index = r.nextInt(deck.size());
        arr.add(deck.get(index));
        deck.remove(index);
        return arr;
    }

    private int getHandTotal(ArrayList<Integer> arr)
    {
        int value = 0;
        for(int i : arr)
        {
            value += i;
        }
        return value;
    }
}
