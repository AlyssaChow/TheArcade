package com.example.TheArcade;

public class DataManager {
    private static Data data;

    public static Data getData() {
        if (data == null)
            data = new Data();
        return data;
    }

    public static Data resetData() {
        data = new Data();
        return data;
    }
}