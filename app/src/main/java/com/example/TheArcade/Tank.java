package com.example.TheArcade;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


public class Tank {
    public Spritetank tankBody, tankBarrel;
    public TankGameView gameView;
    public float xPos, yPos, rotation;
    private float xVel, yVel;
    public float speed = 400;
    public float rotationSpeed = 200;
    public int id;
    public ArrayList<TankPathNode> path = new ArrayList<>();
    public boolean alive = true;

    public boolean playable = false;

    public Tank(Bitmap tank, Bitmap barrel) {
        tankBody = new Spritetank("tank", tank);
        tankBarrel = new Spritetank("barrel", barrel);

        tankBody.addCollisionListener(new SpriteCollisionListener() {
            @Override
            public void onCollision(SpriteCollisionEvent e) {
                if (e.collidedSprite.getName() == "bullet") {
                    alive = false;
                    e.sprite.destroy();
                    e.collidedSprite.destroy();
                    tankBarrel.destroy();
                }

                if (e.collidedSprite != tankBody && e.collidedSprite.getName() == "tank") {
                    alive = false;
                    e.collidedSprite.destroy();
                    e.sprite.destroy();
                    tankBarrel.destroy();
                }
            }
        });
    }

    public boolean isPlayable() {
        return playable;
    }

    public float distanceCalc() {
        double distanceX =
                Math.pow(tankBody.getPosition().x - tankBody.getPlayerPosition().x, 2);
        double distanceY =
                Math.pow(tankBody.getPosition().y - tankBody.getPlayerPosition().y, 2);
        float distance = (float) (Math.sqrt(distanceX + distanceY));
        return distance;
    }

    public void homing() {
        float dx = tankBody.getPlayerPosition().x - tankBody.getPosition().x;
        float dy = tankBody.getPlayerPosition().x - tankBody.getPosition().y;

        xPos += dx;
        yPos += dy;


    }


    int pathStep = 0;
    int pathDir = 1;

    private boolean goTo(double deltaTime, int x, int y) {

        int dx = (int)(x - xPos);
        int dy = (int)(y - yPos);

        if (dx == 0 && dy == 0)
            return true;

        float dist = (float)((speed * deltaTime) / Math.sqrt(dx * dx + dy * dy));

        xVel = dx * dist;
        yVel = dy * dist;
        xPos += xVel;
        yPos += yVel;

        return dist > 0.999;
    }

    private boolean rotateTowards(double deltaTime, int x, int y) {

        int dx = (int)(x - xPos);
        int dy = (int)(y - yPos);

        if (dx == 0 && dy == 0)
            return true;

        float angle = (float)Math.atan2(dy, dx);
        angle = (float)(angle * 180 / Math.PI) - 90; // Change it to degrees

        float maxRotation = (float)(rotationSpeed / 3 * deltaTime);
        float diff = angle - rotation;
        if (diff < -180) // Rule to make it go around smoothly
            diff += 360;

        if (diff > maxRotation)
            diff = maxRotation;
        else if (diff < -maxRotation)
            diff = -maxRotation;

        rotation += diff;

        return Math.abs(diff) < 1.0;
    }


    private double idleRate = 20.0;
    private double idleMovement;

    public void idle(double deltaTime) {

        //Log.d("DEBUG: ", Float.toString(tankBody.getPlayerPosition().x));
        //Log.d("DEBUG: ", Float.toString(tankBody.getPlayerPosition().y));


        float distance = distanceCalc();
        if (distance < 10000) {
            homing();
        } else {
            idleMovement -= deltaTime;

            Random rand = new Random();
            int directionNum = rand.nextInt((4 - 1) + 1) + 1;

            if (directionNum == 1) {
                yPos -= speed * deltaTime;
                idleMovement = idleRate;
            }
            if (directionNum == 2) {
                yPos += speed * deltaTime;
                idleMovement = idleRate;
            }
            if (directionNum == 3) {
                xPos -= speed * deltaTime;
                idleMovement = idleRate;
            }
            if (directionNum == 4) {
                xPos += speed * deltaTime;
                idleMovement = idleRate;
            }
        }
    }

