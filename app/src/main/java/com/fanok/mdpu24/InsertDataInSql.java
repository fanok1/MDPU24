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

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InsertDataInSql extends AsyncTask<String, String, String> {

    private String url;
    private String data;
    private ProgressBar progressBar;
    private View view;
    private Function<ResponseBody, String> postExecute;

    public void setPostExecute(Function<ResponseBody, String> postExecute) {
        this.postExecute = postExecute;
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public InsertDataInSql(View view, String url, ProgressBar progressBar) {
        this.url = url;
        this.progressBar = progressBar;
        this.view = view;
        this.data = "{}";
    }

    public InsertDataInSql(View view, String url, String data, ProgressBar progressBar) {
        this.url = url;
        this.data = data;
        this.progressBar = progressBar;
        this.view = view;
    }

    public InsertDataInSql(View view, String url, String data) {

        this.url = url;
        this.data = data;
        this.view = view;
    }

    public InsertDataInSql(View view, String url) {
        this.url = url;
        this.view = view;
        this.data="{}";
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setData(String data) {
        this.data = data;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("test");
        if (progressBar != null) progressBar.setVisibility(ProgressBar.VISIBLE);
    }



    @Override
    protected String doInBackground(String... strings) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, data);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        if (s.isEmpty()) Toast.makeText(view.getContext(), view.getResources().getString(R.string.error_no_internet_conection), Toast.LENGTH_SHORT).show();
        if (progressBar != null) progressBar.setVisibility(ProgressBar.INVISIBLE);
        if (postExecute != null) postExecute.apply(new ResponseBody(s, view));
        super.onPostExecute(s);
    }

    boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
