package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class FrLoginLehrer extends Fragment {

    private View view;
    private MainActivity mainActivity;
    private boolean passwortAngezeigt = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_login_lehrer, container, false);
        mainActivity = (MainActivity) getActivity();

        // Deklaration der Views
        final Button btnAnmelden = view.findViewById(R.id.btnAnmelden);
        final ImageButton imgbtnPasswortZeigen = view.findViewById(R.id.imgbtnPasswortZeigen);
        final EditText etPasswortLehrer = view.findViewById(R.id.etPasswortLehrer);

        btnAnmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.loginLehrer();
                btnAnmelden.setFocusable(false);
            }
        });

        imgbtnPasswortZeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwortAngezeigt) {
                    etPasswortLehrer.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwortAngezeigt = false;
                } else {
                    etPasswortLehrer.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwortAngezeigt = true;
                } // if
                etPasswortLehrer.setSelection(etPasswortLehrer.getText().length());
            }
        });

        return view;
    } // Methode onCreateView
}
