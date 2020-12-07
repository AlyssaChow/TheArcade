package com.example.TheArcade;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.TheArcade.BrickGame.Brick;

public class tankMain extends AppCompatActivity {
    private SurfaceHolder surfaceHolder;
    private TankGameView gameView;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs = getSharedPreferences("game", Context.MODE_PRIVATE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tankmenu);
    }

    public void startTankGame(View button) {
        setContentView(R.layout.tank);

        // Up Button
        findViewById(R.id.upButton).setOnTouchListener(new CustomOnClick());

        // Down Button
        findViewById(R.id.downButton).setOnTouchListener(new CustomOnClick());

        // Left Button
        findViewById(R.id.leftButton).setOnTouchListener(new CustomOnClick());

        // Right Button
        findViewById(R.id.rightButton).setOnTouchListener(new CustomOnClick());

        // Rotate Left Button
        findViewById(R.id.rotateLeftButton).setOnTouchListener(new CustomOnClick());

        // Rotate Right Button
        findViewById(R.id.rotateRightButton).setOnTouchListener(new CustomOnClick());

        // Shoot Button
        findViewById(R.id.shootButton).setOnTouchListener(new CustomOnClick());

        Button exitButton = findViewById(R.id.back_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIfHighScore();
                Intent intent = new Intent(tankMain.this, MainActivity.class);
                startActivity(intent);
            }


        });
    }
    private void saveIfHighScore()
    {
        if(prefs.getInt("tankHighscore",0)<gameView.currentMap+1)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("tankHighscore",gameView.currentMap+1);
            editor.apply();
        }
    }
    public void returnToMenu(View button) {
        finish();
    }
}