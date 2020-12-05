package com.example.TheArcade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Region;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Map {

    private static Resources resources;
    private static Bitmap[] textures;

    public static void start(Resources resources) {
        Map.resources = resources;
        Map.textures = new Bitmap[1];
        Map.textures[0] = BitmapFactory.decodeResource(resources, R.drawable.wall);
    }

    private int[][] data;

    public Map() {
        this.data = loadMap(R.raw.map1);
    }

    public Map(int id) {
        this.data = loadMap(id);

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                // Spawn the player
                if (data[y][x] == 9) {
                    Tank player = new Tank(Data.tankBmp, Data.barrelBmp);
                    player.xPos = (x + 0.5f) * textures[0].getWidth();
                    player.yPos = (y + 0.5f) * textures[0].getHeight();
                    player.playable = true;
                    Data.get().tank = player;
                    continue;
                }

                // Check if this is an enemy node
                if (data[y][x] > 9) {
                    // This is an enemy spawn
                    if (data[y][x] % 10 == 0) {
                        Tank enemy = new Tank(Data.eTankBmp, Data.eBarrelBmp);
                        enemy.id = data[y][x];

                        float xPos = (x + 0.5f) * textures[0].getWidth();
                        float yPos = (y + 0.5f) * textures[0].getHeight();

                        enemy.xPos = xPos;
                        enemy.yPos = yPos;

                        Data.get().enemyTanks.add(enemy);
                    }
                    continue;
                }
            }
        }
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                // Check if this is an enemy node
                if (data[y][x] > 9) {
                    for (int i = 0; i < Data.get().enemyTanks.size(); i++) {
                        if (Data.get().enemyTanks.get(i).id == ((int)(data[y][x]/10.0))*10) {
                            int pathID = data[y][x];
                            int xPos = (int)((x + 0.5f) * textures[0].getWidth());
                            int yPos = (int)((y + 0.5f) * textures[0].getHeight());
                            Data.get().enemyTanks.get(i).path.add(new TankPathNode(pathID, xPos, yPos));
                            Collections.sort(Data.get().enemyTanks.get(i).path);
                        }
                    }
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                if (data[y][x] == 0)
                    continue;

                int tex = data[y][x] - 1;
                if (tex > -1 && tex < 8) {
                    canvas.drawBitmap(textures[tex], getMatrix(x, y), null);
                }
            }
        }
    }

    private Matrix getMatrix(int x, int y) {
        Matrix matrix = new Matrix();
        matrix.postTranslate(x * textures[0].getWidth(),
                y * textures[0].getHeight());
        return matrix;
    }

    public int[][] loadMap(int id) {
        //destroy();
        InputStream databaseInputStream = resources.openRawResource(id);
        BufferedReader r = new BufferedReader(new InputStreamReader(databaseInputStream));
        try {
            ArrayList<int[]> map = new ArrayList<>();
            for (String line; (line = r.readLine()) != null; ) {
                String[] characters = line.split(",");
                int[] row = new int[characters.length];
                for (int c = 0; c < characters.length; c++) {
                    String ch = characters[c].trim().replaceAll("\\uFEFF", "");
                    if (ch.length() > 0)
                        row[c] = Integer.parseInt(ch);
                }
                map.add(row);
            }
            int[][] formattedMap = new int[map.size()][map.get(0).length];
            for (int row = 0; row < map.size(); row++) {
                formattedMap[row] = map.get(row);
            }
            return formattedMap;
        } catch (Exception e) {
            Log.d("reeee: ", "catch: " + e);
        };
        return null;
    }

    public int getWidth() {
        return data[0].length * textures[0].getWidth();
    }

    public int getHeight() {
        return data.length * textures[0].getHeight();
    }

    public boolean collision(Spritetank tank) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                if (data[y][x] < 1 || data[y][x] > 8) continue;

                int tw = textures[data[y][x] - 1].getWidth();
                int th = textures[data[y][x] - 1].getHeight();
                float[] points = {
                        0, 0,
                        0, th,
                        tw, th,
                        tw, 0
                };
                getMatrix(x, y).mapPoints(points);

                Path path = new Path();
                path.moveTo(points[0], points[1]);
                path.lineTo(points[2], points[3]);
                path.lineTo(points[4], points[5]);
                path.lineTo(points[6], points[7]);
                path.lineTo(points[0], points[1]);
                path.close();

                Region region = new Region();
                region.setPath(path, new Region(0, 0, 10000, 10000));

                if (tank.getRegion().op(region, Region.Op.INTERSECT)) {
                    return true;
                }
            }
        }

        return false;
    }

    public Path getRay(Spritetank tank1, Spritetank tank2) {
        Path rayPath = new Path();
        rayPath.moveTo(tank1.getPosition().x, tank1.getPosition().y);
        rayPath.lineTo(tank2.getPosition().x, tank2.getPosition().y);
        rayPath.lineTo(tank2.getPosition().x + 10, tank2.getPosition().y + 10);
        rayPath.lineTo(tank1.getPosition().x + 10, tank1.getPosition().y + 10);
        rayPath.lineTo(tank1.getPosition().x, tank1.getPosition().y);
        rayPath.close();
        return rayPath;
    }

    public boolean los(Spritetank tank1, Spritetank tank2) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                if (data[y][x] < 1 || data[y][x] > 8) continue;

                int tw = textures[data[y][x] - 1].getWidth();
                int th = textures[data[y][x] - 1].getHeight();
                float[] points = {
                        0, 0,
                        0, th,
                        tw, th,
                        tw, 0
                };
                getMatrix(x, y).mapPoints(points);

                Path path = new Path();
                path.moveTo(points[0], points[1]);
                path.lineTo(points[2], points[3]);
                path.lineTo(points[4], points[5]);
                path.lineTo(points[6], points[7]);
                path.lineTo(points[0], points[1]);
                path.close();

                Region ray = new Region();
                ray.setPath(getRay(tank1, tank2), new Region(0, 0, 10000, 10000));

                Region region = new Region();
                region.setPath(path, new Region(0, 0, 10000, 10000));

                if (ray.op(region, Region.Op.INTERSECT)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void destroy() {
        Data.get().tank.tankBody.destroy();
        Data.get().tank.tankBarrel.destroy();
        Data.get().tank = null;

        for (Tank enemy : Data.get().enemyTanks) {
            enemy.tankBody.destroy();
            enemy.tankBarrel.destroy();
        }
        Data.get().enemyTanks.clear();

        Bullet1.destroyBullets();
    }
}
