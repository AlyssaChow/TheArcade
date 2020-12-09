package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import com.example.TheArcade.R;


public class BrickSprite {
    RectF block;
    int brickType;
    int BrickScore = 0;
    BrickLevel map;

    private int x ,y;
    private int xLength,yLength;
    private boolean collision;

    Paint paintColor;

    public BrickSprite(int type,int x ,int y, RectF block, Paint paint, BrickLevel map){
        this.block = block;
        this.brickType = type;
        this.x=x;
        this.y=y;
        this.xLength = x+50;
        this.yLength = y+25;
        this.map = map;
        collision = true;

        this.paintColor = paint;
    }

    public void draw(Canvas canvas){
                    canvas.drawRect(block,paintColor);
    }

    public void remove(){
        block.setEmpty();
    }
    public int getX(){return x;}
    public int getY(){return y;}

    public boolean intersects(BrickBall ball){
        int ballX=ball.getX();
        int ballY=ball.getY();

        if(collision) {
            if (ballX > (this.x - 40) && ballX < (this.xLength + 40) && ballY < (this.yLength) && ballY > (this.y)) {

                if (brickType == 3){
                    brickType =2;
                    map.increaseScore();
                    paintColor.setColor(Color.GREEN);
                    ball.bounceY();
                }else if(brickType == 2){
                    brickType =1;
                    map.increaseScore();
                    paintColor.setColor(Color.RED);
                    ball.bounceY();
                } else{
                    map.increaseScore();
                    ball.bounceY();
                    this.collision = false;
                    this.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public int getBrickScore(){return BrickScore;}


}
