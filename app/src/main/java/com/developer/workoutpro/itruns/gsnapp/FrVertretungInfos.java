package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FrVertretungInfos extends Fragment {

    private View view;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_vertretung_infos, container, false);
        mainActivity = (MainActivity) getActivity();

        toolbarEinrichten();

        ImageView imgvGSN = view.findViewById(R.id.imgvGSN);
        imgvGSN.setImageResource(R.drawable.gsn_klein);

        return view;
    } // Methode onCreate View

    private void toolbarEinrichten() {
        Toolbar toolbar = view.findViewById(R.id.toolbarVertretungsplanInfos);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.vertretungsplanOeffnen();
            }
        });

    } // Methode toolbarEinrichten

} // Klasse FrVertretugnInfos
