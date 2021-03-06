package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class FrLehrerliste extends Fragment {

    private View view;
    private boolean aktualisierungLaeuft = false;

    // Attribute für die Website
    private WebsiteLehrerliste websiteLehrerliste;
    private String htmlText;
    private boolean htmlTextEnde = false;
    private String teil1 [];
    private String element [];

    // Attribute für die Lehrer
    private ArrayList<String> kuerzel;
    private ArrayList<String> nachname;
    private ArrayList<String> vorname;
    private ArrayList<String> fach1;
    private ArrayList<String> fach2;
    private ArrayList<String> fach3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fr_lehrerliste, container, false);

        NavigationView navigation = getActivity().findViewById(R.id.nav_view);
        Menu drawer_menu = navigation.getMenu();
        MenuItem menuItem;
        menuItem = drawer_menu.findItem(R.id.nav_lehrerliste);
        if(! menuItem.isChecked()) {
            menuItem.setChecked(true);
        } // if

        toolbarEinrichten();

        recyclerViewVorbereiten();

        swipeToRefresh();

        return view;
    } // Methode onCreateView

    private void toolbarEinrichten() {
        Toolbar toolbar = view.findViewById(R.id.toolbarLehrerliste);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        toolbar.setTitle("Lehrerliste");
        toolbar.setTitleTextColor(getResources().getColor(R.color.weiss));

        toolbar.inflateMenu(R.menu.header_lehrerliste);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.lehrerlisteAktualisieren:
                        if (!aktualisierungLaeuft) {
                            lehrerlisteUpdaten(0);
                            final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
                            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
                            swipeRefreshLayout.setRefreshing(true);
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }, 10000);
                        } // if
                        return true;

                    default:
                        return false;
                }
            }
        });
    } // Methode toolbarEinrichten

    public void setLehrerAttribute(ArrayList<String> kuerzel, ArrayList<String> nachname, ArrayList<String> vorname, ArrayList<String> fach1, ArrayList<String> fach2, ArrayList<String> fach3) {
        this.kuerzel = kuerzel;
        this.nachname = nachname;
        this.vorname = vorname;
        this.fach1 = fach1;
        this.fach2 = fach2;
        this.fach3 = fach3;
    } // Methode setLehrerAttribute

    private void recyclerViewVorbereiten() {
        RecyclerView recyclerView = view.findViewById(R.id.rvLehrerliste);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewLehrerliste adapter = new RecyclerViewLehrerliste(getActivity(), kuerzel, nachname, vorname, fach1, fach2, fach3);
        recyclerView.setAdapter(adapter);
        aktualisierungLaeuft = false;
    } // Methode recyclerViewVorbereiten

    public void lehrerlisteUpdaten(final int tag) {
        if (tag == 0) {
            aktualisierungLaeuft = false;
        } // if
        if (!aktualisierungLaeuft) {
            aktualisierungLaeuft = true;
            websiteLehrerliste = new WebsiteLehrerliste();
            websiteLehrerliste.execute();

            kuerzel = new ArrayList<>();
            nachname = new ArrayList<>();
            vorname = new ArrayList<>();
            fach1 = new ArrayList<>();
            fach2 = new ArrayList<>();
            fach3 = new ArrayList<>();
            htmlTextEnde = false;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    htmlText = websiteLehrerliste.getHtmlText();
                    teil1 = htmlText.split("contenttable-0");
                    htmlText = teil1[1];

                    loescheKlammern(2);

                    while (!htmlText.substring(0, 7).equals("</tbody")) {
                        for (int index = 0; index < 6; index++) {
                            if (!htmlTextEnde) {
                                loesche2KlammernAnfang();
                            } // if

                            if (!htmlTextEnde) {
                                element = htmlText.split("<");
                                if (element[0].equals("&nbsp;")) {
                                    element[0] = "---";
                                } // if

                                switch (index) {
                                    case 0:
                                        kuerzel.add(element[0]);
                                        break;
                                    case 1:
                                        nachname.add(element[0]);
                                        break;
                                    case 2:
                                        vorname.add(element[0]);
                                        break;
                                    case 3:
                                        fach1.add(element[0]);
                                        break;
                                    case 4:
                                        fach2.add(element[0]);
                                        break;
                                    case 5:
                                        fach3.add(element[0]);
                                        break;
                                } // switch
                            } // if
                        } // for
                        if (!htmlTextEnde) {
                            loescheKlammern(2);
                        } // if
                    } // while
                    if (tag == 0) {
                        recyclerViewVorbereiten();
                    } // if

                    setLehrerAttribute();
                }
            }, 10000);
        } // if
    } // Methode lehrerlisteLaden

    private void swipeToRefresh() {
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!aktualisierungLaeuft) {
                    lehrerlisteUpdaten(0);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 10000);
                } // if
            }

        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
    } // Methode swipeToRefresh

    private void loescheKlammern(int anzahl) {
        for (int index = 0; index < anzahl; index++) {
            while (htmlText.charAt(0) != '>') {
                htmlText = htmlText.substring(1);
            } // while
            htmlText = htmlText.substring(1);
        } // for
    } // Methode loescheKlammern

    private void loesche2KlammernAnfang() {
        // dient dem Überprüfen, ob die Tabelle zuende ist
        while (htmlText.charAt(0) != '<') {
            htmlText = htmlText.substring(1);
        } // while

        if (htmlText.substring(0,7).equals("</tbody")) {
            htmlTextEnde = true;
            return;
        } // if

        while (htmlText.charAt(0) != '>') {
            htmlText = htmlText.substring(1);
        } // while
        htmlText = htmlText.substring(1);

        while (htmlText.charAt(0) != '>') {
            htmlText = htmlText.substring(1);
        } // while
        htmlText = htmlText.substring(1);
    } // Methode loesche2KlammernEnde

    private void setLehrerAttribute() {
        MainActivity.setLehrerAttribute(kuerzel, nachname, vorname, fach1, fach2, fach3);
    } // Methode setLehrerAttribute

} // Klasse FrLehrerliste
