package com.fanok.mdpu24.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanok.mdpu24.R;
import com.fanok.mdpu24.TypeTimeTable;
import com.fanok.mdpu24.activity.AddAbsenteeismActivity;
import com.fanok.mdpu24.adapter.PagerAbsenteeismAdapter;

public class FragmentAbsenteeism extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_absenteeism, container, false);
        setHasOptionsMenu(true);
        TabLayout tab = view.findViewById(R.id.tabLayout);
        ViewPager pager = view.findViewById(R.id.viewPager);

        FragmentPagerAdapter pagerAdapter = new PagerAbsenteeismAdapter(getChildFragmentManager(), tab.getTabCount());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(pagerAdapter.getCount() > 1 ? pagerAdapter.getCount() - 1 : 1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
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
            if (TypeTimeTable.getType() == TypeTimeTable.studentTimeTable) return false;
            Intent intent = new Intent(getContext(), AddAbsenteeismActivity.class);
            startActivity(intent);
            return false;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}

