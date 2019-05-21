package teammerlin.mobilemerlin;

import android.graphics.Canvas;
import android.graphics.Paint;

import teammerlin.game.ID;

public class CircleButton extends Button {

    private int light;

    public CircleButton(int x, int y, int w, int h, float scale, ID id)
    {
        super(x, y, w, h, scale, id);

        light = 0;
    }

    @Override
    public void draw(Canvas c, Paint p) {
        super.draw(c, p);
    }

    public void draw(Canvas c, Paint p, boolean  blinking, boolean isRed) {
        super.draw(c, p);

        //Draw Square
        if(isRed)
        {
            p.setARGB(255, 120, 0,1);
        }
        else
        {
            p.setARGB(255, 12, 12,55);
        }
        c.drawRect(x, y, x + s(50), y + s(50), p);

        //Draw Silver Circle
        p.setARGB(255, 211, 211 , 211);
        c.drawOval(x + s(6), y + s(6), x + s(44), y + s(44), p);

        //Draw Black Circle
        p.setARGB(255, 0, 0 , 0);
        c.drawOval(x + s(14), y + s(14), x + s(36), y + s(36), p);

        //Draw Light Circle
        if(light == 0)
        {
            p.setARGB(255, 0, 0 , 0);
            c.drawOval(x + s(17), y + s(17), x + s(33), y + s(33), p);
        }
        else if(light == 1)
        {
            p.setARGB(255, 255, 0 , 0);
            c.drawOval(x + s(17), y + s(17), x + s(33), y + s(33), p);
        }
        else
        {
            if(blinking)
            {
                p.setARGB(255, 255, 0 , 0);
                c.drawOval(x + s(17), y + s(17), x + s(33), y + s(33), p);
            }
            else
            {
                p.setARGB(255, 0, 0 , 0);
                c.drawOval(x + s(17), y + s(17), x + s(33), y + s(33), p);
            }
        }
    }

    public int getLight()
    {
        return light;
    }

    public void setLight(int value)
    {
        light = value;
    }
}
