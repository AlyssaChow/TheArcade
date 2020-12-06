package com.example.TheArcade.BrickGame;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.res.Resources;
import com.example.TheArcade.R;
import java.util.ArrayList;

public class BrickLives {
    private static Resources resources;
    private int screenWidth;
    //private int screenHeight;
    private int numLives =3;
    private ArrayList<LiveSprite> lives;
    public BrickLives(Resources resources){
        this.resources = resources;
        screenWidth = resources.getSystem().getDisplayMetrics().widthPixels;
        //screenHeight = resources.getSystem().getDisplayMetrics().heightPixels;
        lives = new ArrayList<LiveSprite>();
    }

    public void GenerateLives(Canvas canvas){
        int CurrentX = screenWidth - 400;
        for(int i = 0;i<numLives;i++){
            LiveSprite n = new LiveSprite(CurrentX,100,BitmapFactory.decodeResource(resources, R.drawable.pink));
            lives.add(n);
            CurrentX += 30;
        }
    }

    public void remove(int i){
        lives.remove(i).remove();
    }

    public void draw(Canvas canvas){
        for(int i = 0;i<numLives;i++) {
            (lives.get(i)).draw(canvas);
        }
    }

}
