package teammerlin.mobilemerlin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import teammerlin.game.ID;

public class SquareButton extends Button {

    private Bitmap image;

    public SquareButton(int x, int y, int w, int h, Bitmap image, float scale, ID id)
    {
        super(x, y, w, h, scale, id);

        this.image = Bitmap.createScaledBitmap(image, w, h, false);
    }

    @Override
    public void draw(Canvas c, Paint p) {
        super.draw(c, p);

        c.drawBitmap(image, x, y, p);
    }
}
