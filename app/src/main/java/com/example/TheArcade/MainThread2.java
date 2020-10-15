package com.example.TheArcade;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread2 extends Thread{

    private SurfaceHolder surfaceHolder;
    private GameView2 gameView;
    private boolean running = false;
    private Canvas canvas;
    private final long fps = 10;
    public MainThread2(SurfaceHolder surfaceHolder, GameView2 gameView)
    {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    @Override
    public void run() {
        long ticks = 1000/fps;
        long startTime;
        long sleepTime;
        //long time;


        //game's loop
        while(running)
        {
            startTime = System.currentTimeMillis();
            canvas=null;

            try{
                canvas = surfaceHolder.lockCanvas();

                synchronized ((surfaceHolder))
                {
                    gameView.update();
                    gameView.draw(canvas);
                }

            } catch(Exception e){
                e.printStackTrace();
            } finally {
                if(canvas != null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }


            sleepTime = (System.currentTimeMillis() - startTime)/ticks;


            try {
                if(sleepTime > 0)
                {
                    sleep(sleepTime);
                }
                else {
                    sleep(10);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
