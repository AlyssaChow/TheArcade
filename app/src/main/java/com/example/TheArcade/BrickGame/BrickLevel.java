package com.example.TheArcade.BrickGame;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.example.TheArcade.R;
import com.example.TheArcade.R;
import java.util.ArrayList;



public class BrickLevel {
    private static Resources resources;
    private ArrayList<BrickSprite> map;

    private int bricksizeX = (int)(50*2.5);
    private int bricksizeY = (int)(25*2.5);

    private static int offsetX = 150;
    private static int offsetY = 400;
    private static int blocksize = 100;
    private static int blockheight = 50;
    private int screenWidth;

    private enum Difficulty{EASY,MED,HARD};


    public BrickLevel(Resources resources){
        map = new ArrayList<BrickSprite>();
        this.resources = resources;
        screenWidth = resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public void mapCreate(int mapX, int mapY){
        BrickSprite gen;
        RectF rectangle;
        int startX = getXstart(mapX);
        int currentX = startX, currentY = offsetY;
        for(int i = 0;i<mapY;i++){ //Generates row of blocks
            for(int j = 0;j<mapX;j++){ //Generates blocks within a row
                if(i<1){
                    rectangle = new RectF(currentX,currentY,currentX+blocksize,currentY+blockheight);
                    if (j % 2 == 0 && mapY>2){
                        gen = new BrickSprite(3,currentX,currentY, rectangle, genColor(3));
                    } else {
                        gen = new BrickSprite(2,currentX,currentY, rectangle, genColor(2));
                    }
                }else{
                    rectangle = new RectF(currentX,currentY,currentX+blocksize,currentY+blockheight);
                    gen = new BrickSprite(1,currentX,currentY, rectangle, genColor(1));
                }
                map.add(gen);
                currentX += offsetX + bricksizeX;
            }
            currentY += 15 + bricksizeY;
            currentX =startX;
        }
    }
    private Paint genColor(int type){
        Paint retPaint = new Paint();
        switch(type){
            case 3: retPaint.setColor(Color.MAGENTA);
                    break;
            case 2: retPaint.setColor(Color.GREEN);
                    break;
            default: retPaint.setColor(Color.RED);
        }
        return retPaint;
    }

    public void drawMap(Canvas canvas){
        for(int i=0;i<map.size();i++){
            map.get(i).draw(canvas);
        }
    }

    public void update(BrickBall ball){
        for(int i=map.size();i>0;i--){
            if(map.get(i-1).intersects(ball)){
                map.remove(i-1);
                //Add level done;
            }
        }
    }
    //returns the position where the blocks will be centered in the middle of screen
    //Half the screen - the total length of the map
    private int getXstart(int mapX){
        return (screenWidth/2) - ((blocksize *(mapX-1)) + offsetX);
    }
    public ArrayList getmap(){
        return map;
    }

    public boolean isEmpty(){return map.isEmpty();}


}
