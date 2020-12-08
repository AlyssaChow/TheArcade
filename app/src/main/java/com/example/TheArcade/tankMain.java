package com.example.TheArcade;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.TheArcade.BrickGame.Brick;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tankMain extends AppCompatActivity {
    private SurfaceHolder surfaceHolder;
    public TankGameView gameView;
    public  TankGameView game;
    private SharedPreferences prefs;
    private int lvls;
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

        //findViewById(R.id.back_button).setOnTouchListener(new CustomOnClick());

        Button exitButton = findViewById(R.id.back_button);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("DEBUG3", "HEYB");
                Log.d("DEBUG3", "HEY");
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference ref = database.getReference("TankHighscore");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lvls = snapshot.getValue(int.class);
                        Log.d("DEBUG3:", "grab " + lvls+"");
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("tankHighscore",lvls);
                        editor.apply();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("TESTING:", "oop");
                    }
                });
                Log.d("DEBUG3", "HEYA");
                Intent intent = new Intent(tankMain.this, MainActivity.class);
                startActivity(intent);
            }


        });
    }

    public void returnToMenu(View button) {
        finish();
    }
}