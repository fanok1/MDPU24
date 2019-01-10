package com.fanok.mdpu24;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class DowlandGroupsName extends DowladParent {

    private ListView listView;
    private ArrayList<String> grouplist;
    private ArrayAdapter adapter;

    DowlandGroupsName(@NonNull View view, @NonNull String url, @NonNull ListView listView, @NonNull ArrayList<String> grouplist) {
        super(view, url);
        this.listView = listView;
        this.grouplist = grouplist;
    }

    @Override
    protected void parce(Document data) {
        try {
            JSONArray jArray = new JSONArray(data.getElementsByTag("body").text());
            for (int i = 0; i < jArray.length(); i++) {
                grouplist.add(jArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter = new ArrayAdapter(getView().getContext(), android.R.layout.simple_list_item_1, grouplist);
        listView.setAdapter(adapter);
        super.onPostExecute(aVoid);
    }
}


