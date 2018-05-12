package com.developer.workoutpro.itruns.gsnapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Attribute für das Layout
    public static DrawerLayout mDrawerLayout;

    // Attribute für die Einstellungen
    public boolean erstesLogin;

    //Attribute für die Benachrichtigungseinstellungen
    public boolean benachrichtigungVertretung;
    public boolean benachrichtigungAktuelles;
    public boolean benachrichtigungTermine;
    public boolean benachrichtigungBewertung;

    // Attribute für die Anmeldedaten
    public String benutzername;
    public String passwort;
    public int jahrgangsstufe;

    // Attribute für die Website
    private Website website;

    // Attribute für den Vertretungsplan
    private boolean aktualisierungLaeuft = false;
    private String teil1 [];
    private String teil2 [];
    private String element [];
    private String vertretungen;
    public String stand;

    // Attribute für den Vertretungsplan Heute
    private String htmlTextHeute;
    private String datumHeute;
    private ArrayList<String> kursHeute;
    private ArrayList<String> stundeHeute;
    private ArrayList<String> vertreterHeute;
    private ArrayList<String> fachHeute;
    private ArrayList<String> raumHeute;
    private ArrayList<String> infoHeute;
    private ArrayList<Boolean> ausgewaehltHeute;

    // Attribute für den Vertretungsplan Morgen
    private String htmlTextMorgen;
    private String datumMorgen;
    private ArrayList<String> kursMorgen;
    private ArrayList<String> stundeMorgen;
    private ArrayList<String> vertreterMorgen;
    private ArrayList<String> fachMorgen;
    private ArrayList<String> raumMorgen;
    private ArrayList<String> infoMorgen;
    private ArrayList<Boolean> ausgewaehltMorgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesLaden();
    } // Methode onCreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    } // Methode onBackPressed

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

        SharedPreferences jahrgangsstufePref = getSharedPreferences("jahrgangsstufe", 0);
        jahrgangsstufe = jahrgangsstufePref.getInt("jahrgangsstufe", 5);

        // Vertretungselemente laden
        Gson gson = new Gson();
        Type typeString = new TypeToken<ArrayList<String>>() {}.getType();
        Type typeBoolean = new TypeToken<ArrayList<Boolean>>() {}.getType();

        // Datum
        SharedPreferences datumHeutePref = getSharedPreferences("datumHeute", 0);
        datumHeute = datumHeutePref.getString("datumHeute", "");

        SharedPreferences datumMorgenPref = getSharedPreferences("datumMorgen", 0);
        datumMorgen = datumMorgenPref.getString("datumMorgen", "");

        // Stand
        SharedPreferences standPref = getSharedPreferences("stand", 0);
        stand = standPref.getString("stand", "");

        // Kurs
        SharedPreferences kursHeutePref = getSharedPreferences("kursHeute", 0);
        String kursHeuteJson = kursHeutePref.getString("kursHeute", "");
        kursHeute = gson.fromJson(kursHeuteJson, typeString);

        SharedPreferences kursMorgenPref = getSharedPreferences("kursMorgen", 0);
        String kursMorgenJson = kursMorgenPref.getString("kursMorgen", "");
        kursMorgen = gson.fromJson(kursMorgenJson, typeString);

        // Stunde
        SharedPreferences stundeHeutePref = getSharedPreferences("stundeHeute", 0);
        String stundeHeuteJson = stundeHeutePref.getString("stundeHeute", "");
        stundeHeute = gson.fromJson(stundeHeuteJson, typeString);

        SharedPreferences stundeMorgenPref = getSharedPreferences("stundeMorgen", 0);
        String stundeMorgenJson = stundeMorgenPref.getString("stundeMorgen", "");
        stundeMorgen = gson.fromJson(stundeMorgenJson, typeString);

        // Vertreter
        SharedPreferences vertreterHeutePref = getSharedPreferences("vertreterHeute", 0);
        String vertreterHeuteJson = vertreterHeutePref.getString("vertreterHeute", "");
        vertreterHeute = gson.fromJson(vertreterHeuteJson, typeString);

        SharedPreferences vertreterMorgenPref = getSharedPreferences("vertreterMorgen", 0);
        String vertreterMorgenJson = vertreterMorgenPref.getString("vertreterMorgen", "");
        vertreterMorgen = gson.fromJson(vertreterMorgenJson, typeString);

        // Fach
        SharedPreferences fachHeutePref = getSharedPreferences("fachHeute", 0);
        String fachHeuteJson = fachHeutePref.getString("fachHeute", "");
        fachHeute = gson.fromJson(fachHeuteJson, typeString);

        SharedPreferences fachMorgenPref = getSharedPreferences("fachMorgen", 0);
        String fachMorgenJson = fachMorgenPref.getString("fachMorgen", "");
        fachMorgen = gson.fromJson(fachMorgenJson, typeString);

        // Raum
        SharedPreferences raumHeutePref = getSharedPreferences("raumHeute", 0);
        String raumHeuteJson = raumHeutePref.getString("raumHeute", "");
        raumHeute = gson.fromJson(raumHeuteJson, typeString);

        SharedPreferences raumMorgenPref = getSharedPreferences("raumMorgen", 0);
        String raumMorgenJson = raumMorgenPref.getString("raumMorgen", "");
        raumMorgen= gson.fromJson(raumMorgenJson, typeString);

        // Info
        SharedPreferences infoHeutePref = getSharedPreferences("infoHeute", 0);
        String infoHeuteJson = infoHeutePref.getString("infoHeute", "");
        infoHeute = gson.fromJson(infoHeuteJson, typeString);

        SharedPreferences infoMorgenPref = getSharedPreferences("infoMorgen", 0);
        String infoMorgenJson = infoMorgenPref.getString("infoMorgen", "");
        infoMorgen = gson.fromJson(infoMorgenJson, typeString);

        // Ausgewählt
        SharedPreferences ausgewaehltHeutePref = getSharedPreferences("ausgewaehltHeute", 0);
        String ausgewaehltHeuteJson = ausgewaehltHeutePref.getString("ausgewaehltHeute", "");
        ausgewaehltHeute = gson.fromJson(ausgewaehltHeuteJson, typeBoolean);

        SharedPreferences ausgewaehltMorgenPref = getSharedPreferences("ausgewaehltMorgen", 0);
        String ausgewaehltMorgenJson = ausgewaehltMorgenPref.getString("ausgewaehltMorgen", "");
        ausgewaehltMorgen = gson.fromJson(ausgewaehltMorgenJson, typeBoolean);

        // Benachrichtigungseinstellungen laden
        //Vertretung
        SharedPreferences benachrichtigungVertretungPref = getSharedPreferences("benachrichtigungVertretung", 0);
        benachrichtigungVertretung = benachrichtigungVertretungPref.getBoolean("benachrichtigungVertretung", true);

        //Aktuelles
        SharedPreferences benachrichtigungAktuellesPref = getSharedPreferences("benachrichtigungAktuelles", 0);
        benachrichtigungAktuelles = benachrichtigungAktuellesPref.getBoolean("benachrichtigungAktuelles", true);

        //Termine
        SharedPreferences benachrichtigungTerminePref = getSharedPreferences("benachrichtigungTermine", 0);
        benachrichtigungTermine = benachrichtigungTerminePref.getBoolean("benachrichtigungTermine", true);

        //Bewertung
        SharedPreferences benachrichtigungBewertungPref = getSharedPreferences("benachrichtigungBewertung", 0);
        benachrichtigungBewertung = benachrichtigungBewertungPref.getBoolean("benachrichtigungBewertung", true);

        // Programm starten
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

            // Jahrgangsstufe
            Spinner spinnerJahrgangsstufe = findViewById(R.id.spinnerJahrgangsstufe);
            spinnerJahrgangsstufe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch(position) {
                                case 0: jahrgangsstufe = 5; break;
                                case 1: jahrgangsstufe = 6; break;
                                case 2: jahrgangsstufe = 7; break;
                                case 3: jahrgangsstufe = 8; break;
                                case 4: jahrgangsstufe = 9; break;
                                case 5: jahrgangsstufe = 10; break;
                                case 6: jahrgangsstufe = 11; break;
                                case 7: jahrgangsstufe = 12; break;
                            } // switch
                        }

                        public void onNothingSelected(AdapterView<?> parent) {
                            jahrgangsstufe = 5;
                        }
                    });
        } else {
            setContentView(R.layout.activity_main);
            vertretungsplanOeffnen();
            menueleiste();
        } // if
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

        SharedPreferences jahrgangsstufePref = getSharedPreferences("jahrgangsstufe", 0);
        SharedPreferences.Editor editorJahrgangsstufe = jahrgangsstufePref.edit();
        editorJahrgangsstufe.putInt("jahrgangsstufe", jahrgangsstufe);
        editorJahrgangsstufe.apply();

        // Vertretungselemente speichern
        Gson gson = new Gson();

        // Datum
        SharedPreferences datumHeutePref = getSharedPreferences("datumHeute", 0);
        SharedPreferences.Editor editorDatumHeute = datumHeutePref.edit();
        editorDatumHeute.putString("datumHeute", datumHeute);
        editorDatumHeute.apply();

        SharedPreferences datumMorgenPref = getSharedPreferences("datumMorgen", 0);
        SharedPreferences.Editor editorDatumMorgen = datumMorgenPref.edit();
        editorDatumMorgen.putString("datumMorgen", datumMorgen);
        editorDatumMorgen.apply();

        // Stand
        SharedPreferences standPref = getSharedPreferences("stand", 0);
        SharedPreferences.Editor editorStand = standPref.edit();
        editorStand.putString("stand", stand);
        editorStand.apply();

        // Kurs
        SharedPreferences kursHeutePref = getSharedPreferences("kursHeute", 0);
        SharedPreferences.Editor editorKursHeute = kursHeutePref.edit();
        String kursHeuteJson = gson.toJson(kursHeute);
        editorKursHeute.putString("kursHeute", kursHeuteJson);
        editorKursHeute.apply();

        SharedPreferences kursMorgenPref = getSharedPreferences("kursMorgen", 0);
        SharedPreferences.Editor editorKursMorgen = kursMorgenPref.edit();
        String kursMorgenJson = gson.toJson(kursMorgen);
        editorKursMorgen.putString("kursMorgen", kursMorgenJson);
        editorKursMorgen.apply();

        // Stunde
        SharedPreferences stundeHeutePref = getSharedPreferences("stundeHeute", 0);
        SharedPreferences.Editor editorStundeHeute = stundeHeutePref.edit();
        String stundeHeuteJson = gson.toJson(stundeHeute);
        editorStundeHeute.putString("stundeHeute", stundeHeuteJson);
        editorStundeHeute.apply();

        SharedPreferences stundeMorgenPref = getSharedPreferences("stundeMorgen", 0);
        SharedPreferences.Editor editorStundeMorgen = stundeMorgenPref.edit();
        String stundeMorgenJson = gson.toJson(stundeMorgen);
        editorStundeMorgen.putString("stundeMorgen", stundeMorgenJson);
        editorStundeMorgen.apply();

        // Vertreter
        SharedPreferences vertreterHeutePref = getSharedPreferences("vertreterHeute", 0);
        SharedPreferences.Editor editorVertreterHeute = vertreterHeutePref.edit();
        String vertreterHeuteJson = gson.toJson(vertreterHeute);
        editorVertreterHeute.putString("vertreterHeute", vertreterHeuteJson);
        editorVertreterHeute.apply();

        SharedPreferences vertreterMorgenPref = getSharedPreferences("vertreterMorgen", 0);
        SharedPreferences.Editor editorVertreterMorgen = vertreterMorgenPref.edit();
        String vertreterMorgenJson = gson.toJson(vertreterMorgen);
        editorVertreterMorgen.putString("vertreterMorgen", vertreterMorgenJson);
        editorVertreterMorgen.apply();

        // Fach
        SharedPreferences fachHeutePref = getSharedPreferences("fachHeute", 0);
        SharedPreferences.Editor editorFachHeute = fachHeutePref.edit();
        String fachHeuteJson = gson.toJson(fachHeute);
        editorFachHeute.putString("fachHeute", fachHeuteJson);
        editorFachHeute.apply();

        SharedPreferences fachMorgenPref = getSharedPreferences("fachMorgen", 0);
        SharedPreferences.Editor editorFachMorgen = fachMorgenPref.edit();
        String fachMorgenJson = gson.toJson(fachMorgen);
        editorFachMorgen.putString("fachMorgen", fachMorgenJson);
        editorFachMorgen.apply();

        // Raum
        SharedPreferences raumHeutePref = getSharedPreferences("raumHeute", 0);
        SharedPreferences.Editor editorRaumHeute = raumHeutePref.edit();
        String raumHeuteJson = gson.toJson(raumHeute);
        editorRaumHeute.putString("raumHeute", raumHeuteJson);
        editorRaumHeute.apply();

        SharedPreferences raumMorgenPref = getSharedPreferences("raumMorgen", 0);
        SharedPreferences.Editor editorRaumMorgen = raumMorgenPref.edit();
        String raumMorgenJson = gson.toJson(raumMorgen);
        editorRaumMorgen.putString("raumMorgen", raumMorgenJson);
        editorRaumMorgen.apply();

        // Info
        SharedPreferences infoHeutePref = getSharedPreferences("infoHeute", 0);
        SharedPreferences.Editor editorInfoHeute = infoHeutePref.edit();
        String infoHeuteJson = gson.toJson(infoHeute);
        editorInfoHeute.putString("infoHeute", infoHeuteJson);
        editorInfoHeute.apply();

        SharedPreferences infoMorgenPref = getSharedPreferences("infoMorgen", 0);
        SharedPreferences.Editor editorInfoMorgen = infoMorgenPref.edit();
        String infoMorgenJson = gson.toJson(infoMorgen);
        editorInfoMorgen.putString("infoMorgen", infoMorgenJson);
        editorInfoMorgen.apply();

        // Ausgewählt
        SharedPreferences ausgewaehltHeutePref = getSharedPreferences("ausgewaehltHeute", 0);
        SharedPreferences.Editor editorAusgewaehltHeute = infoHeutePref.edit();
        String ausgewaehltHeuteJson = gson.toJson(ausgewaehltHeute);
        editorAusgewaehltHeute.putString("ausgewaehltHeute", ausgewaehltHeuteJson);
        editorAusgewaehltHeute.apply();

        SharedPreferences ausgewaehltMorgenPref = getSharedPreferences("ausgewaehltMorgen", 0);
        SharedPreferences.Editor editorAusgewaehltMorgen = ausgewaehltMorgenPref.edit();
        String ausgewaehltMorgenJson = gson.toJson(ausgewaehltMorgen);
        editorAusgewaehltMorgen.putString("ausgewaehltMorgen", ausgewaehltMorgenJson);
        editorAusgewaehltMorgen.apply();

        //Benachrichtigungseinstellungen speichern
        //Vertretung
        SharedPreferences benachrichtigungVertretungPref = getSharedPreferences("benachrichtigungVertretung", 0);
        SharedPreferences.Editor editorBenachrichtigungVertretung = benachrichtigungVertretungPref.edit();
        editorBenachrichtigungVertretung.putBoolean("benachrichtigungVertretung", benachrichtigungVertretung);
        editorBenachrichtigungVertretung.apply();

        //Aktuelles
        SharedPreferences benachrichtigungAktuellesPref = getSharedPreferences("benachrichtigungAktuelles", 0);
        SharedPreferences.Editor editorBenachrichtigungAktuelles = benachrichtigungAktuellesPref.edit();
        editorBenachrichtigungAktuelles.putBoolean("benachrichtigungAktuelles", benachrichtigungAktuelles);
        editorBenachrichtigungAktuelles.apply();

        //Termine
        SharedPreferences benachrichtigungTerminePref = getSharedPreferences("benachrichtigungTermine", 0);
        SharedPreferences.Editor editorBenachrichtigungTermine = benachrichtigungTerminePref.edit();
        editorBenachrichtigungTermine.putBoolean("benachrichtigungTermine", benachrichtigungTermine);
        editorBenachrichtigungTermine.apply();

        //Bewertung
        SharedPreferences benachrichtigungBewertungPref = getSharedPreferences("benachrichtigungBewertung", 0);
        SharedPreferences.Editor editorBenachrichtigungBewertung = benachrichtigungBewertungPref.edit();
        editorBenachrichtigungBewertung.putBoolean("benachrichtigungBewertung", benachrichtigungBewertung);
        editorBenachrichtigungBewertung.apply();

    } // Methode sharedPreferencesSpeichern

    public void menueleiste() {
        mDrawerLayout = findViewById(R.id.drawerLayout);

        // Hintergrund dunkler machen
        mDrawerLayout.setScrimColor(Color.parseColor("#33000000"));

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Item markieren
                        menuItem.setChecked(true);

                        // nach dem Auswählen den Navigator wieder schließen
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.vertretungsplan:
                                vertretungsplanOeffnen();
                                break;
                            case R.id.einstellungen:
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                FrEinstellungenSchueler frEinstellungenSchueler = new FrEinstellungenSchueler();
                                fragmentTransaction.replace(R.id.bereich_fragments, frEinstellungenSchueler, "einstellungen");
                                fragmentTransaction.addToBackStack(null);
                                fragmentManager.executePendingTransactions();
                                fragmentTransaction.commit();
                                break;
                        } // switch

                        return true;
                    }
                });
    } // Methode menueLeiste

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
                    htmlTextHeute = website.getVertretungHeute();
                    htmlTextMorgen = website.getVertretungMorgen();

                    if (htmlTextHeute.contains("falschen Benutzernamen oder ein falsches Passwort")) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);
                        Toast.makeText(MainActivity.this, "Ungültiger Benutzername oder falsches Passwort.", Toast.LENGTH_LONG).show();
                    } else {
                        if (! htmlTextHeute.contains("mon_title")) {
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            btnAnmelden.setFocusable(true);
                            btnAnmelden.setClickable(true);
                            return;
                        } // if

                        if (! htmlTextMorgen.contains("mon_title")) {
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            btnAnmelden.setFocusable(true);
                            btnAnmelden.setClickable(true);
                            return;
                        } // if

                        getStand();
                        getDatum(0);
                        getDatum(1);
                        getVertretungsstunde(0);
                        getVertretungsstunde(1);
                        vertretungenSortieren();

                        progressBar.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);

                        setContentView(R.layout.activity_main);

                        vertretungsplanOeffnen();
                        menueleiste();

                        erstesLogin = false;
                    } // if
                }
            }, 10000);

        } // if
    } // Methode login

    public void logout(View view){
        benutzername = "";
        passwort = "";
        jahrgangsstufe = 5;
        erstesLogin = true;
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

        // Jahrgangsstufe
        Spinner spinnerJahrgangsstufe = findViewById(R.id.spinnerJahrgangsstufe);
        spinnerJahrgangsstufe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0: jahrgangsstufe = 5; break;
                    case 1: jahrgangsstufe = 6; break;
                    case 2: jahrgangsstufe = 7; break;
                    case 3: jahrgangsstufe = 8; break;
                    case 4: jahrgangsstufe = 9; break;
                    case 5: jahrgangsstufe = 10; break;
                    case 6: jahrgangsstufe = 11; break;
                    case 7: jahrgangsstufe = 12; break;
                } // switch
            }

            public void onNothingSelected(AdapterView<?> parent) {
                jahrgangsstufe = 5;
            }
        });
    }

    private void getStand() {
        teil1 = htmlTextHeute.split("Stand:");

        // Alle Leerstellen vor der ersten Ziffer entfernen
        while (!((int) teil1[1].charAt(0) > 47 && (int) teil1[1].charAt(0) < 58)) {
            teil1[1] = teil1[1].substring(1);
        } // while

        teil2 = teil1[1].split("</p>");

        stand = "Stand: " + teil2[0];
    } // Methode getStand

    private void getDatum(int tag) {
        String hilfsDatum;

        switch (tag) {
            case 0: teil1 = htmlTextHeute.split("<div class=\"mon_title\">"); break;
            case 1: teil1 = htmlTextMorgen.split("<div class=\"mon_title\">"); break;
        } // switch
        teil2 = teil1[1].split(",");
        hilfsDatum = teil2[0];

        // Alle Leerstellen vor der ersten Ziffer entfernen
        while (!((int) hilfsDatum.charAt(0) > 47 && (int) hilfsDatum.charAt(0) < 58)) {
            hilfsDatum = hilfsDatum.substring(1);
        } // while

        switch (tag) {
            case 0: datumHeute = hilfsDatum; break;
            case 1: datumMorgen = hilfsDatum; break;
        } // switch
    } // Methode getDatum

    private void getVertretungsstunde(int tag) {
        boolean erstesElement = true;
        boolean falscheVertretungsstunde = false;

        switch (tag) {
            case 0:
                teil1 = htmlTextHeute.split("<table class=\"mon_list\">");
                kursHeute = new ArrayList<>();
                stundeHeute = new ArrayList<>();
                vertreterHeute = new ArrayList<>();
                fachHeute = new ArrayList<>();
                raumHeute = new ArrayList<>();
                infoHeute = new ArrayList<>();
                ausgewaehltHeute = new ArrayList<>();
                break;
            case 1:
                teil1 = htmlTextMorgen.split("<table class=\"mon_list\">");
                kursMorgen = new ArrayList<>();
                stundeMorgen = new ArrayList<>();
                vertreterMorgen = new ArrayList<>();
                fachMorgen = new ArrayList<>();
                raumMorgen = new ArrayList<>();
                infoMorgen = new ArrayList<>();
                ausgewaehltMorgen = new ArrayList<>();
                break;
        } // switch

        vertretungen = teil1[1];

        entferne1Klammern();
        entferne2Klammern();

        while (! (vertretungen.substring(0,7).equals("</table"))) {
            for (int index = 0; index < 6; index++) {
                if (! erstesElement) {
                    element = vertretungen.split("<");

                    // angezeigte Leerzeichen entfernen
                    if (element[0].equals("&nbsp;")) {
                        element[0] = "";
                    } // if

                    if (! falscheVertretungsstunde) {
                        if (tag == 0) {
                            switch (index) {
                                case 0:
                                    if (element[0].equals("")) {
                                        falscheVertretungsstunde = true;
                                    } else if (element[0].charAt(0) == '(') {
                                        falscheVertretungsstunde = true;
                                    } else {
                                        kursHeute.add(element[0]);
                                        ausgewaehltHeute.add(false);
                                    } // if
                                    break;
                                case 1: stundeHeute.add(element[0]); ausgewaehltHeute.add(false); break;
                                case 2: vertreterHeute.add(element[0]); ausgewaehltHeute.add(false); break;
                                case 3: fachHeute.add(element[0]); ausgewaehltHeute.add(false); break;
                                case 4: raumHeute.add(element[0]); ausgewaehltHeute.add(false); break;
                                case 5: infoHeute.add(element[0]); ausgewaehltHeute.add(false); break;
                            } // switch
                        } else {
                                switch (index) {
                                    case 0:
                                        if (element[0].equals("")) {
                                            falscheVertretungsstunde = true;
                                        } else if (element[0].charAt(0) == '(') {
                                            falscheVertretungsstunde = true;
                                        } else {
                                            kursMorgen.add(element[0]);
                                            ausgewaehltMorgen.add(false);
                                        } // if
                                        break;
                                    case 1: stundeMorgen.add(element[0]); ausgewaehltMorgen.add(false); break;
                                    case 2: vertreterMorgen.add(element[0]); ausgewaehltMorgen.add(false); break;
                                    case 3: fachMorgen.add(element[0]); ausgewaehltMorgen.add(false); break;
                                    case 4: raumMorgen.add(element[0]); ausgewaehltMorgen.add(false); break;
                                    case 5: infoMorgen.add(element[0]); ausgewaehltMorgen.add(false); break;
                                } // switch
                        } // if
                    } // if
                } // if
                entferne2Klammern();
            } // for
            erstesElement = false;
            falscheVertretungsstunde = false;
            entferne2KlammernEnde();
        } // while

    } // Methode getVertretungsstunde

    private void vertretungenSortieren() {
        String gesucht;
        int gefunden = 0;

        if (jahrgangsstufe == 10) {
            gesucht = "EF";
        } else if (jahrgangsstufe == 11) {
            gesucht = "Q1";
        } else if (jahrgangsstufe == 12) {
            gesucht = "Q2";
        } else {
            gesucht = "0" + Integer.toString(jahrgangsstufe);
        } // if

        for (int index = 0; index < kursHeute.size(); index++) {
            if (kursHeute.get(index).contains(gesucht)) {
                kursHeute.add(kursHeute.get(kursHeute.size() - 1));
                fachHeute.add(fachHeute.get(fachHeute.size() - 1));
                stundeHeute.add(stundeHeute.get(stundeHeute.size() - 1));
                raumHeute.add(raumHeute.get(raumHeute.size() - 1));
                vertreterHeute.add(vertreterHeute.get(vertreterHeute.size() - 1));
                infoHeute.add(infoHeute.get(infoHeute.size() - 1));
                ausgewaehltHeute.add(ausgewaehltHeute.get(ausgewaehltHeute.size() - 1));

                for (int index1 = kursHeute.size() - 2; index1 >= gefunden; index1--) {
                    kursHeute.set(index1 + 1, kursHeute.get(index1));
                    fachHeute.set(index1 + 1, fachHeute.get(index1));
                    stundeHeute.set(index1 + 1, stundeHeute.get(index1));
                    raumHeute.set(index1 + 1, raumHeute.get(index1));
                    vertreterHeute.set(index1 + 1, vertreterHeute.get(index1));
                    infoHeute.set(index1 + 1, infoHeute.get(index1));
                    ausgewaehltHeute.set(index1 + 1, ausgewaehltHeute.get(index1));
                } // for

                kursHeute.set(gefunden, kursHeute.get(index + 1));
                fachHeute.set(gefunden, fachHeute.get(index + 1));
                stundeHeute.set(gefunden, stundeHeute.get(index + 1));
                raumHeute.set(gefunden, raumHeute.get(index + 1));
                vertreterHeute.set(gefunden, vertreterHeute.get(index + 1));
                infoHeute.set(gefunden, infoHeute.get(index + 1));
                ausgewaehltHeute.set(gefunden, true);

                kursHeute.remove(index + 1);
                fachHeute.remove(index + 1);
                stundeHeute.remove(index + 1);
                raumHeute.remove(index + 1);
                vertreterHeute.remove(index + 1);
                infoHeute.remove(index + 1);
                ausgewaehltHeute.remove(index + 1);

                gefunden++;
            } // if
        } // for

        gefunden = 0;
        for (int index = 0; index < kursMorgen.size(); index++) {
            if (kursMorgen.get(index).contains(gesucht)) {
                kursMorgen.add(kursMorgen.get(kursMorgen.size() - 1));
                fachMorgen.add(fachMorgen.get(fachMorgen.size() - 1));
                stundeMorgen.add(stundeMorgen.get(stundeMorgen.size() - 1));
                raumMorgen.add(raumMorgen.get(raumMorgen.size() - 1));
                vertreterMorgen.add(vertreterMorgen.get(vertreterMorgen.size() - 1));
                infoMorgen.add(infoMorgen.get(infoMorgen.size() - 1));
                ausgewaehltMorgen.add(ausgewaehltMorgen.get(ausgewaehltMorgen.size() - 1));

                for (int index1 = kursMorgen.size() - 2; index1 >= gefunden; index1--) {
                    kursMorgen.set(index1 + 1, kursMorgen.get(index1));
                    fachMorgen.set(index1 + 1, fachMorgen.get(index1));
                    stundeMorgen.set(index1 + 1, stundeMorgen.get(index1));
                    raumMorgen.set(index1 + 1, raumMorgen.get(index1));
                    vertreterMorgen.set(index1 + 1, vertreterMorgen.get(index1));
                    infoMorgen.set(index1 + 1, infoMorgen.get(index1));
                    ausgewaehltMorgen.set(index1 + 1, ausgewaehltMorgen.get(index1));
                } // for

                kursMorgen.set(gefunden, kursMorgen.get(index + 1));
                fachMorgen.set(gefunden, fachMorgen.get(index + 1));
                stundeMorgen.set(gefunden, stundeMorgen.get(index + 1));
                raumMorgen.set(gefunden, raumMorgen.get(index + 1));
                vertreterMorgen.set(gefunden, vertreterMorgen.get(index + 1));
                infoMorgen.set(gefunden, infoMorgen.get(index + 1));
                ausgewaehltMorgen.set(gefunden, true);

                kursMorgen.remove(index + 1);
                fachMorgen.remove(index + 1);
                stundeMorgen.remove(index + 1);
                raumMorgen.remove(index + 1);
                vertreterMorgen.remove(index + 1);
                infoMorgen.remove(index + 1);
                ausgewaehltMorgen.remove(index + 1);

                gefunden++;
            } // if
        } // for
    } // vertretungenSortieren

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

    public void vertretungsplanOeffnen() {
        // Fragment Vertretungsplan erzeugen
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrVertretungsplan frVertretungsplan = new FrVertretungsplan();

        // Vertretungselemente weitergeben
        frVertretungsplan.setVertretungsElemente(0, datumHeute, kursHeute, stundeHeute, vertreterHeute, fachHeute, raumHeute, infoHeute, ausgewaehltHeute);
        frVertretungsplan.setVertretungsElemente(1, datumMorgen, kursMorgen, stundeMorgen, vertreterMorgen, fachMorgen, raumMorgen, infoMorgen, ausgewaehltMorgen);

        fragmentTransaction.replace(R.id.bereich_fragments, frVertretungsplan, "vertretungsplan");

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // Methode vertretungsplanAusgeben

    public void vertretungsplanAktualisieren(View v) {
        if (! aktualisierungLaeuft) {
            aktualisierungLaeuft = true;
            website = new Website(benutzername, passwort);
            website.execute();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    htmlTextHeute = website.getVertretungHeute();
                    htmlTextMorgen = website.getVertretungMorgen();

                    if (!htmlTextHeute.contains("mon_title")) {
                        Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                        return;
                    } // if

                    if (!htmlTextMorgen.contains("mon_title")) {
                        Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                        return;
                    } // if

                    ArrayList<String> kursHeuteKopie, stundeHeuteKopie, vertreterHeuteKopie, fachHeuteKopie, raumHeuteKopie, infoHeuteKopie;
                    ArrayList<String> kursMorgenKopie, stundeMorgenKopie, vertreterMorgenKopie, fachMorgenKopie, raumMorgenKopie, infoMorgenKopie;

                    kursHeuteKopie = kursHeute;
                    stundeHeuteKopie = stundeHeute;
                    vertreterHeuteKopie = vertreterHeute;
                    fachHeuteKopie = fachHeute;
                    raumHeuteKopie = raumHeute;
                    infoHeuteKopie = infoHeute;

                    kursMorgenKopie = kursMorgen;
                    stundeMorgenKopie = stundeMorgen;
                    vertreterMorgenKopie = vertreterMorgen;
                    fachMorgenKopie = fachMorgen;
                    raumMorgenKopie = raumMorgen;
                    infoMorgenKopie = infoMorgen;

                    getStand();
                    getDatum(0);
                    getDatum(1);
                    getVertretungsstunde(0);
                    getVertretungsstunde(1);
                    vertretungenSortieren();

                    // Überprüfen, ob es Änderungen gab
                    if (!(kursHeuteKopie.equals(kursHeute) && stundeHeuteKopie.equals(stundeHeute) && vertreterHeuteKopie.equals(vertreterHeute) && fachHeuteKopie.equals(fachHeute) && raumHeuteKopie.equals(raumHeute) && infoHeuteKopie.equals(infoHeute)
                            && kursMorgenKopie.equals(kursMorgen) && stundeMorgenKopie.equals(stundeMorgen) && vertreterMorgenKopie.equals(vertreterMorgen) && fachMorgenKopie.equals(fachMorgen) && raumMorgenKopie.equals(raumMorgen) && infoMorgenKopie.equals(infoMorgen))) {
                        vertretungsplanOeffnen();
                    } // if
                    Toast.makeText(MainActivity.this, stand, Toast.LENGTH_LONG).show();
                    aktualisierungLaeuft = false;
                }
            }, 10000);
        } // if
    } // Methode vertretungsplanAktualisieren

    public void oeffneVertretungInfos(){
        // Fragment Vertretungsplan erzeugen
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrVertretungInfos frVertretungInfos = new FrVertretungInfos();

        fragmentTransaction.replace(R.id.bereich_fragments, frVertretungInfos, "vertretungInfos");
        fragmentTransaction.addToBackStack(null);
        fragmentManager.executePendingTransactions();
        fragmentTransaction.commit();
    } // Methode oeffneVertretungInfos

} // Klasse MainActivity