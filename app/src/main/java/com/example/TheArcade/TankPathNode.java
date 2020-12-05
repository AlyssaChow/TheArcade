package com.example.TheArcade;

import android.util.Log;

public class TankPathNode implements Comparable<TankPathNode> {
    public int id;
    public int x, y;

    public TankPathNode(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(TankPathNode node) {
        return id > node.id ? 1 : -1;
    }
}
