package com.fanok.mdpu24.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fanok.mdpu24.activity.TaskAddActivity;
import com.fanok.mdpu24.adapter.PagerTaskAdaptor;
import com.fanok.mdpu24.dowland.DowlandStudentGroups;
import com.fanok.mdpu24.dowland.DowlandTaskGroups;

import java.util.ArrayList;

public class FragmentTask extends FragmentStudentInfo {

    @Override
    protected FragmentPagerAdapter getAdapter(FragmentManager fm, int tabCount, ArrayList<String> groups) {
        return new PagerTaskAdaptor(fm, tabCount, groups);
    }

    @Override
    protected void start(int n) {
        super.start(6);
    }

    @Override
    protected DowlandStudentGroups getDowland(View view, String url, TabLayout tab, ViewPager pager, FragmentManager fm) {
        return new DowlandTaskGroups(view, url, tab, pager, fm);
    }

    @Override
    protected Intent getIntent() {
        return new Intent(getContext(), TaskAddActivity.class);
    }
}
