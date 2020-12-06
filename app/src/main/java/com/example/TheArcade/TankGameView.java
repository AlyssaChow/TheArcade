package com.example.TheArcade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class TankGameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private int currentMap;
    private int[] maps = {
            R.raw.map1,
            R.raw.map2,
            R.raw.map3,
            R.raw.map4,
            R.raw.map5,
            R.raw.map6
    };

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
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date time = cal.getTime();
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

        Data.get().startTime = date.format(time);

        try {
            Data.get().start = date.parse(Data.get().startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("Time: ", Data.get().start.getTime() + "");

        reset();

        Data.tankBmp = BitmapFactory.decodeResource(getResources(), R.drawable.tank);
        Data.barrelBmp = BitmapFactory.decodeResource(getResources(), R.drawable.barrel);
        Data.eTankBmp = BitmapFactory.decodeResource(getResources(), R.drawable.enemytank);
        Data.eBarrelBmp = BitmapFactory.decodeResource(getResources(), R.drawable.enemybarrel);

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

    public void reset() {
        currentMap = 0;
        mapTimeout = 0;
        won = false;

        if (Data.get().map != null)
            Data.get().map.destroy();

        DataManager.resetData();

        Map.start(getResources());
    }

    private double mapTimeout;
    private boolean won;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(double deltaTime) {
        if (won) {
            return;
        }
        if (Data.get().map != null) {
            mapTimeout = 1.5;

            //Log.d("DEBUG: ", "in tgv update, map != null");

            Data.get().tank.update(deltaTime);
            if (!Data.get().tank.alive) {
                // Player lost
                Data.get().map.destroy();
                Data.get().map = null;
                try {
                    //Bundle bundle = new Bundle();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference ref = database.getReference("TankHighscore");
                    //mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    ref.push().setValue(currentMap+1);
                } catch (Exception e) {
                    Log.d("DEBUG:", "oop");

                }

                return;
            }

            int numEnemies = Data.get().enemyTanks.size();

            for (int i = 0; i < Data.get().enemyTanks.size(); i++) {
                Data.get().enemyTanks.get(i).update(deltaTime);
                if (!Data.get().enemyTanks.get(i).alive)
                    numEnemies--;
            }

            if (numEnemies == 0) {
                // Next map
                currentMap++;
                Data.get().map.destroy();
                Data.get().map = null;
                return;
            }

            Bullet1.update(deltaTime);
            Spritetank.updateSprites(deltaTime);
        } else {

            //Log.d("DEBUG: ", "in tgv update, map == null");
            if (mapTimeout <= 0) {
                if (currentMap == 5) {
                    //WINNER
                    try {
                        //Bundle bundle = new Bundle();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        DatabaseReference ref = database.getReference("TankHighscore");
                        //mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                        ref.push().setValue(5);
                    } catch (Exception e) {
                        Log.d("DEBUG:", "oop");

                    }
                    won = true;
                    if (mapTimeout <= 0) {
                        Data.get().map = new Map(maps[currentMap]);
                    } else {
                        mapTimeout -= deltaTime;
                    }
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                    Date time = cal.getTime();
                    SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss a");
                    date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

                    Data.get().endTime = date.format(time);

                    try {
                        Data.get().end = date.parse(Data.get().endTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.d("Time: ", Data.get().end.getTime() + "");
                    Log.d("Time: ", Data.get().start.getTime() + "");

                    //Data.get().diff = Data.get().end.getTime() - Data.get().start.getTime();
                    //Data.get().diffSeconds = Data.get().diff / 1000;
                    //Log.d("TIME: ","Time in seconds: " + Data.get().diff + " seconds.");


                    return;
                } else {
                    Data.get().map = new Map(maps[currentMap]);
                }
            } else {
                mapTimeout -= deltaTime;
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null && Data.get().map != null) {
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

            Data.get().tank.draw(canvas);
            for (int i = 0; i < Data.get().enemyTanks.size(); i++)
                Data.get().enemyTanks.get(i).draw(canvas);

            Spritetank.drawSprites(canvas);
        }
    }
}
