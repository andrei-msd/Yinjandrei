package com.example.assignmenteight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.DisplayMetrics;

import java.util.Random;


public class ObstBomb extends Sprite implements Obstacle {

    float x;
    float y;
    public float xSpeed = 0;
    public float ySpeed = 10;
    int radius;
    int maxTime = 1000;
    boolean hittable;
    final static int Y_OFFSET = 150;
   // final static int X_OFFSET = 21;
    Paint paint;
    int width;
    int height;
    int startingX;
    boolean hit;
   // int bitX = 40;
   // int bitY=100;
   // int startingY;
    static int SCREEN_OFFSET = 400;
    int color;
    //int targetxStart;
    Target targetz;
    Random rnd = new Random();
    Bitmap dagger;
    Bitmap resizedBitmap;

    public ObstBomb(Target target, Context context) {
        hit = false;
      //  color = Color.rgb(200, 0, 0);
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        x = (target.getX() - Y_OFFSET);
        xSpeed = ((-10 +rnd.nextInt(20)) );
        hittable = false;
       // y = (target.getY() + target.radius);
        y =(target.getY());
        radius = 150;
        targetz =target;
        paint = new Paint();
        paint.setColor(color);


        dagger = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemystar);
        resizedBitmap = Bitmap.createScaledBitmap(
                dagger, radius, radius, false);

    }

    public void Move() {

       // ySpeed = 10;
        x += xSpeed;
        y += ySpeed;

    }

    public void onDraw(Canvas canvas) {

       if(drawable()){
         //  x = targetz.getX();

          // canvas.drawCircle(x, y, radius, paint);
           canvas.drawBitmap(resizedBitmap,x,y,null);
           Move();
        //   targetz.refresh();

       }
    }

    private  boolean drawable(){
        if(targetz.getX() <= targetz.getBombX() -Y_OFFSET ) {
          return true;
        }
        else {
            return false;
        }
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
        ball.setYSpeed(30);
        ball.setBombHit(true);
    }

    public void playerCollision(Player player) {
        player.setBombHit(true);
    }


    public boolean offScreen(Canvas canvas){
        height = canvas.getHeight();
        if(y >= height+SCREEN_OFFSET)
            return true;
        else {
            return false;
        }
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
