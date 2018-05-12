package com.developer.workoutpro.itruns.gsnapp;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Benachrichtigungen extends Service {

    // Attribute für die Website
    private Website website;
    private String benutzername;
    private String passwort;
    private int jahrgangsstufe;

    // Attribute für den Vertretungsplan
    private String htmlTextHeute;
    private String htmlTextMorgen;
    private String teil1 [];
    private String element [];
    private String vertretungen;

    // Attribute für den Timer
    private Handler handler = new Handler();
    private Timer timer;
    private TimerTask timerTask;

    // Attribute für die Benachrichtigungen
    private NotificationCompat.Builder mBuilder;
    private int notificationId;

    private ArrayList<String> kursHeute;
    private ArrayList<String> stundeHeute;
    private ArrayList<String> vertreterHeute;
    private ArrayList<String> fachHeute;
    private ArrayList<String> raumHeute;
    private ArrayList<String> infoHeute;
    private ArrayList<Integer> ausgewaehltHeute;

    private ArrayList<String> kursMorgen;
    private ArrayList<String> stundeMorgen;
    private ArrayList<String> vertreterMorgen;
    private ArrayList<String> fachMorgen;
    private ArrayList<String> raumMorgen;
    private ArrayList<String> infoMorgen;
    private ArrayList<Integer> ausgewaehltMorgen;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    } // Methode onBind

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent != null) {
            Bundle extra = intent.getExtras();

            kursHeute = extra.getStringArrayList("kursHeute");
            stundeHeute = extra.getStringArrayList("stundeHeute");
            vertreterHeute = extra.getStringArrayList("vertreterHeute");
            fachHeute = extra.getStringArrayList("fachHeute");
            raumHeute = extra.getStringArrayList("raumHeute");
            infoHeute = extra.getStringArrayList("infoHeute");
            ausgewaehltHeute = extra.getIntegerArrayList("ausgewaehltHeute");

            kursMorgen = extra.getStringArrayList("kursMorgen");
            stundeMorgen = extra.getStringArrayList("stundeMorgen");
            vertreterMorgen = extra.getStringArrayList("vertreterMorgen");
            fachMorgen = extra.getStringArrayList("fachMorgen");
            raumMorgen = extra.getStringArrayList("raumMorgen");
            infoMorgen = extra.getStringArrayList("infoMorgen");
            ausgewaehltMorgen = extra.getIntegerArrayList("ausgewaehltMorgen");

            benutzername = extra.getString("benutzername");
            passwort = extra.getString("passwort");
            jahrgangsstufe = extra.getInt("jahrgangsstufe");


            if (! benutzername.equals("") && ! passwort.equals("")) {
                startTimer();
            } // if

        } // if

        return START_STICKY;
    } // Methode onStartCommand


    @Override
    public void onCreate() {
    } // Methode onCreate

    @Override
    public void onDestroy() {
        stopTimer();
        super.onDestroy();
    } // Methode onDestroy

    public void startTimer() {
        timer = new Timer();
        initTimerTask();
        // nach 30 Minuten läuft der Timer alle 30 Minuten (1.800.000)
        timer.schedule(timerTask, 3000, 30000);
    } // Methode startTimer

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        } // if
    } // Methode stopTimer

    public void initTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        vertretungsplanAktualisieren();
                    }
                });
            }
        };
    } // Methode initTimerTask

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
        } // if

        ausgewaehltHeute = new ArrayList<>();
        ausgewaehltMorgen = new ArrayList<>();

        for (int index = 0; index < kursHeute.size(); index++) {
            ausgewaehltHeute.add(0);
        } // for
        for (int index = 0; index < kursMorgen.size(); index++) {
            ausgewaehltMorgen.add(0);
        } // for

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

        for (int index = gefunden; index < kursHeute.size(); index++) {
            kursHeute.remove(index);
            fachHeute.remove(index);
            stundeHeute.remove(index);
            raumHeute.remove(index);
            vertreterHeute.remove(index);
            infoHeute.remove(index);
            ausgewaehltHeute.remove(index);
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

        for (int index = gefunden; index < kursMorgen.size(); index++) {
            kursMorgen.remove(index);
            fachMorgen.remove(index);
            stundeMorgen.remove(index);
            raumMorgen.remove(index);
            vertreterMorgen.remove(index);
            infoMorgen.remove(index);
            ausgewaehltMorgen.remove(index);
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

    private void vertretungsplanAktualisieren() {
        website = new Website(benutzername, passwort);
        website.execute();
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                htmlTextHeute = website.getVertretungHeute();
                htmlTextMorgen = website.getVertretungMorgen();

                ArrayList<String> kursHeuteKopie, stundeHeuteKopie, vertreterHeuteKopie, fachHeuteKopie, raumHeuteKopie, infoHeuteKopie;
                ArrayList<String> kursMorgenKopie, stundeMorgenKopie, vertreterMorgenKopie, fachMorgenKopie, raumMorgenKopie, infoMorgenKopie;

                kursHeuteKopie = new ArrayList<>();
                stundeHeuteKopie = new ArrayList<>();
                vertreterHeuteKopie = new ArrayList<>();
                fachHeuteKopie = new ArrayList<>();
                raumHeuteKopie = new ArrayList<>();
                infoHeuteKopie = new ArrayList<>();

                kursMorgenKopie = new ArrayList<>();
                stundeMorgenKopie = new ArrayList<>();
                vertreterMorgenKopie = new ArrayList<>();
                fachMorgenKopie = new ArrayList<>();
                raumMorgenKopie = new ArrayList<>();
                infoMorgenKopie = new ArrayList<>();

                int anzahlAusgewaehltHeute = 0, anzahlAusgewaehltMorgen = 0;
                for (int index = 0; index < kursHeute.size(); index++) {
                    if (ausgewaehltHeute.get(index) == 1) {
                        kursHeuteKopie.add(kursHeute.get(anzahlAusgewaehltHeute));
                        stundeHeuteKopie.add(stundeHeute.get(anzahlAusgewaehltHeute));
                        vertreterHeuteKopie.add(vertreterHeute.get(anzahlAusgewaehltHeute));
                        fachHeuteKopie.add(fachHeute.get(anzahlAusgewaehltHeute));
                        raumHeuteKopie.add(raumHeute.get(anzahlAusgewaehltHeute));
                        infoHeuteKopie.add(infoHeute.get(anzahlAusgewaehltHeute));
                        anzahlAusgewaehltHeute++;
                    } // if
                } // for
                for (int index = 0; index < kursMorgen.size(); index++) {
                    if (ausgewaehltMorgen.get(index) == 1) {
                        kursMorgenKopie.add(kursMorgen.get(anzahlAusgewaehltMorgen));
                        stundeMorgenKopie.add(stundeMorgen.get(anzahlAusgewaehltMorgen));
                        vertreterMorgenKopie.add(vertreterMorgen.get(anzahlAusgewaehltMorgen));
                        fachMorgenKopie.add(fachMorgen.get(anzahlAusgewaehltMorgen));
                        raumMorgenKopie.add(raumMorgen.get(anzahlAusgewaehltMorgen));
                        infoMorgenKopie.add(infoMorgen.get(anzahlAusgewaehltMorgen));
                        anzahlAusgewaehltMorgen++;
                    } // if
                } // for

                getVertretungsstunde(0);
                getVertretungsstunde(1);
                vertretungenSortieren();

                // Überprüfen, ob es Änderungen gab
                for (int index = 0; index < anzahlAusgewaehltHeute; index++) {
                    if (kursHeuteKopie.get(index).equals(kursHeute.get(index)) && stundeHeuteKopie.get(index).equals(stundeHeute.get(index)) && vertreterHeuteKopie.get(index).equals(vertreterHeute.get(index))
                            && fachHeuteKopie.get(index).equals(fachHeute.get(index)) && raumHeuteKopie.get(index).equals(raumHeute.get(index)) && infoHeuteKopie.get(index).equals(infoHeute.get(index))) {

                    } else {
                        benachrichtigungErstellen();
                        benachrichtigungAnzeigen();
                        return;
                    } // if
                } // for

                for (int index = 0; index < anzahlAusgewaehltMorgen; index++) {
                    if (kursMorgenKopie.get(index).equals(kursMorgen.get(index)) && stundeMorgenKopie.get(index).equals(stundeMorgen.get(index)) && vertreterMorgenKopie.get(index).equals(vertreterMorgen.get(index))
                            && fachMorgenKopie.get(index).equals(fachMorgen.get(index)) && raumMorgenKopie.get(index).equals(raumMorgen.get(index)) && infoMorgenKopie.get(index).equals(infoMorgen.get(index))) {
                    } else {
                        benachrichtigungErstellen();
                        benachrichtigungAnzeigen();
                        return;
                    } // if
                } // for
            }
        }, 10000);
    } // Methode vertretungsplanAktualisieren

    private void benachrichtigungErstellen() {
        // Touch für das Öffnen vorbereiten
        Intent intent = new Intent(this, Ladebildschirm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder = new NotificationCompat.Builder(this, "ChannelID")
                .setSmallIcon(R.drawable.ic_apps_blau_24dp)
                .setContentTitle("Neue Vertretungen")
                .setContentText("Es gibt Vertretungsplan Änderungen für deine Jahrgangsstufe.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Es gibt Vertretungsplan Änderungen für deine Jahrgangsstufe."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    } // Methode benachrichtigungErstellen

    private void benachrichtigungAnzeigen() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, mBuilder.build());
    } // Methode benachrichtigungAnzeigen

} // Klasse Benachrichtigungen
