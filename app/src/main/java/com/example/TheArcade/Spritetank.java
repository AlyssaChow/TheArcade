package com.example.TheArcade;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

import java.util.ArrayList;

public class Spritetank {
    private static ArrayList<Spritetank> sprites = new ArrayList<Spritetank>();

    private Object parent;

    private String name;
    private Bitmap image;
    private float x, y;
    private float rotation;
    private float scaleX = 1f, scaleY = 1f;
    private float anchorX = 0.5f, anchorY = 0.5f;

    public float color = 1f;

    private boolean visible = true;

    private boolean collisionEnabled = true;
    private ArrayList<SpriteCollisionListener> listeners = new ArrayList<>();

    public Spritetank(String name, Bitmap image) {
        this.name = name;
        this.image = image;
        sprites.add(this);
    }

    public Spritetank(String name, Bitmap image, float x, float y, float rotation) {
        this.name = name;
        this.image = image;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        sprites.add(this);
    }

    public static void updateSprites(double deltaTime) {
        for (Spritetank sprite : sprites) {
            sprite.update(deltaTime);
        }
    }

    private void update(double deltaTime) {
        for (Spritetank sprite : getCollisions()) {
            for (SpriteCollisionListener listener : listeners) {
                listener.onCollision(new SpriteCollisionEvent(this, sprite));
            }
        }
    }

    public static void drawSprites(Canvas canvas) {
        for (Spritetank sprite : sprites) {
            sprite.draw(canvas);
        }
    }

    private void draw(Canvas canvas) {
        if (visible) {
            Paint p = new Paint();
            ColorMatrix m = new ColorMatrix();
            m.setSaturation(color);
            ColorFilter f = new ColorMatrixColorFilter(m);
            p.setColorFilter(f);
            canvas.drawBitmap(image, getMatrix(), p);
        }
    }

    public void destroy() {
        listeners.clear();
        sprites.remove(this);
    }

    private ArrayList<Spritetank> getCollisions() {
        ArrayList<Spritetank> collisions = new ArrayList<>();
        if (!this.collisionEnabled) return collisions;
        for (Spritetank sprite : sprites) {
            if (sprite.collisionEnabled) {
                if (sprite.getRegion().op(this.getRegion(), Region.Op.INTERSECT)) {
                    collisions.add(sprite);
                }
            }
        }
        return collisions;
    }

    private Matrix getMatrix() {
        Matrix matrix = new Matrix();
        float ax = image.getWidth() * anchorX;
        float ay = image.getHeight() * anchorY;
        matrix.postScale(scaleX, scaleY, ax, ay);
        matrix.postRotate(rotation, ax, ay);
        matrix.postTranslate((int) x - ax, (int) y - ay);
        return matrix;
    }

    public Path getPath() {
        float[] points = getPoints();

        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.lineTo(points[6], points[7]);
        path.lineTo(points[0], points[1]);
        path.close();

        return path;
    }

    public Region getRegion() {
        Path path = getPath();
        Region region = new Region();
        region.setPath(path, new Region(0, 0, 10000, 10000));
        return region;
    }

    private float[] getPoints() {
        float[] pts = {
                0, 0,
                0, image.getHeight(),
                image.getWidth(), image.getHeight(),
                image.getWidth(), 0
        };
        getMatrix().mapPoints(pts);
        return pts;
    }

    public PointF getPoint(int x, int y) {
        float[] point = new float[]{x, y};
        getMatrix().mapPoints(point);
        return new PointF(point[0], point[1]);
    }

    public void addCollisionListener(SpriteCollisionListener listener) {
        listeners.add(listener);
    }

    public void removeCollisionLister(SpriteCollisionListener listener) {
        listeners.remove(listener);
    }

    public Object getParent() {
        return this.parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public PointF getPosition() {
        return new PointF(this.x, this.y);
    }

    public float getXPosition() {
        return this.x;
    }

    public float getYPosition() {
        return this.y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PointF getScale() {
        return new PointF(this.scaleX, this.scaleY);
    }

    public void setScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public PointF getAnchor() {
        return new PointF(this.anchorX, this.anchorY);
    }

    public void setAnchor(float x, float y) {
        this.anchorX = x;
        this.anchorY = y;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setCollision(boolean enabled) {
        this.collisionEnabled = enabled;
    }

    public boolean isCollisionEnabled() {
        return this.collisionEnabled;
    }

    public float getPlayerPositionX() {
        for (int count = 0; count < sprites.size(); count++) {
            if (sprites.get(count).getName() == "tank") {
                float xPos = sprites.get(count).getXPosition();
                return xPos;
            }
        }
        return 0;
    }

    public PointF getPlayerPosition() {
        //

        for (int count = 0; count < sprites.size(); count++) {
            if (sprites.get(count).getName() == "tank") {

                return sprites.get(count).getPosition();
            }
        }
        return null;
    }
}
