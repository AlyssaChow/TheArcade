package com.example.TheArcade;

import java.util.Random;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import android.graphics.Rect;


public class Portal {

    // direction = 0 up, 1 left, 2 down, 3 right,

    // animation = 3 back, 1 left, 0 front, 2 right

    int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};

    private static final int BMP_ROWS = 1;

    private static final int BMP_COLUMNS = 3;

    private static final int MAX_SPEED = 5;

    private SpriteGameView gameView;

    private Bitmap bmp;

    private int x = 0;

    private int y = 0;

    private int xSpeed;

    private int ySpeed;
    private double dx = 0;
    private double dy = 0;
    private int currentFrame = 0;

    private int width;

    private int height;
    private int x1;

    private int y1;

    //private Sprite enemy;
    public Portal(SpriteGameView gameView, Bitmap bmp) {

        this.width = bmp.getWidth() / BMP_COLUMNS;

        this.height = bmp.getHeight() / BMP_ROWS;

        this.gameView = gameView;

        this.bmp = bmp;


        Random rnd = new Random();

        x = 800;

        y = 0;

        //   xSpeed = rnd.nextInt(MAX_SPEED * 3) - MAX_SPEED;

        // ySpeed = rnd.nextInt(MAX_SPEED * 3) - MAX_SPEED;

    }


    public void update() {


        currentFrame = ++currentFrame % BMP_COLUMNS;
        // Update velocity based on actuator of joystick

    }


    public void onDraw(Canvas canvas) {

        update();
        int srcX = currentFrame * width;

        int srcY = getAnimationRow() * height;

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect(x, y, x + 100, y + 100);


        canvas.drawBitmap(bmp, src, dst, null);

    }


    private int getAnimationRow() {

        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);

        int direction = (int) Math.round(dirDouble) % BMP_COLUMNS;

        return DIRECTION_TO_ANIMATION_MAP[direction];

    }


    public boolean isCollition(float x2, float y2) {

        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;

    }

    public static boolean isColliding(EnergyBall obj1, Sprite obj2) {
        double distance = getDistanceBetweenObjects1(obj1, obj2);
        double distanceToCollision = 50;
        return distance < distanceToCollision;
    }

    public int getPositionx1() {
        return x;
    }

    public int getPositiony1() {
        return y;
    }

    public double getDirectionX1() {
        return dx;
    }

    public double getDirectionY1() {
        return dy;
    }

    public static double getDistanceBetweenObjects(Sprite obj1, Hero obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionx() - obj1.getPositionx1(), 2) +
                        Math.pow(obj2.getPositiony() - obj1.getPositiony1(), 2)
        );
    }

    public static double getDistanceBetweenObjects1(EnergyBall obj1, Sprite obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionx1() - obj1.getPositionx(), 2) +
                        Math.pow(obj2.getPositiony1() - obj1.getPositiony(), 2)
        );
    }

    double getwidth() {
        return this.width;
    }
}