package com.example.TheArcade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class GameView2 extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread2 thread;
    private boolean isRunning = true;
    private PlaneSprite plane;
    private Background background;
    private Pipe pipe1, pipe2, pipe3;
    public static final int backgroundWidth = 856;
    public static final int backgroundHeight = 480;
    Random random;


    public GameView2(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread2(getHolder(), this);
        plane = new PlaneSprite(getResources());
        pipe1 = new Pipe(GameView2.this,BitmapFactory.decodeResource(getResources(), R.drawable.pipe_down),BitmapFactory.decodeResource(getResources(), R.drawable.pipe_up),0,2000);
        pipe2 = new Pipe(GameView2.this,BitmapFactory.decodeResource(getResources(), R.drawable.pipe_down),BitmapFactory.decodeResource(getResources(), R.drawable.pipe_up),-250,3200);
        pipe3 = new Pipe(GameView2.this,BitmapFactory.decodeResource(getResources(), R.drawable.pipe_down),BitmapFactory.decodeResource(getResources(), R.drawable.pipe_up),0,4500);
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));


    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(isRunning);
        thread.start();
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new MainThread2(surfaceHolder, this);
        } else {
            System.out.println("Something is wrong with the game");
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(!isRunning);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }



    public void update() {
        pipe1.pipeX-=10;
        pipe1.pipeY-=10;
        pipe1.update();
        pipe2.update();
        pipe3.update();
        plane.update();
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        update();
        final float scaleX = getWidth() / (float)backgroundWidth;
        final float scaleY = getHeight() / (float)backgroundHeight;
        if(canvas != null) {
            int savedState = canvas.save();
            canvas.scale(scaleX, scaleY);
            background.draw(canvas);
            canvas.restoreToCount(savedState);
        }
        plane.draw(canvas);
        assert canvas != null;

        pipe1.ondraw(canvas);
        pipe2.ondraw(canvas);
        pipe3.ondraw(canvas);


    }

    public boolean onTouchEvent(MotionEvent e) {
        plane.onTouchEvent();
        return super.onTouchEvent(e);
    }


}
