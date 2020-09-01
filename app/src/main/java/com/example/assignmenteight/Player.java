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

public class Player extends Sprite {

    float x;
    float y;
    public float xSpeed;
    public float ySpeed;
    final static int START_RADIUS = 100;
    int radius;
    Paint paint;
    boolean flinged;
    int width;
    int height;
    public static int HEART_OFFSET = 55;
    int startingX;
    boolean bombHit;
    boolean shielded;
    int startingY;
    double distance;
    static int SCREEN_OFFSET = 400;
    private Bitmap Person;
    Bitmap resizedBitmap;
    private Bitmap Life[] = new Bitmap[2];
    private int lifeHealth;

    Context text;


    public Player(Context context)
    {
        text = context;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        x = width/2;
        y = height- (height/6);
        radius = 250;
       // radius =5;
        paint = new Paint();
        bombHit = false;
        shielded = false;
        paint.setColor(Color.rgb(0,0,200));
        flinged = false;

        Life[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.health);
        Life[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.dead);
        lifeHealth = 3;
        Life[0] = Bitmap.createScaledBitmap(
                Life[0], START_RADIUS, START_RADIUS, false);

        Life[1] = Bitmap.createScaledBitmap(Life[1],START_RADIUS,START_RADIUS,false);
        Person = BitmapFactory.decodeResource(context.getResources(),R.drawable.playertall);
        resizedBitmap = Bitmap.createScaledBitmap(Person,radius,radius,false);

    }

    public  void Move(){
        x+=xSpeed;
        y+=ySpeed;
    }
    public  void onDraw(Canvas canvas) {

        Move();
        if (!onScreen(canvas)) {
            refresh(canvas);
        }
        if (!flinged) {
            if (x  <= 0) {
                x = 0;
            } else if (x + radius >= width) {
                x = width - radius;
            }

        }


        lifeCheck(canvas);
       // checkGameOver();
        //  resizedBitmap.reconfigure(radius,radius, resizedBitmap.getConfig());
        canvas.drawBitmap(resizedBitmap,x,y,null);
//        canvas.drawCircle(x, y, radius, paint); //This could be anything eg not a circle
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

    public void refresh(Canvas canvas){
        startingX = canvas.getWidth()/2;
        startingY= canvas.getHeight() - (canvas.getHeight()/10);
        x=startingX;
        y= startingY;
        xSpeed = 0;
        ySpeed = 0;
        bombHit =false;
        radius = START_RADIUS;
       // radius=5;
        resizedBitmap = Bitmap.createScaledBitmap(Person,radius,radius,false);
        flinged = false;
        shielded = false;
    }


    public boolean collidesWith(Sprite target)
    {
/*        if (x + radius + target.getRadius() > target.getX()
                && x < target.getX() + radius + target.getRadius()
                && y + radius + target.getRadius() > target.getY()
                && y < target.getY() + radius + target.getRadius())
                {
                    distance = Math.sqrt(
                            ((x - target.getX()) * (x - target.getX()))
                                    + ((y - target.getY()) * (y - target.getY()))
                    );
                    if(distance < radius + target.getRadius())*/{

        if(Math.abs(x - target.getX()) < radius && Math.abs(y - target.getY()) < radius-(radius/2))
        {

            return true;
        }
        else{
            return  false;
        }
    }

    }

    public boolean collidesWithCircle(Sprite target)     {
        if (x + radius + target.getRadius() > target.getX()
                && x < target.getX() + radius + target.getRadius()
                && y + radius + target.getRadius() > target.getY()
                && y < target.getY() + radius + target.getRadius())
        {
            distance = Math.sqrt(
                    ((x - target.getX()) * (x - target.getX()))
                            + ((y - target.getY()) * (y - target.getY()))
            );
            if(distance < radius + target.getRadius()){
                return  true;
            }
            else{
                return  false;
            }
        }
        else
        {
            return false;
        }

    }


    public void lifeCheck(Canvas canvas)
    {
        for(int i = 0; i<3; i++)
        {
            int x =  ((canvas.getWidth() - SCREEN_OFFSET +HEART_OFFSET) + (Life[1].getWidth() + 10) * i);
            int y = HEART_OFFSET;

            if(i<lifeHealth)
            {
                canvas.drawBitmap(Life[0], x, y, null);

            }
            else
            {
                canvas.drawBitmap(Life[1],x,y,null);
            }
        }

    }

    public boolean checkGameOver()
    {
        if(lifeHealth == 0)
        {

            return true;
        }
        else
            return false;
    }

    public void setX(float i){
        this.x = i;
    }
    public void setXSpeed(float i){
        this.xSpeed = i;
    }


    public float getX(){
        return this.x;
    }



    public void setLife(int i){
        this.lifeHealth = i;
    }

    public int getLife(){
        return this.lifeHealth;
    }
    public int getRadius(){
        return this.radius;
    }
    public float getstartingY(){
        return this.startingY;
    }
    public float getY(){
        return this.y;
    }

    public void setBombHit(boolean i){
        this.bombHit = i;
    }
    public void setShielded(boolean i){
        this.shielded = i;
    }
    public void setBitmap(Bitmap i){this.resizedBitmap = i;}
    public boolean isShielded(){
        return this.shielded;
    }

    public boolean isFlinged(){
        return this.flinged;
    }
    public void setRadius(int i){
        this.radius = i;
    }


}