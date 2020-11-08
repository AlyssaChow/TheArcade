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
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public BrickBall(Bitmap ball){
        this.image = ball;
        x = screenWidth/2;
        y = screenHeight-100;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }

    public void update(double deltatime){
        x += xVelocity * deltatime;
        y += yVelocity * deltatime;
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            xVelocity = xVelocity*-1;
        }
        if ((y > screenHeight - image.getHeight()) || (y < 0)) {
            yVelocity = yVelocity*-1;
        }
    }
}
