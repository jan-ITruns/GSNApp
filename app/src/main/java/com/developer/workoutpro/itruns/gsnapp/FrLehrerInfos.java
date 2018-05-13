package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FrLehrerInfos extends Fragment{

    private View view;
    private MainActivity mainActivity;

    // Informationen
    private int index;
    private String vorname;
    private String nachname;
    private String kuerzel;
    private String email;
    private String fach1;
    private String fach2;
    private String fach3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_lehrer_infos, container, false);
        mainActivity = (MainActivity) getActivity();

        toolbarEinrichten();
        informationenEinrichten();

        return view;
    } // Methode onCreate View

    public void setzeLehrerInformationen(String vorname, String nachname, String kuerzel, String fach1, String fach2, String fach3) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.kuerzel = kuerzel;
        this.fach1 = fach1;
        this.fach2 = fach2;
        this.fach3 = fach3;
    } // Methode setzeVertretungsInformationen

    private void toolbarEinrichten() {
        // Deklaration der Views
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        AppBarLayout appBarLayout = view.findViewById(R.id.appBarLayout);
        Toolbar toolbarVertretungsplanInfo = view.findViewById(R.id.toolbarVertretungsplanInfo);

        collapsingToolbarLayout.setTitle("Lehrer");

        toolbarVertretungsplanInfo.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarVertretungsplanInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.lehrerlisteOeffnen();
            }
        });

    } // Methode toolbarEinrichten

    private void informationenEinrichten() {
        TextView tvLehrerName = view.findViewById(R.id.tvLehrerName);
        TextView tvKuerzel = view.findViewById(R.id.tvLehrerKuerzel);
        TextView tvEmail = view.findViewById(R.id.tvLehrerEmailAdresse);
        TextView tvFach1 = view.findViewById(R.id.tvLehrerFaecher1);
        TextView tvFach2 = view.findViewById(R.id.tvLehrerFaecher2);
        TextView tvFach3 = view.findViewById(R.id.tvLehrerFaecher3);

        tvLehrerName.setText(vorname + " " + nachname);
        tvKuerzel.setText(kuerzel);
        tvEmail.setText(mainActivity.lehrerEmail);
        tvFach1.setText(fach1);
        tvFach2.setText(fach2);
        tvFach3.setText(fach3);
    } // Methode informationenEinrichten


} // Klasse FrLehrerInfos