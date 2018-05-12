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

public class FrVertretungInfos extends Fragment {

    private View view;
    private MainActivity mainActivity;

    // Informationen
    private int index;
    private String kurs;
    private String stunde;
    private String vertreter;
    private String fach;
    private String raum;
    private String info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_vertretung_infos, container, false);
        mainActivity = (MainActivity) getActivity();

        toolbarEinrichten();
        informationenEinrichten();

        return view;
    } // Methode onCreate View

    public void setzeVertretungsInformationen(String kurs, String stunde, String vertreter, String fach, String raum, String info) {
        this.kurs = kurs;
        this.stunde = stunde;
        this.vertreter = vertreter;
        this.fach = fach;
        this.raum = raum;
        this.info = info;
    } // Methode setzeVertretungsInformationen

    private void toolbarEinrichten() {
        // Deklaration der Views
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        AppBarLayout appBarLayout = view.findViewById(R.id.appBarLayout);
        Toolbar toolbarVertretungsplanInfo = view.findViewById(R.id.toolbarVertretungsplanInfo);

        collapsingToolbarLayout.setTitle("Vertretung");

        toolbarVertretungsplanInfo.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbarVertretungsplanInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.vertretungsplanOeffnen();
            }
        });

    } // Methode toolbarEinrichten

    private void informationenEinrichten() {
        TextView tvKurs = view.findViewById(R.id.tvKurs);
        TextView tvFach = view.findViewById(R.id.tvFach);
        TextView tvStunde = view.findViewById(R.id.tvStunde);
        TextView tvRaum = view.findViewById(R.id.tvRaum);
        TextView tvVertreter = view.findViewById(R.id.tvVertreter);
        TextView tvInfo = view.findViewById(R.id.tvInfo);

        tvKurs.setText(kurs);
        tvFach.setText(fach);
        tvStunde.setText(stunde);
        tvRaum.setText(raum);
        tvVertreter.setText(vertreter);
        tvInfo.setText(info);
    } // Methode informationenEinrichten

} // Klasse FrVertretugnInfos