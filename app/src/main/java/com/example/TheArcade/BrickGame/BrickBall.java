package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.constraintlayout.solver.widgets.Rectangle;

public class BrickBall {
    private Bitmap image;
    private int x,y;
    //private int xVelocity = 100;
    //private int yVelocity = -900;
    private int xVelocity = 350;
    private int yVelocity = -540;
    private int screenWidth = (Resources.getSystem().getDisplayMetrics().widthPixels);
    private int screenHeight = (Resources.getSystem().getDisplayMetrics().heightPixels);
    private int startLocX= screenWidth/2;
    private int startLocY= screenHeight-100;
    private int paddlelocation;
    private int paddleWidth;

    private boolean outofbounds;
    private boolean bounce;
    public BrickBall(Bitmap ball){
        this.image = ball;
        bounce = true;
        outofbounds = false;
        paddlelocation = screenWidth -100;
        x = startLocX;
        y = startLocY;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }

    //Called when a life is lost
    public void restart(){
        outofbounds = false;
        xVelocity = 350;
        yVelocity = -540;
        x = startLocX;
        y = startLocY;
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
        if(x>(paddlelocation - 50) && x < (paddlelocation +250) && y > (screenHeight-100)){
            if(bounce == true){
                bounceY();
                angleCalc();
            }
            bounce = false;

        }else if(y > screenHeight + (5 * image.getHeight())) {
                outofbounds = true;
                bounce = true;
        }
    }

    private void angleCalc(){
        int ballLoc = distanceDif(paddlelocation,x);

        if(ballLoc < 40){
            setVelocity(1);
        }else if(ballLoc >= 40 && ballLoc< 80){
            setVelocity(2);
        }else if(ballLoc >= 80 && ballLoc< 120){
            setVelocity(3);
        }else if(ballLoc >= 120 && ballLoc< 160){
            setVelocity(4);
        }else if(ballLoc >= 160){
            setVelocity(5);
        }

    }
    private void setVelocity(int i){
        switch(i){
            case 1: xVelocity = -450;
                    yVelocity = -450;
                break;
            case 2: xVelocity = -350;
                    yVelocity = -540;
                break;
            case 3: xVelocity = 0;
                    yVelocity = -650;
                break;
            case 4: xVelocity = 350;
                    yVelocity = -540;
                break;
            case 5: xVelocity = 450;
                    yVelocity = -450;
                break;
        }
    }

    private int distanceDif(int paddle, int xLoc){
        return xLoc +25 - (paddle);
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
        bounce = true;
    }
    public void bounceY(){
        yVelocity = yVelocity * -1;
        bounce = true;
    }

    public int getX(){
        return  x;
    }

    public int getY(){
        return  y;

    }
}
