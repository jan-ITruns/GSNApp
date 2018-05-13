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

import java.util.ArrayList;

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

    // Attribute für die Lehrer
    private ArrayList<String> kuerzel;
    private ArrayList<String> nachname;
    private ArrayList<String> vorname;
    private ArrayList<String> fach1;
    private ArrayList<String> fach2;
    private ArrayList<String> fach3;

    private String kuerzelStr;
    private String nachnameStr;
    private String vornameStr;
    private String fach1Str;
    private String fach2Str;
    private String fach3Str;


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

    public void setLehrerAttribute(ArrayList<String> kuerzel, ArrayList<String> nachname, ArrayList<String> vorname, ArrayList<String> fach1, ArrayList<String> fach2, ArrayList<String> fach3) {
        this.kuerzel = kuerzel;
        this.nachname = nachname;
        this.vorname = vorname;
        this.fach1 = fach1;
        this.fach2 = fach2;
        this.fach3 = fach3;
        findeRichtigenLehrer();
    } // Methode setLehrerAttribute

    public void findeRichtigenLehrer() {
        for (int index = 0; index < kuerzel.size(); index++) {
            if (vertreter.equals(kuerzel.get(index))) {
                kuerzelStr = kuerzel.get(index);
                nachnameStr = nachname.get(index);
                vornameStr = vorname.get(index);
                fach1Str = fach1.get(index);
                fach2Str = fach2.get(index);
                fach3Str = fach3.get(index);
                return;
            } // if
        } // for
        nachnameStr = "---";
        vornameStr = "---";
        fach1Str = "---";
        fach2Str = "---";
        fach3Str = "---";
    } // Methode findeRichtigenLehrer

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
        TextView tvVertreterName = view.findViewById(R.id.tvVertreterName);
        TextView tvVertreterFaecher = view.findViewById(R.id.tvVertreterFaecher);
        TextView tvInfo = view.findViewById(R.id.tvInfo);

        tvKurs.setText(kurs);
        tvFach.setText(fach);
        tvStunde.setText(stunde);
        tvRaum.setText(raum);
        tvVertreter.setText(vertreter);
        if (vornameStr.equals("---")) {
            tvVertreterName.setText(nachnameStr);
        } else if (nachnameStr.equals("---")) {
            tvVertreterName.setText(vornameStr);
        } else {
            tvVertreterName.setText(vornameStr + " " + nachnameStr);
        } // if
        if (fach2Str.equals("---") && fach3Str.equals("---")) {
            tvVertreterFaecher.setText(fach1Str);
        } else if (fach3Str.equals("---")) {
            tvVertreterFaecher.setText(fach1Str + " | " + fach2Str);
        } else {
            tvVertreterFaecher.setText(fach1Str + " | " + fach2Str + " | " + fach3Str);
        } // if
        tvInfo.setText(info);
    } // Methode informationenEinrichten

} // Klasse FrVertretugnInfos