package com.example.TheArcade;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.util.Random;


public class Tank {
    public Spritetank tankBody, tankBarrel;
    public TankGameView gameView;
    public float xPos, yPos, rotation;
    public float speed = 400;
    public float rotationSpeed = 200;

    public boolean playable = false;

    public Tank(Bitmap tank, Bitmap barrel) {
        tankBody = new Spritetank("tank", tank);
        tankBarrel = new Spritetank("barrel", barrel);

        tankBody.addCollisionListener(new SpriteCollisionListener() {
            @Override
            public void onCollision(SpriteCollisionEvent e) {
                if (e.collidedSprite.getName() == "bullet") {
                    e.collidedSprite.destroy();
                    tankBarrel.destroy();
                    e.sprite.destroy();
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

            //Log.d("YO: ", Integer.toString(directionNum));
            // int directionNum = ThreadLocalRandom.current().nextInt(1, 4+1);
        }


    }

    public void attacking() {

    }

    public void shoot() {
        PointF barrel = tankBarrel.getPoint(70, 400);
        new Bullet1(this, barrel.x, barrel.y, rotation);
    }

    private double fireRate = 0.5;
    private double fireCooldown;

    public void update(double deltaTime) {

        fireCooldown -= deltaTime;

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
        }

        tankBody.setPosition(xPos, yPos);
        tankBarrel.setPosition(xPos, yPos);
        tankBarrel.setRotation(rotation);
    }
}
