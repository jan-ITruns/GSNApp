package com.developer.workoutpro.itruns.gsnapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
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

    // Attribute für den Vertretungsplan
    private String teil1 [];
    private String teil2 [];
    private String element [];
    private String vertretungen;

    // Attribute für den Vertretungsplan Heute
    private String htmlTextHeute;
    private String datumHeute;
    private ArrayList<String> kursHeute;
    private ArrayList<String> stundeHeute;
    private ArrayList<String> vertreterHeute;
    private ArrayList<String> fachHeute;
    private ArrayList<String> raumHeute;
    private ArrayList<String> infoHeute;

    // Attribute für den Vertretungsplan Morgen
    private String htmlTextMorgen;
    private String datumMorgen;
    private ArrayList<String> kursMorgen;
    private ArrayList<String> stundeMorgen;
    private ArrayList<String> vertreterMorgen;
    private ArrayList<String> fachMorgen;
    private ArrayList<String> raumMorgen;
    private ArrayList<String> infoMorgen;


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

        // Vertretungselemente laden
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        // Datum
        SharedPreferences datumHeutePref = getSharedPreferences("datumHeute", 0);
        datumHeute = datumHeutePref.getString("datumHeute", "");

        SharedPreferences datumMorgenPref = getSharedPreferences("datumMorgen", 0);
        datumMorgen = datumMorgenPref.getString("datumMorgen", "");

        // Kurs
        SharedPreferences kursHeutePref = getSharedPreferences("kursHeute", 0);
        String kursHeuteJson = kursHeutePref.getString("kursHeute", "");
        kursHeute = gson.fromJson(kursHeuteJson, type);

        SharedPreferences kursMorgenPref = getSharedPreferences("kursMorgen", 0);
        String kursMorgenJson = kursMorgenPref.getString("kursMorgen", "");
        kursMorgen = gson.fromJson(kursMorgenJson, type);

        // Stunde
        SharedPreferences stundeHeutePref = getSharedPreferences("stundeHeute", 0);
        String stundeHeuteJson = stundeHeutePref.getString("stundeHeute", "");
        stundeHeute = gson.fromJson(stundeHeuteJson, type);

        SharedPreferences stundeMorgenPref = getSharedPreferences("stundeMorgen", 0);
        String stundeMorgenJson = stundeMorgenPref.getString("stundeMorgen", "");
        stundeMorgen = gson.fromJson(stundeMorgenJson, type);

        // Vertreter
        SharedPreferences vertreterHeutePref = getSharedPreferences("vertreterHeute", 0);
        String vertreterHeuteJson = vertreterHeutePref.getString("vertreterHeute", "");
        vertreterHeute = gson.fromJson(vertreterHeuteJson, type);

        SharedPreferences vertreterMorgenPref = getSharedPreferences("vertreterMorgen", 0);
        String vertreterMorgenJson = vertreterMorgenPref.getString("vertreterMorgen", "");
        vertreterMorgen = gson.fromJson(vertreterMorgenJson, type);

        // Fach
        SharedPreferences fachHeutePref = getSharedPreferences("fachHeute", 0);
        String fachHeuteJson = fachHeutePref.getString("fachHeute", "");
        fachHeute = gson.fromJson(fachHeuteJson, type);

        SharedPreferences fachMorgenPref = getSharedPreferences("fachMorgen", 0);
        String fachMorgenJson = fachMorgenPref.getString("fachMorgen", "");
        fachMorgen = gson.fromJson(fachMorgenJson, type);

        // Raum
        SharedPreferences raumHeutePref = getSharedPreferences("raumHeute", 0);
        String raumHeuteJson = raumHeutePref.getString("raumHeute", "");
        raumHeute = gson.fromJson(raumHeuteJson, type);

        SharedPreferences raumMorgenPref = getSharedPreferences("raumMorgen", 0);
        String raumMorgenJson = raumMorgenPref.getString("raumMorgen", "");
        raumMorgen= gson.fromJson(raumMorgenJson, type);

        // Info
        SharedPreferences infoHeutePref = getSharedPreferences("infoHeute", 0);
        String infoHeuteJson = infoHeutePref.getString("infoHeute", "");
        infoHeute = gson.fromJson(infoHeuteJson, type);

        SharedPreferences infoMorgenPref = getSharedPreferences("infoMorgen", 0);
        String infoMorgenJson = infoMorgenPref.getString("infoMorgen", "");
        infoMorgen = gson.fromJson(infoMorgenJson, type);

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
    } // Methode sharedPreferencesSpeichern

    public void menueleiste() {
        final DrawerLayout mDrawerLayout = findViewById(R.id.drawerLayout);
        final TextView tvUeberschrift = findViewById(R.id.tvUeberschrift);

        // Hintergrund dunkler machen
        mDrawerLayout.setScrimColor(Color.parseColor("#33000000"));

        NavigationView navigationView = findViewById(R.id.nav_view);

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
                                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.bereich_fragments)).commit();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                FrEinstellungen frEinstellungen = new FrEinstellungen();
                                fragmentTransaction.replace(R.id.bereich_fragments, frEinstellungen, "einstellungen");
                                tvUeberschrift.setText("Einstellungen");
                                fragmentTransaction.addToBackStack(null);
                                fragmentManager.executePendingTransactions();
                                fragmentTransaction.commit();
                                break;
                        } // switch

                        return true;
                    }
                });

        ImageButton imgbtnMenueleiste = findViewById(R.id.imgbtnMenueleiste);
        imgbtnMenueleiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
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

                        getDatum(0);
                        getDatum(1);
                        getVertretungsstunde(0);
                        getVertretungsstunde(1);

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
                break;
            case 1:
                teil1 = htmlTextMorgen.split("<table class=\"mon_list\">");
                kursMorgen = new ArrayList<>();
                stundeMorgen = new ArrayList<>();
                vertreterMorgen = new ArrayList<>();
                fachMorgen = new ArrayList<>();
                raumMorgen = new ArrayList<>();
                infoMorgen = new ArrayList<>();
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
                                    } // if
                                    break;
                                case 1: stundeHeute.add(element[0]); break;
                                case 2: vertreterHeute.add(element[0]); break;
                                case 3: fachHeute.add(element[0]); break;
                                case 4: raumHeute.add(element[0]); break;
                                case 5: infoHeute.add(element[0]); break;
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
                                        } // if
                                        break;
                                    case 1: stundeMorgen.add(element[0]); break;
                                    case 2: vertreterMorgen.add(element[0]); break;
                                    case 3: fachMorgen.add(element[0]); break;
                                    case 4: raumMorgen.add(element[0]); break;
                                    case 5: infoMorgen.add(element[0]); break;
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

    private void vertretungsplanOeffnen() {
        // Fragment Vertretungsplan erzeugen
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrVertretungsplan frVertretungsplan = new FrVertretungsplan();

        // Vertretungselemente weitergeben
        frVertretungsplan.setVertretungsElemente(0, datumHeute, kursHeute, stundeHeute, vertreterHeute, fachHeute, raumHeute, infoHeute);
        frVertretungsplan.setVertretungsElemente(1, datumMorgen, kursMorgen, stundeMorgen, vertreterMorgen, fachMorgen, raumMorgen, infoMorgen);

        fragmentTransaction.replace(R.id.bereich_fragments, frVertretungsplan, "vertretungsplan");

        TextView tvUeberschrift = findViewById(R.id.tvUeberschrift);
        tvUeberschrift.setText("Vertretungsplan");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    } // Methode vertretungsplanAusgeben

    public void vertretungsplanAktualisieren(View v) {
        website = new Website(benutzername, passwort);
        website.execute();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                htmlTextHeute = website.getVertretungHeute();
                htmlTextMorgen = website.getVertretungMorgen();

                if (! htmlTextHeute.contains("mon_title")) {
                    Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                    return;
                } // if

                if (! htmlTextMorgen.contains("mon_title")) {
                    Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                    return;
                } // if

                getDatum(0);
                getDatum(1);
                getVertretungsstunde(0);
                getVertretungsstunde(1);

                vertretungsplanOeffnen();
            }
        }, 10000);
    } // Methode vertretungsplanAktualisieren

    public void oeffneVertretungInfos(){
        // Fragment Vertretungsplan erzeugen
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrVertretungInfos frVertretungInfos = new FrVertretungInfos();

        fragmentTransaction.replace(R.id.bereich_fragments, frVertretungInfos, "vertretungInfos");
        TextView tvUeberschrift = findViewById(R.id.tvUeberschrift);
        tvUeberschrift.setText("Vertretungs Informationen");
        fragmentTransaction.addToBackStack(null);
        fragmentManager.executePendingTransactions();
        fragmentTransaction.commit();
    } // Methode oeffneVertretungInfos

} // Klasse MainActivity