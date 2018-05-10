package com.developer.workoutpro.itruns.gsnapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Attribute für die Website
    private Website website = new Website();
    private String htmlText;

    // Attribute für den Vertretungsplan
    private String teil1 [];
    private String teil2 [];
    private String datum;
    private String vertretungen;
    private String element [];
    private ArrayList<String> klasse = new ArrayList<>();
    private ArrayList<String> stunde = new ArrayList<>();
    private ArrayList<String> vertreter = new ArrayList<>();
    private ArrayList<String> fach = new ArrayList<>();
    private ArrayList<String> raum = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        website.execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                htmlText = website.getLoggedIn();

                getDatum();
                getVertretungsstunde();

                String ausgabe = datum;
                for (int index = 1; index < klasse.size(); index++) {
                    ausgabe = ausgabe + "\n" + klasse.get(index) + stunde.get(index) + vertreter.get(index) + fach.get(index) + raum.get(index) + text.get(index);
                } // for

                TextView tv = findViewById(R.id.tv);
                tv.setText(ausgabe);
            }
        }, 10000);

    } // Methode onCreate

    private void getDatum() {
        teil1 = htmlText.split("<div class=\"mon_title\">");
        teil2 = teil1[1].split(",");
        datum = teil2[0];

        // Alle Leerstellen vor der ersten Ziffer entfernen
        while (!((int) datum.charAt(0) > 47 && (int) datum.charAt(0) < 58)) {
            datum = datum.substring(1);
        } // while
    } // Methode getDatum

    private void getVertretungsstunde() {
        teil1 = htmlText.split("<table class=\"mon_list\">");
        vertretungen = teil1[1];

        entferne1Klammern();
        entferne2Klammern();

        while (! (vertretungen.substring(0,7).equals("</table"))) {
            for (int index = 0; index < 6; index++) {
                element = vertretungen.split("<");

                // angezeigte Leerzeichen entfernen
                if (element[0].equals("&nbsp;")) {
                    element[0] = "";
                } // if

                switch (index) {
                    case 0: klasse.add(element[0]); break;
                    case 1: stunde.add(element[0]); break;
                    case 2: vertreter.add(element[0]); break;
                    case 3: fach.add(element[0]); break;
                    case 4: raum.add(element[0]); break;
                    case 5: text.add(element[0]); break;
                } // switch

                entferne2Klammern();
            } // for
            entferne2KlammernEnde();
        } // while

    } // Methode getVertretungsstunde

    private void entferne2Klammern() {
        while (vertretungen.charAt(0) != '>') {
            vertretungen = vertretungen.substring(1);
        } // while
        vertretungen = vertretungen.substring(1);
        while (vertretungen.charAt(0) != '>') {
            vertretungen = vertretungen.substring(1);
        } // while
        vertretungen = vertretungen.substring(1);
    } // Methode enferne2Klammern

    private void entferne2KlammernEnde() {
        while (vertretungen.charAt(0) != '>') {
            vertretungen = vertretungen.substring(1);
        } // while
        vertretungen = vertretungen.substring(1);

        // dient dem Überprüfen, ob die Tabelle zuende ist
        while (vertretungen.charAt(0) != '<') {
            vertretungen = vertretungen.substring(1);
        } // while
        if (vertretungen.substring(0, 7).equals("</table")) {
            return;
        } // if

        while (vertretungen.charAt(0) != '>') {
            vertretungen = vertretungen.substring(1);
        } // while
        vertretungen = vertretungen.substring(1);
    } // Methode enferne2KlammernEnde

    private void entferne1Klammern() {
        while (vertretungen.charAt(0) != '>') {
            vertretungen = vertretungen.substring(1);
        } // while
        vertretungen = vertretungen.substring(1);
    } // Methode enferne1Klammern

}