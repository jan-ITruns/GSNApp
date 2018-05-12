package com.developer.workoutpro.itruns.gsnapp;

import android.os.AsyncTask;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class WebsiteVertretungsplan extends AsyncTask<Void, Void, String>  {

    // Attribute für die Daten
    private String benutzername;
    private String passwort;

    // Attribute für die Website
    private String loginURL;
    private String heuteURL;
    private String morgenURL;
    private String htmlTextHeute;
    private String htmlTextMorgen;
    private Document doc;

    public WebsiteVertretungsplan(String benutzername, String passwort) {
        this.benutzername = benutzername;
        this.passwort = passwort;
    } // Konstruktor WebsiteVertretungsplan

    @Override
    protected void onPreExecute() {
        loginURL = "https://bid.lspb.de/signin/";
        heuteURL = "https://bid.lspb.de/explorer/ViewDocument/1306997/";
        morgenURL = "https://bid.lspb.de/explorer/ViewDocument/1306999/";
        htmlTextHeute = "";
        htmlTextMorgen = "";
    } // Methode onPreExecute

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

            htmlTextHeute = doc.outerHtml();

            doc = Jsoup.connect(morgenURL)
                    .cookies(loginForm.cookies())
                    .post();

            htmlTextMorgen = doc.outerHtml();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    } // Methode doInBackground

    @Override
    protected void onPostExecute(String string) {
    } // Methode onPostExecute

    public String getVertretungHeute() {
        return htmlTextHeute;
    } // Methode getVertretungHeute

    public String getVertretungMorgen() {
        return htmlTextMorgen;
    } // Methode getVertretungMorgen

} // Klasse WebsiteVertretungsplan
