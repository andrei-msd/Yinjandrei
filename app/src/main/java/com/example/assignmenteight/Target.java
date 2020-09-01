package com.example.assignmenteight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Target extends Sprite {

    float x;
    float y;
    public float xSpeed = 9;
    public float ySpeed;
    int radius;
    final static int Y_OFFSET = 200;
    final static int X_OFFSET = 21;
    Paint paint;
    int width;
    int height;
    boolean hit;
    float bombX;
    static int SCREEN_OFFSET = 400;
    Random rnd = new Random();
    int color;
    Bitmap target;
    Bitmap resizedBitmap;
    public Target(Context context)
    {
       // color = Color.rgb(0,200,0);
        hit = false;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        x = width + X_OFFSET;
        y = rnd.nextInt((height-(height/3)) - Y_OFFSET) + Y_OFFSET;
        radius = 150;
        paint = new Paint();

        paint.setColor(color);

        target = BitmapFactory.decodeResource(context.getResources(),R.drawable.ninja);
        resizedBitmap = Bitmap.createScaledBitmap(
                target, radius, radius, false);

    }

    public  void Move(){
        x-=xSpeed;
        y+= ySpeed;
    }

    public  void onDraw(Canvas canvas){
        Move();

        //canvas.drawCircle(x,y,radius,paint); //This could be anything eg not a circle
        canvas.drawBitmap(resizedBitmap,x,y,null);
    }

    public boolean onScreen(Canvas canvas){
        width = canvas.getWidth();
        height = canvas.getHeight();
        if((x - radius <= -SCREEN_OFFSET)||x + radius >= width +SCREEN_OFFSET||(y - radius <=  -SCREEN_OFFSET)||y + radius >= height + SCREEN_OFFSET)
        {
            return false;
        }
        else{
            return  true;
        }
    }

    public ObstBomb makeObstBomb(Context context)
    {
        float startX = x;
        Random rnd = new Random();
        final Target target = this;
        int i = rnd.nextInt(600);

        if(i < 1) {
            bombX = x;
           // color = Color.rgb(100, 50, 50);
           // paint.setColor(color);
            Bitmap ninjathrow;
            Bitmap bitmap;
            ninjathrow = BitmapFactory.decodeResource(context.getResources(),R.drawable.ninjathrow);
            bitmap = Bitmap.createScaledBitmap(
                    ninjathrow, radius, radius, false);
            resizedBitmap = bitmap;
            return new ObstBomb(this, context);

        }

        else
        {
            return null;
        }
    }

/*    public  void refresh(){
   *//*     Bitmap ninjathrow;
        Bitmap bitmap;
        ninjathrow = BitmapFactory.decodeResource(context.getResources(),R.drawable.ninja);
        bitmap = Bitmap.createScaledBitmap(
                ninjathrow, radius, radius, false);
        resizedBitmap = bitmap;*//*
    //  color = Color.rgb(0,200,0);
      // paint.setColor(color);
    }*/

    public boolean offScreen(Canvas canvas){
        width = canvas.getWidth();
        if(x <= -SCREEN_OFFSET)
            return true;
        else {
            return false;
        }
    }


    public void setX (float i){
    this.x = i;
}
    public void setXSpeed(float i){
        this.xSpeed = i;
    }

    public void setYSpeed(float i){
        this.ySpeed = i;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }
    public float getXSpeed(){
        return this.xSpeed;
    }

    public float getBombX(){
        return this.bombX;
    }
    public int getRadius() {return  this.radius;}

    public boolean isHit(){
        return this.hit;
    }

    public void setHit(boolean i){
        this.hit = i;
    }
    public void setColor(int i){
        this.color = i;
    }


}
