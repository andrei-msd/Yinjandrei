package com.example.assignmenteight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {

    int lastScore;
    Boolean playing = true;
    Integer[] scoreArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        TextView highScoreLabel0 = (TextView) findViewById(R.id.textViewScore);
        TextView yourSchoorTv = findViewById(R.id.textViewYourScore);
        scoreArray = new Integer[6];
        Intent myIntent = getIntent();


        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        lastScore = myIntent.getIntExtra("score",0);
        scoreArray[0] = preferences.getInt("best1", 0);
        scoreArray[1] = preferences.getInt("best2", 0);
        scoreArray[2] = preferences.getInt("best3", 0);
        scoreArray[3] = preferences.getInt("best4", 0);
        scoreArray[4] = preferences.getInt("best5", 0);
        scoreArray[5] = -1;

        Arrays.sort(scoreArray, Collections.reverseOrder());
        addScore(lastScore);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("best1",scoreArray[0]);
        editor.putInt("best2",scoreArray[1]);
        editor.putInt("best3",scoreArray[2]);
        editor.putInt("best4",scoreArray[3]);
        editor.putInt("best5",scoreArray[4]);
        editor.apply();
        editor.commit();

        yourSchoorTv.setText(getResources().getString(R.string.your_score)+ lastScore);
        highScoreLabel0.setText(getResources().getString(R.string.leaderboard) + getResources().getString(R.string.one) + scoreArray[0] +
                getResources().getString(R.string.two) + scoreArray[1] +getResources().getString(R.string.three)+ scoreArray[2] +
                getResources().getString(R.string.four)+ scoreArray[3] + getResources().getString(R.string.five) + scoreArray[4]);




    }
    public void addScore(int score)
    {
        if(score >= scoreArray[4])
        {
            scoreArray[5] = score;
            Arrays.sort(scoreArray,Collections.reverseOrder());
        }

    }

    public void onclickReplay(View v)
    {
     //   finishAffinity();
        Intent intent = new Intent(this, GameActivity.class );
        intent.putExtra("playing", playing);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
    public void onclickHome(View v)
    {

        Intent intent = new Intent(this, HomeActivity.class );
        startActivity(intent);
        finish();
    }

}

