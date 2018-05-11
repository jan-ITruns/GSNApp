package com.developer.workoutpro.itruns.gsnapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Attribute für die Einstellungen
    private boolean erstesLogin;

    // Attribute für die Anmeldedaten
    private String benutzername;
    private String passwort;

    // Attribute für die Website
    private Website website;
    private String htmlText;

    // Attribute für den Vertretungsplan
    private String teil1 [];
    private String teil2 [];
    private String datum;
    private String vertretungen;
    private String element [];
    private ArrayList<String> kurs;
    private ArrayList<String> stunde;
    private ArrayList<String> vertreter;
    private ArrayList<String> fach;
    private ArrayList<String> raum;
    private ArrayList<String> info;
    private String vertretungsplanAusgabe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesLaden();

        if (erstesLogin) {
            setContentView(R.layout.activity_login);

            // Deklaration der Views
            final Button btnAnmelden = findViewById(R.id.btnAnmelden);

            btnAnmelden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                    btnAnmelden.setFocusable(false);
                }
            });

        } else {
            setContentView(R.layout.activity_main);
            vertretungsplanAusgeben();
        } // if

    } // Methode onCreate

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferencesSpeichern();
    } // Methode onPause

    private void sharedPreferencesLaden() {
        // Laden, ob sich der Benutzer schon einmal eingeloggt hat
        SharedPreferences erstesLoginPref = getSharedPreferences("erstesLogin", 0);
        erstesLogin = erstesLoginPref.getBoolean("erstesLogin", true);

        // Benutzerdaten laden
        SharedPreferences benutzernamePref = getSharedPreferences("benutzername", 0);
        benutzername = benutzernamePref.getString("benutzername", "");

        SharedPreferences passwortPref = getSharedPreferences("passwort", 0);
        passwort = passwortPref.getString("passwort", "");

        // Vertretungsplanausgabe laden
        SharedPreferences vertretungsplanAusgabePref = getSharedPreferences("vertretungsplanAusgabe", 0);
        vertretungsplanAusgabe = vertretungsplanAusgabePref.getString("vertretungsplanAusgabe", "");

        // Vertretungselemente laden
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        // Kurs
        SharedPreferences kursPref = getSharedPreferences("kurs", 0);
        String kursJson = kursPref.getString("kurs", "");
        kurs = gson.fromJson(kursJson, type);

        // Stunde
        SharedPreferences stundePref = getSharedPreferences("stunde", 0);
        String stundeJson = stundePref.getString("stunde", "");
        stunde = gson.fromJson(stundeJson, type);

        // Vertreter
        SharedPreferences vertreterPref = getSharedPreferences("vertreter", 0);
        String vertreterJson = vertreterPref.getString("vertreter", "");
        vertreter = gson.fromJson(vertreterJson, type);

        // Fach
        SharedPreferences fachPref = getSharedPreferences("fach", 0);
        String fachJson = fachPref.getString("fach", "");
        fach = gson.fromJson(fachJson, type);

        // Raum
        SharedPreferences raumPref = getSharedPreferences("raum", 0);
        String raumJson = raumPref.getString("raum", "");
        raum = gson.fromJson(raumJson, type);

        // Info
        SharedPreferences infoPref = getSharedPreferences("info", 0);
        String infoJson = infoPref.getString("info", "");
        info = gson.fromJson(infoJson, type);
    } // Methode sharedPreferencesLaden

    private void sharedPreferencesSpeichern() {
        // Speichern, ob sich der Benutzer schon einmal eingeloggt hat
        SharedPreferences erstesLoginPref = getSharedPreferences("erstesLogin", 0);
        SharedPreferences.Editor editorErstesLogin = erstesLoginPref.edit();
        editorErstesLogin.putBoolean("erstesLogin", erstesLogin);
        editorErstesLogin.apply();

        // Benutzerdaten speichern
        SharedPreferences benutzernamePref = getSharedPreferences("benutzername", 0);
        SharedPreferences.Editor editorBenutzername = benutzernamePref.edit();
        editorBenutzername.putString("benutzername", benutzername);
        editorBenutzername.apply();

        SharedPreferences passwortPref = getSharedPreferences("passwort", 0);
        SharedPreferences.Editor editorPasswort = passwortPref.edit();
        editorPasswort.putString("passwort", passwort);
        editorPasswort.apply();

        // Vertretungsplanausgabe speichern
        SharedPreferences vertretungsplanAusgabePref = getSharedPreferences("vertretungsplanAusgabe", 0);
        SharedPreferences.Editor editorVertretungsplanAusgabe = vertretungsplanAusgabePref.edit();
        editorVertretungsplanAusgabe.putString("vertretungsplanAusgabe", vertretungsplanAusgabe);
        editorVertretungsplanAusgabe.apply();

        // Vertretungselemente speichern
        Gson gson = new Gson();

        // Kurs
        SharedPreferences kursPref = getSharedPreferences("kurs", 0);
        SharedPreferences.Editor editorKurs = kursPref.edit();
        String kursJson = gson.toJson(kurs);
        editorKurs.putString("kurs", kursJson);
        editorKurs.apply();

        // Stunde
        SharedPreferences stundePref = getSharedPreferences("stunde", 0);
        SharedPreferences.Editor editorStunde = stundePref.edit();
        String stundeJson = gson.toJson(stunde);
        editorStunde.putString("stunde", stundeJson);
        editorStunde.apply();

        // Vertreter
        SharedPreferences vertreterPref = getSharedPreferences("vertreter", 0);
        SharedPreferences.Editor editorVertreter = vertreterPref.edit();
        String vertreterJson = gson.toJson(vertreter);
        editorVertreter.putString("vertreter", vertreterJson);
        editorVertreter.apply();

        // Fach
        SharedPreferences fachPref = getSharedPreferences("fach", 0);
        SharedPreferences.Editor editorFach = fachPref.edit();
        String fachJson = gson.toJson(fach);
        editorFach.putString("fach", fachJson);
        editorFach.apply();

        // Raum
        SharedPreferences raumPref = getSharedPreferences("raum", 0);
        SharedPreferences.Editor editorRaum = raumPref.edit();
        String raumJson = gson.toJson(raum);
        editorRaum.putString("raum", raumJson);
        editorRaum.apply();

        // Info
        SharedPreferences infoPref = getSharedPreferences("info", 0);
        SharedPreferences.Editor editorInfo = infoPref.edit();
        String infoJson = gson.toJson(info);
        editorInfo.putString("info", infoJson);
        editorInfo.apply();
    } // Methode sharedPreferencesSpeichern

    private void login() {
        // Deklaration der Views
        final EditText etBenutzername = findViewById(R.id.etBenutzername);
        final EditText etPasswort = findViewById(R.id.etPasswort);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button btnAnmelden = findViewById(R.id.btnAnmelden);

        // Überprüfen, ob alle Anmeldedaten eingegeben wurden
        if (etBenutzername.getText().toString().isEmpty()) {
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Benutzernamen eingeben.", Toast.LENGTH_LONG).show();
        } else if (etPasswort.getText().toString().isEmpty()) {
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Passwort eingeben.", Toast.LENGTH_LONG).show();
        } else {
            btnAnmelden.setClickable(false);
            benutzername = etBenutzername.getText().toString();
            passwort = etPasswort.getText().toString();

            website = new Website(benutzername, passwort);
            website.execute();

            progressBar.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    htmlText = website.getLoggedIn();

                    if (htmlText.contains("falschen Benutzernamen oder ein falsches Passwort")) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);
                        Toast.makeText(MainActivity.this, "Ungültiger Benutzername oder falsches Passwort.", Toast.LENGTH_LONG).show();
                    } else {
                        getDatum();
                        if (datum.equals("")) {
                            progressBar.setVisibility(View.INVISIBLE);
                            btnAnmelden.setFocusable(true);
                            btnAnmelden.setClickable(true);
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            return;
                        } // if
                        getVertretungsstunde();

                        vertretungsplanAusgabe = datum;
                        for (int index = 1; index < kurs.size(); index++) {
                            vertretungsplanAusgabe = vertretungsplanAusgabe + "\n" + kurs.get(index) + stunde.get(index) + vertreter.get(index) + fach.get(index) + raum.get(index) + info.get(index);
                        } // for

                        progressBar.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);

                        setContentView(R.layout.activity_main);

                        vertretungsplanAusgeben();

                        erstesLogin = false;
                    } // if
                }
            }, 5000);

        } // if
    } // Methode login

    private void getDatum() {
        datum = "";
        if (htmlText.contains("<div class=\"mon_title\">")) {
            teil1 = htmlText.split("<div class=\"mon_title\">");
            teil2 = teil1[1].split(",");
            datum = teil2[0];

            // Alle Leerstellen vor der ersten Ziffer entfernen
            while (!((int) datum.charAt(0) > 47 && (int) datum.charAt(0) < 58)) {
                datum = datum.substring(1);
            } // while
        } else {
            return;
        } // if
    } // Methode getDatum

    private void getVertretungsstunde() {
        kurs = new ArrayList<>();
        stunde = new ArrayList<>();
        vertreter = new ArrayList<>();
        fach = new ArrayList<>();
        raum = new ArrayList<>();
        info = new ArrayList<>();

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
                    case 0: kurs.add(element[0]); break;
                    case 1: stunde.add(element[0]); break;
                    case 2: vertreter.add(element[0]); break;
                    case 3: fach.add(element[0]); break;
                    case 4: raum.add(element[0]); break;
                    case 5: info.add(element[0]); break;
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

    private void vertretungsplanAusgeben() {
        // Fragment Vertretungsplan erzeugen
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrVertretungsplan frVertretungsplan = new FrVertretungsplan();

        // Vertretungselemente weitergeben
        frVertretungsplan.setVertretungsElemente(kurs, stunde, vertreter, fach, raum, info);

        fragmentTransaction.add(R.id.bereich_fragments, frVertretungsplan, "vertretungsplan");
        fragmentManager.executePendingTransactions();
        fragmentTransaction.commit();
    } // Methode vertretungsplanAusgeben

    public void vertretungsplanAktualisieren(View v) {
        website.execute();
        htmlText = website.getLoggedIn();

        getDatum();
        if (datum.equals("")) {
            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
            return;
        } // if
        getVertretungsstunde();

        vertretungsplanAusgabe = datum;
        for (int index = 1; index < kurs.size(); index++) {
            vertretungsplanAusgabe = vertretungsplanAusgabe + "\n" + kurs.get(index) + stunde.get(index) + vertreter.get(index) + fach.get(index) + raum.get(index) + info.get(index);
        } // for

        vertretungsplanAusgeben();
    }

} // Klasse MainActivity