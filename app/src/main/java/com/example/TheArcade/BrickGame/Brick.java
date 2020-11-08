package com.example.TheArcade.BrickGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

import com.example.TheArcade.CustomOnClick;
import com.example.TheArcade.R;

public class Brick extends AppCompatActivity {
    private SurfaceHolder surfaceHolder;
    private BrickGameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.brick_activity);


        findViewById(R.id.PaddleLeft).setOnTouchListener(new CustomOnClick());
        findViewById(R.id.PaddleRight).setOnTouchListener(new CustomOnClick());
    }

}