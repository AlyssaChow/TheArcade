package com.example.TheArcade;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


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
        ImageButton fab10 = findViewById(R.id.imageButton);
        Button cookieButton = findViewById(R.id.button2);
        Button brickButton = findViewById(R.id.button3);
        Button tankButton = findViewById(R.id.button4);
        FloatingActionButton fab5 = findViewById(R.id.floatingActionButton2);

        dungeonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Dungeon_menu.class);
                startActivity(intent);
            }
        });
        brickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Brick.class);
                startActivity(intent);
            }
        });

        cookieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Write a message to the database
                /*
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(R.id.button2));
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "fab2");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                 */
                Intent intent = new Intent(MainActivity.this, CookieClicker.class);

                startActivity(intent);
            }


        });
        tankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, tankMain.class);
                startActivity(intent);
            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                startActivity(intent);
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


}
