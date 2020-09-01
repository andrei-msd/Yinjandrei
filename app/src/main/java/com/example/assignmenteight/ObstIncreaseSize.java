package com.example.assignmenteight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;

import java.util.Random;

public class ObstIncreaseSize extends Sprite implements Obstacle {

    float x;
    float y;
    CountDownTimer countDownTimer;
    public float xSpeed = 0;
    public float ySpeed = 0;
    int radius;
    final static int Y_OFFSET = 100;
    final static int X_OFFSET = 100;
    Paint paint;
    int width;
    public  static  int MAX_TIME = 5000;
    int height;
    boolean hit;
    static int SCREEN_OFFSET = 400;
    static int SHURIKE_SIZE = 300;
    Bitmap obstacle;
    Bitmap resizedBitmap;
    Random rnd = new Random();

    public ObstIncreaseSize(Context context) {
        hit = false;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        x = rnd.nextInt((width) - X_OFFSET) + X_OFFSET;
        y = (2 * (height / 3) + Y_OFFSET);
        radius = 100;
        paint = new Paint();
        paint.setColor(Color.rgb(100, 0, 100));


        obstacle = BitmapFactory.decodeResource(context.getResources(),R.drawable.upsize);
        resizedBitmap = Bitmap.createScaledBitmap(
                obstacle, radius, radius, false);

    }

    public void Move() {
        x -= xSpeed;
        y += ySpeed;
    }

    public void onDraw(Canvas canvas) {
        Move();
        timer();
       // canvas.drawCircle(x, y, radius, paint); //This could be anything eg not a circle
        canvas.drawBitmap(resizedBitmap,x,y,null);
    }

    private void timer(){
        countDownTimer = new CountDownTimer(MAX_TIME,1000) {
            @Override
            public void onTick(long l) {

                l--;


            }

            @Override
            public void onFinish() {
               x =-(width + radius);

            }

        } .start();

    }

    public boolean onScreen(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        if ((x - radius <= -SCREEN_OFFSET) || x + radius >= width + SCREEN_OFFSET || (y - radius <= -SCREEN_OFFSET) || y + radius >= height + SCREEN_OFFSET) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void ballCollision(Ball ball) {
        ball.setShielded(true);
         Bitmap Shuriken;
        Bitmap resizedBitmap;
        Shuriken = BitmapFactory.decodeResource(ball.text.getResources(),R.drawable.shurikenshield);
        resizedBitmap = Bitmap.createScaledBitmap(Shuriken,SHURIKE_SIZE,SHURIKE_SIZE,false);
       // ball.setRadius(ball.getRadius() + (ball.getRadius()/3));
        ball.setBitmap(resizedBitmap);
    }


    public void setX(float i) {
        this.x = i;
    }

    public void setXSpeed(float i) {
        this.xSpeed = i;
    }

    public void setYSpeed(float i) {
        this.ySpeed = i;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean isHit() {
        return this.hit;
    }

    public void setHit(boolean i) {
        this.hit = i;


    }
}