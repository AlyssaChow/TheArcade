package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Region;

public class BrickSprite {
    Bitmap image;
    int brickType;
    int Health;
    private int x ,y;
    private int xLength,yLength;
    private boolean collision;

    public BrickSprite(int type,int x ,int y, Bitmap image){
        this.image = image;
        this.brickType = type;
        this.x=x;
        this.y=y;
        this.xLength = x+50;
        this.yLength = y+25;
        collision = true;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
    }

    public void remove(){image.recycle();}
    public int getX(){return x;}
    public int getY(){return y;}

    public boolean intersects(BrickBall ball){
        int ballX=ball.getX();
        int ballY=ball.getY();

        if(collision) {
            if (ballX > (this.x) && ballX < (this.xLength) && ballY < (this.yLength) && ballY > (this.y)) {

                ball.bounceY();
                collision = false;
                this.remove();
                return true;
            }
        }
        return false;
    }

}
