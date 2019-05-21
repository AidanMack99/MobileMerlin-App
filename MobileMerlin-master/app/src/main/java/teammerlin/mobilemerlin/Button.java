package teammerlin.mobilemerlin;

import android.graphics.Canvas;
import android.graphics.Paint;

import teammerlin.game.GameObject;
import teammerlin.game.ID;
import teammerlin.game.TouchState;


public class Button extends GameObject {

	int w, h;
	boolean state;
	
	public Button(int x, int y, int w, int h, float scale, ID id)
	{
		super(x, y, scale, id);
		this.w = w;
		this.h = h;
		state = false;
	}

	@Override
	public void update() {
		
		
	}
	
	public void update(TouchState touch)
	{
		if(touch.getTouched() && contains(touch.getX(), touch.getY()))
		{
			state = true;
		}
		else
		{
			state = false;
		}
	}
	
	public boolean getState()
	{
		return state;
	}

	@Override
	public void draw(Canvas c, Paint p) {
		
	}
	
	public boolean contains(int mX, int mY)
	{
	  if(x+w > mX &&
	     x < mX &&
	     y < mY &&
	     y+h > mY)
      {
		  return true;
	  }
	  return false;
	}
}
