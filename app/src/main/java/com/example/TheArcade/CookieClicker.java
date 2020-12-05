package com.example.TheArcade;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TheArcade.MainActivity;
import com.example.TheArcade.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CookieClicker extends AppCompatActivity implements View.OnClickListener {
    private double numClicks = 0;
    private double grandmaPrice = 100;
    private double ovenPrice = 1000;
    private int grandmaLevel = 0;
    private int ovenLevel = 0;
    private double cookiesPerSecond = 0;
    private String s1,s2, s3, s4, s5, s6;
    public TextView cookiesCollected, cps, gLevel,gCost,oLevel,oCost;
    private ImageView cookie;
    Timer timer;
    private Random random;
    Button backButton;
    MediaPlayer crunchSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cookie_activity);
        cookiesCollected = findViewById(R.id.cookiesCollected);
        cps = findViewById(R.id.cps);
        cookie = findViewById(R.id.imgCookie);
        gLevel = findViewById(R.id.grandmaLevel);
        gCost = findViewById(R.id.grandmaCost);
        oLevel = findViewById(R.id.ovenLevel);
        oCost =  findViewById(R.id.ovenCost);
        backButton = findViewById(R.id.button);
        random = new Random();
        crunchSound = MediaPlayer.create(this, R.raw.crunch);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.imgCookie) {
            crunchSound.start();
            Animation a = AnimationUtils.loadAnimation(this, R.anim.cookie_anim);
            a.setAnimationListener(new CookieAnimation()
            {
                @Override
                public void onAnimationEnd(Animation animation)
                {
                    clickOnCookie();
                }
            });
            view.startAnimation(a);

        }

        if(view.getId() == R.id.grandma)
        {
            if (numClicks >= grandmaPrice) {
                numClicks = numClicks - grandmaPrice;
                cookiesPerSecond = cookiesPerSecond + 0.1;
                s2 = String.format("%.1f Per Second", cookiesPerSecond);
                cps.setText(s2);
                setCps();

                grandmaLevel += 1;
                s3 = String.format("Level %d", grandmaLevel);
                gLevel.setText(s3);

                grandmaPrice += 200;
                s4 = String.format("Costs %d Cookies!", (int)grandmaPrice);
                gCost.setText(s4);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "You do not have enough money to level up Grandma",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        }

        if(view.getId() == R.id.oven)
        {
            if (numClicks >= ovenPrice)
            {
                    numClicks = numClicks - ovenPrice;
                    cookiesPerSecond = cookiesPerSecond + 0;
                    s2 = String.format("%.1f Per Second", cookiesPerSecond);
                    cps.setText(s2);
                    setCps();

                    ovenLevel += 1;
                    s5 = String.format("Level %d", ovenLevel);
                    oLevel.setText(s5);

                    ovenPrice += 500;
                    s6 = String.format("Costs %d Cookies!", (int)ovenPrice);
                    oCost.setText(s6);
            } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You do not have enough money to level up Oven",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
        }
    }


        public void onClick2(View view)
        {
            if(view.getId() == R.id.button) {
                Intent intent = new Intent(CookieClicker.this, MainActivity.class);
                startActivity(intent);
            }
        }

        private void clickOnCookie() {
            numClicks= numClicks + 1;
            cookiesCollected.setText(String.format("%.0f Cookies!", numClicks));
            popup(R.string.clicksValue);

    }

    class updateCps extends TimerTask
    {
        public void run () {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    numClicks += cookiesPerSecond;
                    s1 = String.format("%.0f Cookies!", numClicks);
                    cookiesCollected.setText(s1);
                }
            });
        }
    }

    private void setCps()
    {
        timer = new Timer();
        timer.schedule(new updateCps(), 0 , 1100);
    }
    private void popup(int num) { //this shows the popup for the +1 toast every time a user clicks on the cookie
        final Toast clicksAdded = new Toast(this);
        clicksAdded.setGravity(Gravity.CENTER, random.nextInt(500)-200, random.nextInt(600)-550);
        clicksAdded.setDuration(clicksAdded.LENGTH_SHORT);
        TextView text = new TextView(this);
        text.setText(num);
        textViewEdit(text);
        clicksAdded.setView(text);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                if (System.currentTimeMillis() - startTime > 0.001 * 1000) {
                    clicksAdded.cancel();
                    timer.cancel();
                } else {
                    clicksAdded.show();
                }
            }
        }, 100, 10);
        clicksAdded.show();

    }

    private TextView textViewEdit(TextView t) {
        t.setTextSize(40f);
        t.setTextColor(Color.WHITE);
        return t;
    }
}


