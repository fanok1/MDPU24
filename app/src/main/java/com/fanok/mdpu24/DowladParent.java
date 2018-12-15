package com.fanok.mdpu24;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public abstract class DowladParent extends AsyncTask<Void, Void, Void> {

    private String url;
    private HashMap<String, String> data = new HashMap<>();
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;
    @SuppressLint("StaticFieldLeak")
    private View view;

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public View getView() {
        return view;
    }



    DowladParent(View view, String url) {

        this.url = url;
        this.view = view;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setData(String key, String value) {
        this.data.put(key, value);
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (progressBar != null) progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Connection connection = Jsoup.connect(url);
        if (data.size() != 0) connection.data(data);
        connection.method(Connection.Method.POST);
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //connection.referrer("https://mdpu.org.ua/");
        try {
            connection.execute();
            Document data = connection.post();
            parce(data);

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    protected void parce(Document data) {
    }
}
