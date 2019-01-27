package com.fanok.mdpu24.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanok.mdpu24.R;
import com.fanok.mdpu24.StartActivity;
import com.fanok.mdpu24.TinyDB;
import com.fanok.mdpu24.activity.RegistrationActivity;
import com.fanok.mdpu24.adapter.PagerStudentInfoAdaptor;
import com.fanok.mdpu24.dowland.DowlandStudentGroups;

import java.util.ArrayList;

public class FragmentStudentInfo extends android.support.v4.app.Fragment {

    private int level;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table, container, false);
        SharedPreferences mPref = view.getContext().getSharedPreferences(StartActivity.PREF_NAME, StartActivity.MODE_PRIVATE);
        level = mPref.getInt("level", 0);
        String login = mPref.getString("login", "");
        setHasOptionsMenu(true);
        TabLayout tab = view.findViewById(R.id.tabLayout);
        ViewPager pager = view.findViewById(R.id.viewPager);
        FragmentManager fm = getChildFragmentManager();
        final String url = getResources().getString(R.string.server_api) + "get_groups_student.php";

        DowlandStudentGroups dowland = new DowlandStudentGroups(view, url, tab, pager, fm);
        if (dowland.isOnline()) {
            dowland.setData("login", login);
            dowland.setProgressBar(view.findViewById(R.id.progressBar));
            dowland.execute();
        } else {
            TinyDB tinyDB = new TinyDB(getContext());
            ArrayList<String> groups = tinyDB.getListString("GroupsList");
            if (groups.size() > 0) {
                for (int i = 0; i < groups.size(); i++) {
                    tab.addTab(tab.newTab().setText(groups.get(i)));
                }
                FragmentPagerAdapter pagerAdapter = new PagerStudentInfoAdaptor(fm, tab.getTabCount(), groups);
                pager.setAdapter(pagerAdapter);
                pager.setOffscreenPageLimit(pagerAdapter.getCount() > 1 ? pagerAdapter.getCount() - 1 : 1);
                pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
            }

        }

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.plus_button_menu, menu);
        menu.getItem(0).setOnMenuItemClickListener(menuItem -> {
            if (level != 4) return false;
            Intent intent = new Intent(getContext(), RegistrationActivity.class);
            intent.putExtra("login", "");
            intent.putExtra("type", "1");
            startActivity(intent);
            return false;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}

