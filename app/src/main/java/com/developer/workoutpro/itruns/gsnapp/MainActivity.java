package com.developer.workoutpro.itruns.gsnapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Attribute für das Layout
    public static DrawerLayout mDrawerLayout;

    // Attribute für die Einstellungen
    public boolean erstesLogin;
    private View view;
    private ViewPager viewPager;

    //Attribute für die Benachrichtigungseinstellungen
    public boolean benachrichtigungVertretung;
    public boolean benachrichtigungAktuelles;
    public boolean benachrichtigungTermine;
    public boolean benachrichtigungBewertung;

    // Attribute für die Anmeldedaten
    public String benutzername;
    public String passwort;
    public String lehrerKuerzel="";
    public int jahrgangsstufe=0;

    // Attribute für die WebsiteVertretungsplan
    private WebsiteVertretungsplan websiteVertretungsplan;

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
    private ArrayList<Integer> ausgewaehltHeute;

    // Attribute für den Vertretungsplan Morgen
    private String htmlTextMorgen;
    private String datumMorgen;
    private ArrayList<String> kursMorgen;
    private ArrayList<String> stundeMorgen;
    private ArrayList<String> vertreterMorgen;
    private ArrayList<String> fachMorgen;
    private ArrayList<String> raumMorgen;
    private ArrayList<String> infoMorgen;
    private ArrayList<Integer> ausgewaehltMorgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesLaden();
    } // Methode onCreate

    @Override
    public void onBackPressed() {
        // Testen, ob man bei der Webview, sofern geöffnet, zurück kann
        WebView webView = findViewById(R.id.webView);
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            } // if
        } else {
            super.onBackPressed();
        } // if
    } // Methode onBackPressed

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(MainActivity.this, Benachrichtigungen.class);

        intent.putExtra("kursHeute", kursHeute);
        intent.putExtra("stundeHeute", stundeHeute);
        intent.putExtra("vertreterHeute", vertreterHeute);
        intent.putExtra("fachHeute", fachHeute);
        intent.putExtra("raumHeute", raumHeute);
        intent.putExtra("infoHeute", infoHeute);
        intent.putExtra("ausgewaehltHeute", ausgewaehltHeute);

        intent.putExtra("kursMorgen", kursMorgen);
        intent.putExtra("stundeMorgen", stundeMorgen);
        intent.putExtra("vertreterMorgen", vertreterMorgen);
        intent.putExtra("fachMorgen", fachMorgen);
        intent.putExtra("raumMorgen", raumMorgen);
        intent.putExtra("infoMorgen", infoMorgen);
        intent.putExtra("ausgewaehltMorgen", ausgewaehltMorgen);

        intent.putExtra("benutzername", benutzername);
        intent.putExtra("passwort", passwort);
        intent.putExtra("jahrgangsstufe", jahrgangsstufe);

        startService(intent);
        finish();
    } // Methode onStop

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
        jahrgangsstufe = jahrgangsstufePref.getInt("jahrgangsstufe", 0);

        SharedPreferences lehrerKuerzelPref = getSharedPreferences("lehrerKuerzel", 0);
        lehrerKuerzel = lehrerKuerzelPref.getString("lehrerKuerzel", "");

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
            loginOeffnen();
        } else {
            setContentView(R.layout.activity_main);
            vertretungenSortieren();
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

        SharedPreferences lehrerKuerzelPref = getSharedPreferences("lehrerKuerzel", 0);
        SharedPreferences.Editor editorLehrerKuerzel = lehrerKuerzelPref.edit();
        editorLehrerKuerzel.putString("lehrerKuerzel", lehrerKuerzel);
        editorLehrerKuerzel.apply();

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

        LinearLayout linearLayout = navigationView.getHeaderView(0).findViewById(R.id.layoutNavHeader);
        linearLayout.setBackgroundResource(R.drawable.gsn_klein);

        ImageView imgv = navigationView.getHeaderView(0).findViewById(R.id.imgvBenutzerbild);
        imgv.setImageResource(R.drawable.ic_teacher_24dp);

        TextView tvBenutzername = navigationView.getHeaderView(0).findViewById(R.id.tvBenutzername);
        tvBenutzername.setText(benutzername);

        TextView tvBenutzerinfo = navigationView.getHeaderView(0).findViewById(R.id.tvBenutzerinfo);
        if (lehrerKuerzel.isEmpty()){
            tvBenutzerinfo.setText("Jahrgangsstufe " + Integer.toString(jahrgangsstufe));
        } else {
            tvBenutzerinfo.setText(lehrerKuerzel);
        }//if

        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Item markieren
                        menuItem.setChecked(true);

                        // nach dem Auswählen den Navigator wieder schließen
                        mDrawerLayout.closeDrawers();

                        FragmentManager fragmentManager;
                        FragmentTransaction fragmentTransaction;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_vertretungsplan:
                                vertretungsplanOeffnen();
                                break;
                            case R.id.nav_lehrerliste:
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                FrLehrerliste frLehrerliste = new FrLehrerliste();
                                fragmentTransaction.replace(R.id.bereich_fragments, frLehrerliste, "lehrerliste");
                                fragmentTransaction.addToBackStack(null);
                                fragmentManager.executePendingTransactions();
                                fragmentTransaction.commit();
                                break;
                            case R.id.nav_homepage:
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                FrHomepage frHomepage = new FrHomepage();
                                fragmentTransaction.replace(R.id.bereich_fragments, frHomepage, "homepage");
                                fragmentTransaction.addToBackStack(null);
                                fragmentManager.executePendingTransactions();
                                fragmentTransaction.commit();
                                break;
                            case R.id.nav_einstellungen:
                                if(lehrerKuerzel.isEmpty()){
                                    fragmentManager = getSupportFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    FrEinstellungenSchueler frEinstellungenSchueler = new FrEinstellungenSchueler();
                                    fragmentTransaction.replace(R.id.bereich_fragments, frEinstellungenSchueler, "einstellungen");
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentManager.executePendingTransactions();
                                    fragmentTransaction.commit();
                                } else {
                                    fragmentManager = getSupportFragmentManager();
                                    fragmentTransaction = fragmentManager.beginTransaction();
                                    FrEinstellungenLehrer frEinstellungenLehrer = new FrEinstellungenLehrer();
                                    fragmentTransaction.replace(R.id.bereich_fragments, frEinstellungenLehrer, "einstellungen");
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentManager.executePendingTransactions();
                                    fragmentTransaction.commit();
                                }//if
                                break;
                        } // switch

                        return true;
                    }
                });
    } // Methode menueLeiste

    public void loginLehrer() {
        // Deklaration der Views
        final EditText etBenutzername = findViewById(R.id.etBenutzernameLehrer);
        final EditText etPasswort = findViewById(R.id.etPasswortLehrer);
        final EditText etLehrerkuerzel = findViewById(R.id.etLehrerkuerzel);
        etLehrerkuerzel.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        final ProgressBar progressBarLehrer = findViewById(R.id.progressBarLehrer);
        final Button btnAnmelden = findViewById(R.id.btnAnmelden);

        // Überprüfen, ob alle Anmeldedaten eingegeben wurden
        if (etBenutzername.getText().toString().isEmpty()) {
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Benutzernamen eingeben.", Toast.LENGTH_LONG).show();
        } else if (etPasswort.getText().toString().isEmpty()) {
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Passwort eingeben.", Toast.LENGTH_LONG).show();
        } else if (etLehrerkuerzel.getText().toString().isEmpty()){
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Lehrerkürzel eingeben.", Toast.LENGTH_LONG).show();
        } else {
            btnAnmelden.setClickable(false);
            benutzername = etBenutzername.getText().toString();
            passwort = etPasswort.getText().toString();
            lehrerKuerzel = etLehrerkuerzel.getText().toString();
            jahrgangsstufe = 0;

            websiteVertretungsplan = new WebsiteVertretungsplan(benutzername, passwort);
            websiteVertretungsplan.execute();

            progressBarLehrer.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    htmlTextHeute = websiteVertretungsplan.getVertretungHeute();
                    htmlTextMorgen = websiteVertretungsplan.getVertretungMorgen();

                    if (htmlTextHeute.contains("falschen Benutzernamen oder ein falsches Passwort")) {
                        progressBarLehrer.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);
                        Toast.makeText(MainActivity.this, "Ungültiger Benutzername oder falsches Passwort.", Toast.LENGTH_LONG).show();
                    } else {
                        if (! htmlTextHeute.contains("mon_title")) {
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            progressBarLehrer.setVisibility(View.INVISIBLE);
                            btnAnmelden.setFocusable(true);
                            btnAnmelden.setClickable(true);
                            return;
                        } // if

                        if (! htmlTextMorgen.contains("mon_title")) {
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            progressBarLehrer.setVisibility(View.INVISIBLE);
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

                        progressBarLehrer.setVisibility(View.INVISIBLE);
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

    public void loginSchueler() {
        // Deklaration der Views
        final EditText etBenutzername = findViewById(R.id.etBenutzernameSchueler);
        final EditText etPasswort = findViewById(R.id.etPasswortSchueler);
        final ProgressBar progressBarSchueler = findViewById(R.id.progressBarSchueler);
        final Button btnAnmelden = findViewById(R.id.btnAnmelden);

        // Überprüfen, ob alle Anmeldedaten eingegeben wurden
        if (etBenutzername.getText().toString().isEmpty()) {
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Benutzernamen eingeben.", Toast.LENGTH_LONG).show();
        } else if (etPasswort.getText().toString().isEmpty()) {
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Passwort eingeben.", Toast.LENGTH_LONG).show();
        } else if (jahrgangsstufe == 0){
            btnAnmelden.setFocusable(true);
            Toast.makeText(MainActivity.this, "Bitte Jahrgangsstufe auswählen.", Toast.LENGTH_LONG).show();
        } else {
            btnAnmelden.setClickable(false);
            benutzername = etBenutzername.getText().toString();
            passwort = etPasswort.getText().toString();
            lehrerKuerzel = "";

            websiteVertretungsplan = new WebsiteVertretungsplan(benutzername, passwort);
            websiteVertretungsplan.execute();

            progressBarSchueler.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    htmlTextHeute = websiteVertretungsplan.getVertretungHeute();
                    htmlTextMorgen = websiteVertretungsplan.getVertretungMorgen();

                    if (htmlTextHeute.contains("falschen Benutzernamen oder ein falsches Passwort")) {
                        progressBarSchueler.setVisibility(View.INVISIBLE);
                        btnAnmelden.setFocusable(true);
                        btnAnmelden.setClickable(true);
                        Toast.makeText(MainActivity.this, "Ungültiger Benutzername oder falsches Passwort.", Toast.LENGTH_LONG).show();
                    } else {
                        if (! htmlTextHeute.contains("mon_title")) {
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            progressBarSchueler.setVisibility(View.INVISIBLE);
                            btnAnmelden.setFocusable(true);
                            btnAnmelden.setClickable(true);
                            return;
                        } // if

                        if (! htmlTextMorgen.contains("mon_title")) {
                            Toast.makeText(MainActivity.this, "Ein unerwarteter Fehler ist aufgetreten.", Toast.LENGTH_LONG).show();
                            progressBarSchueler.setVisibility(View.INVISIBLE);
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

                        progressBarSchueler.setVisibility(View.INVISIBLE);
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

    public void setBackgroundColor(int btn){
        //Deklaration
        final Button btnJgst5 = findViewById(R.id.btnJgs5);
        final Button btnJgst6 = findViewById(R.id.btnJgs6);
        final Button btnJgst7 = findViewById(R.id.btnJgs7);
        final Button btnJgst8 = findViewById(R.id.btnJgs8);
        final Button btnJgst9 = findViewById(R.id.btnJgs9);
        final Button btnJgst10 = findViewById(R.id.btnJgs10);
        final Button btnJgst11 = findViewById(R.id.btnJgs11);
        final Button btnJgst12 = findViewById(R.id.btnJgs12);

        //Hintergrund prüfen
        btnJgst5.setBackgroundColor(0xFFCECECE);
        btnJgst6.setBackgroundColor(0xFFCECECE);
        btnJgst7.setBackgroundColor(0xFFCECECE);
        btnJgst8.setBackgroundColor(0xFFCECECE);
        btnJgst9.setBackgroundColor(0xFFCECECE);
        btnJgst10.setBackgroundColor(0xFFCECECE);
        btnJgst11.setBackgroundColor(0xFFCECECE);
        btnJgst12.setBackgroundColor(0xFFCECECE);

        switch (btn){
            case 5:
                btnJgst5.setBackgroundColor(0xCCFF9E37);
                break;
            case 6:
                btnJgst6.setBackgroundColor(0xCCFF9E37);
                break;
            case 7:
                btnJgst7.setBackgroundColor(0xCCFF9E37);
                break;
            case 8:
                btnJgst8.setBackgroundColor(0xCCFF9E37);
                break;
            case 9:
                btnJgst9.setBackgroundColor(0xCCFF9E37);
                break;
            case 10:
                btnJgst10.setBackgroundColor(0xCCFF9E37);
                break;
            case 11:
                btnJgst11.setBackgroundColor(0xCCFF9E37);
                break;
            case 12:
                btnJgst12.setBackgroundColor(0xCCFF9E37);
                break;
        }
    }

    public void logout(View view){
        benutzername = "";
        passwort = "";
        jahrgangsstufe = 0;
        lehrerKuerzel = "";
        erstesLogin = true;
        loginOeffnen();
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
                                        ausgewaehltHeute.add(0);

                                        if (kursHeute.get(kursHeute.size() - 1).contains("EF")) {
                                            kursHeute.set(kursHeute.size() - 1, "EF");
                                        } else if (kursHeute.get(kursHeute.size() - 1).contains("Q")) {
                                            if (kursHeute.get(kursHeute.size() - 1).contains("1")) {
                                                kursHeute.set(kursHeute.size() - 1, "Q1");
                                            } else {
                                                kursHeute.set(kursHeute.size() - 1, "Q2");
                                            } // if
                                        } // if
                                    } // if
                                    break;
                                case 1:
                                    if (element[0].equals("")) {
                                        stundeHeute.add("---");
                                    } else {
                                        stundeHeute.add(element[0]);
                                    } // if
                                    break;
                                case 2:
                                    if (element[0].equals("")) {
                                        vertreterHeute.add("---");
                                    } else {
                                        vertreterHeute.add(element[0]);
                                    } // if
                                    break;
                                case 3:
                                    if (element[0].equals("")) {
                                        fachHeute.add("---");
                                    } else {
                                        fachHeute.add(element[0]);
                                    } // if
                                    break;
                                case 4:
                                    if (element[0].equals("")) {
                                        raumHeute.add("---");
                                    } else {
                                        raumHeute.add(element[0]);
                                    } // if
                                    break;
                                case 5:
                                    if (element[0].equals("")) {
                                        infoHeute.add("---");
                                    } else {
                                        infoHeute.add(element[0]);
                                    } // if
                                    break;
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
                                            ausgewaehltMorgen.add(0);

                                            if (kursMorgen.get(kursMorgen.size() - 1).contains("EF")) {
                                                kursMorgen.set(kursMorgen.size() - 1, "EF");
                                            } else if (kursMorgen.get(kursMorgen.size() - 1).contains("Q")) {
                                                if (kursMorgen.get(kursMorgen.size() - 1).contains("1")) {
                                                    kursMorgen.set(kursMorgen.size() - 1, "Q1");
                                                } else {
                                                    kursMorgen.set(kursMorgen.size() - 1, "Q2");
                                                } // if
                                            } // if
                                        } // if
                                        break;
                                    case 1:
                                        if (element[0].equals("")) {
                                            stundeMorgen.add("---");
                                        } else {
                                            stundeMorgen.add(element[0]);
                                        } // if
                                        break;
                                    case 2:
                                        if (element[0].equals("")) {
                                            vertreterMorgen.add("---");
                                        } else {
                                            vertreterMorgen.add(element[0]);
                                        } // if
                                        break;
                                    case 3:
                                        if (element[0].equals("")) {
                                            fachMorgen.add("---");
                                        } else {
                                            fachMorgen.add(element[0]);
                                        } // if
                                        break;
                                    case 4:
                                        if (element[0].equals("")) {
                                            raumMorgen.add("---");
                                        } else {
                                            raumMorgen.add(element[0]);
                                        } // if
                                        break;
                                    case 5:
                                        if (element[0].equals("")) {
                                            infoMorgen.add("---");
                                        } else {
                                            infoMorgen.add(element[0]);
                                        } // if
                                        break;
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
        } //if

        if (!lehrerKuerzel.isEmpty()) {
            gesucht = lehrerKuerzel;
        }//if

        ausgewaehltHeute = new ArrayList<>();
        ausgewaehltMorgen = new ArrayList<>();

        for (int index = 0; index < kursHeute.size(); index++) {
            ausgewaehltHeute.add(0);
        } // for
        for (int index = 0; index < kursMorgen.size(); index++) {
            ausgewaehltMorgen.add(0);
        } // for

        if (lehrerKuerzel.isEmpty()) {
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
                    ausgewaehltHeute.set(gefunden, 1);

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
        } else {
            for (int index = 0; index < kursHeute.size(); index++) {
                if (vertreterHeute.get(index).contains(gesucht)) {
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
                    ausgewaehltHeute.set(gefunden, 1);

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
        }

        gefunden = 0;
        if (lehrerKuerzel.isEmpty()) {
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
                    ausgewaehltMorgen.set(gefunden, 1);

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
        } else {
            for (int index = 0; index < kursMorgen.size(); index++) {
                if (vertreterMorgen.get(index).contains(gesucht)) {
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
                    ausgewaehltMorgen.set(gefunden, 1);

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
        }//if
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

    public void loginOeffnen(){
        //Fragment Login erzeugen

        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        /*FrLogin frLogin = new FrLogin();

        fragmentTransaction.replace(R.id.bereich_fragments, frLogin, "login");*/

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        viewPager = findViewById(R.id.containerLogin);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabsLogin);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager pViewPager) {

        TabViewLogin adapter = new TabViewLogin(getSupportFragmentManager());

        // Schueler-Login hinzufügen
        FrLoginSchueler frLoginSchueler = new FrLoginSchueler();
        adapter.addFragment(frLoginSchueler, "Schüler");

        // Lehrer-Login hinzufügen
        FrLoginLehrer frLoginLehrer = new FrLoginLehrer();
        adapter.addFragment(frLoginLehrer, "Lehrer");

        pViewPager.setAdapter(adapter);
    } // Methode setupViewPager

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
            websiteVertretungsplan = new WebsiteVertretungsplan(benutzername, passwort);
            websiteVertretungsplan.execute();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    htmlTextHeute = websiteVertretungsplan.getVertretungHeute();
                    htmlTextMorgen = websiteVertretungsplan.getVertretungMorgen();

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

    public void oeffneVertretungInfos(int tag, int index){
        // Fragment Vertretungsplan erzeugen
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FrVertretungInfos frVertretungInfos = new FrVertretungInfos();

        switch (tag) {
            case 0: frVertretungInfos.setzeVertretungsInformationen(kursHeute.get(index), stundeHeute.get(index), vertreterHeute.get(index), fachHeute.get(index), raumHeute.get(index), infoHeute.get(index)); break;
            case 1: frVertretungInfos.setzeVertretungsInformationen(kursMorgen.get(index), stundeMorgen.get(index), vertreterMorgen.get(index), fachMorgen.get(index), raumMorgen.get(index), infoMorgen.get(index)); break;
        } // switch

        fragmentTransaction.replace(R.id.bereich_fragments, frVertretungInfos, "vertretungInfos");
        fragmentTransaction.addToBackStack(null);
        fragmentManager.executePendingTransactions();
        fragmentTransaction.commit();
    } // Methode oeffneVertretungInfos

} // Klasse MainActivity