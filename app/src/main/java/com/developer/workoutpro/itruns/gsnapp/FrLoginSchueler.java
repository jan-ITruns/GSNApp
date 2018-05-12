package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class FrLoginSchueler extends Fragment{

    private View view;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_login_schueler, container, false);
        mainActivity = (MainActivity) getActivity();

        // Deklaration der Views
        final Button btnAnmelden = view.findViewById(R.id.btnAnmelden);

        btnAnmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.login();
                btnAnmelden.setFocusable(false);
            }
        });

        return view;
    } // Methode onCreateView
}
