package com.developer.workoutpro.itruns.gsnapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FrLehrerliste extends Fragment {

    private View view;

    // Attribute für die Website
    private WebsiteLehrerliste websiteLehrerliste = new WebsiteLehrerliste();
    private String htmlText;
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

        lehrerlisteLaden();

        return view;
    } // Methode onCreateView

    private void lehrerlisteLaden() {
        htmlText = websiteLehrerliste.getHtmlText();
        teil1 = htmlText.split("contenttable-0");
        htmlText = teil1[1];

        loescheKlammern(1);

        while(! htmlText.substring(0, 7).equals("</table>")) {
            for (int index = 0; index < 6; index++) {
                loescheKlammern(2);

                element = htmlText.split("<");
                if (element[0].equals("&nbsp;")) {
                    element[0] = "---";
                } // if

                switch (index) {
                    case 0: kuerzel.add(element[0]); break;
                    case 1: nachname.add(element[0]); break;
                    case 2: vorname.add(element[0]); break;
                    case 3: fach1.add(element[0]); break;
                    case 4: fach2.add(element[0]); break;
                    case 5: fach3.add(element[0]); break;
                } // switch
            } // for
            loescheKlammern(2);
            Log.d("TAG", kuerzel.get(kuerzel.size() - 1));
        } // while

    } // Methode lehrerlisteLaden

    private void loescheKlammern(int anzahl) {
        for (int index = 0; index < anzahl; index++) {
            while (htmlText.charAt(0) != '>') {
                htmlText = htmlText.substring(1);
            } // while
            htmlText = htmlText.substring(1);
        } // for
    } // Methode loesche2Klammern

} // Klasse FrLehrerliste
