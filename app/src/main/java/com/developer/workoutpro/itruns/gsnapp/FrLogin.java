package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FrLogin extends Fragment{

    private View view;
    private ViewPager viewPager;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_login, container, false);
        mainActivity = (MainActivity) getActivity();

        viewPager = view.findViewById(R.id.containerLogin);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabsLogin);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    } // Methode onCreateView

    private void setupViewPager(ViewPager pViewPager) {

        TabViewLogin adapter = new TabViewLogin(getChildFragmentManager());

        // Schueler-Login hinzufügen
        FrLoginSchueler frLoginSchueler = new FrLoginSchueler();
        adapter.addFragment(frLoginSchueler, "Schüler");

        // Lehrer-Login hinzufügen
        FrLoginLehrer frLoginLehrer = new FrLoginLehrer();
        adapter.addFragment(frLoginLehrer, "Lehrer");

        pViewPager.setAdapter(adapter);
    } // Methode setupViewPager
}
