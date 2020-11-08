package com.example.TheArcade.BrickGame;

import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class BrickThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private BrickGameView gameView;
    private boolean isRunning;
    private Canvas canvas;

    private int targetfps = 60;
    private double averageFPS;

    public BrickThread(SurfaceHolder surfaceHolder, BrickGameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / targetfps;

        double currentTime = SystemClock.elapsedRealtime();
        while(isRunning) {
            canvas = null;
            startTime = SystemClock.elapsedRealtime();

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    double newTime = SystemClock.elapsedRealtime();
                    double frameTime = (newTime - currentTime) / 1000.0f;
                    currentTime = newTime;
                    this.gameView.update(frameTime);
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == targetfps) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                //System.out.println(averageFPS);
            }
        }
    }

    public void runGame(boolean gameRun){
        isRunning = gameRun;
    }
}
