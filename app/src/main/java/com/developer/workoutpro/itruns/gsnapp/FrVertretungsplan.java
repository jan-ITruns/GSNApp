package com.developer.workoutpro.itruns.gsnapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class FrVertretungsplan extends Fragment {

    private View view;

    private ArrayList<String> kurs;
    private ArrayList<String> stunde;
    private ArrayList<String> vertreter;
    private ArrayList<String> fach;
    private ArrayList<String> raum;
    private ArrayList<String> info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_vertretungsplan, container, false);

        recyclerViewVorbereiten();

        return view;
    } // Methode onCreateView

    public void setVertretungsElemente(ArrayList<String> kurs, ArrayList<String> stunde, ArrayList<String> vertreter, ArrayList<String> fach, ArrayList<String> raum, ArrayList<String> info) {
        this.kurs = kurs;
        this.stunde = stunde;
        this.vertreter = vertreter;
        this.fach = fach;
        this.raum = raum;
        this.info = info;
    } // Methode getVertretungsElemente

    private void recyclerViewVorbereiten() {
        RecyclerView recyclerView = view.findViewById(R.id.rvVertretungsplan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewVertretungsplan adapter = new RecyclerViewVertretungsplan(getActivity(), kurs, stunde, vertreter, fach, raum, info);
        recyclerView.setAdapter(adapter);
    } // Methode recyclerViewVorbereiten

} // Klasse FrVertretungsplan
