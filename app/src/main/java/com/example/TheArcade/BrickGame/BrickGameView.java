package com.example.TheArcade.BrickGame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import androidx.annotation.NonNull;

import com.example.TheArcade.R;
import com.example.TheArcade.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BrickGameView  extends SurfaceView implements SurfaceHolder.Callback {
    private BrickThread thread;
    private BrickLevel brickLevel;
    private BrickBall ball;
    private Paddle paddle;
    private BrickSprite gamebrick;
    private int bricksizeX = (int)(50*2.5);
    private int bricksizeY = (int)(25*2.5);

    public BrickGameView(Context context, AttributeSet attributeSet){
        super(context);
        getHolder().addCallback(this);
        thread = new BrickThread(getHolder(), this);
        setFocusable(true);
    }
    @Override

    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.runGame(true);
        thread.start();
        ball = new BrickBall(BitmapFactory.decodeResource(getResources(), R.drawable.brickball_1));
        paddle = new Paddle(BitmapFactory.decodeResource(getResources(),R.drawable.paddle));
        brickLevel = new BrickLevel(getResources());

        brickLevel.mapCreate(4,7);
        //gamebrick = new BrickSprite(1,400,400,BitmapFactory.decodeResource(getResources(),R.drawable.brick_1));

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

        boolean retry = true;
        while (retry){
            try {
                thread.runGame(false);
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(double deltatime){
        ball.update(deltatime);
        paddle.update(deltatime);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas!=null){
            canvas.drawColor(Color.BLUE);
            ball.draw(canvas);
            paddle.draw(canvas);
            brickLevel.drawMap(canvas);

            //Paint paint = new Paint();
            //gamebrick.draw(canvas);
            //paint.setColor(Color.rgb(255,255,0));
            //canvas.drawRect(100,100,100,200,paint);
            //canvas.drawRect(50,100,50,200,paint);
            //canvas.drawRect(200,100,150,200,paint);

        }
    }

}
