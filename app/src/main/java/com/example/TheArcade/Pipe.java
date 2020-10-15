package com.example.TheArcade;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Pipe{


    private Bitmap bottomPipe;
    private Bitmap topPipe;
    public int pipeX = 1000;
    public int pipeY=1000;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int speedOfGame = 10;
    private int gapBetweenTubes = 500;

GameView2 gameView2;
    public Pipe(GameView2 gameView2,Bitmap bPipe, Bitmap tPipe , int x ,int y)
    {this.gameView2=gameView2;
        bottomPipe = bPipe;
        topPipe = tPipe;
        pipeX = 1000;
        pipeY = y;
    }




    public void ondraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bottomPipe, pipeX,  1000, null);
        canvas.drawBitmap(topPipe,  pipeX,  -1000, null);

    }


    public void update() {

        pipeX-=1;


    }
}
