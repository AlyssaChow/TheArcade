package com.example.TheArcade.BrickGame;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.res.Resources;
import android.util.Log;

import com.example.TheArcade.R;
import com.example.TheArcade.R;
import java.util.ArrayList;



public class BrickLevel {
    private static Resources resources;
    private ArrayList<BrickSprite> map;

    private int bricksizeX = (int)(50*2.5);
    private int bricksizeY = (int)(25*2.5);

    private static int offsetX = 200;
    private static int offsetY = 600;
    private static int blocksize = 50;
    private int screenWidth;

    private enum Difficulty{EASY,MED,HARD};


    public BrickLevel(Resources resources){
        map = new ArrayList<BrickSprite>();
        this.resources = resources;
        screenWidth = resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public void mapCreate(int mapX, int mapY){
        BrickSprite gen;
        int startX = getXstart(mapX);
        int currentX = startX, currentY = offsetY;
        for(int i = 0;i<mapY;i++){ //Generates row of blocks
            for(int j = 0;j<mapX;j++){ //Generates blocks within a row
                if(i<1){
                    gen = new BrickSprite(2,currentX,currentY, BitmapFactory.decodeResource(resources, R.drawable.brick_2));
                }else{
                    gen = new BrickSprite(1,currentX,currentY, BitmapFactory.decodeResource(resources, R.drawable.brick_1));
                }
                map.add(gen);
                currentX += offsetX + bricksizeX;
            }
            currentY += 5 + bricksizeY;
            currentX =startX;
        }
    }

    public void drawMap(Canvas canvas){
        for(int i=0;i<map.size();i++){
            map.get(i).draw(canvas);
        }
    }

    public void update(BrickBall ball){
        for(int i=map.size();i>0;i--){
            if(map.get(i-1).intersects(ball)){
                map.remove(i);
            }
        }
    }
    //returns the position where the blocks will be centered in the middle of screen
    //Half the screen - the total length of the map
    private int getXstart(int mapX){
        return (screenWidth/2) - ((blocksize*(mapX))+(offsetX * (mapX-1)));
    }
    public ArrayList getmap(){
        return map;
    }


}
