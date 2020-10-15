package com.example.TheArcade;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CustomOnClick implements View.OnTouchListener {

    private static ArrayList<CustomOnClick> events = new ArrayList<CustomOnClick>();

    public static boolean isDown(int id) {
        for (CustomOnClick e : events) {
            if (e.id == id)
                return e.down;
        }
        return false;
    }

    public CustomOnClick() {
        events.add(this);
    }

    private int id;
    private boolean down;

    public boolean onTouch(View yourButton, MotionEvent theMotion) {
        this.id = yourButton.getId();
        switch (theMotion.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down = true;
                break;
            case MotionEvent.ACTION_UP:
                down = false;
                break;
        }
        return true;
    }
}