    public void attacking() {

    }

    public void shoot() {
        PointF barrel = tankBarrel.getPoint(70, 400);
        new Bullet1(this, barrel.x, barrel.y, rotation);
    }

    private double fireRate = 1.5;
    private double fireCooldown;

    private double losCooldown;
    private double playerLosCooldown;

    public void update(double deltaTime) {

        if (!alive) return;

        fireCooldown -= deltaTime;
        losCooldown -= deltaTime;

        if (playable) {
            if (CustomOnClick.isDown(R.id.upButton)) {
                float t = yPos;
                yPos -= speed * deltaTime;
                tankBody.setPosition(xPos, yPos);

                if (DataManager.getData().map.collision(tankBody))
                    yPos = t;
            }

            if (CustomOnClick.isDown(R.id.downButton)) {
                float t = yPos;
                yPos += speed * deltaTime;
                tankBody.setPosition(xPos, yPos);

                if (DataManager.getData().map.collision(tankBody))
                    yPos = t;
            }

            if (CustomOnClick.isDown(R.id.leftButton)) {
                float t = xPos;
                xPos -= speed * deltaTime;
                tankBody.setPosition(xPos, yPos);

                if (DataManager.getData().map.collision(tankBody))
                    xPos = t;
            }

            if (CustomOnClick.isDown(R.id.rightButton)) {
                float t = xPos;
                xPos += speed * deltaTime;
                tankBody.setPosition(xPos, yPos);

                if (DataManager.getData().map.collision(tankBody))
                    xPos = t;
            }

            if (CustomOnClick.isDown(R.id.rotateLeftButton)) {
                rotation -= rotationSpeed * deltaTime;
            }

            if (CustomOnClick.isDown(R.id.rotateRightButton)) {
                rotation += rotationSpeed * deltaTime;
            }

            if (CustomOnClick.isDown(R.id.shootButton) && fireCooldown <= 0) {
                shoot();
                fireCooldown = fireRate;
            }
        } else {
            // AI FOR ENEMY TANKS
            tankBody.setName("EnemyTank");
            tankBarrel.setName("EnemyTank");

            if (losCooldown <= 0) {
                losCooldown = 0.1;
            }

            // If the player can be seen by this enemy, turn the barrel towards them
            Tank tank = Data.get().tank;
            if (tank.alive && Data.get().map.los(tankBody, tank.tankBody)) {
                playerLosCooldown = 1.5;
                if (rotateTowards(deltaTime, (int) tank.xPos, (int) tank.yPos)) {
                    // Shoot a bullet once the barrel is in their direction
                    if (fireCooldown <= 0) {
                        shoot();
                        fireCooldown = fireRate * 3;
                    }
                }
            } else {
                // Aim the barrel towards the moving direction
                // playerLosCooldown is a timeout for the enemy to lose interest of the player
                if (playerLosCooldown <= 0) {
                    rotateTowards(deltaTime, (int) (xPos + xVel * 10), (int) (yPos + yVel * 10));
                } else {
                    playerLosCooldown -= deltaTime;
                }
            }

            if (goTo(deltaTime, path.get(pathStep).x, path.get(pathStep).y)) {
                if (pathDir == 1) {

                    if (pathStep == path.size() - 1) {
                        pathDir = -1;
                        pathStep--;
                    }
                    else
                        pathStep++;
                } else {
                    if (pathStep == 0) {
                        pathDir = 1;
                        pathStep++;
                    }
                    else
                        pathStep--;
                }
            }
        }

        tankBody.setPosition(xPos, yPos);
        tankBarrel.setPosition(xPos, yPos);
        tankBarrel.setRotation(rotation);
    }

    public void draw(Canvas canvas) {
        if (alive && !playable && Data.get().tank.alive) {
            if (Data.get().map.los(Data.get().tank.tankBody, tankBody)) {
                Path ray = Data.get().map.getRay(Data.get().tank.tankBody, tankBody);
                Paint p = new Paint();
                p.setColor(Color.RED);
                p.setStrokeWidth(20);
                canvas.drawPath(ray, p);
            }
        }
    }
}
