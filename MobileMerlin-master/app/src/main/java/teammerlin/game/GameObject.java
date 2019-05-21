package teammerlin.game;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject 
{
	protected int x, y;
	protected float scale;
	protected ID id;
	
	public GameObject()
	{
		x = 0;
		y = 0;
		scale = 0;
		id = ID.Blank;
	}
	
	public GameObject(int x, int y, float scale, ID id)
	{
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.id = id;
	}
	
	public abstract void update();
	public abstract void draw(Canvas c, Paint p);
	
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	
	public ID getID()
	{
		return id;
	}
	public void setID(ID id)
	{
		this.id = id;
	}

	public int s(int value)
	{
		return (int) (value*scale);
	}
}
