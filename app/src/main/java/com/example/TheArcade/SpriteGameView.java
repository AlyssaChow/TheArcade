package com.example.TheArcade;


import java.util.ArrayList;

import java.util.List;
import java.util.Random;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import android.view.SurfaceHolder;

import android.view.SurfaceHolder.Callback;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


public class SpriteGameView extends SurfaceView {

    private Bitmap bmp;

    protected double directionX = 1.0;
    protected double directionY = 0.0;

    private SurfaceHolder holder;

    private GameLoopThread gameLoopThread;
    private Canvas canvas;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<Portal> portal = new ArrayList<Portal>();
    private Rect ballRect;
    private long lastClick;
    private List<EnergyBall> energyBalls = new ArrayList<EnergyBall>();
    private int joystickPointerId = 0;
    private Joystick joystick;
    private Shoot shoot;
    private Shoot shoot2;
    private Shoot shoot3;
    private int score;
    private Paint paint;
    private int numberOfSpellsToCast = 0;
    private List<Hero> hero = new ArrayList<Hero>();
    Bitmap b;
    Bitmap b1;
    Bitmap b2;
    Bitmap b3;
    Bitmap b4;
    Bitmap b5;
    Bitmap b6;
    Bitmap b7;
    Bitmap b8_1;
    Bitmap b8_2;
    Bitmap b8_3;
    Bitmap b8_4;
    Bitmap b8_5;
    Bitmap b8_6;
    Bitmap b9;
    Sprite b10;
    ImageButton B;
    GameOver over;
    private boolean pauseflag;
    private SharedPreferences prefs;
    private Dungeon_activity dungeon;
    Dungeon_activity dun;
    boolean gameover=false;
    @SuppressLint("WrongViewCast")
    public SpriteGameView(Context context, Dungeon_activity dungeon) {

        super(dungeon);

        pauseflag = false;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.dungeon = dungeon;
        over = new GameOver(context);

        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        paint = new Paint();
        prefs = dungeon.getSharedPreferences("game", Context.MODE_PRIVATE);
        joystick = new Joystick(200, 900, 70, 40);

        shoot = new Shoot(2600, 900, 70);
        shoot2 = new Shoot(10, 5, 70);
        shoot3 = new Shoot(400, 200, 70);
        setFocusable(true);
        b = BitmapFactory.decodeResource(getResources(), R.drawable.tile1);
        b1 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_side_mid_right)
        ;
        b2 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_side_mid_left)
        ;
        b3 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_top_mid)
        ;
        b4 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_top_top)
        ;
        b5 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_corner_front_right)
        ;
        b6 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_inner_corner_l_top_left)
        ;
        b7 = BitmapFactory.decodeResource(getResources(), R.drawable.wall_inner_corner_l_top_rigth)
        ;
        b8_1 = BitmapFactory.decodeResource(getResources(), R.drawable.ui_heart_full)
        ;
        b8_2 = BitmapFactory.decodeResource(getResources(), R.drawable.ui_heart_full)
        ;
        b8_3 = BitmapFactory.decodeResource(getResources(), R.drawable.ui_heart_full)
        ;
        b8_4 = BitmapFactory.decodeResource(getResources(), R.drawable.ui_heart_empty)
        ;
        b8_5 = BitmapFactory.decodeResource(getResources(), R.drawable.ui_heart_empty)
        ;
        b8_6 = BitmapFactory.decodeResource(getResources(), R.drawable.ui_heart_empty)
        ;
        b9 = BitmapFactory.decodeResource(getResources(), R.drawable.flask_big_red)
        ;

        ;
        this.ballRect = new Rect(0, 0, 0 + 40, 0 + 50);
        b = Bitmap.createScaledBitmap(b, 100, 100, false);
        b1 = Bitmap.createScaledBitmap(b1, 100, 100, false);
        b2 = Bitmap.createScaledBitmap(b2, 100, 100, false);
        b3 = Bitmap.createScaledBitmap(b3, 100, 100, false);
        b4 = Bitmap.createScaledBitmap(b4, 100, 100, false);
        b5 = Bitmap.createScaledBitmap(b5, 100, 100, false);
        b6 = Bitmap.createScaledBitmap(b6, 100, 100, false);
        b7 = Bitmap.createScaledBitmap(b7, 100, 100, false);
        b8_1 = Bitmap.createScaledBitmap(b8_1, 100, 100, false);
        b8_2 = Bitmap.createScaledBitmap(b8_2, 100, 100, false);
        b8_3 = Bitmap.createScaledBitmap(b8_3, 100, 100, false);
        b8_4 = Bitmap.createScaledBitmap(b8_4, 100, 100, false);
        b8_5 = Bitmap.createScaledBitmap(b8_5, 100, 100, false);
        b8_6 = Bitmap.createScaledBitmap(b8_6, 100, 100, false);
        b9 = Bitmap.createScaledBitmap(b9, 100, 100, false);

        gameLoopThread = new GameLoopThread(this);

        holder = getHolder();

        holder.addCallback(new Callback() {


            @Override

            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override

            public void surfaceCreated(SurfaceHolder holder) {

                createSprites();

                gameLoopThread.setRunning(true);

                gameLoopThread.start();
            }

            @Override

            public void surfaceChanged(SurfaceHolder holder, int format,

                                       int width, int height) {
            }
        });
    }

    private void createSprites() {


        hero.add(createSprite1(R.drawable.hero1));
        portal.add(createSprite3(R.drawable.portal));

    }


    private Hero createSprite1(int resouce) {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

        return new Hero(SpriteGameView.this, bmp, joystick);

    }

    private Sprite createSprite(int resouce) {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

        return new Sprite(SpriteGameView.this, bmp, hero.get(0));

    }

    private EnergyBall createSprite2(int resouce) {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

        return new EnergyBall(SpriteGameView.this, bmp, hero.get(0));

    }

    private Portal createSprite3(int resouce) {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);

        return new Portal(SpriteGameView.this, bmp);

    }

    @Override

    protected void onDraw(Canvas canvas) {
        update();


        canvas.drawColor(Color.BLACK);


        for (int i = 0; i < getWidth(); i += b.getWidth()) {
            for (int j = 0; j < getHeight(); j += b.getWidth()) canvas.drawBitmap(b, i, j, null);
        }

        for (int f = 0; f < getWidth(); f += b.getWidth()) {
            canvas.drawBitmap(b5, f, 0, null);
        }
        for (int k = 0; k < getWidth(); k += b.getWidth()) {
            canvas.drawBitmap(b5, k, getHeight() - b.getWidth(), null);
        }
        for (int e = 0; e < getWidth(); e += b.getWidth()) {
            canvas.drawBitmap(b4, e, 0, null);
        }
        for (int t = 0; t < getWidth(); t += b.getWidth()) {
            canvas.drawBitmap(b3, t, getHeight() - 2 * b.getWidth(), null);
        }
        for (int w = 0; w < getHeight() - 2 * b.getWidth(); w += b.getWidth()) {
            canvas.drawBitmap(b1, 0, w, null);
        }
        for (int r = 0; r < getHeight() - 2 * b.getWidth(); r += b.getWidth()) {
            canvas.drawBitmap(b2, getWidth() - b.getWidth(), r, null);
        }
        canvas.drawBitmap(b6, 0, getHeight() - 2 * b.getWidth(), null);
        canvas.drawBitmap(b7, getWidth() - b.getWidth(), getHeight() - 2 * b.getWidth(), null);
        canvas.drawBitmap(b8_1, 0, getHeight() - b.getWidth(), null);
        canvas.drawBitmap(b8_2, b.getWidth(), getHeight() - b.getWidth(), null);
        canvas.drawBitmap(b8_3, 2 * b.getWidth(), getHeight() - b.getWidth(), null);
        Random random = new Random();
        joystick.draw(canvas);
        shoot.draw(canvas);
        shoot2.draw(canvas);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(90);
        canvas.drawText("Score:" + score, getWidth() - 400, 90, paint);

        for (Portal port : portal) {

            port.onDraw(canvas);

        }
        for (Hero hero1 : hero) {

            hero1.onDraw(canvas);

        }
        for (Sprite sprite : sprites) {

            sprite.onDraw(canvas);

        }
        for (EnergyBall ener : energyBalls) {

            ener.onDraw(canvas);
        }

        if (pauseflag) {

                over.draw(canvas);
            try {

                gameLoopThread.setRunning(false);
                gameLoopThread.sleep(2000);

                dungeon.startActivity(new Intent(dungeon, Dungeon_menu.class));

                dungeon.finish();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (gameover) {





            waitBeforeExiting();


        }


    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (shoot2.isPressed((int) event.getX(), (int) event.getY())) {
                    // Joystick was not previously, and is not pressed in this event -> cast spell

                    pauseflag = true;


                }
                if (shoot3.isPressed((int) event.getX(), (int) event.getY())) {
                    // Joystick was not previously, and is not pressed in this event -> cast spell

                    return true;
                }
                if (shoot.isPressed((int) event.getX(), (int) event.getY())) {
                    // Joystick was not previously, and is not pressed in this event -> cast spell
                    numberOfSpellsToCast++;

                }
                if (joystick.isPressed((int) event.getX(), (int) event.getY())) {
                    // Joystick is pressed in this event -> setIsPressed(true) and store pointer id

                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }


                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()) {
                    // Joystick was pressed previously and is now moved

                    joystick.setActuator((int) event.getX(), (int) event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }
        return super.onTouchEvent(event);

    }

    public void update() {

        // Update game state


        joystick.update();
        shoot.update();
        while (numberOfSpellsToCast > 0) {
            sprites.add(createSprite(R.drawable.dark_skull));
            energyBalls.add(new EnergyBall(this, b9, hero.get(0)));
            numberOfSpellsToCast--;
        }

        for (int i = sprites.size() - 1; i >= 0; i--) {

            Sprite sprite = sprites.get(i);

            if (Sprite.isColliding2(sprite, hero) && b8_3 != b8_6) {

                b8_3 = b8_6;

                sprites.remove(sprite);

            } else if (Sprite.isColliding2(sprite, hero) && b8_3 == b8_6 && b8_2 != b8_5) {
                b8_2 = b8_5;
                sprites.remove(sprite);

            } else if (Sprite.isColliding2(sprite, hero) && b8_3 == b8_6 && b8_2 == b8_5 && b8_1 != b8_4) {
                b8_1 = b8_4;
                sprites.remove(sprite);
                saveIfHighScore();

                pauseflag = true;
            }


            for (int j = energyBalls.size() - 1; j >= 0; j--) {

                EnergyBall energy = energyBalls.get(j);

                if (Sprite.isColliding(energy, sprite)) {


                    energyBalls.remove(energy);
                    sprites.remove(sprite);
                    score++;
                    break;
                }
            }
        }
    }

    private void saveIfHighScore() {

        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

    }

    public void pause() throws InterruptedException {
        gameLoopThread.setRunning(false);


    }

    public void resume() throws InterruptedException {


        gameLoopThread.setRunning(true);
        gameLoopThread.join();
        gameLoopThread = new GameLoopThread(this);
        gameLoopThread.start();
    }

    private void waitBeforeExiting() {

        try {
            Thread.sleep(3000);

            gameLoopThread.setRunning(false);
            dungeon.startActivity(new Intent(dungeon, Dungeon_menu.class));
            dungeon.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


