package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class LiveSprite {
    Bitmap image;
    private int x ,y;

    public LiveSprite(int x ,int y, Bitmap image){
        this.image = image;
        this.x=x;
        this.y=y;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }
    public void remove(){
        image.recycle();
    }

    public int getX(){return x;}
    public int getY(){return y;}
}