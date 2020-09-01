package com.example.assignmenteight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    Mplayer musicPlayer;
    SensorManager sensorMgr;
    Sensor accelerometer;
    private float sensorX;
    CountDownTimer countDownTimer;
    TextView scoreCounter;
    static final int MAX_TIME = 30000;
    Boolean playable = false;
    Boolean playing = true;
    int score = 0;
    int width;
    public Ball ball;
    public Player player;
    private  SoundPlayer sound;

    TextView timerCounter;

    DisplayMetrics displayMetrics = new DisplayMetrics();
    private ArrayList<Sprite> spriteList = new ArrayList<>();
    private ArrayList<Target> targetList = new ArrayList<>();
    private ArrayList<ObstIncreaseSize> upSizeList = new ArrayList<>();
    private ArrayList<ObstBomb> bombList = new ArrayList<>();


    SensorEventListener accelListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            sensorX = event.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    public class GraphicsView extends View {

        Ball ball;
        GestureDetector gestureDetector;

        final static int BALL_AREA_PADDING = 350;
        final static int VELOCITY_DIVISOR = 300;

        public GraphicsView(Context context) {
            super(context);
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            player = new Player(context);
            spriteList.add(player);
            ball = new Ball(context, player);

            gestureDetector = new GestureDetector(context, new MyGestureListener());
            spriteList.add(ball);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            player.setXSpeed(-sensorX * 3);
            for (Sprite sprite : spriteList) {
                sprite.onDraw(canvas);
            }
            if (!ball.isFlinged()) {
                ball.setXSpeed(-sensorX * 3);



            /*  if (ball.getX() - ball.getRadius() <= 0){
                  ball.setX(ball.getRadius());
              }
              else if(ball.getX() + ball.getRadius() >= width){
                  ball.setX(width-ball.getRadius());
              }*/
            }

            Target target = ball.makeTarget(getContext());
            ObstIncreaseSize upSize = ball.makeObstSizeUp(getContext());

            if (target != null) {
                spriteList.add(target);
                targetList.add(target);

            }
            if (upSize != null) {
                spriteList.add(upSize);
                upSizeList.add(upSize);
            }
            invalidate();

            for (Target hitTarget : targetList) {
                if (hitTarget.offScreen(canvas)) {
                    spriteList.remove(hitTarget);
                    //targetList.remove(hitTarget);
                    //continue;
                }

                if (ball.collidesWith(hitTarget)) {
                    hitTarget.setHit(true);
                    hitTarget.setX(-(canvas.getWidth() + ball.getRadius()));
                    spriteList.remove(hitTarget);
                    score++;
                    scoreCounter.setText(getResources().getString(R.string.your_score) + score);

                }
                ObstBomb bomb = hitTarget.makeObstBomb(getContext());
                if (bomb != null) {
                    spriteList.add(bomb);
                    bombList.add(bomb);
                }



            }


            for (ObstIncreaseSize hitObst : upSizeList) {
                if(!hitObst.onScreen(canvas)) {
                    spriteList.remove(hitObst);
                  //  upSizeList.remove(hitObst);
                   // continue;
                }


                if (ball.collidesWith(hitObst)) {
                    sound.playSizeHit();
                    hitObst.ballCollision(ball);
                    hitObst.setHit(true);
                    hitObst.setX(-(canvas.getWidth() + ball.getRadius()));
                    spriteList.remove(hitObst);
                }

            }


            for (ObstBomb hitBomb : bombList) {
                if (hitBomb.offScreen(canvas)) {
                    spriteList.remove(hitBomb);
                  //  bombList.remove(hitBomb);
                  //  continue;
                }
                // hitBomb.setXSpeed(-sensorX);

                if (ball.collidesWith(hitBomb) && !ball.isShielded()) {
                    sound.playBallHit();
                    hitBomb.ballCollision(ball);
                    hitBomb.setHit(true);
                    hitBomb.setX(-(canvas.getWidth() + ball.getRadius()));
                    spriteList.remove(hitBomb);
                    // ball.setLife(ball.getLife()-1);

                    score -= 5;
                    scoreCounter.setText(getResources().getString(R.string.your_score) + score);
                }
                if (player.collidesWith(hitBomb) && !player.isShielded()) {
                    sound.playPlayerHit();
                    hitBomb.playerCollision(player);
                    hitBomb.setHit(true);
                    hitBomb.setX(-(canvas.getWidth() + ball.getRadius()));
                    player.setLife(player.getLife() - 1);
                    if (player.getLife() == 0) {
                        endGame();
                        break;
                    }


                }

            }


            for (Iterator<Target> it = targetList.iterator(); it.hasNext(); ) {
                Target target1 = it.next();
                if (target1.offScreen(canvas)) {
                    it.remove();
                }
            }
            for (Iterator<ObstIncreaseSize> it = upSizeList.iterator(); it.hasNext(); ) {
                ObstIncreaseSize obstIncreaseSize = it.next();
                if (!obstIncreaseSize.onScreen(canvas)) {
                    it.remove();
                }
            }
            for (Iterator<ObstBomb> it = bombList.iterator(); it.hasNext(); ) {
                ObstBomb bomb = it.next();
                if (bomb.offScreen(canvas)) {
                    it.remove();
                }
            }



        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (gestureDetector.onTouchEvent(event)) {
                return true;
            }
            return super.onTouchEvent(event);

        }

        class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getY() >= ball.getY() - BALL_AREA_PADDING && ball.isFlinged() == false) {

                    ball.setXSpeed((velocityX / VELOCITY_DIVISOR));
                    ball.setYSpeed((velocityY / VELOCITY_DIVISOR));
                    ball.setFlinged(true);
                    sound.playBallThrow();
                }
                return true;
            }

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintlayoutRoot);

        GraphicsView graphicsView = new GraphicsView(this);
        constraintLayout.addView(graphicsView);
        sound = new SoundPlayer(this);
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Log.d("TAG", "Obtained accelerometer " + accelerometer);

        score = 0;
        scoreCounter = findViewById(R.id.scorecounter);
        timerCounter = findViewById(R.id.timerCounter);
        if (getIntent().getExtras() != null) {
            playing = getIntent().getExtras().getBoolean("playing");
            if (playing) {
                musicPlayer = new Mplayer(this, R.raw.gamebgm);
                musicPlayer.startMusic();
                playable = true;
            }
        }


        countDownTimer = new CountDownTimer(MAX_TIME, 1000) {
            @Override
            public void onTick(long l) {
                timerCounter.setText(getResources().getString(R.string.timer) + l / 1000);

            }

            @Override
            public void onFinish() {
                //Game Over
                endGame();

            }

        }.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorMgr.registerListener(accelListener, accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
        if (! playing && playable)
            musicPlayer.startMusic();

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(accelListener, accelerometer);
        if (playing && playable)
            musicPlayer.stopMusic();
        playing = false;
    }
    public void onBackPressed() {
        clearLists();
        //finishAffinity();
        countDownTimer.cancel();
        Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


    private void endGame() {
       /* if (score < 0) {
            score = 0;
        }*/
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastscore", score);
        editor.apply();
        clearLists();
        //finishAffinity();

        countDownTimer.cancel();

        Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
        intent.putExtra("score", score);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void clearLists() {
        spriteList.clear();
        targetList.clear();
        upSizeList.clear();
        bombList.clear();
    }
}