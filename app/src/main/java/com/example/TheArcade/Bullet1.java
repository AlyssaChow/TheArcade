package com.example.TheArcade;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class Bullet1 {
    private static Bitmap image;
    private static List<Bullet1> bullets = new ArrayList<>();

    public Spritetank bulletSprite;

    private double velX, velY;
    private double speed = 800;

    public Bullet1(Tank tank, double x, double y, double angle) {
        this.velX = (float) Math.cos(Math.toRadians(angle + 90));
        this.velY = (float) Math.sin(Math.toRadians(angle + 90));

        bulletSprite = new Spritetank("bullet", image);
        bulletSprite.setPosition((float) (x + velX * 100), (float) (y + velY * 100));
        bulletSprite.setRotation((float) angle);
        bulletSprite.addCollisionListener(new SpriteCollisionListener() {
            @Override
            public void onCollision(SpriteCollisionEvent e) {
                if (e.collidedSprite != bulletSprite && e.collidedSprite.getName() == "bullet") {
                    e.collidedSprite.destroy();
                    e.sprite.destroy();
                }
            }
        });

        bullets.add(this);
    }

    public static void start(Bitmap bmp) {
        image = bmp;
    }

    public static void update(double deltaTime) {
        for (Bullet1 bullet : bullets) {
            PointF position = bullet.bulletSprite.getPosition();
            double x = position.x + bullet.velX * bullet.speed * deltaTime;
            double y = position.y + bullet.velY * bullet.speed * deltaTime;
            bullet.bulletSprite.setPosition((float)x, (float)y);

            if (Data.get().map.collision(bullet.bulletSprite)) {
                bullet.destroy();
            }
        }
    }

    public void destroy() {
        bullets.remove(this);
        bulletSprite.destroy();
    }

    public static void destroyBullets() {
        for (Bullet1 bullet : bullets) {
            bullet.bulletSprite.destroy();
        }
        bullets.clear();
    }
}
