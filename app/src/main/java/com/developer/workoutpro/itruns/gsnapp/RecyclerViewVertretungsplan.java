package com.developer.workoutpro.itruns.gsnapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.developer.itruns.gsnapp.R;
import java.util.ArrayList;

public class RecyclerViewVertretungsplan extends RecyclerView.Adapter {

    private ArrayList<String> kurs = new ArrayList<>();
    private ArrayList<String> stunde = new ArrayList<>();
    private ArrayList<String> vertreter = new ArrayList<>();
    private ArrayList<String> fach = new ArrayList<>();
    private ArrayList<String> raum = new ArrayList<>();
    private ArrayList<String> info = new ArrayList<>();

    @Override
    public RecyclerViewVertretungsplan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fr_vertretungsplan_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout clVertretungsplanRow;
        TextView tvKurs;
        TextView tvFach;
        TextView tvStunde;
        TextView tvVertreter;
        TextView tvRaum;
        TextView tvInfo;

        public ViewHolder(View view) {
            super(view);
            clVertretungsplanRow = view.findViewById(R.id.clVertretungsplanRow);
            tvKurs = view.findViewById(R.id.tvKurs);
            tvFach = view.findViewById(R.id.tvFach);
            tvStunde = view.findViewById(R.id.tvStunde);
            tvVertreter = view.findViewById(R.id.tvVertreter);
            tvRaum = view.findViewById(R.id.tvRaum);
            tvInfo = view.findViewById(R.id.tvInfo);
        }
    }
}
