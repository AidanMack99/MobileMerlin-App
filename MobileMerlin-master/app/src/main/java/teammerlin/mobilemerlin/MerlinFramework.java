package teammerlin.mobilemerlin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

import java.util.HashMap;

import teammerlin.game.Game;
import teammerlin.game.GameState;
import teammerlin.game.ID;
//klkl
public class MerlinFramework extends Game
{
	private static final long serialVersionUID = 2338590104281194985L;

	//Comment
	GameState nextState, currentState, previousState;
	Bitmap[] images;
	HashMap<String, Integer> sounds;
    Panel panel;
	Minigame tictactoe, musicmachine, echo, blackjack, mindbender, magicsquare;

	public MerlinFramework(Context context, int baseWidth, int baseHeight)
	{
		super(context, baseWidth, baseHeight);
	}

	@Override
	public void init()
	{
		//Game Initialization

		//GameState
        nextState = GameState.Default;
		currentState = GameState.Default;
        previousState = null;

		//Images
		loadImages();

		//Sounds
		loadSounds();

		//Panel
        panel = new Panel(anchorX, anchorY, scale, images, sounds, soundPool, ID.Panel);

        //Minigames
        tictactoe = new TicTacToe();
        musicmachine = new MusicMachine();
        echo = new Echo();
        blackjack = new Blackjack();
        mindbender = new Mindbender();
        magicsquare = new MagicSquare();
	}

	@Override
	public void update()
	{
		//Game Updating
		panel.update(touchState);

		//Toggle Colour Button
        if(panel.getButton(15))
        {
            panel.toggleColour();
        }

        //Manual Button
        if(panel.getButton(16))
        {
            String url = "http://www.theelectronicwizard.com/manual.pdf";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }

		switch(currentState)
		{
			case Default:
                if(newState())
                {
                    panel.clearLights();
                    panel.setLight(10, 2);
                }

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }
				break;

			case NewGame:
			    if(newState())
                {
                    panel.clearLights();
                    panel.setLight(0, 2);
                }

                if(panel.getButton(11))
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                newGameSelection();
				break;

            case SameGame:
                if(newState())
                {
                    nextState = previousState;
                }
                break;

			case TicTacToe://1
                if(newState())
                {
                    panel.clearLights();
                    panel.setLight(10, 2);
                    panel.playSound("select");
                    tictactoe = new TicTacToe();
                }

                tictactoe.update(panel);

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                if(panel.getButton(12))//Same Game pressed
                {
                    nextState = GameState.SameGame;
                }
				break;

            case MusicMachine://2
                if(newState())
                {
                    panel.clearLights();
                    panel.setLight(10, 2);
                    panel.playSound("select");
                    panel.setTimer(24);
                    musicmachine = new MusicMachine();
                }

                musicmachine.update(panel);

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                if(panel.getButton(12))//Same Game pressed
                {
                    nextState = GameState.SameGame;
                }
                break;

            case Echo://3
                if(newState())
                {
                    panel.clearLights();
                    panel.setLight(0, 2);
                    panel.setLight(10, 2);
                    panel.playSound("select");
                    echo = new Echo();
                }

                echo.update(panel);

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                if(panel.getButton(12))//Same Game pressed
                {
                    nextState = GameState.SameGame;
                }
                break;

            case Blackjack://4
                if(newState())
                {
                    panel.clearLights();
                    panel.setLight(5,1);
                    panel.playSound("select");
                    panel.setTimer(24);
                    
                    blackjack = new Blackjack();
                }
                blackjack.update(panel);

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                if(panel.getButton(12))//Same Game pressed
                {
                    blackjack.sameGame(panel);
                }
                break;

            case MagicSquare://5
                if(newState())
                {


                    panel.playSound("select");
                    magicsquare = new MagicSquare();
                }

                magicsquare.update(panel);

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                if(panel.getButton(12))//Same Game pressed
                {
                    nextState = GameState.SameGame;
                }
                break;

            case Mindbender:
                if(newState())
                {
                    panel.clearLights();
                    panel.setLight(0, 2);
                    panel.setLight(10, 2);
                    panel.playSound("select");
                    mindbender = new Mindbender();
                }
                mindbender.update(panel);

                if(panel.getButton(11))//New Game pressed
                {
                    panel.playSound("newgame");
                    nextState = GameState.NewGame;
                }

                if(panel.getButton(12))//Same Game pressed
                {
                    mindbender.sameGame(panel);
                }
                break;

		}

		previousState = currentState;
        currentState = nextState;
	}

	@Override
	public void draw(Canvas c, Paint p)
	{
		//Game Drawing
		panel.draw(c, p);

/*        p.setARGB(255, 0, 0,0);
        p.setTextSize(80);
        c.drawText(Integer.toString(timer), 1, 100, p);*/
	}

	private void loadImages()
	{
		images = new Bitmap[6];

		images[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.newgamebutton);
		images[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.samegamebutton);
		images[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hitmebutton);
		images[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.compturnbutton);
		images[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.togglecolour);
        images[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.manualbutton);
	}

	private void loadSounds()
	{
		sounds = new HashMap<String, Integer>();

		sounds.put("mm0", soundPool.load(context, R.raw.music0, 1));
        sounds.put("mm1", soundPool.load(context, R.raw.music1, 1));
        sounds.put("mm2", soundPool.load(context, R.raw.music2, 1));
        sounds.put("mm3", soundPool.load(context, R.raw.music3, 1));
        sounds.put("mm4", soundPool.load(context, R.raw.music4, 1));
        sounds.put("mm5", soundPool.load(context, R.raw.music5, 1));
        sounds.put("mm6", soundPool.load(context, R.raw.music6, 1));
        sounds.put("mm7", soundPool.load(context, R.raw.music7, 1));
        sounds.put("mm8", soundPool.load(context, R.raw.music8, 1));
        sounds.put("mm9", soundPool.load(context, R.raw.music9, 1));
        sounds.put("blackjackComp", soundPool.load(context, R.raw.blackjackcomp, 1));
        sounds.put("buzz", soundPool.load(context, R.raw.buzz, 1));
        sounds.put("tttCircle", soundPool.load(context, R.raw.circle, 1));
        sounds.put("tttCross", soundPool.load(context, R.raw.cross, 1));
        sounds.put("lose", soundPool.load(context, R.raw.lose, 1));
        sounds.put("newgame", soundPool.load(context, R.raw.newgame, 1));
        sounds.put("select", soundPool.load(context, R.raw.select, 1));
        sounds.put("tie", soundPool.load(context, R.raw.tie, 1));
        sounds.put("win", soundPool.load(context, R.raw.win, 1));
	}

	private boolean newState()
    {
        return (currentState != previousState);
    }

    private void newGameSelection()
    {
        if(panel.getButton(1))
        {
            nextState = GameState.TicTacToe;
        }
        else if(panel.getButton(2))
        {
            nextState = GameState.MusicMachine;
        }
        else if(panel.getButton(3))
        {
            nextState = GameState.Echo;
        }
        else if(panel.getButton(4))
        {
            nextState = GameState.Blackjack;
        }
        else if(panel.getButton(5))
        {
            nextState = GameState.MagicSquare;
        }
        else if(panel.getButton(6))
        {
            nextState = GameState.Mindbender;
        }
        else if(panel.getButton(7) || panel.getButton(8) || panel.getButton(9) || panel.getButton(10))
        {
            panel.playSound("lose");
        }
    }

}
