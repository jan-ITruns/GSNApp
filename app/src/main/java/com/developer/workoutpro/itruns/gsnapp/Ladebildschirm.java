package com.developer.workoutpro.itruns.gsnapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Ladebildschirm extends AppCompatActivity {

    private static int Ladedauer = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladebildschirm);

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

