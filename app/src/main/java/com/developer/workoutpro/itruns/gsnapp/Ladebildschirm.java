package com.developer.workoutpro.itruns.gsnapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class Ladebildschirm extends AppCompatActivity {

    private static int Ladedauer = 5000;
    private ImageView imgvLadebalken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladebildschirm);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ladebalken);
        imgvLadebalken = findViewById(R.id.imgvLadebalken);
        imgvLadebalken.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Ladebildschirm.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }

        }, Ladedauer);

    } // Methode onCreate

} // Klasse Ladebildschirm

