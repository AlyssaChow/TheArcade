package com.example.TheArcade;

public class Utils {

    /**
     * getDistanceBetweenPoints returns the distance between 2d points p1 and p2
     *
     * @param p1x
     * @param p1y
     * @param p2x
     * @param p2y
     * @return
     */
    public static int getDistanceBetweenPoints(int p1x, int p1y, int p2x, int p2y) {
        return (int) Math.sqrt(Math.pow(p1x - p2x, 2) + Math.pow(p1y - p2y, 2));
    }
}