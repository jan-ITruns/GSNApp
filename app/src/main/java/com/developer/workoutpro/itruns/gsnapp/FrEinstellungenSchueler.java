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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class FrEinstellungenSchueler extends Fragment {

    private View view;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_einstellungen_schueler, container, false);
        mainActivity = (MainActivity)getActivity();
        toolbarEinrichten();

        benachrichtigungen();

        return view;
    } // Methode onCreateView

    private void benachrichtigungen(){
        //Benachrichtigungseinstellugen swiches deklarieren
        Switch switchVertretung = view.findViewById(R.id.switchBenachrichtigungenVertretung);
        Switch switchAktuelles = view.findViewById(R.id.switchBenachrichtigungAktuelles);
        Switch switchTermine = view.findViewById(R.id.switchBenachrichtigungTermine);
        Switch switchBewertung = view.findViewById(R.id.switchBenachrichtigungBewertung);

        //Benachrichtigungseinstellugen prüfen
        switchVertretung.setChecked(mainActivity.benachrichtigungVertretung);
        switchAktuelles.setChecked(mainActivity.benachrichtigungAktuelles);
        switchTermine.setChecked(mainActivity.benachrichtigungTermine);
        switchBewertung.setChecked(mainActivity.benachrichtigungBewertung);

        //OnCheckListener setzen
        switchVertretung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mainActivity.benachrichtigungVertretung=true;
                    Toast.makeText(getActivity(), "Benachrichtigungen über Vertretungen erhalten.", Toast.LENGTH_LONG).show();
                }
                else{
                    mainActivity.benachrichtigungVertretung=false;
                    Toast.makeText(getActivity(), "Keine Benachrichtigungen über Vertretungen erhalten.", Toast.LENGTH_LONG).show();
                }
            }
        });

        switchAktuelles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mainActivity.benachrichtigungAktuelles=true;
                    Toast.makeText(getActivity(), "Benachrichtigungen über Aktuelles erhalten.", Toast.LENGTH_LONG).show();
                }
                else{
                    mainActivity.benachrichtigungAktuelles=false;
                    Toast.makeText(getActivity(), "Keine Benachrichtigungen über Aktuelles erhalten.", Toast.LENGTH_LONG).show();
                }
            }
        });

        switchTermine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mainActivity.benachrichtigungTermine=true;
                    Toast.makeText(getActivity(), "Benachrichtigungen über Termine erhalten.", Toast.LENGTH_LONG).show();
                }
                else{
                    mainActivity.benachrichtigungTermine=false;
                    Toast.makeText(getActivity(), "Keine Benachrichtigungen über Termine erhalten.", Toast.LENGTH_LONG).show();
                }
            }
        });

        switchBewertung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mainActivity.benachrichtigungBewertung=true;
                    Toast.makeText(getActivity(), "Benachrichtigungen über Bewertungen erhalten.", Toast.LENGTH_LONG).show();
                }
                else{
                    mainActivity.benachrichtigungBewertung=false;
                    Toast.makeText(getActivity(), "Keine Benachrichtigungen über Bewertungen erhalten.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

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
