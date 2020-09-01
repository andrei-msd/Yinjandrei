package com.example.assignmenteight;

import android.graphics.Canvas;
import android.graphics.Paint;


public abstract class Sprite {

    protected float x;
    protected float y;
    protected int radius;



    public Sprite(int x, int y, int radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;

    }
    public Sprite()
    {


    }


    public abstract void Move();

    public abstract void onDraw(Canvas canvas);



    public abstract boolean onScreen(Canvas canvas);


    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public int getRadius(){
        return this.radius;
    }
    /*public void setX(int i){
        this.x = i;
    }*/

    public void setY(int i){
        this.y = i;
    }

    public void setRadius(int i){
        this.radius = i;
    }


}
