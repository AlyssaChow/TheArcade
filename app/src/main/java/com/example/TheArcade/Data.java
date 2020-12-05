package com.example.TheArcade;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Data implements Serializable {

    public static Data get() {
        return DataManager.getData();
    }

    // Non-serialized data
    public static Bitmap tankBmp;
    public static Bitmap barrelBmp;
    public static Bitmap eTankBmp;
    public static Bitmap eBarrelBmp;

    public String startTime;
    public String endTime;
    public Date start = null;
    public Date end = null;
    public long diff;
    public long diffSeconds;

    public boolean endState;

    // Serialized data
    public Tank tank;
    public ArrayList<Tank> enemyTanks = new ArrayList<>();
    public Map map;
}