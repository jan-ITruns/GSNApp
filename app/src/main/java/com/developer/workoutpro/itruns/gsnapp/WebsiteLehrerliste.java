package com.developer.workoutpro.itruns.gsnapp;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebsiteLehrerliste extends AsyncTask<Void, Void, String>  {

    // Attribute für die Website
    private String url;
    private String htmlText;
    private Document doc;

    @Override
    protected void onPreExecute() {
        url = "http://gymnasium-schloss-neuhaus.de/menschen/die-lehrerinnen-und-lehrer.html";
        htmlText = "";
    } // Methode onPreExecute

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Connection.Response loginForm = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .timeout(10000)
                    .execute();

            doc = Jsoup.connect(url).post();

            htmlText = doc.outerHtml();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    } // Methode doInBackground

    @Override
    protected void onPostExecute(String string) {
    } // Methode onPostExecute

    public String getHtmlText() {
        return htmlText;
    } // Methode getHtmlText

} // Klasse WebsiteVertretungsplan
