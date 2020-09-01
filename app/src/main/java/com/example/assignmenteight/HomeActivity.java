package com.example.assignmenteight;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
     Boolean playing = true;
 Mplayer musicPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);


        musicPlayer = new Mplayer(this, R.raw.introbgm);
        musicPlayer.startMusic();
        playing =true;


    }
        public void onclickStart(View v)
        {

            //finishAffinity();
           // finish();
            Intent intent = new Intent(this, GameActivity.class );
            intent.putExtra("playing", playing);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }


        public void onclickScore(View v)
        {
            //finish();
           // finishAffinity();
            Intent intent = new Intent(this, ScoreActivity.class );
            startActivity(intent);
            finish();
        }

    public void onclickMusic(View v)
    {

        if(playing){
            musicPlayer.stopMusic();
            playing = false;

        }
        else {
            musicPlayer.startMusic();
            playing = true;

        }


    }

    @Override
    protected  void onResume() {
        super.onResume();
        if (playing)
      musicPlayer.startMusic();
      playing = true;

    }


    @Override
    protected  void onPause() {
        super.onPause();

        musicPlayer.stopMusic();
        playing = false;
    }



}
