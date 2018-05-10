package com.developer.workoutpro.itruns.gsnapp;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<String> klasse = new ArrayList<>();
    private ArrayList<String> stunde = new ArrayList<>();
    private ArrayList<String> vertreter = new ArrayList<>();
    private ArrayList<String> fach = new ArrayList<>();
    private ArrayList<String> raum = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesLaden();

        if (erstesLogin) {
            setContentView(R.layout.activity_login);

            // Deklaration der Views
            final Button btnAnmelden = findViewById(R.id.btnAnmelden);
            final EditText etBenutzername = findViewById(R.id.etBenutzername);
            final EditText etPasswort = findViewById(R.id.etPasswort);

            btnAnmelden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                    btnAnmelden.setFocusable(false);
                }
            });

        } else {
            setContentView(R.layout.activity_main);
        } // if

    } // Methode onCreate

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferencesSpeichern();
    } // Methode onPause

    private void sharedPreferencesLaden() {
        SharedPreferences erstesLoginPref = getSharedPreferences("erstesLogin", 0);
        erstesLogin = erstesLoginPref.getBoolean("erstesLogin", true);
    } // Methode sharedPreferencesLaden

    private void sharedPreferencesSpeichern() {
        SharedPreferences erstesLoginPref = getSharedPreferences("erstesLogin", 0);
        SharedPreferences.Editor editorErstesLogin = erstesLoginPref.edit();
        editorErstesLogin.putBoolean("erstesLogin", erstesLogin);
        editorErstesLogin.commit();
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
                        getVertretungsstunde();

                        String ausgabe = datum;
                        for (int index = 1; index < klasse.size(); index++) {
                            ausgabe = ausgabe + "\n" + klasse.get(index) + stunde.get(index) + vertreter.get(index) + fach.get(index) + raum.get(index) + text.get(index);
                        } // for

                        progressBar.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);

                        setContentView(R.layout.activity_main);

                        TextView tv = findViewById(R.id.tv);
                        tv.setText(ausgabe);

                        erstesLogin = false;
                    } // if
                }
            }, 5000);

        } // if
    } // Methode login

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