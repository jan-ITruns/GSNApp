package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class FrLoginSchueler extends Fragment{

    private View view;
    private MainActivity mainActivity;
    private boolean passwortAngezeigt = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_login_schueler, container, false);
        mainActivity = (MainActivity) getActivity();

        // Deklaration der Views
        final Button btnAnmelden = view.findViewById(R.id.btnAnmelden);
        final Button btnJgst5 = view.findViewById(R.id.btnJgs5);
        final Button btnJgst6 = view.findViewById(R.id.btnJgs6);
        final Button btnJgst7 = view.findViewById(R.id.btnJgs7);
        final Button btnJgst8 = view.findViewById(R.id.btnJgs8);
        final Button btnJgst9 = view.findViewById(R.id.btnJgs9);
        final Button btnJgst10 = view.findViewById(R.id.btnJgs10);
        final Button btnJgst11 = view.findViewById(R.id.btnJgs11);
        final Button btnJgst12 = view.findViewById(R.id.btnJgs12);
        final ImageButton imgbtnPasswortZeigen = view.findViewById(R.id.imgbtnPasswortZeigen);
        final EditText etPasswortSchueler = view.findViewById(R.id.etPasswortSchueler);

        btnJgst5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst5.setFocusable(true);
                mainActivity.setBackgroundColor(5);
                mainActivity.jahrgangsstufe = 5;
            }
        });

        btnJgst6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst6.setFocusable(true);
                mainActivity.setBackgroundColor(6);
                mainActivity.jahrgangsstufe = 6;
            }
        });

        btnJgst7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst7.setFocusable(true);
                mainActivity.setBackgroundColor(7);
                mainActivity.jahrgangsstufe = 7;
            }
        });

        btnJgst8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst8.setFocusable(true);
                mainActivity.setBackgroundColor(8);
                mainActivity.jahrgangsstufe = 8;
            }
        });

        btnJgst9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst9.setFocusable(true);
                mainActivity.setBackgroundColor(9);
                mainActivity.jahrgangsstufe = 9;
            }
        });

        btnJgst10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst10.setFocusable(true);
                mainActivity.setBackgroundColor(10);
                mainActivity.jahrgangsstufe = 10;
            }
        });

        btnJgst11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst11.setFocusable(true);
                mainActivity.setBackgroundColor(11);
                mainActivity.jahrgangsstufe = 11;
            }
        });

        btnJgst12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJgst12.setFocusable(true);
                mainActivity.setBackgroundColor(12);
                mainActivity.jahrgangsstufe = 12;
            }
        });

        btnAnmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.loginSchueler();
                btnAnmelden.setFocusable(false);
            }
        });

        imgbtnPasswortZeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwortAngezeigt) {
                    etPasswortSchueler.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwortAngezeigt = false;
                } else {
                    etPasswortSchueler.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwortAngezeigt = true;
                } // if
                etPasswortSchueler.setSelection(etPasswortSchueler.getText().length());
            }
        });

        return view;
    } // Methode onCreateView

}
