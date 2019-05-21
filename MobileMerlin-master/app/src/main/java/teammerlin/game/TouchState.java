package teammerlin.game;

public class TouchState {

    int touchX, touchY;
    boolean touched, previous;

    public TouchState()
    {
        touchX = 0;
        touchY = 0;
        touched = false;
        previous = false;
    }

    public void update()
    {
        if(previous)
        {
            touched = false;
        }

        previous = touched;
    }

    public int getX()
    {
        return touchX;
    }

    public int getY()
    {
        return touchY;
    }

    public boolean getTouched()
    {
        return touched;
    }

}
