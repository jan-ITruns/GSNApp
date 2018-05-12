package com.developer.workoutpro.itruns.gsnapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FrVertretungsplan extends Fragment {

    private View view;
    private ViewPager viewPager;
    private MainActivity mainActivity;

    // Attribute für den Vertretungsplan Heute
    private String datumHeute;
    private ArrayList<String> kursHeute;
    private ArrayList<String> stundeHeute;
    private ArrayList<String> vertreterHeute;
    private ArrayList<String> fachHeute;
    private ArrayList<String> raumHeute;
    private ArrayList<String> infoHeute;

    // Attribute für den Vertretungsplan Morgen
    private String datumMorgen;
    private ArrayList<String> kursMorgen;
    private ArrayList<String> stundeMorgen;
    private ArrayList<String> vertreterMorgen;
    private ArrayList<String> fachMorgen;
    private ArrayList<String> raumMorgen;
    private ArrayList<String> infoMorgen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_vertretungsplan, container, false);
        mainActivity = (MainActivity) getActivity();

        toolbarEinrichten();

        viewPager = view.findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    } // Methode onCreateView

    private void toolbarEinrichten() {
        Toolbar toolbar = view.findViewById(R.id.toolbarVertretungsplan);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        toolbar.setTitle("Vertretungsplan");
        toolbar.setTitleTextColor(getResources().getColor(R.color.weiss));

        toolbar.inflateMenu(R.menu.header_vertretungsplan);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.vertretungsplanAktualisieren:
                        mainActivity.vertretungsplanAktualisieren(view);
                        return true;

                    case R.id.vertretungsplanInformation:
                        Toast.makeText(getActivity(), mainActivity.stand, Toast.LENGTH_LONG).show();
                        return true;

                    default:
                        return false;
                }
            }
        });
    } // Methode toolbarEinrichten

    public void setVertretungsElemente(int tag, String datum, ArrayList<String> kurs, ArrayList<String> stunde, ArrayList<String> vertreter, ArrayList<String> fach, ArrayList<String> raum, ArrayList<String> info) {
        switch (tag) {
            case 0:
                datumHeute = datum;
                kursHeute = kurs;
                stundeHeute = stunde;
                vertreterHeute = vertreter;
                fachHeute = fach;
                raumHeute = raum;
                infoHeute = info;
                break;
            case 1:
                datumMorgen = datum;
                kursMorgen = kurs;
                stundeMorgen = stunde;
                vertreterMorgen = vertreter;
                fachMorgen = fach;
                raumMorgen = raum;
                infoMorgen = info;
                break;
        } // switch
    } // Methode getVertretungsElemente

    private void setupViewPager(ViewPager pViewPager) {
        TabViewVertretungsplan adapter = new TabViewVertretungsplan(getChildFragmentManager());

        // Vertretungsplan Heute hinzufügen
        FrVertretungsplanHeute frVertretungsplanHeute = new FrVertretungsplanHeute();
        frVertretungsplanHeute.setVertretungsElemente(kursHeute, stundeHeute, vertreterHeute, fachHeute, raumHeute, infoHeute);
        adapter.addFragment(frVertretungsplanHeute, datumHeute);

        // Vertretungsplan Morgen hinzufügen
        FrVertretungsplanMorgen frVertretungsplanMorgen = new FrVertretungsplanMorgen();
        frVertretungsplanMorgen.setVertretungsElemente(kursMorgen, stundeMorgen, vertreterMorgen, fachMorgen, raumMorgen, infoMorgen);
        adapter.addFragment(frVertretungsplanMorgen, datumMorgen);

        pViewPager.setAdapter(adapter);
    } // Methode setupViewPager

} // Klasse FrVertretungsplan
