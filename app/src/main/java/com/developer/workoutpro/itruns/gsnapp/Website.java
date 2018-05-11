package com.developer.workoutpro.itruns.gsnapp;

import android.os.AsyncTask;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class Website extends AsyncTask<Void, Void, String>  {

    // Attribute für die Daten
    private boolean erstesLogin;
    private String benutzername;
    private String passwort;

    // Attribute für die Website
    private String loginURL;
    private String heuteURL;
    private String htmlText;
    private Document doc;

    public Website(boolean erstesLogin, String benutzername, String passwort) {
        this.erstesLogin = erstesLogin;
        this.benutzername = benutzername;
        this.passwort = passwort;
    } // Konstruktor Website

    @Override
    protected void onPreExecute() {
        loginURL = "https://bid.lspb.de/signin/";
        heuteURL = "https://bid.lspb.de/explorer/ViewDocument/1306999/";
        htmlText = "";
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Connection.Response loginForm = Jsoup.connect(loginURL)
                    .method(Connection.Method.GET)
                    .timeout(10000)
                    .execute();

            doc = Jsoup.connect(loginURL)
                    .data("values[login]", benutzername)
                    .data("values[password]", passwort)
                    .data("values[req]", "/")
                    .cookies(loginForm.cookies())
                    .post();

            doc = Jsoup.connect(heuteURL)
                    .cookies(loginForm.cookies())
                    .post();

            htmlText = doc.outerHtml();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return htmlText;
    } // Methode doInBackground

    @Override
    protected void onPostExecute(String string) {
        htmlText = string;
    }

    public String getLoggedIn() {
        return htmlText;
    } // Methode getLoggedIn

} // Klasse Website
