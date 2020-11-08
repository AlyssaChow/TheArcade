package com.example.TheArcade.BrickGame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.TheArcade.CustomOnClick;
import com.example.TheArcade.R;

public class Paddle {
    private Bitmap image;
    private int x;
    private int y;
    private int xSpeed = 500;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private float xLoc;

    public Paddle(Bitmap image){
        this.image = image;
        x = screenWidth/2 -100;
        y = screenHeight - 50;
    }

    public void draw(Canvas canvas){canvas.drawBitmap(image,x,y,null);}

    public void update(double deltatime){
        xLoc = x;
        if(CustomOnClick.isDown(R.id.PaddleLeft)) {
            System.out.println("test");
            if (x < 0 ) {
                xLoc = x;
            } else{
                xLoc -= xSpeed * deltatime;
            }
        }
        if (CustomOnClick.isDown(R.id.PaddleRight)) {
            if (x > screenWidth - image.getWidth()) {
                xLoc = x;
            } else{
                xLoc += xSpeed * deltatime;
            }
        }
        x =(int)xLoc;

    }
}
