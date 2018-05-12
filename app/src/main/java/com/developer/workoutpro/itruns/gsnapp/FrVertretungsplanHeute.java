package com.developer.workoutpro.itruns.gsnapp;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FrVertretungsplanHeute extends Fragment {

    private View view;
    public SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mainActivity;

    private ArrayList<String> kurs;
    private ArrayList<String> stunde;
    private ArrayList<String> vertreter;
    private ArrayList<String> fach;
    private ArrayList<String> raum;
    private ArrayList<String> info;
    private ArrayList<Boolean> ausgewaehlt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_vertretungsplan_heute, container, false);
        mainActivity = (MainActivity) getActivity();

        recyclerViewVorbereiten();
        swipeToRefresh();

        return view;
    } // Methode onCreateView

    public void setVertretungsElemente(ArrayList<String> kurs, ArrayList<String> stunde, ArrayList<String> vertreter, ArrayList<String> fach, ArrayList<String> raum, ArrayList<String> info, ArrayList<Boolean> ausgewaehlt) {
        this.kurs = kurs;
        this.stunde = stunde;
        this.vertreter = vertreter;
        this.fach = fach;
        this.raum = raum;
        this.info = info;
        this.ausgewaehlt = ausgewaehlt;
    } // Methode getVertretungsElemente

    private void recyclerViewVorbereiten() {
        RecyclerView recyclerView = view.findViewById(R.id.rvVertretungsplan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewVertretungsplan adapter = new RecyclerViewVertretungsplan(getActivity(), kurs, stunde, vertreter, fach, raum, info, ausgewaehlt);
        recyclerView.setAdapter(adapter);
    } // Methode recyclerViewVorbereiten

    private void swipeToRefresh() {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainActivity.vertretungsplanAktualisieren(view);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 10000);
            }

        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
    } // Methode swipeToRefresh

} // Klasse FrVertretungsplanHeute
