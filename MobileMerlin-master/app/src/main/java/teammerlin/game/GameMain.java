package teammerlin.game;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GameMain extends AppCompatActivity {

	private GameFramework myGame;
	private int baseWidth = 100;
	private int baseHeight = 100;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		try
		{
			this.getSupportActionBar().hide();
		}
		catch (NullPointerException e){}

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Lock the screen to portrait

		myGame = new GameFramework(this, baseWidth, baseHeight);

		myGame.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);//Fill the screen with GameView

		setContentView(myGame);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		myGame.pause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		myGame.resume();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

	}
}
