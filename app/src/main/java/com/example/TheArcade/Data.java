package com.example.TheArcade;

import java.io.Serializable;

public class Data implements Serializable {

    public static Data get() {
        return DataManager.getData();
    }

    // Serialized data
    public Tank tank;
    public Tank enemyTank;
    public Map map;
}