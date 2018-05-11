package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FrEinstellungenLehrer extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_einstellungen_lehrer, container, false);

        toolbarEinrichten();

        return view;
    } // Methode onCreateView

    private void toolbarEinrichten() {
        Toolbar toolbar = view.findViewById(R.id.toolbarEinstellungen);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        toolbar.setTitle("Einstellungen");
        toolbar.setTitleTextColor(getResources().getColor(R.color.weiss));
    } // Methode toolbarEinrichten

} // Klasse FrEinstellungenSchueler
