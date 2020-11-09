package com.example.TheArcade;

import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import android.graphics.Rect;


public class Sprite {

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

    private int xSpeed;

    private int ySpeed;
    private double dx = 1;
    private double dy = 0;
    private int currentFrame = 0;

    private int width;

    private int height;
    private int x1;

    private int y1;

    private Hero hero;

    public Sprite(SpriteGameView gameView, Bitmap bmp, Hero hero) {

        this.width = bmp.getWidth() / BMP_COLUMNS;

        this.height = bmp.getHeight() / BMP_ROWS;

        this.gameView = gameView;

        this.bmp = bmp;

        this.hero = hero;

        Random rnd = new Random();

        x = 800;

        y = 0;

        //   xSpeed = rnd.nextInt(MAX_SPEED * 3) - MAX_SPEED;

        // ySpeed = rnd.nextInt(MAX_SPEED * 3) - MAX_SPEED;

    }


    public void update() {

        if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0) {

            xSpeed = -xSpeed;

        }


        if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed <= 0) {

            ySpeed = -ySpeed;

        }


        double distanceToPlayerX1 = hero.getPositionx() - x;
        double distanceToPlayerY1 = hero.getPositiony() - y;

        // Calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = getDistanceBetweenObjects(this, hero);

        // Calculate direction from enemy to player
        double directionX1 = distanceToPlayerX1 / distanceToPlayer;
        double directionY1 = distanceToPlayerY1 / distanceToPlayer;

        // Set velocity in the direction to the player
        if (distanceToPlayer > 0) { // Avoid division by zero
            xSpeed = (int) (directionX1 * 5);
            ySpeed = (int) (directionY1 * 5);
        } else {
            xSpeed = 0;
            ySpeed = 0;
        }

        // =========================================================================================
        //   Update position of the enemy
        // =========================================================================================
        x += xSpeed;
        y += ySpeed;


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

        int direction = (int) Math.round(dirDouble) % BMP_ROWS;

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
    public static double getDistanceBetweenObjects2(Sprite obj1, List<Hero> obj2) {
        return Math.sqrt(
                Math.pow(obj2.get(0).getPositionx() - obj1.getPositionx1(), 2) +
                        Math.pow(obj2.get(0).getPositiony() - obj1.getPositiony1(), 2)
        );
    }
    public static boolean isColliding2(Sprite obj1, List<Hero> obj2) {
        double distance = getDistanceBetweenObjects2(obj1, obj2);
        double distanceToCollision = 50;
        return distance < distanceToCollision;
    }

    double getwidth() {
        return this.width;
    }
}