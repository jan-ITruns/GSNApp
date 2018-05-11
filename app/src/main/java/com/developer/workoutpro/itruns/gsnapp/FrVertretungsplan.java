package com.developer.workoutpro.itruns.gsnapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.itruns.gsnapp.R;

public class FrVertretungsplan extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_vertretungsplan, container, false);

        return view;
    } // Methode onCreateView

    private void recyclerViewVorbereiten() {
        RecyclerView recyclerView = view.findViewById(R.id.rvVertretungsplan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SwipeRecyclerViewAdapterWorkoutAnsicht(getActivity(), mName, mMuskelgruppe, mBeschreibung, mMinuten, mSekunden, this, btnsdraggen);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
    }

} // Klasse FrVertretungsplan
