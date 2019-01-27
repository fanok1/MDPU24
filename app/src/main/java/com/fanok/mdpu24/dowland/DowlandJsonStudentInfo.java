package com.fanok.mdpu24.dowland;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.fanok.mdpu24.StartActivity;

import org.jsoup.nodes.Document;

import static android.content.Context.MODE_PRIVATE;

public class DowlandJsonStudentInfo extends DowladParent {

    @SuppressLint("StaticFieldLeak")
    private ListView listView;
    private String name;
    private String result;

    public DowlandJsonStudentInfo(@NonNull View view, @NonNull String url, ListView listView, String name) {
        super(view, url);
        this.listView = listView;
        this.name = name;

        setData("name", name);
    }

    @Override
    protected void parce(Document data) {
        result = data.getElementsByTag("body").text();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        SharedPreferences preferences = getView().getContext().getSharedPreferences(StartActivity.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Student_" + name, result);
        editor.apply();
        super.onPostExecute(aVoid);
        ParceJsonStudentInfo parceJson = new ParceJsonStudentInfo(result, listView);
        parceJson.setProgressBar(getProgressBar());
        parceJson.execute();
    }
}
