package com.example.TheArcade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
public class Background {
    private Bitmap background;
    private int x, y, dx;

    public Background(Bitmap resource)
    {
        background = resource;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, x ,y, null);
        if(x < 0) {
            canvas.drawBitmap(background, x+GameView2.backgroundWidth, y, null);
        }
    }

    public void update() {
        x -= 3;
        if(x < -GameView2.backgroundWidth)
        {
            x = 0;
        }
    }

}
   /* int x = 0, y = 0;
    Bitmap background;

    Background(int screenX, int screenY, Resources res) {

        background = BitmapFactory.decodeResource(res, R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

    }

}*/