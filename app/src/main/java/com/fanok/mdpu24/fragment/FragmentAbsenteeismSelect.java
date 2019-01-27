package com.fanok.mdpu24.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fanok.mdpu24.R;
import com.fanok.mdpu24.StartActivity;
import com.fanok.mdpu24.TypeTimeTable;
import com.fanok.mdpu24.dowland.DowlandGroupsCurator;

import java.util.Objects;

public class FragmentAbsenteeismSelect extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String url = getResources().getString(R.string.server_api) + "get_groups_absenteeism.php";
        View view = inflater.inflate(R.layout.fragment_time_table_select, container, false);
        ListView listView = view.findViewById(R.id.listView);
        DowlandGroupsCurator dowland = new DowlandGroupsCurator(view, url, listView);
        if (dowland.isOnline()) {
            SharedPreferences mPref = view.getContext().getSharedPreferences(StartActivity.PREF_NAME, StartActivity.MODE_PRIVATE);
            String login = mPref.getString("login", "");
            dowland.setData("login", login);
            dowland.setProgressBar(view.findViewById(R.id.progressBar));
            dowland.execute();
        }
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            TypeTimeTable.setGroup(adapterView.getItemAtPosition(i).toString());
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAbsenteeism()).commit();
        });
        return view;
    }
}

