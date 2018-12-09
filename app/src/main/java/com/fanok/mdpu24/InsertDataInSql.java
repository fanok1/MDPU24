package com.fanok.mdpu24;

import android.annotation.SuppressLint;
import android.arch.core.util.Function;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class InsertDataInSql extends AsyncTask<String, String, Document> {

    private String url;
    private HashMap<String, String> data = new HashMap<>();
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;
    @SuppressLint("StaticFieldLeak")
    private View view;
    private Function<ResponseBody, String> postExecute;


    void setPostExecute(Function<ResponseBody, String> postExecute) {
        this.postExecute = postExecute;
    }

    public InsertDataInSql(View view, String url, ProgressBar progressBar) {
        this.url = url;
        this.progressBar = progressBar;
        this.view = view;
    }


    public InsertDataInSql(View view, String url) {

        this.url = url;
        this.view = view;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    void setData(String key, String value) {
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
    protected Document doInBackground(String... strings) {
        Connection connection = Jsoup.connect(url);
        if (data.size() != 0) connection.data(data);
        connection.method(Connection.Method.POST);
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        connection.referrer("https://mdpu.org.ua/");
        try {
            connection.execute();
            return connection.post();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected void onPostExecute(Document s) {
        if (s == null)
            Toast.makeText(view.getContext(), view.getResources().getString(R.string.error_no_internet_conection), Toast.LENGTH_SHORT).show();
        if (progressBar != null) progressBar.setVisibility(ProgressBar.INVISIBLE);
        if (postExecute != null) postExecute.apply(new ResponseBody(s, view));
        super.onPostExecute(s);
    }

    public boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
