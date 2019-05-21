package teammerlin.mobilemerlin;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import teammerlin.game.GameFramework;

public class MerlinMain extends AppCompatActivity {

	private MerlinFramework myGame;
	private int baseWidth = 270;
	private int baseHeight = 480;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		try
		{
			this.getSupportActionBar().hide();
		}
		catch (NullPointerException e){}

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Lock the screen to portrait

		myGame = new MerlinFramework(this, baseWidth, baseHeight);

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
}
