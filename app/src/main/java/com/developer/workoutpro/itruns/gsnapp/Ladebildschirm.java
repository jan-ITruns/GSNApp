package com.developer.workoutpro.itruns.gsnapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

public class Ladebildschirm extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private boolean firstLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladebildschirm);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                if (firstLogin) {
                    setContentView(R.layout.activity_login);
                } else {
                    Intent homeIntent = new Intent(Ladebildschirm.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                } // if
            }

        }, SPLASH_TIME_OUT);
    }
}
