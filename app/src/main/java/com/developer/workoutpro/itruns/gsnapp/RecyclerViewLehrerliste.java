package com.developer.workoutpro.itruns.gsnapp;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewLehrerliste extends RecyclerView.Adapter<RecyclerViewLehrerliste.ViewHolder>  {

    private MainActivity mainActivity;
    private ArrayList<String> kuerzel;
    private ArrayList<String> nachname;
    private ArrayList<String> vorname;
    private ArrayList<String> fach1;
    private ArrayList<String> fach2;
    private ArrayList<String> fach3;

    public RecyclerViewLehrerliste(Context context, ArrayList<String> kuerzel, ArrayList<String> nachname, ArrayList<String> vorname, ArrayList<String> fach1, ArrayList<String> fach2, ArrayList<String> fach3) {
        mainActivity = (MainActivity) context;
        this.kuerzel = kuerzel;
        this.nachname = nachname;
        this.vorname = vorname;
        this.fach1 = fach1;
        this.fach2 = fach2;
        this.fach3 = fach3;
    } // Konstruktor RecyclerViewVertretungsplan

    @Override
    public RecyclerViewLehrerliste.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fr_lehrerliste_row, viewGroup, false);

        return new RecyclerViewLehrerliste.ViewHolder(view);
    } // Konstruktor RecyclerViewVertretungsplan

    @Override
    public void onBindViewHolder(RecyclerViewLehrerliste.ViewHolder viewHolder, final int i) {
        viewHolder.tvKuerzel.setText(kuerzel.get(i));
        viewHolder.tvNachname.setText(nachname.get(i));
        viewHolder.tvVorname.setText(vorname.get(i));
        viewHolder.tvFaecher.setText(fach1.get(i) + "\n" + fach2.get(i) + "\n" + fach3.get(i));
    } // Methode onBindViewHolder

    @Override
    public int getItemCount() {
        if (kuerzel != null) {
            return kuerzel.size();
        } else {
            return 0;
        } // if
    } // Methode getItemCount

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout clLehrerlisteRow;
        TextView tvKuerzel;
        TextView tvNachname;
        TextView tvVorname;
        TextView tvFaecher;

        public ViewHolder(View view) {
            super(view);
            clLehrerlisteRow = view.findViewById(R.id.ConstraintLayout);
            tvKuerzel = view.findViewById(R.id.tvKuerzel);
            tvNachname = view.findViewById(R.id.tvNachname);
            tvVorname = view.findViewById(R.id.tvVorname);
            tvFaecher = view.findViewById(R.id.tvFaecher);
        } // Konstruktor ViewHolder

    } // Klasse ViewHolder

} // Klasse RecyclerViewLehrerliste
