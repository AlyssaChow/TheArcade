package com.example.TheArcade.BrickGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.TheArcade.CustomOnClick;
import com.example.TheArcade.MainActivity;
import com.example.TheArcade.R;
import com.example.TheArcade.tankMain;

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
        TextView playerScore = findViewById(R.id.brick_lives);
        playerScore.setText("3");
        Button exitButton = findViewById(R.id.exit_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Brick.this, MainActivity.class);
                startActivity(intent);
            }


        });
    }

}