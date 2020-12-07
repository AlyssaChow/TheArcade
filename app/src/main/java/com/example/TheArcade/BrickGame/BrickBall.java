package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class BrickBall {
    private Bitmap image;
    private int x,y;
    private int xVelocity = 100;
    private int yVelocity = -900;
    private int screenWidth = (Resources.getSystem().getDisplayMetrics().widthPixels)/2;
    private int screenHeight = (Resources.getSystem().getDisplayMetrics().heightPixels)-100;

    private int paddlelocation;

    private boolean outofbounds;
    private boolean bounce;
    public BrickBall(Bitmap ball){
        this.image = ball;
        bounce = true;
        outofbounds = false;
        paddlelocation = screenWidth -100;
        x = screenWidth;
        y = screenHeight;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }

    //Called when a life is lost
    public void restart(){
        outofbounds = false;
        xVelocity = 100;
        yVelocity = -900;
        x = screenWidth;
        y = screenHeight;
    }

    //update ball position
    public void update(double deltatime){
        x += xVelocity * deltatime;
        y += yVelocity * deltatime;
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            bounceX();
            bounce = true;
        }
        if(y < 0) {
            bounceY();
            bounce = true;
        }
        if(x>(paddlelocation - 50) && x < (paddlelocation +250) && y > (screenHeight-30)){
            if(bounce == true){
                bounceY();
            }

            bounce = false;
        }else if(y > screenHeight + (5 * image.getHeight())) {
                outofbounds = true;
                bounce = true;
        }
    }

    private float angleCalc(int xVelocity, int yVelocity){
        if(xVelocity > 0 && yVelocity > 0){
            return 0;
        }
        return 1;
    }

    private float distanceDif(int paddle, int xLoc){
        return (paddle+50) - xLoc;
    }
    public boolean isOutofbounds(){
        return outofbounds;
    }

    public void removeImage(){
        image.recycle();
    }

    public void updatePaddle(int update){
        paddlelocation = update;
    }

    public void bounceX(){
        xVelocity = xVelocity * -1;
    }
    public void bounceY(){
        yVelocity = yVelocity * -1;
    }

    public int getX(){
        return  x;
    }

    public int getY(){
        return  y;
    }
}