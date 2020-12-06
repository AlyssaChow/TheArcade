package com.example.TheArcade.BrickGame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.TheArcade.R;
import com.example.TheArcade.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BrickGameView  extends SurfaceView implements SurfaceHolder.Callback {
    private int lives;
    private BrickThread thread;
    private BrickLevel brickLevel;
    private BrickBall ball;
    private Paddle paddle;
    //private BrickLives liveDisplay;
    private BrickSprite gamebrick;

    private TextView playerScore;

    private int bricksizeX = (int)(50*2.5);
    private int bricksizeY = (int)(25*2.5);

    public BrickGameView(Context context, AttributeSet attributeSet){
        super(context);
        getHolder().addCallback(this);
        thread = new BrickThread(getHolder(), this);
        setFocusable(true);
        lives = 3;
    }
    @Override

    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        //playerScore = findViewById(R.id.brick_lives);

        thread.runGame(true);
        thread.start();
        ball = new BrickBall(BitmapFactory.decodeResource(getResources(), R.drawable.brickball_1));
        paddle = new Paddle(BitmapFactory.decodeResource(getResources(),R.drawable.paddle));
        brickLevel = new BrickLevel(getResources());
        brickLevel.mapCreate(3,3);
        //liveDisplay = new BrickLives(getResources());


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
        paddle.update(deltatime);
        ball.updatePaddle(paddle.returnX());
        ball.update(deltatime);

        brickLevel.update(ball);
        //ArrayList<BrickSprite> map = brickLevel.getmap();
        int x= 0;
        //while(map.size()>x){
           // map.get(x).intersects(ball);


        //}


        if(ball.isOutofbounds()){
            lives--;
            ball.restart();
        }
        if(lives <= 0){
            thread.runGame(false); //game over
            ball.removeImage();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(canvas!=null){
            canvas.drawColor(Color.BLUE);
            ball.draw(canvas);
            paddle.draw(canvas);
            brickLevel.drawMap(canvas);
            //liveDisplay.draw(canvas);
        }
    }

}
