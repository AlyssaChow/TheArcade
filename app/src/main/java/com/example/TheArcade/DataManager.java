package com.example.TheArcade;

public class DataManager {
    private static Data data;

    public static Data getData() {
        if (data == null)
            data = new Data();
        return data;
    }
}