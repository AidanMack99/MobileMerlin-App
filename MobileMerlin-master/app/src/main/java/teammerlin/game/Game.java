package teammerlin.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends SurfaceView implements Runnable {
	private static final long serialVersionUID = 1843927388793130474L;

	protected SurfaceHolder surfaceHolder;
	protected Context context;
	protected Thread thread;
	protected boolean running;
	protected TouchState touchState;
	protected Paint paint;
	protected SoundPool soundPool;

	protected int baseWidth, baseHeight, anchorX, anchorY;
	protected float scale;

	public Game(Context context, int baseWidth, int baseHeight) {
		super(context);

		surfaceHolder = getHolder();
		this.context = context;
		touchState = new TouchState();
		paint = new Paint();
		AudioAttributes audioAttributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
				.build();
		soundPool = new SoundPool.Builder()
				.setMaxStreams(6)
				.setAudioAttributes(audioAttributes)
				.build();
		soundPool.setVolume(1, 1, 1);
		this.baseWidth = baseWidth;
		this.baseHeight = baseHeight;
		getScale();
		init();
	}

	public void run() {
		Canvas canvas;
		//GAME CLOCK
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				touchState.update();
				update();
				delta--;
			}

			if (surfaceHolder.getSurface().isValid()) {
				canvas = surfaceHolder.lockCanvas();
				canvas.save();

				canvas.drawColor(Color.BLACK);
				draw(canvas, paint);

				canvas.restore();
				surfaceHolder.unlockCanvasAndPost(canvas);
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
	}


	public void pause() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
	}

	public void resume() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void destroy() {
        soundPool.release();
        soundPool = null;
	}
	
	public void init()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void draw(Canvas c, Paint p)
	{
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			touchState.touchX = (int)event.getX();
			touchState.touchY = (int)event.getY();
			touchState.touched = true;
			invalidate();
		}

		return true;
	}

	private void getScale()
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		float scaleX = size.x / baseWidth;
		float scaleY = size.y / baseHeight;

		if(scaleX < scaleY)
		{
			scale = scaleX;
		}
		else
		{
			scale = scaleY;
		}

		anchorX = (int)((size.x - (baseWidth * scale)) / 2);
		anchorY = (int)((size.y - (baseHeight * scale)) / 2);
	}


}
