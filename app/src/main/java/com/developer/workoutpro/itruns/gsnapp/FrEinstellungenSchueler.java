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
import android.widget.Switch;

public class FrEinstellungenSchueler extends Fragment {

    private View view;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_einstellungen_schueler, container, false);
        mainActivity = (MainActivity)getActivity();
        toolbarEinrichten();

        //Benachrichtigungseinstellugen prüfen
        Switch switchVertretung = view.findViewById(R.id.switchBenachrichtigungenVertretung);
        Switch switchAktuelles = view.findViewById(R.id.switchBenachrichtigungAktuelles);
        Switch switchTermine = view.findViewById(R.id.switchBenachrichtigungTermine);
        Switch switchBewertung = view.findViewById(R.id.switchBenachrichtigungBewertung);

        switchVertretung.setChecked(mainActivity.benachrichtigungVertretung);
        switchAktuelles.setChecked(mainActivity.benachrichtigungAktuelles);
        switchTermine.setChecked(mainActivity.benachrichtigungTermine);
        switchBewertung.setChecked(mainActivity.benachrichtigungBewertung);

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
