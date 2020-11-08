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
    }

    public void draw(Canvas canvas) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                if (data[y][x] == 0)
                    continue;

                int tex = data[y][x] - 1;
                canvas.drawBitmap(textures[tex], getMatrix(x, y), null);
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
            for (int row = 0; row < map.size(); row++)
                formattedMap[row] = map.get(row);
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

    public boolean collision(Spritetank sprite) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                if (data[y][x] == 0) continue;

                int tw = textures[0].getWidth();
                int th = textures[0].getHeight();
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

                if (sprite.getRegion().op(region, Region.Op.INTERSECT)) {
                    return true;
                }
            }
        }

        return false;
    }
}
