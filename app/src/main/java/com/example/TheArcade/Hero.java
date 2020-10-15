package com.example.TheArcade;

import java.util.Random;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import android.graphics.Rect;


public class Hero {
    private Joystick joystick;

    // direction = 0 up, 1 left, 2 down, 3 right,

    // animation = 3 back, 1 left, 0 front, 2 right

    int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};

    private static final int BMP_ROWS = 4;

    private static final int BMP_COLUMNS = 3;

    private static final int MAX_SPEED = 5;

    private SpriteGameView gameView;

    private Bitmap bmp;

    private int x;

    private int y;

    private int xSpeed = 0;

    private int ySpeed = 0;

    private int currentFrame = 0;

    private int width;

    private int height;

    private double dx = 1;
    private double dy = 0;

    public Hero(SpriteGameView gameView, Bitmap bmp, Joystick joystick) {

        this.width = bmp.getWidth() / BMP_COLUMNS;

        this.height = bmp.getHeight() / BMP_ROWS;

        this.gameView = gameView;

        this.bmp = bmp;
        this.joystick = joystick;


        Random rnd = new Random();

        x = rnd.nextInt(gameView.getWidth() - width);

        y = rnd.nextInt(gameView.getHeight() - height);


    }


    public void update() {
        xSpeed = (int) (joystick.getActuatorX() * 20);
        ySpeed = (int) (joystick.getActuatorY() * 20);
        if (x >= gameView.getWidth() - 100 - xSpeed || x + xSpeed <= 0) {

            xSpeed = -xSpeed;

        }
        x += xSpeed;

        if (y >= gameView.getHeight() - 200 - ySpeed || y + ySpeed <= 0) {

            ySpeed = -ySpeed;
        }

        // Update position

        y += ySpeed;

        // Update direction


        if (xSpeed != 0 || ySpeed != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, xSpeed, ySpeed);
            dx = (xSpeed / distance);
            dy = (ySpeed / distance);
        }

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }


    public void onDraw(Canvas canvas) {

        update();

        int srcX = currentFrame * width;

        int srcY = getAnimationRow() * height;

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect(x, y, x + 100, y + 100);

        canvas.drawBitmap(bmp, src, dst, null);

    }


    int getAnimationRow() {

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
}