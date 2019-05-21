package teammerlin.mobilemerlin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.HashMap;

import teammerlin.game.GameObject;
import teammerlin.game.ID;
import teammerlin.game.TouchState;

public class Panel extends GameObject
{

	Button[] buttons;
    HashMap<String, Integer> sounds;
    SoundPool soundPool;

	int timer, gameTimer, gameTimerLimit;
	boolean blinking, gameTimerReady, isRed;

	public Panel(int x, int y, float scale, Bitmap[] images, HashMap<String, Integer> sounds, SoundPool soundPool, ID id)
	{
		super(x, y , scale, id);

		setButtons(images);
		this.sounds = sounds;
		this.soundPool = soundPool;

		timer = 0;
		gameTimer = 0;
		gameTimerLimit = 1;
		gameTimerReady = false;
		blinking = false;
		isRed = true;
	}

	@Override
	public void update() {}

	public void update(TouchState touch) {

		//Game Timer
		if(gameTimer == gameTimerLimit)
		{
			gameTimer = 0;
			gameTimerReady = true;
		}
		else
		{
			gameTimer++;
			gameTimerReady = false;
		}


		//Timer
		timer++;
		if(timer == 10)
		{
			blinking = !blinking;
			timer = 0;
		}

		//Buttons
		for(Button b : buttons) {
			b.update(touch);
		}
	}

	public int getTimer()
	{
		return timer;
	}

	public void setTimer(int limit)
	{
		gameTimer = 0;
		gameTimerReady = false;
		gameTimerLimit = limit;
	}

	public boolean timerReady()
	{
		return gameTimerReady;
	}

	@Override
	public void draw(Canvas c, Paint p) {
		
		//Draw Back
		if(isRed)
		{
			p.setARGB(255, 148, 19,9);
		}
		else
		{
			p.setARGB(255, 12, 12,92);
		}
        c.drawRect(x, y, x + s(270), y + s(480), p);

		//Draw Circle Buttons
		((CircleButton)buttons[0]).draw(c, p, blinking, isRed);
		for(int l = 0; l < 3; l++)
		{
			for(int m = 0; m < 3; m++)
			{
				((CircleButton)buttons[((l * 3) + 1) + m]).draw(c, p, blinking, isRed);
			}
		}
		((CircleButton)buttons[10]).draw(c, p, blinking, isRed);
		
		//Draw Square Buttons
		((SquareButton)buttons[11]).draw(c, p);
		((SquareButton)buttons[12]).draw(c, p);
		((SquareButton)buttons[13]).draw(c, p);
		((SquareButton)buttons[14]).draw(c, p);
        ((SquareButton)buttons[15]).draw(c, p);
		((SquareButton)buttons[16]).draw(c, p);
	}

	public void playSound(String key)
	{
		System.out.println(soundPool.play(sounds.get(key), 1, 1, 1, 0, 1));
	}
	
	public void setLight(int index, int value)
	{
		((CircleButton)buttons[index]).setLight(value);
	}

	public void switchLight(int index)
	{
		CircleButton button = (CircleButton)buttons[index];

		if(button.getLight() == 0)
		{
			setLight(index, 2);
		}
		else if(button.getLight() == 2)
		{
			setLight(index, 0);
		}
	}

	public void clearLights()
	{
		for(int l = 0; l < 11; l++)
		{
			((CircleButton)buttons[l]).setLight(0);
		}
	}

	public boolean isMagicSquare()
	{
		boolean value = true;

		for(int i = 1; i < 10; i++)
		{
			if(i != 5)//If not the middle light
			{
				if(((CircleButton)buttons[i]).getLight() != 2)//If light isnt blinking
				{
					value = false;
				}
			}
			else
			{
				if(((CircleButton)buttons[i]).getLight() != 0)//If light isnt off
				{
					value = false;
				}
			}
		}

		return value;
	}

	public void toggleColour()
    {
        if(isRed)
        {
            isRed = false;
        }
        else
        {
            isRed = true;
        }
    }
	
	public boolean getButton(int index)
	{
		return buttons[index].state;
	}

	private void setButtons(Bitmap[] images)
	{
		buttons = new Button[17];

		buttons[0] = new CircleButton(x + s(110), y + s(25), s(50), s(50), scale, ID.CircleButton);
		for(int l = 0; l < 3; l++)
		{
			for(int m = 0; m < 3; m++)
			{
				buttons[((l * 3) + 1) + m] = new CircleButton(x + s(50) + (m * s(60)), y + s(85) + (l * s(60)), s(50), s(50), scale, ID.CircleButton);
			}
		}
		buttons[10] = new CircleButton(x + s(110), y + s(265), s(50), s(50), scale, ID.CircleButton);
		
		buttons[11] = new SquareButton(x + s(75), y + s(340), s(50), s(50), images[0], scale, ID.SquareButton);
		buttons[12] = new SquareButton(x + s(145), y + s(340), s(50), s(50), images[1], scale, ID.SquareButton);
		buttons[13] = new SquareButton(x + s(75), y + s(405), s(50), s(50), images[2], scale, ID.SquareButton);
		buttons[14] = new SquareButton(x + s(145), y + s(405), s(50), s(50), images[3], scale, ID.SquareButton);
		buttons[15] = new SquareButton(x + s(8), y + s(8), s(40), s(40), images[4], scale, ID.SquareButton);
		buttons[16] = new SquareButton(x + s(222), y + s(8), s(40), s(40), images[5], scale, ID.SquareButton);
	}
}
