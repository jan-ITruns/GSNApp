package com.developer.workoutpro.itruns.gsnapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewVertretungsplan extends RecyclerView.Adapter<RecyclerViewVertretungsplan.ViewHolder> {

    private ArrayList<String> kurs;
    private ArrayList<String> stunde;
    private ArrayList<String> vertreter;
    private ArrayList<String> fach;
    private ArrayList<String> raum;
    private ArrayList<String> info;

    public RecyclerViewVertretungsplan(ArrayList<String> kurs, ArrayList<String> stunde, ArrayList<String> vertreter, ArrayList<String> fach, ArrayList<String> raum, ArrayList<String> info) {
        this.kurs = kurs;
        this.stunde = stunde;
        this.vertreter = vertreter;
        this.fach = fach;
        this.raum = raum;
        this.info = info;
    } // Konstruktor RecyclerViewVertretungsplan

    @Override
    public RecyclerViewVertretungsplan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fr_vertretung_row, viewGroup, false);

        return new ViewHolder(view);
    } // Konstruktor RecyclerViewVertretungsplan



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (kurs.get(i).length() > 0) {
            if (kurs.get(i).charAt(0) == 'E' || kurs.get(i).charAt(0) == 'Q') {
                viewHolder.tvKurs.setText(fach.get(i));
            } else {
                viewHolder.tvKurs.setText(kurs.get(i) + " | " + fach.get(i));
            } // if
        } else {
            viewHolder.tvKurs.setText(kurs.get(i) + " | " + fach.get(i));
        } // if
        viewHolder.tvStunde.setText(stunde.get(i));
        viewHolder.tvVertreter.setText(vertreter.get(i));
        viewHolder.tvRaum.setText(raum.get(i));
        viewHolder.tvInfo.setText(info.get(i));
    } // Methode onBindViewHolder

    @Override
    public int getItemCount() {
        if (kurs != null) {
            return kurs.size();
        } else {
            return 0;
        } // if
    } // Methode getItemCount

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout clVertretungsplanRow;
        TextView tvKurs;
        TextView tvStunde;
        TextView tvVertreter;
        TextView tvRaum;
        TextView tvInfo;

        public ViewHolder(View view) {
            super(view);
            clVertretungsplanRow = view.findViewById(R.id.clItemHolder);
            tvKurs = view.findViewById(R.id.tvKurs);
            tvStunde = view.findViewById(R.id.tvStunde);
            tvVertreter = view.findViewById(R.id.tvVertreter);
            tvRaum = view.findViewById(R.id.tvRaum);
            tvInfo = view.findViewById(R.id.tvInfo);
        } // Konstruktor ViewHolder

    } // Klasse ViewHolder

} // Klasse RecyclerViewVertretungsplan
