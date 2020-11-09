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

        DataManager.getData();

        Map.start(getResources());

        Bitmap tankBmp = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
        Bitmap barrelBmp = BitmapFactory.decodeResource(getResources(), R.drawable.barrel);

        Bitmap eTankBmp = BitmapFactory.decodeResource(getResources(), R.drawable.enemytank);
        Bitmap eBarrelBmp = BitmapFactory.decodeResource(getResources(), R.drawable.enemybarrel);

        Data.get().tank = new Tank(tankBmp, barrelBmp);
        Data.get().tank.xPos = 400;
        Data.get().tank.yPos = 600;
        Data.get().tank.playable = true;

        Data.get().enemyTank = new Tank(eTankBmp, eBarrelBmp);
        Data.get().enemyTank.xPos = 3000;
        Data.get().enemyTank.yPos = 600;
        Data.get().enemyTank.rotation = 90;

        Data.get().map = new Map(R.raw.map1);


        Bullet1.start(BitmapFactory.decodeResource(getResources(), R.drawable.bullet));


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
        Data.get().tank.update(deltaTime);
        Data.get().enemyTank.update(deltaTime);
        Bullet1.update(deltaTime);

        Spritetank.updateSprites(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {

            // Camera
            float yScale = (float)canvas.getHeight() / Data.get().map.getHeight();
            float xScale = (float)canvas.getWidth() / Data.get().map.getWidth();

            if (Data.get().map.getWidth() > Data.get().map.getHeight()) {
                canvas.scale(xScale, xScale);
            } else {
                canvas.scale(yScale, yScale);
            }

            canvas.drawColor(Color.LTGRAY);
            Data.get().map.draw(canvas);

            Spritetank.drawSprites(canvas);
        }
    }
}
