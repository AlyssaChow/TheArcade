package com.example.TheArcade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.TheArcade.BrickGame.Brick;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_INVITE = 10;
    private static final String TAG = "hi";
    SnakeEngine snakeEngine;

    BirdActivity gameView;
    Dungeon_menu dungeon;
    tankMain tankmain;
    CookieClicker cookie;
    Brick brick;
    int score = 0;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

// ...

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Get the pixel dimensions of the screen
        Display display = getWindowManager().getDefaultDisplay();
        final Bundle bundle = new Bundle();

        // Initialize the result into a Point object
        Point size = new Point();
        display.getSize(size);

        gameView = new BirdActivity();
        dungeon = new Dungeon_menu();
        // Create a new instance of the SnakeEngine class
        snakeEngine = new SnakeEngine(this, size);
        cookie = new CookieClicker();

        brick = new Brick();
        tankmain = new tankMain();
        // Make snakeEngine the view of the Activity

        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button dungeonButton = findViewById(R.id.button1);
        Button cookieButton = findViewById(R.id.button2);
        Button brickButton = findViewById(R.id.button3);
        Button tankButton = findViewById(R.id.button4);

        Button debug = findViewById(R.id.debugbutton);

        final TextView textDungeon = findViewById(R.id.textDungeon);
        final SharedPreferences prefsDungeon = getSharedPreferences("game", MODE_PRIVATE);
        textDungeon.setText("Highscore: " + prefsDungeon.getInt("dungeonHighscore", 0));

        final TextView textCookie = findViewById(R.id.textCookie);
        final SharedPreferences prefsCookie = getSharedPreferences("game", MODE_PRIVATE);
        textCookie.setText("Highscore: " + prefsCookie.getInt("cookieHighscore", 0));

        final TextView textTank = findViewById(R.id.textTank);
        final SharedPreferences prefsTank = getSharedPreferences("game", MODE_PRIVATE);
        textTank.setText("Highscore: " + prefsTank.getInt("tankHighscore", 0));

        final TextView textBrick = findViewById(R.id.textBrick);
        final SharedPreferences prefsBrick = getSharedPreferences("game", MODE_PRIVATE);
        textBrick.setText("Highscore: " + prefsBrick.getInt("BrickHighscore", 0));




        dungeonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(R.id.button1));
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "fab");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent intent = new Intent(MainActivity.this, Dungeon_menu.class);
                startActivity(intent);
            }
        });
        brickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(R.id.button1));
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "fab");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent intent = new Intent(MainActivity.this, Brick.class);
                startActivity(intent);
            }
        });

        cookieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Write a message to the database
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(R.id.button3));
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "fab3");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent intent = new Intent(MainActivity.this, CookieClicker.class);

                startActivity(intent);
            }


        });
        tankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(R.id.button2));
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "fab2");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent intent = new Intent(MainActivity.this, tankMain.class);
                startActivity(intent);
            }
        });

        debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(prefsBrick,textBrick);
                reset(prefsCookie,textCookie);
                reset(prefsDungeon,textDungeon);
                reset(prefsTank,textTank);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void onResume() {
        super.onResume();
        snakeEngine.resume();


    }

    // Stop the thread in snakeEngine
    @Override
    protected void onPause() {
        super.onPause();
        snakeEngine.pause();


    }


    public void reset(SharedPreferences pref,TextView highscoretxt){
        highscoretxt.setText("Highscore: 0");
        SharedPreferences.Editor preferencesEditor = pref.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

}
