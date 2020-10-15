package com.example.TheArcade;


import java.util.Random;

import android.graphics.Bitmap;

import android.graphics.Canvas;


public class EnergyBall {

    // direction = 0 up, 1 left, 2 down, 3 right,

    // animation = 3 back, 1 left, 0 front, 2 right

    int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};

    private static final int BMP_ROWS = 4;

    private static final int BMP_COLUMNS = 3;

    private static final int MAX_SPEED = 5;

    private SpriteGameView gameView;

    private Bitmap bmp;

    private int x = 0;

    private int y = 0;

    private int xSpeed = 0;

    private int ySpeed = 0;

    private int currentFrame = 0;

    private int width;

    private int height;
    private int dx;
    private int dy;
    private Joystick joystick;

    public EnergyBall(SpriteGameView gameView, Bitmap bmp, Hero hero) {

        this.width = bmp.getWidth();

        this.height = bmp.getHeight();

        this.gameView = gameView;

        this.bmp = bmp;


        Random rnd = new Random();

        x += (hero.getPositionx());

        y += (hero.getPositiony());

        xSpeed = (int) (hero.getDirectionX() * 15);
        ySpeed = (int) (hero.getDirectionY() * 15);

    }


    public void update() {

        x = x + xSpeed;
        y = y + ySpeed;


        // Update velocity based on actuator of joystick
        currentFrame = ++currentFrame;
    }


    public void onDraw(Canvas canvas) {

        update();


        canvas.drawBitmap(bmp, x, y, null);

    }


    private int getAnimationRow1() {

        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);

        int direction = (int) Math.round(dirDouble) % BMP_ROWS;

        return DIRECTION_TO_ANIMATION_MAP[direction];

    }


    public boolean isCollition(float x2, float y2) {

        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

    }

    public int getPositionx() {
        return x;
    }

    public int getPositiony() {
        return y;
    }

    public double getDirectionX() {
        return dx;
    }

    public double getDirectionY() {
        return dy;
    }

    public double getwidth() {
        return this.width;
    }

    public double getheight() {
        return this.height;
    }
}