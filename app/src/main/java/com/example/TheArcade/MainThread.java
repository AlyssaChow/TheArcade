package com.example.TheArcade;

import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private TankGameView gameView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, TankGameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        double currentTime = SystemClock.elapsedRealtime();
        while (running) {
            canvas = null;
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
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}