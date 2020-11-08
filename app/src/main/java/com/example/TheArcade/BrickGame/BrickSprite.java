package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class BrickSprite {
    Bitmap image;
    int brickType;
    int Health;
    private int x ,y;

    public BrickSprite(int type,int x ,int y, Bitmap image){
        this.image = image;
        this.brickType = type;
        this.x=x;
        this.y=y;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }

    public int getX(){return x;}
    public int getY(){return y;}
}
