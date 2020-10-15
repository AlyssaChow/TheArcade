package com.example.TheArcade;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.TheArcade.R;

public class PlaneSprite{

    private Bitmap plane;
    private Bitmap planeExplosion;
    private int planeBitMapW;
    private int planeBitMapH;
    private float planeBitX;
    private float planeBitY;
    private float gravity;
    private float fallSpeed;
    private float upBoost;


    public PlaneSprite(Resources resources) {
        //dimensions for the sprite
        planeBitX = 280;
        planeBitMapW = 264;
        planeBitMapH = 160;
        gravity = 4;
        upBoost = -44;
        Bitmap planeBitMap = BitmapFactory.decodeResource(resources, R.drawable.plane_2_green);
        plane = Bitmap.createScaledBitmap(planeBitMap, planeBitMapW, planeBitMapH, false);
        Bitmap[] planeBitDestroyed = new Bitmap[9];
    }


    public void draw(Canvas canvas) {
        if (fallSpeed < 0) {
            canvas.drawBitmap(plane, planeBitX, planeBitY, null);
        }
        else
        {
            canvas.drawBitmap(plane,planeBitX,planeBitY,null);
        }
    }


    public void update() {
        planeBitY = planeBitY + fallSpeed;
        fallSpeed = fallSpeed + gravity;
    }

    public void onTouchEvent()
    {
        fallSpeed = upBoost;
    }
}
