package com.example.TheArcade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TankGameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public static Tank tank;
    public static Tank enemyTank;
    public static Map map;
    //private Canvas canvas;

    public TankGameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

        Bitmap tankBmp = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
        Bitmap barrelBmp = BitmapFactory.decodeResource(getResources(), R.drawable.barrel);

        Bitmap eTankBmp = BitmapFactory.decodeResource(getResources(), R.drawable.enemytank);
        Bitmap eBarrelBmp = BitmapFactory.decodeResource(getResources(), R.drawable.enemybarrel);

        tank = new Tank(tankBmp, barrelBmp);
        tank.xPos = 300;
        tank.yPos = 600;
        tank.playable = true;

        enemyTank = new Tank(eTankBmp, eBarrelBmp);
        enemyTank.xPos = 3000;
        enemyTank.yPos = 600;
        enemyTank.rotation = 90;


        Bullet1.start(BitmapFactory.decodeResource(getResources(), R.drawable.bullet));

        Map.start(getResources());
        map = new Map(R.raw.map2);


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }


    public void update(double deltaTime) {
        tank.update(deltaTime);
        enemyTank.update(deltaTime);
        Bullet1.update(deltaTime);

        Spritetank.updateSprites(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {

            float yScale = (float) canvas.getHeight() / map.getHeight();
            float xScale = (float) canvas.getWidth() / map.getWidth();

            if (map.getWidth() > map.getHeight()) {
                canvas.scale(xScale, xScale);

            } else {
                canvas.scale(yScale, yScale);
            }

            canvas.drawColor(Color.LTGRAY);
            map.draw(canvas);

            Spritetank.drawSprites(canvas);
        }
    }
}
