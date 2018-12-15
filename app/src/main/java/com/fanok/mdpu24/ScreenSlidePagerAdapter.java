package com.fanok.mdpu24;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fanok.mdpu24.Fragment.ScreenSlidePageFragment;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> picList = new ArrayList<>();

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ScreenSlidePageFragment.newInstance(picList.get(position));
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    public void addAll(List<String> picList) {
        this.picList = picList;
    }
}
